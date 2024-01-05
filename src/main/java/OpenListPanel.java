import javax.swing.*;
import java.awt.*;

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
    private ImageIcon starEmpty;
    private ImageIcon starFull;
    private FavoriteResorts favoriteResorts = FavoriteResorts.getInstance();
    private CountryResorts countryResorts = CountryResorts.getInstance();
    private Font font = new Font("Arial", Font.BOLD, 14);

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

        starEmpty = new ImageIcon(getClass().getResource("starEmpty.png"));
        starFull = new ImageIcon(getClass().getResource("starFull.png"));

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
            favoriteButton.setIcon(starFull);
        }
        else{
            favoriteButton.setIcon(starEmpty);
        }

        favoritePanel.setPreferredSize(new Dimension(100, 100));
        add(favoritePanel);
        favoriteButton.addActionListener(e -> {
            if(isFavorite){
                favoriteButton.setIcon(starEmpty);
                isFavorite = false;
                favoriteResorts.removeFavorite(resort);
                countryResorts.getPanel(resort.country(), resort.openStatus(), resort).unfavorite();
            }
            else{
                favoriteButton.setIcon(starFull);
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

    public void unfavorite(){
        isFavorite = false;
        favoriteButton.setIcon(starEmpty);
        System.out.println(isFavorite);
    }

}
