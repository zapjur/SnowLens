import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private boolean favoriteIsActive = false;
    private FavoriteResorts favoriteResorts = FavoriteResorts.getInstance();

    public MainFrame() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(1000, 800);
        setTitle("Snow Lens");
        setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null);

        favoriteResorts.setFavoriteSavedMap(StorageHandler.loadSetFromFile("data/favorites.json"));

        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        CountryResortsPanel favoritePanel = new CountryResortsPanel(Country.FAVORITE);
        cardPanel.add(favoritePanel, Country.FAVORITE.getCountryName());
        cardLayout.show(cardPanel, Country.FAVORITE.getCountryName());

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
            lastCountryPanel = country.getCountryName();
            menuIsActive = false;
        });
        return button;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

}