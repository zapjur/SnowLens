
public record Resort(
        String name, String updateTime, String openDate, String snowLast24, String currSnow, String snowType,
        String openTrailsDist, String openTrailsPer, String openDist, String openLifts, OpenStatus openStatus, Country country
) {
    public Resort(String name, String updateTime, String openDate, OpenStatus openStatus, Country country) {
        this(name, updateTime, openDate, "N/A", "N/A", "N/A",
                "N/A", "N/A", "N/A", "N/A", openStatus, country);
    }


    public enum OpenStatus {
        CLOSE, OPEN, WEEKEND, TEMPCLOSED
    }
}
