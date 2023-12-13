import javax.swing.*;
import java.awt.*;

public class MenuListPanel extends JPanel {
    private JButton countryButton;
    private JPanel panel1;

    public MenuListPanel(Resort.Country country){
        setSize(1000, 50);
        countryButton.setPreferredSize(new Dimension(800, 50));
        countryButton.setIcon(new ImageIcon(getClass().getResource(country.getFlagUrl())));
        countryButton.setText(country.getCountryName());
        countryButton.setFont(new Font("Arial", Font.PLAIN, 14));
        countryButton.setIconTextGap(10);
        add(countryButton, BorderLayout.CENTER);
    }
}
