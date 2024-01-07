
public class InternetProblemHandler {

    private static boolean scrappedSuccsessfully = false;
    private static InternetProblemPanel internetProblemPanel = new InternetProblemPanel();
    private static String panelName = "InternetProblem";

    public static void scrappingFailed(){
        scrappedSuccsessfully = false;
    }
    public static void scrappingSuccessful(){
        scrappedSuccsessfully = true;
    }

    public static boolean scrappingStatus(){
        return scrappedSuccsessfully;
    }

    public static InternetProblemPanel getInternetProblemPanel(){
        return internetProblemPanel;
    }
    public static String getPanelName(){
        return panelName;
    }
}
