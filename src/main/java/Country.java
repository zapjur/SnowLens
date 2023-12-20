import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

public enum Country {
    POLAND, AUSTRIA, ITALY, FINLAND, FRANCE,
    CZECH, ANDORRA, BELGIUM, SPAIN, LIECHTENSTEIN,
    GERMANY, NORWAY, ROMANIA, SLOVAKIA, SLOVENIA,
    SCOTLAND, SWITZERLAND, SWEDEN, USA, CANADA,
    BULGARIA;


    private static final Map<Country, String> COUNTRY_NAMES = Map.ofEntries(
            entry(Country.POLAND, "Poland"),
            entry(Country.AUSTRIA, "Austria"),
            entry(Country.ITALY, "Italy"),
            entry(Country.FINLAND, "Finland"),
            entry(Country.FRANCE, "France"),
            entry(Country.CZECH, "Czech"),
            entry(Country.ANDORRA, "Andorra"),
            entry(Country.BELGIUM, "Belgium"),
            entry(Country.SPAIN, "Spain"),
            entry(Country.LIECHTENSTEIN, "Liechtenstein"),
            entry(Country.GERMANY, "Germany"),
            entry(Country.NORWAY, "Norway"),
            entry(Country.ROMANIA, "Romania"),
            entry(Country.SLOVAKIA, "Slovakia"),
            entry(Country.SLOVENIA, "Slovenia"),
            entry(Country.SCOTLAND, "Scotland"),
            entry(Country.SWITZERLAND, "Switzerland"),
            entry(Country.SWEDEN, "Sweden"),
            entry(Country.USA, "USA"),
            entry(Country.CANADA, "Canada"),
            entry(Country.BULGARIA, "Bulgaria")
    );

    private static final Map<Country, ScrapSupplier<Map<Resort.OpenStatus, List<Resort>>>> COUNTRY_SCRAPING = Map.ofEntries(
            entry(Country.POLAND, InformationScraper::polandScraping),
            entry(Country.AUSTRIA, InformationScraper::austriaScraping),
            entry(Country.ITALY, InformationScraper::italyScraping),
            entry(Country.FINLAND, InformationScraper::finlandScraping),
            entry(Country.FRANCE, InformationScraper::franceScraping),
            entry(Country.CZECH, InformationScraper::czechScraping),
            entry(Country.ANDORRA, InformationScraper::andorraScraping),
            entry(Country.BELGIUM, InformationScraper::belgiumScraping),
            entry(Country.SPAIN, InformationScraper::spainScraping),
            entry(Country.LIECHTENSTEIN, InformationScraper::liechtensteinScraping),
            entry(Country.GERMANY, InformationScraper::germanyScraping),
            entry(Country.NORWAY, InformationScraper::norwayScraping),
            entry(Country.ROMANIA, InformationScraper::romaniaScraping),
            entry(Country.SLOVAKIA, InformationScraper::slovakiaScraping),
            entry(Country.SLOVENIA, InformationScraper::sloveniaScraping),
            entry(Country.SCOTLAND, InformationScraper::scotlandScraping),
            entry(Country.SWITZERLAND, InformationScraper::switzerlandScraping),
            entry(Country.SWEDEN, InformationScraper::swedenScraping),
            entry(Country.USA, InformationScraper::usaScraping),
            entry(Country.CANADA, InformationScraper::canadaScraping),
            entry(Country.BULGARIA, InformationScraper::bulgariaScraping)
    );

    public static final Map<Country, Map<Resort.OpenStatus, List<Resort>>> COUNTRY_RESORTS = new HashMap<>();

    public String getFlagUrl() {
        return "/flags/" + this.name().toLowerCase() + ".png";
    }

    public String getCountryName() {
        return COUNTRY_NAMES.getOrDefault(this, "N/A");
    }

    public Map<Resort.OpenStatus, List<Resort>> getResortList() throws IOException {
        return Country.COUNTRY_SCRAPING.get(this).get();
    }

    public interface ScrapSupplier<T> {
        T get() throws IOException;
    }
}

