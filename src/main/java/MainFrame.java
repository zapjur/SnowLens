import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    private JPanel cardPanel;
    private JList<Resort> resortList;
    private JPanel upperPanel;
    private JPanel mainPanel;
    private JButton menuButton;
    private JPanel menuPanel;
    private JButton favoriteButton;
    private JPanel upperButtonPanel;
    private JPanel logoPanel;
    private JLabel logoLabel;
    private JPanel fillingPanel;
    private JScrollPane menuScrollPanel;
    private JPanel menuButtonPanel;
    private final String MENU_PANEL = "MenuPanel";
    private String lastCountryPanel = null;
    private boolean menuIsActive = false;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setTitle("Snow Lens");
        setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null);

        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        try {
            CountryResortsPanel countryResortsPanel = new CountryResortsPanel(Country.FRANCE);
            countryResortsPanel.addSortButtonsPanel();

            Map<Resort.OpenStatus, List<Resort>> map = InformationScraper.franceScraping();
            Country.COUNTRY_RESORTS.put(Country.FRANCE, map);

            List<Resort> openResorts = map.get(Resort.OpenStatus.OPEN);

            if(openResorts != null){
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new OpenStatusPanel("Open"));
                for(Resort resort : openResorts){
                    panel.add(new OpenListPanel(resort));
                }
                countryResortsPanel.addToScrollContainer(panel);
            }

            List<Resort> weekendResorts = map.get(Resort.OpenStatus.WEEKEND);
            if(weekendResorts != null){
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new OpenStatusPanel("Weekends Only"));
                for(Resort resort : weekendResorts){
                    panel.add(new OpenListPanel(resort));
                }
                countryResortsPanel.addToScrollContainer(panel);
            }

            List<Resort> tempclosedResorts = map.get(Resort.OpenStatus.TEMPCLOSED);
            if(tempclosedResorts != null){
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new OpenStatusPanel("Temporarily Closed"));
                for(Resort resort : tempclosedResorts){
                    panel.add(new OpenListPanel(resort));
                }
                countryResortsPanel.addToScrollContainer(panel);
            }

            List<Resort> closedResorts = map.get(Resort.OpenStatus.CLOSE);
            if(closedResorts != null){
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new OpenStatusPanel("Closed"));
                for(Resort resort : closedResorts){
                    panel.add(new ClosedListPanel(resort));
                }
                countryResortsPanel.addToScrollContainer(panel);
            }

            countryResortsPanel.setScrollView();
            cardPanel.add(countryResortsPanel, Country.FRANCE.getCountryName());
            cardLayout.show(cardPanel, Country.FRANCE.getCountryName());
            menuIsActive = false;
            lastCountryPanel = Country.FRANCE.getCountryName();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        menuButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        for(Country country : Country.values()){
            MenuButton button = getMenuButton(country, cardLayout, cardPanel);
            menuButtonPanel.add(button, gbc);
        }
        menuScrollPanel.setViewportView(menuButtonPanel);

        menuPanel.add(menuScrollPanel);
        cardPanel.add(menuPanel, MENU_PANEL);

        mainPanel.add(upperPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        menuButton.addActionListener(e -> {
            if(!menuIsActive){
                cardLayout.show(cardPanel, MENU_PANEL);
                menuIsActive = true;
            }
            else{
                cardLayout.show(cardPanel, lastCountryPanel);
                menuIsActive = false;
            }

        });
    }

    private MenuButton getMenuButton(Country country, CardLayout cardLayout, JPanel cardPanel) {
        MenuButton button = new MenuButton(country);
        button.addActionListener(new DisplayResortActionListener(country, cardLayout, cardPanel, lastCountryPanel, menuIsActive));
        button.addActionListener(e -> {
            lastCountryPanel = country.getCountryName();
            menuIsActive = false;
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

}