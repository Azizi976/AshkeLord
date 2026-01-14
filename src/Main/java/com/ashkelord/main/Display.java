package Main.java.com.ashkelord.main;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

public class Display {

    private JFrame frame;
    private Canvas canvas;

    private String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        // מוודא שהמשחק ייסגר כשלוחצים על ה-X
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // מרכוז למסך
        frame.setVisible(true);

        canvas = new Canvas();
        // מגדיר גודל קבוע ל-Canvas כדי שלא יתעוות
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        // חשוב: מאפשר לקנבס לקבל פוקוס (לקליטת מקלדת)
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack(); // מכווץ את ה-Frame בדיוק לגודל ה-Canvas
    }

    // Getters כדי שה-Game Class יוכל לגשת
    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
}
