import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class FavoriteResorts {
    private static volatile FavoriteResorts instance;
    private Map<Resort.OpenStatus, Map<Resort, JPanel>> favoriteMap;

    private FavoriteResorts() {
        favoriteMap = new HashMap<>();
        favoriteMap.put(Resort.OpenStatus.OPEN, new HashMap<Resort, JPanel>());
        favoriteMap.put(Resort.OpenStatus.WEEKEND, new HashMap<Resort, JPanel>());
        favoriteMap.put(Resort.OpenStatus.TEMPCLOSED, new HashMap<Resort, JPanel>());
        favoriteMap.put(Resort.OpenStatus.CLOSE, new HashMap<Resort, JPanel>());
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

}
