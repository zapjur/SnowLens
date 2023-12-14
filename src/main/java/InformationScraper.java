import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InformationScraper {
    private static final Logger logger = LogManager.getLogger(InformationScraper.class);
    public static List<Resort> infoScraping(String url, Resort.Country country, Dictionary.Language lang) {
        logger.info("Start of scraping data from url: " + url);
        List<Resort> resorts = new ArrayList<>();


        try {
            Document document = Jsoup.connect(url).get();

            Elements docTBody = document.select("tbody");
            int counter = 0;
            for (Element currTBody : docTBody) {
                Element currTr = currTBody.select("tr").first();
                boolean closedLeft = false;

                while (currTr != null && counter < 2) { // petla dla otwartych i weekend
                    Element currTd = currTr.select("td").first();
                    String name = currTd.select("a").select("span").text();
                    String updateTime = currTd.select("a").select("time").text();
                    if(lang == Dictionary.Language.ENGLISH) {
                        updateTime = Dictionary.translateTime(updateTime);
                    }

                    currTd = currTd.nextElementSibling();
                    String snowLast24 = currTd.select("span").text();

                    currTd = currTd.nextElementSibling();

                    if (currTd == null) {
                        closedLeft = true;
                        break;
                    }
                    String currSnow = currTd.select("span").text();
                    String[] parts = currSnow.split(" ");
                    currSnow = parts[0];
                    String snowType;
                    if (parts.length > 2) {
                        snowType = parts[1] +" "+ parts[2];
                    } else if (parts.length == 2) {
                        snowType = parts[1];
                    } else {
                        snowType = "N/A";
                    }
                    if(lang == Dictionary.Language.ENGLISH){
                        snowType = Dictionary.getEng2Pol(snowType);
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

                    currTd = currTd.nextElementSibling();
                    String openLifts = currTd.select("span").text();
                    parts = openLifts.split(" ");
                    openLifts = parts[0];

                    if (counter == 0) {
                        resorts.add(new Resort(name, updateTime, "N/A", snowLast24, currSnow, snowType, openTrailsDist,
                                openTrailsPer, openLifts, Resort.OpenStatus.OPEN, country));
                    } else {
                        resorts.add(new Resort(name, updateTime, "N/A", snowLast24, currSnow, snowType, openTrailsDist,
                                openTrailsPer, openLifts, Resort.OpenStatus.WEEKEND, country));
                    }

                    currTr = currTr.nextElementSibling();
                }

                if (counter == 2 || closedLeft) { // dla zamknietych (inne dane)
                    while (currTr != null) {
                        Element currTd = currTr.select("td").first();
                        String name = currTd.select("a").select("span").text();
                        String updateTime = currTd.select("a").select("time").text();

                        currTd = currTd.nextElementSibling();
                        String openDate = currTd.select("span").text();

                        resorts.add(new Resort(name, updateTime, openDate, Resort.OpenStatus.CLOSE, country));

                        currTr = currTr.nextElementSibling();
                    }
                }
                counter++;
            }
            logger.info("Success scraping url: " + url);
        }
        catch (IOException e){
            logger.error("Error while scraping url: " + url);
        }

        return resorts;
    }

    public static List<Resort> polandScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.skiinfo.pl/malopolskie/warunki-narciarskie",
                "https://www.skiinfo.pl/dolnoslaskie/warunki-narciarskie",
                "https://www.skiinfo.pl/gory-swietokrzyskie/warunki-narciarskie",
                "https://www.skiinfo.pl/lodzkie/warunki-narciarskie",
                "https://www.skiinfo.pl/podkarpackie/warunki-narciarskie",
                "https://www.skiinfo.pl/slaskie/warunki-narciarskie"
        );

        List<Resort> resorts = new ArrayList<>();
        for (String url : Urls) {
            resorts.addAll(infoScraping(url, Resort.Country.POLAND, Dictionary.Language.POLISH));
        }
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }

    public static List<Resort> italyScraping() throws IOException {
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

        List<Resort> resorts = new ArrayList<>();
        for (String url : Urls) {
            resorts.addAll(infoScraping(url, Resort.Country.ITALY, Dictionary.Language.POLISH));
        }
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }

    public static List<Resort> austriaScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.skiinfo.pl/dolna-austria/warunki-narciarskie",
                "https://www.skiinfo.pl/gorna-austria/warunki-narciarskie",
                "https://www.skiinfo.pl/karyntia/warunki-narciarskie",
                "https://www.skiinfo.pl/salzburg/warunki-narciarskie",
                "https://www.skiinfo.pl/styria/warunki-narciarskie",
                "https://www.skiinfo.pl/tyrol/warunki-narciarskie",
                "https://www.skiinfo.pl/vorarlberg/warunki-narciarskie"
        );

        List<Resort> resorts = new ArrayList<>();
        for (String url : Urls) {
            resorts.addAll(infoScraping(url, Resort.Country.AUSTRIA, Dictionary.Language.POLISH));
        }
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> czechScraping() throws IOException {
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

        List<Resort> resorts = new ArrayList<>();
        for (String url : Urls) {
            resorts.addAll(infoScraping(url, Resort.Country.CZECH, Dictionary.Language.POLISH));
        }
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> andorraScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/andora/warunki-narciarskie", Resort.Country.ANDORRA,Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> belgiumScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/belgia/warunki-narciarskie", Resort.Country.BELGIUM, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> bulgariaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/bulgaria/warunki-narciarskie", Resort.Country.BULGARIA, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> franceScraping() throws IOException {
        List<String> Urls = List.of(
                "https://www.onthesnow.co.uk/northern-alps/skireport",
                "https://www.onthesnow.co.uk/southern-alps/skireport",
                "https://www.onthesnow.co.uk/massif-central/skireport",
                "https://www.onthesnow.co.uk/pyrenees/skireport",
                "https://www.onthesnow.co.uk/vosges/skireport",
                "https://www.onthesnow.co.uk/jura/skireport"

        );

        List<Resort> resorts = new ArrayList<>();
        for (String url : Urls) {
            resorts.addAll(infoScraping(url, Resort.Country.FRANCE, Dictionary.Language.ENGLISH));
        }
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> finlandScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/finlandia/warunki-narciarskie", Resort.Country.FINLAND, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> spainScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/hiszpania/warunki-narciarskie", Resort.Country.SPAIN, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> liechtensteinScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/liechtenstein/warunki-narciarskie", Resort.Country.LIECHTENSTEIN, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> germanyScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/niemcy/warunki-narciarskie", Resort.Country.GERMANY, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> norwayScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/norwegia/warunki-narciarskie", Resort.Country.NORWAY, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> romaniaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/rumunia/warunki-narciarskie", Resort.Country.ROMANIA, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> slovakiaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/slowacja/warunki-narciarskie", Resort.Country.SLOVAKIA, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> sloveniaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/slowenia/warunki-narciarskie", Resort.Country.SLOVENIA, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> scotlandScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/szkocja/warunki-narciarskie", Resort.Country.SCOTLAND, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> swedenScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/szwecja/warunki-narciarskie", Resort.Country.SWEDEN, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> switzerlandScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/szwajcaria/warunki-narciarskie", Resort.Country.SWITZERLAND, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> usaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/usa/warunki-narciarskie", Resort.Country.USA, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> canadaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/kanada/warunki-narciarskie", Resort.Country.CANADA, Dictionary.Language.POLISH);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }

    public static void main(String[] args) {
        try {
            switzerlandScraping();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
