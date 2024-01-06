import javax.swing.*;
import java.awt.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClosedListPanel extends JPanel {

    private JPanel namePanel = new JPanel();
    private JLabel nameLabel = new JLabel();
    private JLabel updateTimeLabel = new JLabel();
    private JPanel openDatePanel = new JPanel();
    private JLabel openDateLabel = new JLabel();
    private JPanel favoritePanel = new JPanel();
    private JPanel fill, fill1, fill2, fill3;
    private JButton favoriteButton = new JButton();
    private boolean isFavorite = false;
    private static final Logger logger = LogManager.getLogger(ClosedListPanel.class);
    private FavoriteResorts favoriteResorts = FavoriteResorts.getInstance();
    private CountryResorts countryResorts = CountryResorts.getInstance();
    private Font font = new Font("Arial", Font.BOLD, 14);
    private Color white = Color.WHITE;
    private Color gray = new Color(233, 236, 239);

    public ClosedListPanel(Resort resort){
        this.isFavorite = true;
        constructObject(resort);
    }
    public ClosedListPanel(Resort resort, boolean isFavorite){
        this.isFavorite = isFavorite;
        countryResorts.addResort(resort.country(), resort, this);
        constructObject(resort);
    }

    private void constructObject(Resort resort){

        setPreferredSize(new Dimension(1000, 100));
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        String starEmptyPath = "starEmpty.png";
        java.net.URL starEmpty = getClass().getResource(starEmptyPath);

        String starFullPath = "starFull.png";
        java.net.URL starFull = getClass().getResource(starFullPath);

        nameLabel.setFont(font);
        nameLabel.setBackground(Color.WHITE);
        updateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        updateTimeLabel.setBackground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBackground(Color.WHITE);
        namePanel.setPreferredSize(new Dimension(300, 100));
        namePanel.add(nameLabel);
        namePanel.add(updateTimeLabel);
        add(namePanel);

        fill = new JPanel();
        fill.setPreferredSize(new Dimension(100, 100));
        fill.setBackground(Color.WHITE);
        add(fill);

        fill1 = new JPanel();
        fill1.setPreferredSize(new Dimension(100, 100));
        fill1.setBackground(Color.WHITE);
        add(fill1);

        fill2 = new JPanel();
        fill2.setPreferredSize(new Dimension(100, 100));
        fill2.setBackground(Color.WHITE);
        add(fill2);

        fill3 = new JPanel();
        fill3.setPreferredSize(new Dimension(100, 100));
        fill3.setBackground(Color.WHITE);
        add(fill3);

        openDateLabel.setFont(font);
        openDateLabel.setBackground(Color.WHITE);
        openDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        openDatePanel.setLayout(new BoxLayout(openDatePanel, BoxLayout.PAGE_AXIS));
        openDatePanel.setBackground(Color.WHITE);
        openDatePanel.setPreferredSize(new Dimension(200, 100));
        openDatePanel.add(openDateLabel);
        add(openDatePanel);

        if(isFavorite){
            if(starFull != null){
                favoriteButton.setIcon(new ImageIcon(starFull));
            }
            else{
                logger.error("Can't find favorite button image: " + starFullPath);
            }
        }
        else{
            if(starEmpty != null){
                favoriteButton.setIcon(new ImageIcon(starEmpty));
            }
            else{
                logger.error("Can't find favorite button image: " + starEmptyPath);
            }
        }

        favoriteButton.setBackground(Color.WHITE);
        favoriteButton.setBorderPainted(false);
        favoriteButton.setFocusPainted(false);
        favoriteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        favoritePanel.setLayout(new BoxLayout(favoritePanel, BoxLayout.PAGE_AXIS));
        favoritePanel.setPreferredSize(new Dimension(100, 100));
        favoritePanel.setBackground(Color.WHITE);
        favoritePanel.add(favoriteButton);
        add(favoritePanel);

        favoriteButton.addActionListener(e -> {
            if(isFavorite){
                if(starEmpty != null){
                    favoriteButton.setIcon(new ImageIcon(starEmpty));
                }
                else{
                    logger.error("Can't find favorite button image: " + starEmptyPath);
                }
                isFavorite = false;
                favoriteResorts.removeFavorite(resort);
                countryResorts.getPanelClosed(resort.country(), resort).unfavorite(starEmpty, starEmptyPath);
            }
            else{
                if(starFull != null){
                    favoriteButton.setIcon(new ImageIcon(starFull));
                }
                else{
                    logger.error("Can't find favorite button image: " + starFullPath);
                }
                isFavorite = true;
                favoriteResorts.addFavoriteClosed(resort, new ClosedListPanel(resort));
            }
        });

        nameLabel.setText(resort.name());
        updateTimeLabel.setText(resort.updateTime());
        openDateLabel.setText(resort.openDate());
    }
    public void unfavorite(java.net.URL starEmpty, String starEmptyPath){
        isFavorite = false;
        if(starEmpty != null){
            favoriteButton.setIcon(new ImageIcon(starEmpty));
        }
        else{
            logger.error("Can't find favorite button image: " + starEmptyPath);
        }
    }

    public void whiteBackground(){
        changeBackground(white);
    }
    public void grayBackground(){
        changeBackground(gray);
    }

    private void changeBackground(Color color) {
        this.setBackground(color);
        this.namePanel.setBackground(color);
        this.nameLabel.setBackground(color);
        this.fill.setBackground(color);
        this.fill1.setBackground(color);
        this.fill2.setBackground(color);
        this.fill3.setBackground(color);
        this.openDatePanel.setBackground(color);
        this.favoritePanel.setBackground(color);
    }

}
