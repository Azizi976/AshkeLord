package Main.java.com.ashkelord.states;

import Main.java.com.ashkelord.entities.creatures.Player;
import Main.java.com.ashkelord.main.Game;
import Main.java.com.ashkelord.worlds.World;
import java.awt.Graphics;

public class GameState extends State {

    private Player player;
    private World world;

    public GameState(Game game) {
        super(game);
        // יצירת עולם לפני יצירת שחקן (למקרה שנצטרך לבדוק התנגשויות)
        world = new World(game, "res/worlds/world1.txt");
        player = new Player(game, 100, 100);
    }

    @Override
    public void tick() {
        world.tick();
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        // קודם מציירים עולם (רקע), אחר כך שחקן (שכבה עליונה)
        world.render(g);
        player.render(g);
    }
}