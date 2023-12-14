import java.util.Comparator;
import java.util.Map;
import static java.util.Map.entry;

public record Resort(
        String name, String updateTime, String openDate, String snowLast24, String currSnow, String snowType,
        String openTrailsDist, String openTrailsPer, String openLifts, OpenStatus openStatus, Country country
) {
    public Resort(String name, String updateTime, String openDate, OpenStatus openStatus, Country country) {
        this(name, updateTime, openDate, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", openStatus, country);
    }

    public enum OpenStatus {
        CLOSE, OPEN, WEEKEND
    }

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

        public String getFlagUrl(){
            return "/flags/"+this.name().toLowerCase() + ".png";
        }

        public String getCountryName() {
            return COUNTRY_NAMES.getOrDefault(this, "N/A");
        }
    }

    public static class ResortComparator implements Comparator<Resort>{

        @Override
        public int compare(Resort resort1, Resort resort2) {
            return getOrder(resort1.openStatus()) - getOrder(resort2.openStatus());
        }

        private int getOrder(Resort.OpenStatus status) {
            switch (status) {
                case OPEN:
                    return 0;
                case WEEKEND:
                    return 1;
                case CLOSE:
                    return 2;
                default:
                    return Integer.MAX_VALUE;
            }
        }
    }
}
