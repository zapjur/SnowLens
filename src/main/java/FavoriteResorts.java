import javax.swing.*;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;

public class FavoriteResorts {
    private static volatile FavoriteResorts instance;
    private Map<Resort.OpenStatus, Map<Resort, JPanel>> favoriteMap;
    private Map<String, String> favoriteSavedMap;

    private FavoriteResorts() {
        favoriteMap = new EnumMap<>(Resort.OpenStatus.class);
        for (Resort.OpenStatus status : Resort.OpenStatus.values()) {
            favoriteMap.put(status, new HashMap<Resort, JPanel>());
        }
        favoriteSavedMap = new HashMap<>();
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
        favoriteSavedMap.put(resort.name(), resort.url());
    }

    public void removeFavorite(Resort resort) {
        favoriteMap.get(resort.openStatus()).remove(resort);
        favoriteSavedMap.remove(resort.name());
    }

    public Map<Resort, JPanel> getPanels(Resort.OpenStatus status){
        return favoriteMap.get(status);
    }
    public boolean containsResort(Resort resort){
        return favoriteMap.get(resort.openStatus()).containsKey(resort);
    }

    public boolean containsStatus(Resort.OpenStatus status) {
        return favoriteMap.get(status).size() > 0;
    }
    public Map<String, String> getFavoriteSavedMap(){
        return favoriteSavedMap;
    }
    public void setFavoriteSavedMap(Map<String, String> favoriteSavedMap){
        this.favoriteSavedMap = favoriteSavedMap;
    }
}
