package Scrapper;

import Data.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.*;

public class InformationScraper {
    private static final Logger logger = LogManager.getLogger(InformationScraper.class);
    private static int count = 0;
    private static FavoriteResorts favoriteResorts = FavoriteResorts.getInstance();

    public static Resort scrapFavoriteResort(String url, Country country, Dictionary.Language lang, Resort resort) {
        logger.info("Start of looking for favorite resort: " + resort.name());

        String open = "Open";
        String closed = "Closed";
        String weekends = "Weekends Only";
        String tempclosed = "Temporarily Closed";

        switch (lang) {
            case POLISH:
                open = "Otwarty";
                closed = "Zamknięty";
                weekends = "Tylko w weekendy";
                tempclosed = "Tymczasowo nieczynny";
        }

        Resort res = null;

        try {
            Document document = Jsoup.connect(url).get();

            Elements tables = document.select("table.styles_table__jGKQz.styles_collabsibleTable__23cyC");
            count = 0;
            for (Element table : tables) {
                if (!table.select("span.styles_open__3MfH6").isEmpty()) {
                    if (table.select("span.styles_open__3MfH6").text().equals(open)) {
                        res = scrapForOpen(table, country, lang, Resort.OpenStatus.OPEN, url, resort);
                        if(res != null){
                            logger.info("Success scraping favorite resort: " + resort.name());
                            InternetProblemHandler.scrappingSuccessful();
                            return res;
                        }

                    } else if (table.select("span.styles_open__3MfH6").text().equals(weekends)) {
                        res = scrapForOpen(table, country, lang, Resort.OpenStatus.WEEKEND, url, resort);
                        if(res != null){
                            logger.info("Success scraping favorite resort: " + resort.name());
                            InternetProblemHandler.scrappingSuccessful();
                            return res;
                        }
                    }

                } else if (!table.select("span.styles_partial__2pEPh").isEmpty()) {
                    if (table.select("span.styles_partial__2pEPh").text().equals(tempclosed)) {
                        res = scrapForOpen(table, country, lang, Resort.OpenStatus.TEMPCLOSED, url, resort);
                        if(res != null){
                            logger.info("Success scraping favorite resort: " + resort.name());
                            InternetProblemHandler.scrappingSuccessful();
                            return res;
                        }
                    }
                } else if (!table.select("span.styles_closed__2QlIG").isEmpty()) {
                    if (table.select("span.styles_closed__2QlIG").text().equals(closed)) {
                        res = scrapForClosed(table, country, lang, Resort.OpenStatus.CLOSE, url, resort);
                        if(res != null){
                            logger.info("Success scraping favorite resort: " + resort.name());
                            InternetProblemHandler.scrappingSuccessful();
                            return res;
                        }
                    }
                }
            }

        } catch (IOException e){
            logger.error("Error while scraping favorite resort: " + resort.name());
            InternetProblemHandler.scrappingFailed();
        }
        return null;
    }

    private static Resort scrapForOpen(Element table, Country country, Dictionary.Language lang, Resort.OpenStatus status, String url, Resort resort){
        Elements trs = table.select("tbody").select("tr");
        for(Element currTr : trs){

            Element currTd = currTr.select("td").first();
            String name = currTd.select("a").select("span").text();
            String updateTime = currTd.select("a").select("time").text();
            String[] parts = updateTime.split(" ");
            if(lang == Dictionary.Language.POLISH && parts.length >= 3) {
                updateTime = parts[0] +" "+ Dictionary.getPol2Eng(parts[1] + " " + parts[2]);
            }
            currTd = currTd.nextElementSibling();
            currTd.select("span").select("div").remove();
            String snowLast24 = currTd.select("span").text();

            currTd = currTd.nextElementSibling();

            String snowType = currTd.select("span").select("div").first().text();
            currTd.select("span").select("div").remove();
            String currSnow = currTd.select("span").text();

            if(lang == Dictionary.Language.POLISH){
                snowType = Dictionary.getPol2Eng(snowType);
            }

            currTd = currTd.nextElementSibling();
            String openTrailsDist = currTd.select("span").text();
            parts = openTrailsDist.split(" ");
            openTrailsDist = parts[0] + "km";
            String openTrailsPer;
            if (parts.length >= 3) {
                openTrailsPer = parts[2];
            } else {
                openTrailsPer = "N/A";
            }
            parts = openTrailsDist.split("/");
            String openDist = parts[0];

            currTd = currTd.nextElementSibling();
            String openLifts = currTd.select("span").text();
            parts = openLifts.split(" ");
            openLifts = parts[0];

            count++;

            if(name.equals(resort.name())) {
                return new Resort(name, updateTime, "N/A", snowLast24, currSnow, snowType, openTrailsDist,
                        openTrailsPer, openDist, openLifts, status, country, url, lang, favoriteResorts.containsResort(name, status));
            }

        }
        return null;
    }

    private static Resort scrapForClosed(Element table, Country country, Dictionary.Language lang, Resort.OpenStatus status, String url, Resort resort){
        Elements trs = table.select("tbody").select("tr");

        for(Element currTr : trs){

            Element currTd = currTr.select("td").first();
            String name = currTd.select("a").select("span").text();
            String updateTime = currTd.select("a").select("time").text();
            String[] parts = updateTime.split(" ");
            if(lang == Dictionary.Language.POLISH && parts.length >= 3) {
                updateTime = parts[0] +" "+ Dictionary.getPol2Eng(parts[1] + " " + parts[2]);
            }

            currTd = currTd.nextElementSibling();
            currTd.select("span").select("div").remove();
            String openDate = currTd.select("span").text();

            if(name == resort.name()){
                return new Resort(name, updateTime, openDate, Resort.OpenStatus.CLOSE, country, url, lang, favoriteResorts.containsResort(name, status));
            }
            count++;

        }
        return null;
    }

    private static Map<Resort.OpenStatus, List<Resort>> infoScraping(String url, Country country, Dictionary.Language lang) {
        logger.info("Start of scraping data from url: " + url);

        String open = "Open";
        String closed = "Closed";
        String weekends = "Weekends Only";
        String tempclosed = "Temporarily Closed";

        switch(lang){
            case POLISH:
                open  = "Otwarty";
                closed = "Zamknięty";
                weekends = "Tylko w weekendy";
                tempclosed = "Tymczasowo nieczynny";
        }

        Map<Resort.OpenStatus, List<Resort>> map = new HashMap<>();

        try {
            Document document = Jsoup.connect(url).get();

            Elements tables = document.select("table.styles_table__jGKQz.styles_collabsibleTable__23cyC");
            count = 0;
            for(Element table : tables){
                if(!table.select("span.styles_open__3MfH6").isEmpty()){
                    if(table.select("span.styles_open__3MfH6").text().equals(open)){
                        map.put(Resort.OpenStatus.OPEN, scrapForOpen(table, country, lang, Resort.OpenStatus.OPEN, url));
                    }
                    else if (table.select("span.styles_open__3MfH6").text().equals(weekends)){
                        map.put(Resort.OpenStatus.WEEKEND, scrapForOpen(table, country, lang, Resort.OpenStatus.WEEKEND, url));
                    }
                    
                }
                else if (!table.select("span.styles_partial__2pEPh").isEmpty()) {
                    if(table.select("span.styles_partial__2pEPh").text().equals(tempclosed)) {
                        map.put(Resort.OpenStatus.TEMPCLOSED, scrapForOpen(table, country, lang, Resort.OpenStatus.TEMPCLOSED, url));
                    }
                }
                else if (!table.select("span.styles_closed__2QlIG").isEmpty()) {
                    if(table.select("span.styles_closed__2QlIG").text().equals(closed)) {
                        map.put(Resort.OpenStatus.CLOSE, scrapForClosed(table, country, lang, Resort.OpenStatus.CLOSE, url));
                    }
                }
            }

            logger.info("Success scraping " +  count + " resorts from url: " + url);
            InternetProblemHandler.scrappingSuccessful();
        }
        catch (IOException e){
            logger.error("Error while scraping url: " + url);
            InternetProblemHandler.scrappingFailed();
        }

        return map;
    }

    private static List<Resort> scrapForOpen(Element table, Country country, Dictionary.Language lang, Resort.OpenStatus status, String url){
        List<Resort> resorts = new ArrayList<>();
        Elements trs = table.select("tbody").select("tr");
        for(Element currTr : trs){

            Element currTd = currTr.select("td").first();
            String name = currTd.select("a").select("span").text();
            String updateTime = currTd.select("a").select("time").text();
            String[] parts = updateTime.split(" ");
            if(lang == Dictionary.Language.POLISH && parts.length >= 3) {
                updateTime = parts[0] +" "+ Dictionary.getPol2Eng(parts[1] + " " + parts[2]);
            }
            currTd = currTd.nextElementSibling();
            currTd.select("span").select("div").remove();
            String snowLast24 = currTd.select("span").text();

            currTd = currTd.nextElementSibling();

            String snowType = currTd.select("span").select("div").first().text();
            currTd.select("span").select("div").remove();
            String currSnow = currTd.select("span").text();

            if(lang == Dictionary.Language.POLISH){
                snowType = Dictionary.getPol2Eng(snowType);
            }

            currTd = currTd.nextElementSibling();
            String openTrailsDist = currTd.select("span").text();
            parts = openTrailsDist.split(" ");
            openTrailsDist = parts[0] + "km";
            String openTrailsPer;
            if (parts.length >= 3) {
                openTrailsPer = parts[2];
            } else {
                openTrailsPer = "N/A";
            }
            parts = openTrailsDist.split("/");
            String openDist = parts[0];

            currTd = currTd.nextElementSibling();
            String openLifts = currTd.select("span").text();
            parts = openLifts.split(" ");
            openLifts = parts[0];

            if(country == Country.USA || country == Country.CANADA){
                if(!snowLast24.equals("-") && snowLast24.length() > 1) {
                    try {
                        snowLast24 = snowLast24.substring(0, snowLast24.length() - 1);
                        snowLast24 = String.format("%.0f", Float.parseFloat(snowLast24) * 2.54);
                        snowLast24 += "cm";
                    } catch (NumberFormatException ex){
                        logger.error("Error while converting inches to cm in last snow in resort: " + name);
                    }
                }

                if(!currSnow.equals("-")){
                    currSnow = currSnow.substring(0, currSnow.length()-1);
                    parts = currSnow.split("-");

                    if(parts.length > 1){
                        try {
                            parts[0] = String.format("%.0f", Float.parseFloat(parts[0]) * 2.54);
                            parts[1] = String.format("%.0f", Float.parseFloat(parts[1]) * 2.54);
                            currSnow = parts[0] + "-" + parts[1] + "cm";
                        }
                        catch (NumberFormatException ex){
                            logger.error("Error while converting inches to cm in curr snow in resort: " + name);
                        }
                    }
                    else{
                        try {
                            currSnow = String.format("%.0f", Float.parseFloat(currSnow) * 2.54);
                            currSnow += "cm";
                        }
                        catch (NumberFormatException ex){
                            logger.error("Error while converting inches to cm in curr snow in resort: " + name);
                        }
                    }
                }


            }

            resorts.add(new Resort(name, updateTime, "N/A", snowLast24, currSnow, snowType, openTrailsDist,
                    openTrailsPer, openDist, openLifts, status, country, url, lang, favoriteResorts.containsResort(name, status)));

            count++;

        }
        return resorts;
    }

    private static List<Resort> scrapForClosed(Element table, Country country, Dictionary.Language lang, Resort.OpenStatus status, String url){
        List<Resort> resorts = new ArrayList<>();
        Elements trs = table.select("tbody").select("tr");

        for(Element currTr : trs){

            Element currTd = currTr.select("td").first();
            String name = currTd.select("a").select("span").text();
            String updateTime = currTd.select("a").select("time").text();
            String[] parts = updateTime.split(" ");
            if(lang == Dictionary.Language.POLISH && parts.length >= 3) {
                updateTime = parts[0] +" "+ Dictionary.getPol2Eng(parts[1] + " " + parts[2]);
            }

            currTd = currTd.nextElementSibling();
            currTd.select("span").select("div").remove();
            String openDate = currTd.select("span").text();

            resorts.add(new Resort(name, updateTime, openDate, Resort.OpenStatus.CLOSE, country, url, lang, favoriteResorts.containsResort(name, status)));
            count++;

        }
        return resorts;
    }

    public static Map<Resort.OpenStatus, List<Resort>> polandScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.skiinfo.pl/malopolskie/warunki-narciarskie",
                "https://www.skiinfo.pl/dolnoslaskie/warunki-narciarskie",
                "https://www.skiinfo.pl/gory-swietokrzyskie/warunki-narciarskie",
                "https://www.skiinfo.pl/lodzkie/warunki-narciarskie",
                "https://www.skiinfo.pl/podkarpackie/warunki-narciarskie",
                "https://www.skiinfo.pl/slaskie/warunki-narciarskie"
        );

        return scrapLoop(Urls, Country.POLAND, Dictionary.Language.POLISH);
    }

    public static Map<Resort.OpenStatus, List<Resort>> italyScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.skiinfo.pl/abruzja/warunki-narciarskie",
                "https://www.skiinfo.pl/dolina-aosty/warunki-narciarskie",
                "https://www.skiinfo.pl/emilia-romagna/warunki-narciarskie",
                "https://www.skiinfo.pl/lombardia/warunki-narciarskie",
                "https://www.skiinfo.pl/piemonte/warunki-narciarskie",
                "https://www.skiinfo.pl/toskania/warunki-narciarskie",
                "https://www.skiinfo.pl/trentino/warunki-narciarskie",
                "https://www.skiinfo.pl/tyrol-poludniowy/warunki-narciarskie",
                "https://www.skiinfo.pl/veneto/warunki-narciarskie"
        );

        return scrapLoop(Urls, Country.ITALY, Dictionary.Language.POLISH);
    }

    public static Map<Resort.OpenStatus, List<Resort>> austriaScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.skiinfo.pl/dolna-austria/warunki-narciarskie",
                "https://www.skiinfo.pl/gorna-austria/warunki-narciarskie",
                "https://www.skiinfo.pl/karyntia/warunki-narciarskie",
                "https://www.skiinfo.pl/salzburg/warunki-narciarskie",
                "https://www.skiinfo.pl/styria/warunki-narciarskie",
                "https://www.skiinfo.pl/tyrol/warunki-narciarskie",
                "https://www.skiinfo.pl/vorarlberg/warunki-narciarskie"
        );

        return scrapLoop(Urls, Country.AUSTRIA, Dictionary.Language.POLISH);
    }
    public static Map<Resort.OpenStatus, List<Resort>> czechScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.skiinfo.pl/beskidy/warunki-narciarskie",
                "https://www.skiinfo.pl/jizerske-hory/warunki-narciarskie",
                "https://www.skiinfo.pl/orlicke-hory/warunki-narciarskie",
                "https://www.skiinfo.pl/jesenik/warunki-narciarskie",
                "https://www.skiinfo.pl/karkonosze/warunki-narciarskie",
                "https://www.skiinfo.pl/rudawy/warunki-narciarskie",
                "https://www.skiinfo.pl/sumava/warunki-narciarskie",
                "https://www.skiinfo.pl/wysoczyzna/warunki-narciarskie"
        );

        return scrapLoop(Urls, Country.CZECH, Dictionary.Language.POLISH);
    }
    public static Map<Resort.OpenStatus, List<Resort>> andorraScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/andora/warunki-narciarskie", Country.ANDORRA,Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> belgiumScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map  = infoScraping("https://www.skiinfo.pl/belgia/warunki-narciarskie", Country.BELGIUM, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> bulgariaScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map  = infoScraping("https://www.skiinfo.pl/bulgaria/warunki-narciarskie", Country.BULGARIA, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> franceScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.onthesnow.co.uk/northern-alps/skireport",
                "https://www.onthesnow.co.uk/southern-alps/skireport",
                "https://www.onthesnow.co.uk/massif-central/skireport",
                "https://www.onthesnow.co.uk/pyrenees/skireport",
                "https://www.onthesnow.co.uk/vosges/skireport",
                "https://www.onthesnow.co.uk/jura/skireport"

        );

        return scrapLoop(Urls, Country.FRANCE, Dictionary.Language.ENGLISH);
    }
    public static Map<Resort.OpenStatus, List<Resort>> finlandScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/finlandia/warunki-narciarskie", Country.FINLAND, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> spainScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/hiszpania/warunki-narciarskie", Country.SPAIN, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> liechtensteinScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/liechtenstein/warunki-narciarskie", Country.LIECHTENSTEIN, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> germanyScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/niemcy/warunki-narciarskie", Country.GERMANY, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> norwayScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/norwegia/warunki-narciarskie", Country.NORWAY, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> romaniaScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/rumunia/warunki-narciarskie", Country.ROMANIA, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> slovakiaScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/slowacja/warunki-narciarskie", Country.SLOVAKIA, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> sloveniaScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/slowenia/warunki-narciarskie", Country.SLOVENIA, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> scotlandScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/szkocja/warunki-narciarskie", Country.SCOTLAND, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> swedenScraping() throws IOException {
        Map<Resort.OpenStatus, List<Resort>> map = infoScraping("https://www.skiinfo.pl/szwecja/warunki-narciarskie", Country.SWEDEN, Dictionary.Language.POLISH);
        return map;
    }
    public static Map<Resort.OpenStatus, List<Resort>> switzerlandScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.onthesnow.co.uk/bernese-oberland/skireport",
                "https://www.onthesnow.co.uk/central-switzerland/skireport",
                "https://www.onthesnow.co.uk/fribourg/skireport",
                "https://www.onthesnow.co.uk/graubunden/skireport",
                "https://www.onthesnow.co.uk/valais/skireport",
                "https://www.onthesnow.co.uk/vosges/skireport"
        );

        return scrapLoop(Urls, Country.SWITZERLAND, Dictionary.Language.ENGLISH);
    }
    public static Map<Resort.OpenStatus, List<Resort>> usaScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.onthesnow.com/alaska/skireport",
                "https://www.onthesnow.com/idaho/skireport",
                "https://www.onthesnow.com/maryland/skireport",
                "https://www.onthesnow.com/montana/skireport",
                "https://www.onthesnow.com/new-york/skireport",
                "https://www.onthesnow.com/south-dakota/skireport",
                "https://www.onthesnow.com/washington/skireport",
                "https://www.onthesnow.com/arizona/skireport",
                "https://www.onthesnow.com/illinois/skireport",
                "https://www.onthesnow.com/massachusetts/skireport",
                "https://www.onthesnow.com/nevada/skireport",
                "https://www.onthesnow.com/north-carolina/skireport",
                "https://www.onthesnow.com/tennessee/skireport",
                "https://www.onthesnow.com/west-virginia/skireport",
                "https://www.onthesnow.com/california/skireport",
                "https://www.onthesnow.com/indiana/skireport",
                "https://www.onthesnow.com/michigan/skireport",
                "https://www.onthesnow.com/new-hampshire/skireport",
                "https://www.onthesnow.com/ohio/skireport",
                "https://www.onthesnow.com/utah/skireport",
                "https://www.onthesnow.com/wisconsin/skireport",
                "https://www.onthesnow.com/colorado/skireport",
                "https://www.onthesnow.com/iowa/skireport",
                "https://www.onthesnow.com/minnesota/skireport",
                "https://www.onthesnow.com/new-jersey/skireport",
                "https://www.onthesnow.com/oregon/skireport",
                "https://www.onthesnow.com/vermont/skireport",
                "https://www.onthesnow.com/wyoming/skireport",
                "https://www.onthesnow.com/connecticut/skireport",
                "https://www.onthesnow.com/maine/skireport",
                "https://www.onthesnow.com/missouri/skireport",
                "https://www.onthesnow.com/new-mexico/skireport",
                "https://www.onthesnow.com/pennsylvania/skireport",
                "https://www.onthesnow.com/virginia/skireport"
        );

        return scrapLoop(Urls, Country.USA, Dictionary.Language.ENGLISH);
    }
    public static Map<Resort.OpenStatus, List<Resort>> canadaScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.onthesnow.com/alberta/skireport",
                "https://www.onthesnow.com/british-columbia/skireport",
                "https://www.onthesnow.com/ontario/skireport",
                "https://www.onthesnow.com/quebec/skireport"
        );

        return scrapLoop(Urls, Country.CANADA, Dictionary.Language.ENGLISH);
    }

    private static Map<Resort.OpenStatus, List<Resort>> scrapLoop(List<String> urls, Country country, Dictionary.Language lang){
        Map<Resort.OpenStatus, List<Resort>> map = new ConcurrentHashMap<>();

        int threadPoolSize = Math.min(urls.size(), Runtime.getRuntime().availableProcessors() * 2);
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        List<Future<Map<Resort.OpenStatus, List<Resort>>>> futures = new ArrayList<>();

        for (String url : urls) {
            Callable<Map<Resort.OpenStatus, List<Resort>>> task = () -> infoScraping(url, country, lang);
            futures.add(executor.submit(task));
        }

        for (Future<Map<Resort.OpenStatus, List<Resort>>> future : futures) {
            try {
                Map<Resort.OpenStatus, List<Resort>> temp = future.get();
                temp.forEach((key, value) -> map.merge(key, value, (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                }));
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error while processing thread: " + e);
            }
        }

        executor.shutdown();

        return map;
    }

}
