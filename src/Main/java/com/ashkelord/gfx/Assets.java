package com.ashkelord.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int W = 32, H = 32;

    // Player (Nadav)
    public static BufferedImage[][] player_walk; // [dir][frame]

    // NPCs
    public static BufferedImage[][] savta_walk; // [dir][frame] (only down for now, but structure supports more)
    public static BufferedImage[][] kid_walk;

    // Vehicles
    // [color][dir] -> 0=White, 1=Red | 0=Down, 1=Up, 2=Left, 3=Right
    public static BufferedImage[][] cars;

    // Terrain
    public static BufferedImage concrete, grass, shawarma_stand, road, sand, water, wall, curb;
    public static BufferedImage wall_window, wall_door;

    // Props (Entities)
    public static BufferedImage ac_unit, bench, street_light;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheets/main_sheet.png"));

        // Player: rows 0-3
        player_walk = new BufferedImage[4][3];
        for (int dir = 0; dir < 4; dir++) {
            for (int frame = 0; frame < 3; frame++) {
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

        // Props & Wall Details: row 7
        int propRow = 7 * H;
        wall_window = sheet.crop(0, propRow, W, H);
        wall_door = sheet.crop(W, propRow, W, H);
        ac_unit = sheet.crop(W * 2, propRow, W, H);
        bench = sheet.crop(W * 3, propRow, W, H);
        street_light = sheet.crop(W * 4, propRow, W, H);
    }
}
