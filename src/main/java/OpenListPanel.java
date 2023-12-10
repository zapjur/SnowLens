import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
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

    public OpenListPanel(Resort resort){
        setSize(800,100);
        setPreferredSize(new Dimension(800, 100));
        setLayout(new FormLayout("pref:grow, pref, pref, pref, pref, pref", "pref"));
        CellConstraints cc = new CellConstraints();
        add(namePanel,cc.xy(1,1));
        add(snowLastPanel, cc.xy(2,1));
        add(currSnowPanel, cc.xy(3,1));
        add(trailsPanel, cc.xy(4,1));
        add(liftsPanel, cc.xy(5,1));
        add(favoritePanel, cc.xy(6,1));
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
