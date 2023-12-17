import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

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
    private JList menuList;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setTitle("Snow Lens");
        setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null);

        resortList.setCellRenderer(new ResortRenderer());
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        try {
            List<Resort> resorts = InformationScraper.italyScraping();
            Country.COUNTRY_RESORTS.put(Country.ITALY, resorts);
            DefaultListModel<Resort> listModel = new DefaultListModel<>();
            listModel.addAll(resorts);
            resortList.setModel(listModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        defaultPanel.add(scrollDefaultPanel);

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
                DefaultListModel<Resort> listModel = new DefaultListModel<>();
                listModel.addAll(Country.COUNTRY_RESORTS.get(country));
                resortList.setModel(listModel);
            } catch(IOException es){
                es.printStackTrace();
            }
            cardLayout.next(cardPanel);
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    public static class ResortRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Resort) {
                Resort resort = (Resort) value;
                OpenListPanel panel = new OpenListPanel(resort);

                panel.setBackground(list.getBackground());
                panel.setForeground(list.getForeground());

                return panel;
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

}