
public record Resort(
        String name, String updateTime, String openDate, String snowLast24, String currSnow, String snowType,
        String openTrailsDist, String openTrailsPer, String openDist, String openLifts, OpenStatus openStatus, Country country,
        String url
) {
    public Resort(String name, String updateTime, String openDate, OpenStatus openStatus, Country country, String url) {
        this(name, updateTime, openDate, "N/A", "N/A", "N/A",
                "N/A", "N/A", "N/A", "N/A", openStatus, country, url);
    }


    public enum OpenStatus {
        CLOSE, OPEN, WEEKEND, TEMPCLOSED
    }
}
