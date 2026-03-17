package com.ashkelord.main;

public class Launcher {
    public static void main(String[] args) {
        // רזולוציה קלאסית למשחקי רטרו, ניתנת לשינוי בעתיד בקלות
        Game game = new Game("AshkeLord", 800, 600);
        game.start();
    }
}
