package search.gui.mapbuilder.legend;

import javax.swing.*;
import java.awt.*;

public class TextFieldLegend extends ObjectLegend<JTextField> {

    public TextFieldLegend(JPanel panel, String text, JTextField textField, int x, int y, int width, int height) {
        this.text = text;
        this.object = textField;

        textField.setBounds(x + 2 * TEXT_OFFSET, y - height, width, height);
        panel.add(textField);

        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g2d) {
        drawText(g2d, x, y);
    }

    protected void drawText(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(DEFAULT_FONT);
        g2d.drawString(text, x, y);
    }

    public String getFieldText() {
        return object.getText();
    }


}