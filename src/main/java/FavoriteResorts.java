import javax.swing.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class FavoriteResorts {
    private static volatile FavoriteResorts instance;
    private Map<Resort.OpenStatus, Map<Resort, JPanel>> favoriteMap;

    private FavoriteResorts() {
        favoriteMap = new EnumMap<>(Resort.OpenStatus.class);
        for (Resort.OpenStatus status : Resort.OpenStatus.values()) {
            favoriteMap.put(status, new HashMap<Resort, JPanel>());
        }
    }

    public static FavoriteResorts getInstance() {
        if (instance == null) {
            synchronized (FavoriteResorts.class) {
                if (instance == null) {
                    instance = new FavoriteResorts();
                }
            }
        }
        return instance;
    }

    public void addFavorite(Resort resort, JPanel panel) {
        favoriteMap.get(resort.openStatus()).put(resort, panel);
    }

    public void removeFavorite(Resort resort) {
        favoriteMap.get(resort.openStatus()).remove(resort);
    }

    public Map<Resort, JPanel> getPanels(Resort.OpenStatus status){
        return favoriteMap.get(status);
    }
    public boolean containsResort(Resort resort){
        return favoriteMap.get(resort.openStatus()).containsKey(resort);
    }
}
