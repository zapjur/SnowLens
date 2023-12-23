import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortButtonsPanel extends JPanel {

    private JButton sortByNames = new JButton();
    private JButton sortBySnowLast = new JButton();
    private JButton sortByCurrSnow = new JButton();
    private JButton sortByOpenDist = new JButton();
    private JButton sortByOpenLifts = new JButton();


    public SortButtonsPanel(Country country, CountryResortsPanel countryPanel){
        setSize(new Dimension(1000, 50));
        setLayout(new GridLayout(1,5));


        List<JButton> buttons = List.of(sortByNames, sortBySnowLast, sortByCurrSnow, sortByOpenDist, sortByOpenLifts);

        for(JButton button : buttons){
            button.setPreferredSize(new Dimension(150, 50));
            add(button);
        }
        sortByOpenLifts.setText("Lifts");
        sortByOpenDist.setText("Distance");
        sortByCurrSnow.setText("Snow");
        sortBySnowLast.setText("Last Snowfall");
        sortByNames.setText("Name");

        sortByOpenDist.addActionListener(e ->{
            Collections.sort(Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.OPEN), Comparator.comparing((Resort resort) -> {
                try {
                    return Float.parseFloat(resort.openDist());
                } catch (NumberFormatException ex) {
                    return Float.MIN_VALUE;
                }
            }).reversed());
            displayResorts(country, countryPanel);
        });

        sortByCurrSnow.addActionListener(e ->{
            Collections.sort(Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.OPEN), Comparator.comparing((Resort resort) -> {
                try {
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
            }).reversed());
            displayResorts(country, countryPanel);
        });

        sortByOpenLifts.addActionListener(e ->{
            Collections.sort(Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.OPEN), Comparator.comparing((Resort resort) -> {
                try {
                    String[] parts = resort.openLifts().split("/");
                    return Integer.parseInt(parts[0]);
                } catch (NumberFormatException ex) {
                    return Integer.MIN_VALUE;
                }
            }).reversed());
            displayResorts(country, countryPanel);
        });

        sortByNames.addActionListener(e ->{
            Collections.sort(Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.OPEN), Comparator.comparing(Resort::name));

            displayResorts(country, countryPanel);
        });

    }

    private void displayResorts(Country country, CountryResortsPanel countryPanel){
        countryPanel.clearScrollContainer();
        countryPanel.addToScrollContainer(new OpenStatusPanel("Open"));
        for(Resort resort : Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.OPEN)){
            countryPanel.addToScrollContainer(new OpenListPanel(resort));
        }

        countryPanel.addToScrollContainer(new OpenStatusPanel("Weekends Only"));
        for(Resort resort : Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.WEEKEND)){
            countryPanel.addToScrollContainer(new OpenListPanel(resort));
        }

        countryPanel.addToScrollContainer(new OpenStatusPanel("Temporarily Closed"));
        for(Resort resort : Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.TEMPCLOSED)){
            countryPanel.addToScrollContainer(new OpenListPanel(resort));
        }

        countryPanel.addToScrollContainer(new OpenStatusPanel("Closed"));
        for(Resort resort : Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.CLOSE)){
            countryPanel.addToScrollContainer(new ClosedListPanel(resort));
        }

        countryPanel.setScrollView();
    }

}
