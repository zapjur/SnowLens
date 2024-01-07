import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class DisplayResortActionListener implements ActionListener {

    private Country country;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public DisplayResortActionListener(Country country, CardLayout cardLayout, JPanel cardPanel){
        this.country = country;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(!Country.addedCards.contains(country)) {
                if (!Country.COUNTRY_RESORTS.containsKey(country)) {
                    Country.COUNTRY_RESORTS.put(country, country.getResortList());
                }
                if(!InternetProblemHandler.scrappingStatus()){
                    cardPanel.add(InternetProblemHandler.getInternetProblemPanel(), InternetProblemHandler.getPanelName());
                    cardLayout.show(cardPanel, InternetProblemHandler.getPanelName());
                    Country.addedCards.remove(country);
                    Country.COUNTRY_RESORTS.remove(country);
                    InternetProblemHandler.scrappingSuccessful();
                    return;
                }

                CountryResortsPanel countryResortsPanel = new CountryResortsPanel(country);
                countryResortsPanel.addSortButtonsPanel();
                List<Resort> openResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.OPEN);
                if (openResorts != null) {
                    JPanel panel = new JPanel();
                    panel.add(new OpenStatusPanel(Resort.OpenStatus.OPEN));
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    int i = 0;
                    for (Resort resort : openResorts) {
                        OpenListPanel pan = new OpenListPanel(resort, resort.isFavorite());
                        if(i % 2 == 0) pan.whiteBackground();
                        else pan.grayBackground();
                        panel.add(pan);
                        i++;
                    }
                    countryResortsPanel.addToScrollContainer(panel);
                }

                List<Resort> weekendResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.WEEKEND);
                if (weekendResorts != null) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.add(new OpenStatusPanel(Resort.OpenStatus.WEEKEND));
                    int i = 0;
                    for (Resort resort : weekendResorts) {
                        OpenListPanel pan = new OpenListPanel(resort, resort.isFavorite());
                        if(i % 2 == 0) pan.whiteBackground();
                        else pan.grayBackground();
                        panel.add(pan);
                        i++;
                    }
                    countryResortsPanel.addToScrollContainer(panel);
                }

                List<Resort> tempclosedResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.TEMPCLOSED);
                if (tempclosedResorts != null) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.add(new OpenStatusPanel(Resort.OpenStatus.TEMPCLOSED));
                    int i = 0;
                    for (Resort resort : tempclosedResorts) {
                        OpenListPanel pan = new OpenListPanel(resort, resort.isFavorite());
                        if(i % 2 == 0) pan.whiteBackground();
                        else pan.grayBackground();
                        panel.add(pan);
                        i++;
                    }
                    countryResortsPanel.addToScrollContainer(panel);
                }

                List<Resort> closedResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.CLOSE);
                if (closedResorts != null) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.add(new OpenStatusPanel(Resort.OpenStatus.CLOSE));
                    int i = 0;
                    for (Resort resort : closedResorts) {
                        ClosedListPanel pan = new ClosedListPanel(resort, resort.isFavorite());
                        if(i % 2 == 0) pan.whiteBackground();
                        else pan.grayBackground();
                        panel.add(pan);
                        i++;
                    }
                    countryResortsPanel.addToScrollContainer(panel);
                }

                countryResortsPanel.setScrollView();
                cardPanel.add(countryResortsPanel, country.getCountryName());
                Country.addedCards.add(country);
            }

            cardLayout.show(cardPanel, country.getCountryName());


        } catch(IOException es){
            es.printStackTrace();
        }
    }
}
