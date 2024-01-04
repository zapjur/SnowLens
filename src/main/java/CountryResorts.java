import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.Comparator;

public class CountryResorts {
    private static volatile CountryResorts instance;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>>> countryMap;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>>> countryMapSortedByOpenDist;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>>> countryMapSortedByOpenLifts;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>>> countryMapSortedByCurrSnow;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>>> countryMapSortedByLastSnow;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>>> countryMapSortedByNames;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>>> countryMapClosed;
    private Map<Country, Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>>> countryMapClosedSortedByNames;

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

    public Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> getCountryMapClosed(Country country){
        return countryMapClosed.get(country);
    }

    private Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> sortMap(Country country, Comparator<Resort> comparator, Map<Country, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>>> mapToSort) {
        Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> sortedMap = new EnumMap<>(Resort.OpenStatus.class);
        Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> unsortedMap = mapToSort.get(country);
        if (unsortedMap != null) {
            for (Resort.OpenStatus status : unsortedMap.keySet()) {
                Map<Resort, OpenListPanel> sorted = unsortedMap.get(status).entrySet().stream()
                        .sorted(Map.Entry.comparingByKey(comparator))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        ));
                sortedMap.put(status, sorted);
            }
        }
        return sortedMap;
    }

    public Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> getOpenSortedByNames(Country country){
        if (countryMapSortedByNames == null) {
            countryMapSortedByNames = new EnumMap<>(Country.class);
        }
        countryMapSortedByNames.putIfAbsent(country, sortMap(country, Comparator.comparing(Resort::name), countryMap));
        return countryMapSortedByNames.get(country);

    }

    public Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> getOpenSortedByOpenDist(Country country){
        if (countryMapSortedByOpenDist == null) {
            countryMapSortedByOpenDist = new EnumMap<>(Country.class);
        }

        Comparator comparator = Comparator.comparing((Resort resort) -> {
            try {
                return Float.parseFloat(resort.openDist());
            } catch (NumberFormatException e){
                return Float.MIN_VALUE;
            }
        }).reversed();
        countryMapSortedByOpenDist.putIfAbsent(country, sortMap(country, comparator, countryMap));
        return countryMapSortedByOpenDist.get(country);

    }

    public Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> getOpenSortedByOpenLifts(Country country){
        if (countryMapSortedByOpenLifts == null) {
            countryMapSortedByOpenLifts = new EnumMap<>(Country.class);
        }

        Comparator comparator = Comparator.comparing((Resort resort) -> {
            try{
                String[] parts = resort.openLifts().split("/");
                return Integer.parseInt(parts[0]);
            } catch (NumberFormatException e){
                return Integer.MIN_VALUE;
            }
        }).reversed();
        countryMapSortedByOpenLifts.putIfAbsent(country, sortMap(country, comparator, countryMap));
        return countryMapSortedByOpenLifts.get(country);

    }

    public Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> getOpenSortedByCurrSnow(Country country){
        if (countryMapSortedByCurrSnow == null) {
            countryMapSortedByCurrSnow = new EnumMap<>(Country.class);
        }

        Comparator comparator = Comparator.comparing((Resort resort) -> {
            try{
                String[] parts = resort.currSnow().split("-");
                if(parts.length >= 2) {
                    return Integer.parseInt(parts[0]) + Integer.parseInt(parts[1].replace("cm", ""));
                }
                else{
                    if(parts.length == 0) return Integer.MIN_VALUE;
                    return Integer.parseInt((parts[0]));
                }
            } catch (NumberFormatException ex) {
                return Integer.MIN_VALUE;
            }
        }).reversed();
        countryMapSortedByCurrSnow.putIfAbsent(country, sortMap(country, comparator, countryMap));
        return countryMapSortedByCurrSnow.get(country);

    }

    public Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> getOpenSortedByLastSnow(Country country){
        if (countryMapSortedByLastSnow == null) {
            countryMapSortedByLastSnow = new EnumMap<>(Country.class);
        }

        Comparator comparator = Comparator.comparing((Resort resort) -> {
            try{
                String temp = resort.snowLast24().replace("cm", "");
                return Float.parseFloat(temp);
            } catch (NumberFormatException e){
                return Float.MIN_VALUE;
            }
        }).reversed();
        countryMapSortedByLastSnow.putIfAbsent(country, sortMap(country, comparator, countryMap));
        return countryMapSortedByLastSnow.get(country);

    }


    public Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> getClosedSortedByNames(Country country){
        if(countryMapClosedSortedByNames == null){
            countryMapClosedSortedByNames = new EnumMap<>(Country.class);
        }

        if(countryMapClosedSortedByNames.containsKey(country)){
            return countryMapClosedSortedByNames.get(country);
        }

        for (Resort.OpenStatus status : countryMapClosed.get(country).keySet()) {
            Map<Resort, ClosedListPanel> sortedByNames = countryMapClosed.get(country).get(status).entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparing(Resort::name)))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            countryMapClosedSortedByNames.putIfAbsent(country, new EnumMap<>(Resort.OpenStatus.class));
            countryMapClosedSortedByNames.get(country).put(status, sortedByNames);
        }

        return countryMapClosedSortedByNames.get(country);
    }

}
