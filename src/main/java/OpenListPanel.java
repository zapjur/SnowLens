import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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

    public OpenListPanel(Resort resort){
        if(Country.COUNTRY_RESORTS.get(Country.FAVORITE) != null && Country.COUNTRY_RESORTS.get(Country.FAVORITE).get(resort.openStatus()).contains(resort)){
            isFavorite = true;
        }

        setPreferredSize(new Dimension(1000, 100));
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        starEmpty = new ImageIcon(getClass().getResource("starEmpty.png"));
        starFull = new ImageIcon(getClass().getResource("starFull.png"));

        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
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

        currSnowLabel.setFont(new Font("Arial", Font.BOLD, 14));
        snowTypeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        currSnowLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        snowTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currSnowPanel.setLayout(new BoxLayout(currSnowPanel, BoxLayout.PAGE_AXIS));
        currSnowPanel.setPreferredSize(new Dimension(200, 100));
        currSnowPanel.add(currSnowLabel);
        currSnowPanel.add(snowTypeLabel);
        add(currSnowPanel);

        trailsDistLabel.setFont(new Font("Arial", Font.BOLD, 14));
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

                if(Country.COUNTRY_RESORTS.containsKey(Country.FAVORITE) && Country.COUNTRY_RESORTS.get(Country.FAVORITE).containsKey(resort.openStatus())){
                    Country.COUNTRY_RESORTS.get(Country.FAVORITE).get(resort.openStatus()).remove(resort);
                }


            }
            else{
                favoriteButton.setIcon(starFull);
                isFavorite = true;

                if(!Country.COUNTRY_RESORTS.containsKey(Country.FAVORITE)){
                    Country.COUNTRY_RESORTS.put(Country.FAVORITE, new HashMap<Resort.OpenStatus, List<Resort>>());
                }

                if(!Country.COUNTRY_RESORTS.get(Country.FAVORITE).containsKey(resort.openStatus())){
                    Country.COUNTRY_RESORTS.get(Country.FAVORITE).put(resort.openStatus(), new ArrayList<Resort>());
                }

                Country.COUNTRY_RESORTS.get(Country.FAVORITE).get(resort.openStatus()).add(resort);

            }
        });

        nameLabel.setText(resort.name());
        updateTimeLabel.setText(resort.updateTime());
        snowLastLabel.setText(resort.snowLast24());
        currSnowLabel.setText(resort.currSnow());
        snowTypeLabel.setText(resort.snowType());
        trailsDistLabel.setText(resort.openTrailsDist());
        trailsPerLabel.setText(resort.openTrailsPer());
        liftsLabel.setText(resort.openLifts());

    }
}
