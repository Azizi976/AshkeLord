package Main.java.com.ashkelord.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 32, height = 32; // גודל ברירת מחדל של אריח/דמות

    // מאגר התמונות הגלובלי
    public static BufferedImage player_baby, player_intermediate, player_king;
    public static BufferedImage concrete, grass, shawarma_stand;

    public static void init() {
        // טעינת ה-SpriteSheet הראשי
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheets/main_sheet.png"));

        // חיתוך דמויות (לפי הקואורדינטות בציור, כרגע הנחתי שהם בשורה הראשונה)
        player_baby = sheet.crop(0, 0, width, height);
        player_intermediate = sheet.crop(width, 0, width, height);
        player_king = sheet.crop(width * 2, 0, width, height);

        // חיתוך סביבה (שורה שנייה)
        concrete = sheet.crop(0, height, width, height);
        grass = sheet.crop(width, height, width, height);
        shawarma_stand = sheet.crop(width * 2, height, width, height);
    }
}
