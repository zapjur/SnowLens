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
                entry(Country.POLAND, "Polska"),
                entry(Country.AUSTRIA, "Austria"),
                entry(Country.ITALY, "Włochy"),
                entry(Country.FINLAND, "Finlandia"),
                entry(Country.FRANCE, "Francja"),
                entry(Country.CZECH, "Czechy"),
                entry(Country.ANDORRA, "Andora"),
                entry(Country.BELGIUM, "Belgia"),
                entry(Country.SPAIN, "Hiszpania"),
                entry(Country.LIECHTENSTEIN, "Liechtenstein"),
                entry(Country.GERMANY, "Niemcy"),
                entry(Country.NORWAY, "Norwegia"),
                entry(Country.ROMANIA, "Rumunia"),
                entry(Country.SLOVAKIA, "Słowacja"),
                entry(Country.SLOVENIA, "Słowenia"),
                entry(Country.SCOTLAND, "Szkocja"),
                entry(Country.SWITZERLAND, "Szwajcaria"),
                entry(Country.SWEDEN, "Szwecja"),
                entry(Country.USA, "USA"),
                entry(Country.CANADA, "Kanada"),
                entry(Country.BULGARIA, "Bułgaria")
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
