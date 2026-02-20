package com.ashkelord.gfx;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Assets {

    private static final int W = 32, H = 32;

    // Player (Nadav)
    public static BufferedImage[][] player_walk; // [dir][frame]

    // NPCs
    public static BufferedImage[][] savta_walk; // [dir][frame]
    public static BufferedImage[][] kid_walk;
    public static BufferedImage[][] soldier_walk;
    public static BufferedImage[][] ars_walk;

    // Vehicles
    // [color][dir] -> 0=White, 1=Red | 0=Down, 1=Up, 2=Left, 3=Right
    public static BufferedImage[][] cars;

    // Terrain
    public static BufferedImage concrete, grass, shawarma_stand, road, road_h, intersection_road, sand, water, wall, curb;
    public static BufferedImage wall_window, wall_door, wood;
    
    // Ashkelon Specific Props
    public static BufferedImage palm_tree, sabra_cactus, trash_can, plastic_chair, sheshbesh, bougainvillea;
    public static BufferedImage laffa;
    
    // Easter Egg Sprites
    public static BufferedImage rusty_bike, graffiti_wall, abu_rafi;
    
    // Quest NPC & Item Sprites
    public static BufferedImage tzion_barber, avi_gym, yotam_telaviv, ebike_battery, strong_hold_wax;
    
    // Miri and Quest Items (now from sheet)
    public static BufferedImage miri_kapara, golden_amba, oat_milk_cortado;

    // Props (Entities)
    public static BufferedImage ac_unit, bench, street_light;
    
    // Spitting
    public static BufferedImage[] player_spit;
    public static BufferedImage spit_projectile;
    public static BufferedImage liran_boss;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheets/main_sheet.png"));

        // Player: rows 0-3
        player_walk = new BufferedImage[4][4];
        for (int dir = 0; dir < 4; dir++) {
            for (int frame = 0; frame < 4; frame++) {
                player_walk[dir][frame] = sheet.crop(frame * W, dir * H, W, H);
            }
        }

        // Terrain: row 4
        int tileRow = 4 * H;
        concrete = sheet.crop(0, tileRow, W, H);
        grass = sheet.crop(W, tileRow, W, H);
        shawarma_stand = sheet.crop(W * 2, tileRow, W, H);
        road = sheet.crop(W * 3, tileRow, W, H);
        sand = sheet.crop(W * 4, tileRow, W, H);
        water = sheet.crop(W * 5, tileRow, W, H);
        wall = sheet.crop(W * 6, tileRow, W, H);
        curb = sheet.crop(W * 7, tileRow, W, H);

        // Cars: row 5
        // 0-3: White, 4-7: Red
        int carRow = 5 * H;
        cars = new BufferedImage[2][4];
        for (int i = 0; i < 4; i++)
            cars[0][i] = sheet.crop(i * W, carRow, W, H); // White
        for (int i = 0; i < 4; i++)
            cars[1][i] = sheet.crop((i + 4) * W, carRow, W, H); // Red

        // NPCs: row 6
        int npcRow = 6 * H;
        savta_walk = new BufferedImage[1][3]; // Only Down for now
        savta_walk[0][0] = sheet.crop(0, npcRow, W, H);
        savta_walk[0][1] = sheet.crop(W, npcRow, W, H);
        savta_walk[0][2] = sheet.crop(W * 2, npcRow, W, H);

        kid_walk = new BufferedImage[1][3]; // Only Down for now
        kid_walk[0][0] = sheet.crop(W * 3, npcRow, W, H);
        kid_walk[0][1] = sheet.crop(W * 4, npcRow, W, H);
        kid_walk[0][2] = sheet.crop(W * 5, npcRow, W, H);
        
        // Miri Kapara: row 6 slot 6
        miri_kapara = sheet.crop(W * 6, npcRow, W, H);
        // Golden Amba: row 6 slot 7
        golden_amba = sheet.crop(W * 7, npcRow, W, H);

        // Props & Wall Details: row 7
        int propRow = 7 * H;
        wall_window = sheet.crop(0, propRow, W, H);
        wall_door = sheet.crop(W, propRow, W, H);
        ac_unit = sheet.crop(W * 2, propRow, W, H);
        bench = sheet.crop(W * 3, propRow, W, H);
        street_light = sheet.crop(W * 4, propRow, W, H);
        intersection_road = sheet.crop(W * 5, propRow, W, H);
        // Wood: row 7 slot 6
        wood = sheet.crop(W * 6, propRow, W, H);
        // Oat Milk Cortado: row 7 slot 7
        oat_milk_cortado = sheet.crop(W * 7, propRow, W, H);
        
        // Ashkelon Specific Props: row 8
        int ashRow = 8 * H;
        palm_tree = sheet.crop(0, ashRow, W, H);
        sabra_cactus = sheet.crop(W, ashRow, W, H);
        trash_can = sheet.crop(W * 2, ashRow, W, H);
        plastic_chair = sheet.crop(W * 3, ashRow, W, H);
        sheshbesh = sheet.crop(W * 4, ashRow, W, H);
        bougainvillea = sheet.crop(W * 5, ashRow, W, H);
        road_h = sheet.crop(W * 6, ashRow, W, H);
        laffa = sheet.crop(W * 7, ashRow, W, H);
        
        // Ashkelon Specific NPCs: row 9
        int ashNpcRow = 9 * H;
        soldier_walk = new BufferedImage[1][3];
        soldier_walk[0][0] = sheet.crop(0, ashNpcRow, W, H);
        soldier_walk[0][1] = sheet.crop(W, ashNpcRow, W, H);
        soldier_walk[0][2] = sheet.crop(W * 2, ashNpcRow, W, H);
        
        ars_walk = new BufferedImage[1][3];
        ars_walk[0][0] = sheet.crop(W * 3, ashNpcRow, W, H);
        ars_walk[0][1] = sheet.crop(W * 4, ashNpcRow, W, H);
        ars_walk[0][2] = sheet.crop(W * 5, ashNpcRow, W, H);
        
        // Liran Boss: Row 9, Slot 6
        liran_boss = sheet.crop(W * 6, ashNpcRow, W, H);
        
        // Easter Egg & Quest Sprites: row 10
        int eggRow = 10 * H;
        rusty_bike = sheet.crop(0, eggRow, W, H);
        graffiti_wall = sheet.crop(W, eggRow, W, H);
        abu_rafi = sheet.crop(W * 2, eggRow, W, H);
        tzion_barber = sheet.crop(W * 3, eggRow, W, H);
        avi_gym = sheet.crop(W * 4, eggRow, W, H);
        yotam_telaviv = sheet.crop(W * 5, eggRow, W, H);
        ebike_battery = sheet.crop(W * 6, eggRow, W, H);
        strong_hold_wax = sheet.crop(W * 7, eggRow, W, H);
        
        // Spitting Sprites: row 11
        int spitRow = 11 * H;
        player_spit = new BufferedImage[4];
        player_spit[0] = sheet.crop(0, spitRow, W, H); // Down
        player_spit[1] = sheet.crop(W, spitRow, W, H); // Up
        player_spit[2] = sheet.crop(W * 2, spitRow, W, H); // Left
        player_spit[3] = sheet.crop(W * 3, spitRow, W, H); // Right
        
        spit_projectile = sheet.crop(W * 4, spitRow, W, H);
    }
}
