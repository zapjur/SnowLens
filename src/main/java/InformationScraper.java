import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InformationScraper {
    public static List<Resort> infoScraping(String url, Resort.Country country) throws IOException {
        List<Resort> resorts = new ArrayList<>();

        Document document = Jsoup.connect(url).get();

        Elements docTBody = document.select("tbody");
        int counter = 0;
        for(Element currTBody : docTBody){
            Element currTr = currTBody.select("tr").first();
            boolean closedLeft = false;

            while(currTr != null && counter < 2){ // petla dla otwartych i weekend
                Element currTd = currTr.select("td").first();
                String name = currTd.select("a").select("span").text();
                String updateTime = currTd.select("a").select("time").text();

                currTd = currTd.nextElementSibling();
                String snowLast24 = currTd.select("span").text();

                currTd = currTd.nextElementSibling();

                if(currTd == null){
                    closedLeft = true;
                    break;
                }
                String currSnow = currTd.select("span").text();
                String[] parts = currSnow.split(" ");
                currSnow = parts[0];
                String snowType;
                if(parts.length >= 2) {
                    snowType = parts[1];
                }
                else{
                    snowType = "N/A";
                }

                currTd = currTd.nextElementSibling();
                String openTrailsDist = currTd.select("span").text();
                parts = openTrailsDist.split(" ");
                openTrailsDist = parts[0]+"km";
                String openTrailsPer;
                if(parts.length >= 3){
                    openTrailsPer = parts[2];
                }
                else{
                    openTrailsPer = "N/A";
                }

                currTd = currTd.nextElementSibling();
                String openLifts = currTd.select("span").text();
                parts = openLifts.split(" ");
                openLifts = parts[0];

                if(counter == 0) {
                    resorts.add(new Resort(name, updateTime, "N/A", snowLast24, currSnow, snowType, openTrailsDist,
                                           openTrailsPer, openLifts, Resort.OpenStatus.OPEN, country));
                }
                else{
                    resorts.add(new Resort(name, updateTime, "N/A", snowLast24, currSnow, snowType, openTrailsDist,
                                           openTrailsPer, openLifts, Resort.OpenStatus.WEEKEND, country));
                }

                currTr = currTr.nextElementSibling();
            }

            if(counter == 2 || closedLeft){ // dla zamknietych (inne dane)
                while(currTr != null){
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
            resorts.addAll(infoScraping(url, Resort.Country.POLAND));
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
            resorts.addAll(infoScraping(url, Resort.Country.ITALY));
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
            resorts.addAll(infoScraping(url, Resort.Country.AUSTRIA));
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
            resorts.addAll(infoScraping(url, Resort.Country.CZECH));
        }
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> andorraScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/andora/warunki-narciarskie", Resort.Country.ANDORRA);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> belgiumScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/belgia/warunki-narciarskie", Resort.Country.BELGIUM);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> bulgariaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/bulgaria/warunki-narciarskie", Resort.Country.BULGARIA);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> franceScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/francja/warunki-narciarskie", Resort.Country.FRANCE);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> finlandScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/finlandia/warunki-narciarskie", Resort.Country.FINLAND);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> spainScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/hiszpania/warunki-narciarskie", Resort.Country.SPAIN);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> liechtensteinScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/liechtenstein/warunki-narciarskie", Resort.Country.LIECHTENSTEIN);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> germanyScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/niemcy/warunki-narciarskie", Resort.Country.GERMANY);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> norwayScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/norwegia/warunki-narciarskie", Resort.Country.NORWAY);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> romaniaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/rumunia/warunki-narciarskie", Resort.Country.ROMANIA);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> slovakiaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/slowacja/warunki-narciarskie", Resort.Country.SLOVAKIA);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> sloveniaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/slowenia/warunki-narciarskie", Resort.Country.SLOVENIA);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> scotlandScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/szkocja/warunki-narciarskie", Resort.Country.SCOTLAND);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> swedenScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/szwecja/warunki-narciarskie", Resort.Country.SWEDEN);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> switzerlandScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/szwajcaria/warunki-narciarskie", Resort.Country.SWITZERLAND);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> usaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/usa/warunki-narciarskie", Resort.Country.USA);
        resorts.sort(new Resort.ResortComparator());
        return resorts;
    }
    public static List<Resort> canadaScraping() throws IOException {
        List<Resort> resorts = infoScraping("https://www.skiinfo.pl/kanada/warunki-narciarskie", Resort.Country.CANADA);
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
