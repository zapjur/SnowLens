import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import net.miginfocom.swing.MigLayout;
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
        setLayout(new MigLayout("wrap 6", "[][][][][][]", "100px"));
        add(namePanel, "cell 1 1");
        add(snowLastPanel, "cell 2 1");
        add(currSnowPanel, "cell 3 1");
        add(trailsPanel, "cell 4 1");
        add(liftsPanel, "cell 5 1");
        add(favoritePanel, "cell 6 1");

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
