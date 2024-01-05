import javax.swing.*;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;

public class FavoriteResorts {
    private static volatile FavoriteResorts instance;
    private Map<Resort.OpenStatus, Map<Resort, JPanel>> favoriteMap;
    private Map<String, Resort> favoriteSavedMap;

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
        favoriteSavedMap.put(resort.name(), resort);
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
    public boolean containsResort(String name, Resort.OpenStatus status) {
        return favoriteMap.get(status).keySet().stream().anyMatch(resort -> resort.name().equals(name));
    }
    public boolean containsStatus(Resort.OpenStatus status) {
        return favoriteMap.get(status).size() > 0;
    }
    public Map<String, Resort> getFavoriteSavedMap(){
        return favoriteSavedMap;
    }
    public void setFavoriteSavedMap(Map<String, Resort> favoriteSavedMap){
        this.favoriteSavedMap = favoriteSavedMap;
    }

    public void scrapStartFavorite(){
        for(Resort resort : favoriteSavedMap.values()){
            Resort res = InformationScraper.scrapFavoriteResort(resort.url(), resort.country(), resort.lang(), resort);
            if(resort.openStatus() != Resort.OpenStatus.CLOSE) {
                favoriteMap.get(res.openStatus()).put(res, new OpenListPanel(res));
            }
            else{
                favoriteMap.get(Resort.OpenStatus.CLOSE).put(res, new ClosedListPanel(res));
            }
        }
    }
}
