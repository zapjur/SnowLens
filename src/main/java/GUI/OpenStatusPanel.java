package GUI;

import javax.swing.*;
import java.awt.*;
import Data.Resort;

public class OpenStatusPanel extends JPanel {

    Font font = new Font("Arial", Font.BOLD, 22);
    public OpenStatusPanel(Resort.OpenStatus status) {
        setSize(new Dimension(1000, 50));
        setPreferredSize(new Dimension(1000, 50));
        setMaximumSize(new Dimension(1000, 50));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        BorderedLabel blabel;
        switch (status) {
            case OPEN:
                blabel = new BorderedLabel("Open", Color.WHITE, Color.BLACK, font);
                add(blabel);
                setBackground(new Color(101, 183, 65));
                break;

            case CLOSE:
                blabel = new BorderedLabel("Closed", Color.WHITE, Color.BLACK, font);
                add(blabel);
                setBackground(new Color(199, 0, 57));
                break;

            case WEEKEND:
                blabel = new BorderedLabel("Weekends Only", Color.WHITE, Color.BLACK, font);
                add(blabel);
                setBackground(new Color(154, 222, 123));
                break;

            case TEMPCLOSED:
                blabel = new BorderedLabel("Temporarily Closed", Color.WHITE, Color.BLACK, font);
                add(blabel);
                setBackground(new Color(255, 181, 52));
                break;
        }

    }

    public class BorderedLabel extends JComponent {
        private final String text;
        private final Color textColor;
        private final Color borderColor;
        private final Font font;

        public BorderedLabel(String text, Color textColor, Color borderColor, Font font) {
            this.text = text;
            this.textColor = textColor;
            this.borderColor = borderColor;
            this.font = font;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(font);

            FontMetrics metrics = g2d.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(text)) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

            g2d.setColor(borderColor);
            g2d.drawString(text, x - 1, y - 1);
            g2d.drawString(text, x - 1, y + 1);
            g2d.drawString(text, x + 1, y - 1);
            g2d.drawString(text, x + 1, y + 1);

            g2d.setColor(textColor);
            g2d.drawString(text, x, y);
        }
    }
}
