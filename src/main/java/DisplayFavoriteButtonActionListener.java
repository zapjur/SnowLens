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
        favoritePanel.addSortButtonsPanel();

        if(favoriteResorts.containsStatus(Resort.OpenStatus.OPEN)) {
            favoritePanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.OPEN));
            for (JPanel panel : favoriteResorts.getPanels(Resort.OpenStatus.OPEN).values()) {
                favoritePanel.addToScrollContainer(panel);
            }
        }

        if(favoriteResorts.containsStatus(Resort.OpenStatus.WEEKEND)) {
            favoritePanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.WEEKEND));
            for (JPanel panel : favoriteResorts.getPanels(Resort.OpenStatus.WEEKEND).values()) {
                favoritePanel.addToScrollContainer(panel);
            }
        }

        if(favoriteResorts.containsStatus(Resort.OpenStatus.TEMPCLOSED)) {
            favoritePanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.TEMPCLOSED));
            for (JPanel panel : favoriteResorts.getPanels(Resort.OpenStatus.TEMPCLOSED).values()) {
                favoritePanel.addToScrollContainer(panel);
            }
        }

        if(favoriteResorts.containsStatus(Resort.OpenStatus.CLOSE)) {
            favoritePanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.CLOSE));
            for (JPanel panel : favoriteResorts.getPanels(Resort.OpenStatus.CLOSE).values()) {
                favoritePanel.addToScrollContainer(panel);
            }
        }

        favoritePanel.setScrollView();
    }
}
