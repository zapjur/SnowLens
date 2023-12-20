import javax.swing.*;
import java.awt.*;

public class OpenStatusPanel extends JPanel {

    public OpenStatusPanel(String status){
        setSize(new Dimension(1000, 50));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel label = new JLabel(status);
        switch (status){
            case "Open":
                label.setForeground(Color.GREEN);
                break;

            case "Closed":
                label.setForeground(Color.RED);
                break;

            case "Weekends Only":
                label.setForeground(Color.GREEN);
                break;

            case "Temporarily Closed":
                label.setForeground(Color.ORANGE);
                break;
        }
        label.setBackground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 22));

        add(label, BorderLayout.CENTER);
    }
}
