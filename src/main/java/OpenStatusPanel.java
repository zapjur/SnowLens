import javax.swing.*;
import java.awt.*;

public class OpenStatusPanel extends JPanel {

    public OpenStatusPanel(Resort.OpenStatus status){
        setSize(new Dimension(1000, 50));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel label = new JLabel();
        switch (status){
            case OPEN:
                label.setText("Open");
                label.setForeground(Color.GREEN);
                break;

            case CLOSE:
                label.setText("Closed");
                label.setForeground(Color.RED);
                break;

            case WEEKEND:
                label.setText("Weekends Only");
                label.setForeground(Color.GREEN);
                break;

            case TEMPCLOSED:
                label.setText("Temporarily Closed");
                label.setForeground(Color.ORANGE);
                break;
        }
        label.setBackground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 22));

        add(label, BorderLayout.CENTER);
    }
}
