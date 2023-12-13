import javax.swing.*;
import java.awt.*;

public class MenuListPanel extends JPanel {
    private JButton countryButton;
    private JPanel panel1;

    public MenuListPanel(Resort.Country country){
        setSize(1000, 50);
        countryButton.setPreferredSize(new Dimension(1000, 50));
        countryButton.setIcon(new ImageIcon(country.getFlagUrl()));
        countryButton.setText(country.getCountryName());
        countryButton.setFont(new Font("Arial", Font.PLAIN, 14));
        add(countryButton);
    }
}
