package GUI;

import javax.swing.*;
import java.awt.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Data.Country;

public class MenuButton extends JButton {
    private static final Logger logger = LogManager.getLogger(MenuButton.class);
    public MenuButton(Country country){
        setPreferredSize(new Dimension(800, 50));
        String iconPath = country.getFlagUrl();
        java.net.URL icon = getClass().getResource(iconPath);
        if(icon != null){
            setIcon(new ImageIcon(icon));
        }
        else{
            logger.error("Can't find image: " + iconPath);
        }
        setText(country.getCountryName());
        setFont(new Font("Arial", Font.PLAIN, 14));
        setIconTextGap(10);

    }
}
