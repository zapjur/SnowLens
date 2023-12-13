import java.util.Comparator;
import java.util.List;

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

        public String getFlagUrl(){
            switch(this){
                case POLAND -> {
                    return  "/src/main/resources/flags/poland.png";
                }
                case AUSTRIA -> {
                    return "/src/main/resources/flags/austria.png";
                }
                case ITALY -> {
                    return "/src/main/resources/flags/italy.png";
                }
                case FINLAND -> {
                    return "/src/main/resources/flags/finland.png";
                }
                case FRANCE -> {
                    return "/src/main/resources/flags/france.png";
                }
                case CZECH -> {
                    return "/src/main/resources/flags/czech.png";
                }
                case ANDORRA -> {
                    return "/src/main/resources/flags/andorra.png";
                }
                case BELGIUM -> {
                    return "/src/main/resources/flags/belgium.png";
                }
                case SPAIN -> {
                    return "/src/main/resources/flags/spain.png";
                }
                case LIECHTENSTEIN -> {
                    return "/src/main/resources/flags/liechtenstein.png";
                }
                case GERMANY -> {
                    return "/src/main/resources/flags/germany.png";
                }
                case NORWAY -> {
                    return "/src/main/resources/flags/norway.png";
                }
                case ROMANIA -> {
                    return "/src/main/resources/flags/romania.png";
                }
                case SLOVAKIA -> {
                    return "/src/main/resources/flags/slovakia.png";
                }
                case SLOVENIA -> {
                    return "/src/main/resources/flags/slovenia.png";
                }
                case SCOTLAND -> {
                    return "/src/main/resources/flags/scotland.png";
                }
                case SWITZERLAND -> {
                    return "/src/main/resources/flags/switzerland.png";
                }
                case SWEDEN -> {
                    return "/src/main/resources/flags/sweden.png";
                }
                case USA -> {
                    return "/src/main/resources/flags/usa.png";
                }
                case CANADA -> {
                    return "/src/main/resources/flags/canada.png";
                }
                case BULGARIA -> {
                    return "/src/main/resources/flags/bulgaria.png";
                }
                default -> {
                    return "N/A";
                }
            }
        }

        public String getCountryName(){
            switch(this){
                case POLAND -> {
                    return  "Polska";
                }
                case AUSTRIA -> {
                    return "Austria";
                }
                case ITALY -> {
                    return "Włochy";
                }
                case FINLAND -> {
                    return "Finlandia";
                }
                case FRANCE -> {
                    return "Francja";
                }
                case CZECH -> {
                    return "Czechy";
                }
                case ANDORRA -> {
                    return "Andora";
                }
                case BELGIUM -> {
                    return "Belgia";
                }
                case SPAIN -> {
                    return "Hiszpania";
                }
                case LIECHTENSTEIN -> {
                    return "Liechtenstein";
                }
                case GERMANY -> {
                    return "Niemcy";
                }
                case NORWAY -> {
                    return "Norwegia";
                }
                case ROMANIA -> {
                    return "Rumunia";
                }
                case SLOVAKIA -> {
                    return "Słowacja";
                }
                case SLOVENIA -> {
                    return "Słowenia";
                }
                case SCOTLAND -> {
                    return "Szkocja";
                }
                case SWITZERLAND -> {
                    return "Szwajcaria";
                }
                case SWEDEN -> {
                    return "Szwecja";
                }
                case USA -> {
                    return "USA";
                }
                case CANADA -> {
                    return "Kanada";
                }
                case BULGARIA -> {
                    return "Bułgaria";
                }
                default -> {
                    return "N/A";
                }
            }
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
