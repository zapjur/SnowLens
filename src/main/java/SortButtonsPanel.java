import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class SortButtonsPanel extends JPanel {

    private JButton sortByNames = new JButton();
    private JButton sortBySnowLast = new JButton();
    private JButton sortByCurrSnow = new JButton();
    private JButton sortByOpenDist = new JButton();
    private JButton sortByOpenLifts = new JButton();
    private CountryResorts countryResorts = CountryResorts.getInstance();


    public SortButtonsPanel(Country country, CountryResortsPanel countryPanel){
        setSize(new Dimension(1000, 50));
        setLayout(new GridLayout(1,5));


        List<JButton> buttons = List.of(sortByNames, sortBySnowLast, sortByCurrSnow, sortByOpenDist, sortByOpenLifts);

        for(JButton button : buttons){
            button.setPreferredSize(new Dimension(150, 50));
            add(button);
        }
        sortByOpenLifts.setText("Open Lifts");
        sortByOpenDist.setText("Distance");
        sortByCurrSnow.setText("Snow");
        sortBySnowLast.setText("Last Snowfall");
        sortByNames.setText("Name");

        sortByOpenDist.addActionListener(e ->{

            Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> openMap = countryResorts.getOpenSortedByOpenDist(country);
            Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> closedMap = countryResorts.getCountryMapClosed(country);

            display(countryPanel, openMap, closedMap);

        });

        sortByCurrSnow.addActionListener(e ->{

            Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> openMap = countryResorts.getOpenSortedByCurrSnow(country);
            Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> closedMap = countryResorts.getCountryMapClosed(country);

            display(countryPanel, openMap, closedMap);

        });

        sortByOpenLifts.addActionListener(e ->{

            Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> openMap = countryResorts.getOpenSortedByOpenLifts(country);
            Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> closedMap = countryResorts.getCountryMapClosed(country);

            display(countryPanel, openMap, closedMap);

        });

        sortByNames.addActionListener(e ->{

            Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> openMap = countryResorts.getOpenSortedByNames(country);
            Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> closedMap = countryResorts.getClosedSortedByNames(country);

            display(countryPanel, openMap, closedMap);

        });

    }

    private void display(CountryResortsPanel countryPanel, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> openMap, Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> closedMap){
        countryPanel.clearScrollContainer();

        countryPanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.OPEN));
        for(JPanel panel : openMap.get(Resort.OpenStatus.OPEN).values()){
            countryPanel.addToScrollContainer(panel);
        }

        countryPanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.WEEKEND));
        for(JPanel panel : openMap.get(Resort.OpenStatus.WEEKEND).values()){
            countryPanel.addToScrollContainer(panel);
        }

        countryPanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.TEMPCLOSED));
        for(JPanel panel : openMap.get(Resort.OpenStatus.TEMPCLOSED).values()){
            countryPanel.addToScrollContainer(panel);
        }

        countryPanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.CLOSE));
        for(JPanel panel : closedMap.get(Resort.OpenStatus.CLOSE).values()){
            countryPanel.addToScrollContainer(panel);
        }

        countryPanel.setScrollView();
    }

}
