import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class sortButtonsPanel extends JPanel {

    private JButton sortByNames = new JButton();
    private JButton sortBySnowLast = new JButton();
    private JButton sortByCurrSnow = new JButton();
    private JButton sortByOpenDist = new JButton();
    private JButton sortByOpenLifts = new JButton();
    public sortButtonsPanel(MainFrame frame){
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
            Collections.sort(Country.COUNTRY_RESORTS.get(frame.getCurrCountry()), Comparator.comparing((Resort resort) -> {
                try {
                    return Float.parseFloat(resort.openDist());
                } catch (NumberFormatException ex) {
                    return Float.MIN_VALUE;
                }
            }).reversed());

            frame.removeFromScrollContainer();
            for(Resort resort : Country.COUNTRY_RESORTS.get(frame.getCurrCountry())){
                frame.addToScrollContainer(new OpenListPanel(resort));
            }
            frame.setScrollView();
        });

        sortByCurrSnow.addActionListener(e ->{
            Collections.sort(Country.COUNTRY_RESORTS.get(frame.getCurrCountry()), Comparator.comparing((Resort resort) -> {
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

            frame.removeFromScrollContainer();
            for(Resort resort : Country.COUNTRY_RESORTS.get(frame.getCurrCountry())){
                frame.addToScrollContainer(new OpenListPanel(resort));
            }
            frame.setScrollView();
        });

        sortByOpenLifts.addActionListener(e ->{
            Collections.sort(Country.COUNTRY_RESORTS.get(frame.getCurrCountry()), Comparator.comparing((Resort resort) -> {
                try {
                    String[] parts = resort.openLifts().split("/");
                    return Integer.parseInt(parts[0]);
                } catch (NumberFormatException ex) {
                    return Integer.MIN_VALUE;
                }
            }).reversed());

            frame.removeFromScrollContainer();
            for(Resort resort : Country.COUNTRY_RESORTS.get(frame.getCurrCountry())){
                frame.addToScrollContainer(new OpenListPanel(resort));
            }
            frame.setScrollView();
        });

        sortByNames.addActionListener(e ->{
            Collections.sort(Country.COUNTRY_RESORTS.get(frame.getCurrCountry()), Comparator.comparing(Resort::name));

            frame.removeFromScrollContainer();
            for(Resort resort : Country.COUNTRY_RESORTS.get(frame.getCurrCountry())){
                frame.addToScrollContainer(new OpenListPanel(resort));
            }
            frame.setScrollView();
        });

    }

}
