import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayFavoriteButtonActionListener implements ActionListener {

    private CountryResortsPanel favoritePanel;
    private FavoriteResorts favoriteResorts = FavoriteResorts.getInstance();

    public DisplayFavoriteButtonActionListener(CountryResortsPanel favoritePanel){
        this.favoritePanel = favoritePanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        favoritePanel.clearScrollContainer();
        if(!InternetProblemHandler.scrappingStatus()){
            favoritePanel.addToScrollContainer(InternetProblemHandler.getInternetProblemPanel());
            favoritePanel.setScrollView();
            InternetProblemHandler.scrappingSuccessful();
            return;
        }

        if(favoriteResorts.containsStatus(Resort.OpenStatus.OPEN)) {
            favoritePanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.OPEN));
            int i = 0;
            for (OpenListPanel panel : favoriteResorts.getPanels(Resort.OpenStatus.OPEN).values()) {
                if(i % 2 == 0) panel.whiteBackground();
                else panel.grayBackground();
                favoritePanel.addToScrollContainer(panel);
                i++;
            }
        }

        if(favoriteResorts.containsStatus(Resort.OpenStatus.WEEKEND)) {
            favoritePanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.WEEKEND));
            int i = 0;
            for (OpenListPanel panel : favoriteResorts.getPanels(Resort.OpenStatus.WEEKEND).values()) {
                if(i % 2 == 0) panel.whiteBackground();
                else panel.grayBackground();
                favoritePanel.addToScrollContainer(panel);
                i++;
            }
        }

        if(favoriteResorts.containsStatus(Resort.OpenStatus.TEMPCLOSED)) {
            favoritePanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.TEMPCLOSED));
            int i = 0;
            for (OpenListPanel panel : favoriteResorts.getPanels(Resort.OpenStatus.TEMPCLOSED).values()) {
                if(i % 2 == 0) panel.whiteBackground();
                else panel.grayBackground();
                favoritePanel.addToScrollContainer(panel);
                i++;
            }
        }

        if(favoriteResorts.containsStatus(Resort.OpenStatus.CLOSE)) {
            favoritePanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.CLOSE));
            int i = 0;
            for (ClosedListPanel panel : favoriteResorts.getPanelsClosed().values()) {
                if(i % 2 == 0) panel.whiteBackground();
                else panel.grayBackground();
                favoritePanel.addToScrollContainer(panel);
                i++;
            }
        }

        favoritePanel.setScrollView();
    }
}
