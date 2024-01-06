import javax.swing.*;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;

public class FavoriteResorts {
    private static volatile FavoriteResorts instance;
    private Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> favoriteMap;
    private Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> favoriteMapClosed;
    private Map<String, Resort> favoriteSavedMap;

    private FavoriteResorts() {
        favoriteMap = new EnumMap<>(Resort.OpenStatus.class);
        favoriteMapClosed = new EnumMap<>(Resort.OpenStatus.class);
        for (Resort.OpenStatus status : Resort.OpenStatus.values()) {
            if(status != Resort.OpenStatus.CLOSE) {
                favoriteMap.put(status, new HashMap<Resort, OpenListPanel>());
            }
            else{
                favoriteMapClosed.put(status, new HashMap<Resort, ClosedListPanel>());
            }
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

    public void addFavorite(Resort resort, OpenListPanel panel) {
        favoriteMap.get(resort.openStatus()).put(resort, panel);
        favoriteSavedMap.put(resort.name(), resort);
    }
    public void addFavoriteClosed(Resort resort, ClosedListPanel panel) {
        favoriteMapClosed.get(resort.openStatus()).put(resort, panel);
        favoriteSavedMap.put(resort.name(), resort);
    }

    public void removeFavorite(Resort resort) {
        if(resort.openStatus() != Resort.OpenStatus.CLOSE) {
            favoriteMap.get(resort.openStatus()).entrySet().removeIf(entry -> entry.getKey().name().equals(resort.name()));
        }
        else{
            favoriteMapClosed.get(resort.openStatus()).entrySet().removeIf(entry -> entry.getKey().name().equals(resort.name()));
        }
        favoriteSavedMap.remove(resort.name());
    }

    public Map<Resort, OpenListPanel> getPanels(Resort.OpenStatus status){
        return favoriteMap.get(status);
    }
    public Map<Resort, ClosedListPanel> getPanelsClosed(){
        return favoriteMapClosed.get(Resort.OpenStatus.CLOSE);
    }
    public boolean containsResort(Resort resort){
        if(resort.openStatus() != Resort.OpenStatus.CLOSE) {
            return favoriteMap.get(resort.openStatus()).containsKey(resort);
        }
        return favoriteMapClosed.get(resort.openStatus()).containsKey(resort);

    }
    public boolean containsResort(String name, Resort.OpenStatus status) {
        if(status != Resort.OpenStatus.CLOSE) {
            return favoriteMap.get(status).keySet().stream().anyMatch(resort -> resort.name().equals(name));
        }
        return favoriteMapClosed.get(Resort.OpenStatus.CLOSE).keySet().stream().anyMatch(resort -> resort.name().equals(name));
    }
    public boolean containsStatus(Resort.OpenStatus status) {
        if(status != Resort.OpenStatus.CLOSE) {
            return favoriteMap.get(status).size() > 0;
        }
        return favoriteMapClosed.get(Resort.OpenStatus.CLOSE).size() > 0;
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
                favoriteMapClosed.get(Resort.OpenStatus.CLOSE).put(res, new ClosedListPanel(res));
            }
        }
    }
}
