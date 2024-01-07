import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import javax.swing.WindowConstants;
import java.awt.event.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainFrame extends JFrame {
    private JPanel cardPanel;
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
    private JLabel flagIconLabel;
    private static final Logger logger = LogManager.getLogger(MainFrame.class);
    private final String MENU_PANEL = "MenuPanel";
    private String lastCountryPanel = null;
    private boolean menuIsActive = false;
    private boolean favoriteIsActive = false;
    private FavoriteResorts favoriteResorts = FavoriteResorts.getInstance();
    private CountryResortsPanel favoritePanel;

    public MainFrame() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(1000, 800);
        setTitle("Snow Lens");
        setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null);

        String logoPath = "logoSmall.png";
        java.net.URL logo = getClass().getResource(logoPath);
        if(logo != null){
            logoLabel.setIcon(new ImageIcon(logo));
        }
        else{
            logger.error("Can't find logo: " + logoPath);
        }

        String favoritePath = "starFull.png";
        java.net.URL favorite = getClass().getResource(favoritePath);
        if(favorite != null){
            favoriteButton.setIcon(new ImageIcon(favorite));
        }
        else{
            logger.error("Can't find favorite button image: " + favoritePath);
        }

        String menuPath = "menu.png";
        java.net.URL menu = getClass().getResource(menuPath);
        if(menu != null){
            menuButton.setIcon(new ImageIcon(menu));
        }
        else{
            logger.error("Can't find menu button image: " + menuPath);
        }

        favoriteResorts.setFavoriteSavedMap(StorageHandler.loadSetFromFile("data/favorites.json"));

        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        favoritePanel = new CountryResortsPanel(Country.FAVORITE);
        displayStartFavorite(cardLayout);

        menuButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        for(Country country : Country.values()){
            if(country.equals(Country.FAVORITE)) continue;
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
            if(menuIsActive){
                cardLayout.show(cardPanel, lastCountryPanel);
                menuIsActive = false;
            }
            else{
                cardLayout.show(cardPanel, MENU_PANEL);
                menuIsActive = true;
                favoriteIsActive = false;
            }

        });

        favoriteButton.addActionListener(new DisplayFavoriteButtonActionListener(favoritePanel));
        favoriteButton.addActionListener(e -> {
            if(favoriteIsActive){
                cardLayout.show(cardPanel, lastCountryPanel);
                favoriteIsActive = false;
            }
            else{
                cardLayout.show(cardPanel, Country.FAVORITE.getCountryName());
                favoriteIsActive = true;
                menuIsActive = false;
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                StorageHandler.saveSetToFile(favoriteResorts.getFavoriteSavedMap(), "data/favorites.json");
                System.exit(0);
            }
        });
    }

    private MenuButton getMenuButton(Country country, CardLayout cardLayout, JPanel cardPanel) {
        MenuButton button = new MenuButton(country);
        button.addActionListener(new DisplayResortActionListener(country, cardLayout, cardPanel));
        button.addActionListener(e -> {
            String iconPath = country.getFlagUrl();
            java.net.URL icon = getClass().getResource(iconPath);
            if(icon != null){
                flagIconLabel.setIcon(new ImageIcon(icon));
            }
            else{
                logger.error("Can't find image: " + iconPath);
            }
            lastCountryPanel = country.getCountryName();
            menuIsActive = false;
        });
        return button;
    }

    private void displayStartFavorite(CardLayout cardLayout){
        favoriteResorts.scrapStartFavorite();

        if(!InternetProblemHandler.scrappingStatus()){
            cardPanel.add(InternetProblemHandler.getInternetProblemPanel(), InternetProblemHandler.getPanelName());
            cardLayout.show(cardPanel, InternetProblemHandler.getPanelName());
            InternetProblemHandler.scrappingSuccessful();
            return;
        }

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
        cardPanel.add(favoritePanel, Country.FAVORITE.getCountryName());
        cardLayout.show(cardPanel, Country.FAVORITE.getCountryName());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

}