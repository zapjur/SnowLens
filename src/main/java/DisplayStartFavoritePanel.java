import java.util.Map;
import java.util.List;
import java.util.EnumMap;

public class DisplayStartFavoritePanel {

    private FavoriteResorts favoriteResorts = FavoriteResorts.getInstance();
    private Map<Resort.OpenStatus, List<Resort>> list = new EnumMap<>(Resort.OpenStatus.class);

    public static void add(Resort resort){

    }
}
