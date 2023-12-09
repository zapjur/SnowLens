import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.io.File;

public class MainFrame extends JFrame {
    private JPanel cardPanel;
    private JPanel defaultPanel;
    private JScrollPane scrollDefaultPanel;
    private JList<Resort> resortList;
    private JPanel upperPanel;
    private JPanel mainPanel;
    private JButton menuButton;

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

        try{
            BufferedImage menuImage = ImageIO.read(new File("graphics/icons8-menu-60.png"));
            ImageIcon menuIcon = new ImageIcon(menuImage);
            menuButton.setBorderPainted(false);
            menuButton.setIcon(menuIcon);

        }catch (IOException e){
            e.printStackTrace();
        }

        JScrollPane scrollDefaultPanel = new JScrollPane(resortList);
        scrollDefaultPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        defaultPanel.add(scrollDefaultPanel);
        cardPanel.add(defaultPanel);

        mainPanel.add(upperPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);


        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    public static class ResortRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Resort) {
                Resort resort = (Resort) value;
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(800, 100));
                panel.setLayout(new FlowLayout(FlowLayout.CENTER));
                panel.add(new JLabel(resort.name()));
                panel.add(new JLabel(resort.country().toString()));
                panel.add(new JLabel(resort.openStatus().toString()));
                panel.add(new JLabel(resort.currSnow()));
                panel.add(new JLabel(resort.snowType()));

                if (isSelected) {
                    panel.setBackground(list.getSelectionBackground());
                    panel.setForeground(list.getSelectionForeground());
                } else {
                    panel.setBackground(list.getBackground());
                    panel.setForeground(list.getForeground());
                }

                return panel;
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
}
