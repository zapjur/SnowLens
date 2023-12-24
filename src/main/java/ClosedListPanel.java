import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClosedListPanel extends JPanel {

    private JPanel namePanel = new JPanel();
    private JLabel nameLabel = new JLabel();
    private JLabel updateTimeLabel = new JLabel();
    private JPanel openDatePanel = new JPanel();
    private JLabel openDateLabel = new JLabel();
    private JPanel favoritePanel = new JPanel();
    private JButton favoriteButton = new JButton();
    private boolean isFavorite = false;
    private ImageIcon starEmpty;
    private ImageIcon starFull;
    public ClosedListPanel(Resort resort){
        if(Country.COUNTRY_RESORTS.get(Country.FAVORITE) != null && Country.COUNTRY_RESORTS.get(Country.FAVORITE).get(resort.openStatus()).contains(resort)){
            isFavorite = true;
        }
        setPreferredSize(new Dimension(1000, 100));
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        starEmpty = new ImageIcon(getClass().getResource("starEmpty.png"));
        starFull = new ImageIcon(getClass().getResource("starFull.png"));

        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
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

        JPanel fill = new JPanel();
        fill.setPreferredSize(new Dimension(100, 100));
        fill.setBackground(Color.WHITE);
        add(fill);

        JPanel fill1 = new JPanel();
        fill1.setPreferredSize(new Dimension(100, 100));
        fill1.setBackground(Color.WHITE);
        add(fill1);

        JPanel fill2 = new JPanel();
        fill2.setPreferredSize(new Dimension(100, 100));
        fill2.setBackground(Color.WHITE);
        add(fill2);

        JPanel fill3 = new JPanel();
        fill3.setPreferredSize(new Dimension(100, 100));
        fill3.setBackground(Color.WHITE);
        add(fill3);

        openDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        openDateLabel.setBackground(Color.WHITE);
        openDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        openDatePanel.setLayout(new BoxLayout(openDatePanel, BoxLayout.PAGE_AXIS));
        openDatePanel.setBackground(Color.WHITE);
        openDatePanel.setPreferredSize(new Dimension(200, 100));
        openDatePanel.add(openDateLabel);
        add(openDatePanel);

        if(isFavorite){
            favoriteButton.setIcon(starFull);
        }
        else{
            favoriteButton.setIcon(starEmpty);
        }

        favoriteButton.setBackground(Color.WHITE);
        favoriteButton.setBorderPainted(false);
        favoriteButton.setFocusPainted(false);
        favoritePanel.setPreferredSize(new Dimension(100, 100));
        favoritePanel.setBackground(Color.WHITE);
        favoritePanel.add(favoriteButton);
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
        openDateLabel.setText(resort.openDate());
    }
}
