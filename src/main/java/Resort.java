import java.util.Comparator;

public record Resort(
        String name, String updateTime, String openDate, String snowLast24, String currSnow, String snowType,
        String openTrailsDist, String openTrailsPer, String openDist, String openLifts, OpenStatus openStatus, Country country
) {
    public Resort(String name, String updateTime, String openDate, OpenStatus openStatus, Country country) {
        this(name, updateTime, openDate, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", openStatus, country);
    }

    public enum OpenStatus {
        CLOSE, OPEN, WEEKEND, TEMPCLOSED
    }
    public static class ResortComparator implements Comparator<Resort> {

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
                case TEMPCLOSED:
                    return 2;
                case CLOSE:
                    return 3;
                default:
                    return Integer.MAX_VALUE;
            }
        }
    }
}
