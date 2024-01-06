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
        setBackground(Color.WHITE);


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

        sortBySnowLast.addActionListener(e -> {

            Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> openMap = countryResorts.getOpenSortedByLastSnow(country);
            Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> closedMap = countryResorts.getCountryMapClosed(country);

            display(countryPanel, openMap, closedMap);

        });

    }

    private void display(CountryResortsPanel countryPanel, Map<Resort.OpenStatus, Map<Resort, OpenListPanel>> openMap, Map<Resort.OpenStatus, Map<Resort, ClosedListPanel>> closedMap){
        countryPanel.clearScrollContainer();
        int i = 0;
        countryPanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.OPEN));
        for(OpenListPanel panel : openMap.get(Resort.OpenStatus.OPEN).values()){
            if(i % 2 == 0) panel.whiteBackground();
            else panel.grayBackground();
            countryPanel.addToScrollContainer(panel);
            i++;
        }
        i = 0;
        countryPanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.WEEKEND));
        for(OpenListPanel panel : openMap.get(Resort.OpenStatus.WEEKEND).values()){
            if(i % 2 == 0) panel.whiteBackground();
            else panel.grayBackground();
            countryPanel.addToScrollContainer(panel);
            i++;
        }

        i = 0;
        countryPanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.TEMPCLOSED));
        for(OpenListPanel panel : openMap.get(Resort.OpenStatus.TEMPCLOSED).values()){
            if(i % 2 == 0) panel.whiteBackground();
            else panel.grayBackground();
            countryPanel.addToScrollContainer(panel);
            i++;
        }
        i = 0;
        countryPanel.addToScrollContainer(new OpenStatusPanel(Resort.OpenStatus.CLOSE));
        for(ClosedListPanel panel : closedMap.get(Resort.OpenStatus.CLOSE).values()){
            if(i % 2 == 0) panel.whiteBackground();
            else panel.grayBackground();
            countryPanel.addToScrollContainer(panel);
            i++;
        }

        countryPanel.setScrollView();
    }

}
