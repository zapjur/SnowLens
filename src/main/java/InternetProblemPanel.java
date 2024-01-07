import javax.swing.*;
import java.awt.*;

public class InternetProblemPanel extends JPanel {

    public InternetProblemPanel(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel label = new JLabel("Internet Problem");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.BLACK);
        label.setBackground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
