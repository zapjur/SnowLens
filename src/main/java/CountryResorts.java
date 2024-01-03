import javax.swing.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class CountryResorts {
    private static volatile CountryResorts instance;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>>> countryMap;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>>> countryMapClosed;

    private CountryResorts() {
        countryMap = new EnumMap<>(Country.class);
        countryMapClosed = new EnumMap<>(Country.class);
        for (Country country : Country.values()) {
            Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> statusMap = new EnumMap<>(Resort.OpenStatus.class);
            Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> statusMapClosed = new EnumMap<>(Resort.OpenStatus.class);
            for (Resort.OpenStatus status : Resort.OpenStatus.values()) {
                if(status != Resort.OpenStatus.CLOSE) {
                    statusMap.put(status, new HashMap<Resort, OpenListPanel>());
                }
                else{
                    statusMapClosed.put(status, new HashMap<Resort, ClosedListPanel>());
                }
            }
            countryMap.put(country, statusMap);
            countryMapClosed.put(country, statusMapClosed);
        }
    }

    public static CountryResorts getInstance() {
        if (instance == null) {
            synchronized (CountryResorts.class) {
                if (instance == null) {
                    instance = new CountryResorts();
                }
            }
        }
        return instance;
    }

    public void addResort(Country country, Resort resort, OpenListPanel panel) {
        countryMap.get(country).get(resort.openStatus()).put(resort, panel);
    }
    public void addResort(Country country, Resort resort, ClosedListPanel panel) {
        countryMapClosed.get(country).get(resort.openStatus()).put(resort, panel);
    }

    public void removeResort(Country country, Resort resort) {
        if(resort.openStatus() == Resort.OpenStatus.CLOSE){
            countryMapClosed.get(country).get(resort.openStatus()).remove(resort);
        }
        else{
            countryMap.get(country).get(resort.openStatus()).remove(resort);
        }
    }
    public OpenListPanel getPanel(Country country, Resort.OpenStatus status, Resort resort) {
        return countryMap.get(country).get(status).get(resort);
    }
    public ClosedListPanel getPanelClosed(Country country, Resort.OpenStatus status, Resort resort) {
        return countryMapClosed.get(country).get(status).get(resort);
    }

    public boolean containsResort(Country country, Resort resort) {
        if(resort.openStatus() == Resort.OpenStatus.CLOSE){
            return countryMapClosed.get(country).get(resort.openStatus()).containsKey(resort);
        }
        return countryMap.get(country).get(resort.openStatus()).containsKey(resort);
    }

}
