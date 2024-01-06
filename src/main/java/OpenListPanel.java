import javax.swing.*;
import java.awt.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenListPanel extends JPanel {
    private JPanel namePanel;
    private JPanel snowLastPanel;
    private JPanel currSnowPanel;
    private JPanel trailsPanel;
    private JPanel liftsPanel;
    private JPanel favoritePanel;
    private JButton favoriteButton;
    private JLabel nameLabel;
    private JLabel updateTimeLabel;
    private JLabel snowLastLabel;
    private JLabel currSnowLabel;
    private JLabel snowTypeLabel;
    private JLabel trailsDistLabel;
    private JLabel trailsPerLabel;
    private JLabel liftsLabel;
    private boolean isFavorite = false;
    private static final Logger logger = LogManager.getLogger(OpenListPanel.class);
    private FavoriteResorts favoriteResorts = FavoriteResorts.getInstance();
    private CountryResorts countryResorts = CountryResorts.getInstance();
    private Font font = new Font("Arial", Font.BOLD, 14);
    private Color white = Color.WHITE;
    private Color gray = new Color(233, 236, 239);

    public OpenListPanel(Resort resort){
        this.isFavorite = true;
        constructObject(resort);
    }
    public OpenListPanel(Resort resort, boolean isFavorite){
        this.isFavorite = isFavorite;
        constructObject(resort);
        countryResorts.addResort(resort.country(), resort, this);
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
        updateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.PAGE_AXIS));
        namePanel.setPreferredSize(new Dimension(300, 100));
        namePanel.add(nameLabel);
        namePanel.add(updateTimeLabel);
        add(namePanel);

        snowLastPanel.setPreferredSize(new Dimension(100, 100));
        add(snowLastPanel);

        currSnowLabel.setFont(font);
        snowTypeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        currSnowLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        snowTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currSnowPanel.setLayout(new BoxLayout(currSnowPanel, BoxLayout.PAGE_AXIS));
        currSnowPanel.setPreferredSize(new Dimension(200, 100));
        currSnowPanel.add(currSnowLabel);
        currSnowPanel.add(snowTypeLabel);
        add(currSnowPanel);

        trailsDistLabel.setFont(font);
        trailsPerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        trailsDistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        trailsPerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        trailsPanel.setLayout(new BoxLayout(trailsPanel, BoxLayout.PAGE_AXIS));
        trailsPanel.setPreferredSize(new Dimension(200, 100));
        trailsPanel.add(trailsDistLabel);
        trailsPanel.add(trailsPerLabel);
        add(trailsPanel);

        liftsPanel.setPreferredSize(new Dimension(100, 100));
        add(liftsPanel);

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

        favoritePanel.setPreferredSize(new Dimension(100, 100));
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
                countryResorts.getPanel(resort.country(), resort.openStatus(), resort).unfavorite(starEmpty, starEmptyPath);
            }
            else{
                if(starFull != null){
                    favoriteButton.setIcon(new ImageIcon(starFull));
                }
                else{
                    logger.error("Can't find favorite button image: " + starFullPath);
                }
                isFavorite = true;
                favoriteResorts.addFavorite(resort, new OpenListPanel(resort));
            }
        });

        nameLabel.setText(resort.name());
        updateTimeLabel.setText(resort.updateTime());
        snowLastLabel.setText(resort.snowLast24());
        snowLastLabel.setFont(font);
        currSnowLabel.setText(resort.currSnow());
        snowTypeLabel.setText(resort.snowType());
        trailsDistLabel.setText(resort.openTrailsDist());
        trailsPerLabel.setText(resort.openTrailsPer());
        liftsLabel.setText(resort.openLifts());
        liftsLabel.setFont(font);
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
        this.snowLastPanel.setBackground(color);
        this.snowLastLabel.setBackground(color);
        this.currSnowPanel.setBackground(color);
        this.currSnowLabel.setBackground(color);
        this.snowTypeLabel.setBackground(color);
        this.trailsPanel.setBackground(color);
        this.trailsDistLabel.setBackground(color);
        this.trailsPerLabel.setBackground(color);
        this.liftsPanel.setBackground(color);
        this.liftsLabel.setBackground(color);
        this.favoritePanel.setBackground(color);
    }

}
