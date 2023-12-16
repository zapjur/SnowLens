import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {
    public MenuButton(Resort.Country country){
        setPreferredSize(new Dimension(800, 50));
        setIcon(new ImageIcon(getClass().getResource(country.getFlagUrl())));
        setText(country.getCountryName());
        setFont(new Font("Arial", Font.PLAIN, 14));
        setIconTextGap(10);

    }
}
