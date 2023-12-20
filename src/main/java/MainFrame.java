import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    private JPanel cardPanel;
    private JPanel defaultPanel;
    private JScrollPane scrollDefaultPanel;
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
    private JPanel scrollContainer;
    private JList menuList;
    private Country currCountry;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setTitle("Snow Lens");
        setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null);

        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.Y_AXIS));
        try {
            Map<Resort.OpenStatus, List<Resort>> map = InformationScraper.franceScraping();
            currCountry = Country.ITALY;
            Country.COUNTRY_RESORTS.put(Country.ITALY, map);
            List<Resort> openResorts = map.get(Resort.OpenStatus.OPEN);
            if(openResorts != null){
                for(Resort resort : openResorts){
                    scrollContainer.add(new OpenListPanel(resort));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scrollDefaultPanel.setViewportView(scrollContainer);

        menuButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        for(Country country : Country.values()){
            MenuButton button = getMenuButton(country, cardLayout);
            menuButtonPanel.add(button, gbc);
        }
        menuScrollPanel.setViewportView(menuButtonPanel);

        scrollDefaultPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        defaultPanel.add(scrollDefaultPanel, BorderLayout.CENTER);
        defaultPanel.add(new sortButtonsPanel(this), BorderLayout.NORTH);

        menuPanel.add(menuScrollPanel);
        cardPanel.add(menuPanel);

        mainPanel.add(upperPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        menuButton.addActionListener(e -> {
            cardLayout.next(cardPanel);
        });
    }

    private MenuButton getMenuButton(Country country, CardLayout cardLayout) {
        MenuButton button = new MenuButton(country);
        button.addActionListener(e->{
            try {
                if(!Country.COUNTRY_RESORTS.containsKey(country)){
                    Country.COUNTRY_RESORTS.put(country, country.getResortList());
                }
                scrollContainer.removeAll();

                List<Resort> openResorts = Country.COUNTRY_RESORTS.get(country).get(Resort.OpenStatus.OPEN);
                if(openResorts != null){
                    for(Resort resort : openResorts){
                        scrollContainer.add(new OpenListPanel(resort));
                    }
                }
                scrollDefaultPanel.setViewportView(scrollContainer);
            } catch(IOException es){
                es.printStackTrace();
            }
            currCountry = country;
            cardLayout.next(cardPanel);
        });
        return button;
    }

    public void addToScrollContainer(JPanel panel){
        scrollContainer.add(panel);
    }
    public void removeFromScrollContainer(){
        scrollContainer.removeAll();
    }
    public void setScrollView(){
        scrollDefaultPanel.setViewportView(scrollContainer);
    }
    public Country getCurrCountry(){
        return currCountry;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

}