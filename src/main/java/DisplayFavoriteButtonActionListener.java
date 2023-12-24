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

        for(JPanel panel : favoriteResorts.getPanels(Resort.OpenStatus.OPEN).values()){
            favoritePanel.addToScrollContainer(panel);
        }

        favoritePanel.setScrollView();
    }
}
