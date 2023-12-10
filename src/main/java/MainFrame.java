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

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setTitle("Snow Lens");
        setBackground(Color.WHITE);
        setResizable(false);
        setLocationRelativeTo(null);

        resortList.setCellRenderer(new ResortRenderer());

        try {
            List<Resort> resorts = InformationScraper.polandScraping();
            DefaultListModel<Resort> listModel = new DefaultListModel<>();
            listModel.addAll(resorts);
            resortList.setModel(listModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        JScrollPane scrollDefaultPanel = new JScrollPane(resortList);
        scrollDefaultPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        defaultPanel.add(scrollDefaultPanel);

        mainPanel.add(upperPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        menuButton.addActionListener(e -> {
            cardLayout.next(cardPanel);
        });
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
