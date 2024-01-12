package Data;

import java.util.Map;
import static java.util.Map.entry;

public class Dictionary {
    private static final Map<String, String> pol2EngDic = Map.ofEntries(
            entry("Puch", "Powder"),
            entry("Ratrakowany", "Machine Groomed"),
            entry("Mokry śnieg", "Wet Snow"),
            entry("Puch zsiadły", "Packed Powder"),
            entry("Sztuczny śnieg", "Machine Made"),
            entry("Zróżnicowane warunki", "Variable Conditions"),
            entry("Ubity śnieg", "Hard Packed"),
            entry("Śnieg ziarnisty", "Corn Snow"),
            entry("Firn", "Spring Snow"),
            entry("godzinę temu", "hour ago"),
            entry("godzin temu", "hours ago"),
            entry("godziny temu", "hours ago"),
            entry("dzień temu", "day ago"),
            entry("dni temu", "days ago"),
            entry("minutę temu", "minute ago"),
            entry("minut temu", "minutes ago"),
            entry("minuty temu", "minutes ago"),
            entry("miesiąc temu", "month ago"),
            entry("miesięcy temu", "months ago"),
            entry("miesiące temu", "months ago"),
            entry("tydzień temu", "week ago"),
            entry("tygodni temu", "weeks ago"),
            entry("tygodnie temu", "weeks ago")

    );

    public static String getPol2Eng(String key) {
        return pol2EngDic.getOrDefault(key, key);
    }

    public enum Language{
        POLISH, ENGLISH
    }
}
