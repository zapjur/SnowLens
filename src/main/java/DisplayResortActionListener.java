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
        if(country.equals(Country.FAVORITE) && Country.addedCards.contains(Country.FAVORITE)){
            Country.addedCards.remove(Country.FAVORITE);
            return;
        }
        if(Country.addedCards.contains(country) && !country.equals(Country.FAVORITE)) {
            cardLayout.show(cardPanel, country.getCountryName());
            return;
        }
        try {

                if (!Country.COUNTRY_RESORTS.containsKey(country) && !country.equals(Country.FAVORITE)) {
                    Country.COUNTRY_RESORTS.put(country, country.getResortList());
                }

                CountryResortsPanel countryResortsPanel = new CountryResortsPanel(country);
                countryResortsPanel.addSortButtonsPanel();
                List<Resort> openResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.OPEN);
                if (openResorts != null) {
                    JPanel panel = new JPanel();
                    panel.add(new OpenStatusPanel("Open"));
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    for (Resort resort : openResorts) {
                        panel.add(new OpenListPanel(resort));
                    }
                    countryResortsPanel.addToScrollContainer(panel);
                }

                List<Resort> weekendResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.WEEKEND);
                if (weekendResorts != null) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.add(new OpenStatusPanel("Weekends Only"));
                    for (Resort resort : weekendResorts) {
                        panel.add(new OpenListPanel(resort));
                    }
                    countryResortsPanel.addToScrollContainer(panel);
                }

                List<Resort> tempclosedResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.TEMPCLOSED);
                if (tempclosedResorts != null) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.add(new OpenStatusPanel("Temporarily Closed"));
                    for (Resort resort : tempclosedResorts) {
                        panel.add(new OpenListPanel(resort));
                    }
                    countryResortsPanel.addToScrollContainer(panel);
                }

                List<Resort> closedResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.CLOSE);
                if (closedResorts != null) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.add(new OpenStatusPanel("Closed"));
                    for (Resort resort : closedResorts) {
                        panel.add(new ClosedListPanel(resort));
                    }
                    countryResortsPanel.addToScrollContainer(panel);
                }
                countryResortsPanel.setScrollView();
                cardPanel.add(countryResortsPanel, country.getCountryName());
                Country.addedCards.add(country);


            cardLayout.show(cardPanel, country.getCountryName());


        } catch(IOException es){
            es.printStackTrace();
        }
    }
}
