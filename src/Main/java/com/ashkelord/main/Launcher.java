package Main.java.com.ashkelord.main;

public class Launcher {
    public static void main(String[] args) {
        // רזולוציה קלאסית למשחקי רטרו, ניתנת לשינוי בעתיד בקלות
        Game game = new Game("AshkeLord: Legend of the Golden Chai", 800, 600);
        game.start();
    }
}
