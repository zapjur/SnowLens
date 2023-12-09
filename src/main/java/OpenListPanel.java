import javax.swing.*;

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

    public OpenListPanel(Resort resort){
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
