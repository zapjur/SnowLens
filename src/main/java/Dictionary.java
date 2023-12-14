import java.util.Map;
import static java.util.Map.entry;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dictionary {
    private static final Map<String, String> engDic = Map.ofEntries(
            entry("Powder", "Puch"),
            entry("Machine Groomed", "Ratrakowany"),
            entry("Wet Snow", "Mokry Śnieg"),
            entry("Packed Powder", "Puch zsiadły"),
            entry("Machine Made", "Sztuczny śnieg"),
            entry("Variable Conditions", "Zróżnicowane warunki"),
            entry("Hard Packed", "Ubity śnieg"),
            entry("Corn Snow", "Śnieg ziarnisty"),
            entry("Spring Snow", "Firn"),
            entry("hour ago", "godzinę temu"),
            entry("hours ago", "godzin temu"),
            entry("day ago", "dzień temu"),
            entry("days ago", "dni temu"),
            entry("minute ago", "minutę temu"),
            entry("minutes ago", "minut temu"),
            entry("month ago", "miesiąc temu"),
            entry("months ago", "miesięcy temu")
    );

    public static String getEng2Pol(String key){
        return engDic.getOrDefault(key, key);
    }
    public static String translateTime(String time){
        for(Entry<String, String> entry : engDic.entrySet()){
            if(time.endsWith(entry.getKey())){
                if ("days ago".equals(entry.getKey())) {
                    return time.replace(entry.getKey(), entry.getValue());
                }
                int num = extractNum(time);
                return correctForm(num, time, entry.getValue());
            }
        }
        return time;
    }

    private static int extractNum(String time){
        Matcher matcher = Pattern.compile("\\d+").matcher(time);
        if(matcher.find()){
            return Integer.parseInt(matcher.group());
        }
        return -1;
    }

    private static String correctForm(int num, String orgTime, String polTime){
        if(num == -1) return orgTime;

        if (num == 1) {

            return orgTime.replace("hour ago", "godzinę temu").replace("day ago", "dzień temu");
        } else if (num % 10 >= 2 && num % 10 <= 4 && (num % 100 < 10 || num % 100 >= 20)) {
            return num + " " + polTime.replace(" temu", "y temu");
        } else {
            return num + " " + polTime;
        }
    }


    public enum Language{
        POLISH, ENGLISH
    }
}
