package Main.java.com.ashkelord.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            // מנסה לטעון את הקובץ האמיתי
            if (ImageLoader.class.getResource(path) == null) {
                throw new IOException("File not found: " + path);
            }
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            // אם אין קובץ, במקום לקרוס - אנחנו מייצרים גרפיקה זמנית
            System.out.println("Warning: Could not load " + path + ". Using placeholder graphics.");
            return createPlaceholderImage();
        }
    }

    // פונקציה לייצור "גרפיקת מתכנתים" (Developer Art)
    private static BufferedImage createPlaceholderImage() {
        int width = 128; // רוחב הגריד
        int height = 128; // גובה הגריד
        int tileSize = 32;

        BufferedImage placeholder = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = placeholder.createGraphics();

        // --- שורה 0: דמויות ---

        // (0,0) Baby Ars -> כחול
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, tileSize, tileSize);
        g.setColor(Color.WHITE);
        g.drawString("Baby", 2, 20);

        // (32,0) Intermediate Ars -> ציאן
        g.setColor(Color.CYAN);
        g.fillRect(tileSize, 0, tileSize, tileSize);

        // (64,0) King Ars -> מג'נטה
        g.setColor(Color.MAGENTA);
        g.fillRect(tileSize * 2, 0, tileSize, tileSize);

        // --- שורה 1: סביבה ---

        // (0,32) Concrete -> אפור
        g.setColor(Color.GRAY);
        g.fillRect(0, tileSize, tileSize, tileSize);

        // (32,32) Grass -> ירוק
        g.setColor(Color.GREEN);
        g.fillRect(tileSize, tileSize, tileSize, tileSize);

        // (64,32) Shawarma -> כתום
        g.setColor(Color.ORANGE);
        g.fillRect(tileSize * 2, tileSize, tileSize, tileSize);

        g.dispose();
        return placeholder;
    }
}