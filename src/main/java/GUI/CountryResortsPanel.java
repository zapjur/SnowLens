package GUI;

import javax.swing.*;
import java.awt.*;
import Data.Country;

public class CountryResortsPanel extends JPanel {

    private JScrollPane scrollPanel = new JScrollPane();
    private JPanel scrollContainer = new JPanel();
    private Country country;

    public CountryResortsPanel(Country country){
        setLayout(new BorderLayout());
        add(scrollPanel, BorderLayout.CENTER);
        scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.Y_AXIS));
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.country = country;
    }

    public void addToScrollContainer(JPanel panel){
        scrollContainer.add(panel);
    }

    public void setScrollView(){
        scrollPanel.setViewportView(scrollContainer);
    }
    public void addSortButtonsPanel(){
        add(new SortButtonsPanel(country, this), BorderLayout.NORTH);
    }

    public void clearScrollContainer(){
        scrollContainer.removeAll();
    }
}
