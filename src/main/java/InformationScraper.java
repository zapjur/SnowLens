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
                String openTrailsPer = parts[2];

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
        List<String> polandUrls = List.of(
                "https://www.skiinfo.pl/malopolskie/warunki-narciarskie",
                "https://www.skiinfo.pl/dolnoslaskie/warunki-narciarskie",
                "https://www.skiinfo.pl/gory-swietokrzyskie/warunki-narciarskie",
                "https://www.skiinfo.pl/lodzkie/warunki-narciarskie",
                "https://www.skiinfo.pl/podkarpackie/warunki-narciarskie",
                "https://www.skiinfo.pl/slaskie/warunki-narciarskie"
        );

        List<Resort> polandResorts = new ArrayList<>();
        for (String url : polandUrls) {
            polandResorts.addAll(infoScraping(url, Resort.Country.POLAND));
        }

        return polandResorts;
    }

    public static List<Resort> italyScraping() throws IOException {
        List<String> italyUrls = List.of(
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

        List<Resort> italyResorts = new ArrayList<>();
        for (String url : italyUrls) {
            italyResorts.addAll(infoScraping(url, Resort.Country.ITALY));
        }

        return italyResorts;
    }

    public static List<Resort> austriaScraping() throws IOException {
        List<String> austriaUrls = List.of(
                "https://www.skiinfo.pl/dolna-austria/warunki-narciarskie",
                "https://www.skiinfo.pl/gorna-austria/warunki-narciarskie",
                "https://www.skiinfo.pl/karyntia/warunki-narciarskie",
                "https://www.skiinfo.pl/salzburg/warunki-narciarskie",
                "https://www.skiinfo.pl/styria/warunki-narciarskie",
                "https://www.skiinfo.pl/tyrol/warunki-narciarskie",
                "https://www.skiinfo.pl/vorarlberg/warunki-narciarskie"
        );

        List<Resort> austriaResorts = new ArrayList<>();
        for (String url : austriaUrls) {
            austriaResorts.addAll(infoScraping(url, Resort.Country.AUSTRIA));
        }
        System.out.println(austriaResorts.get(5).toString());
        return austriaResorts;
    }

    public static void main(String[] args) {
        try {
            austriaScraping();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
