package com.ashkelord.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SpriteSheetGenerator {

    private static final int S = 32;
    private static final int COLS = 8;
    private static final int ROWS = 8; // Increased rows for cars/NPCs/props

    public static void main(String[] args) throws Exception {
        BufferedImage sheet = new BufferedImage(S * COLS, S * ROWS, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sheet.createGraphics();

        // Player Colors (Nadav - Ars)
        Color skin = new Color(0xD4, 0xA8, 0x78);
        Color shirt = new Color(0x1A, 0x1A, 0x1A);
        Color pants = new Color(0x2A, 0x2A, 0x40);
        Color hair = new Color(0x1A, 0x0E, 0x04);
        Color shoes = new Color(0xE0, 0xE0, 0xE0);
        Color chain = new Color(0xD4, 0xA0, 0x17);

        // Player Walk Cycles (Rows 0-3)
        // Row 0: Down
        drawCharDown(g, 0, 0, skin, shirt, pants, hair, shoes, chain, 0);
        drawCharDown(g, S, 0, skin, shirt, pants, hair, shoes, chain, -1);
        drawCharDown(g, S * 2, 0, skin, shirt, pants, hair, shoes, chain, 1);
        // Row 1: Up
        drawCharUp(g, 0, S, skin, shirt, pants, hair, shoes, chain, 0);
        drawCharUp(g, S, S, skin, shirt, pants, hair, shoes, chain, -1);
        drawCharUp(g, S * 2, S, skin, shirt, pants, hair, shoes, chain, 1);
        // Row 2: Left
        drawCharSide(g, 0, S * 2, skin, shirt, pants, hair, shoes, chain, 0, true);
        drawCharSide(g, S, S * 2, skin, shirt, pants, hair, shoes, chain, -1, true);
        drawCharSide(g, S * 2, S * 2, skin, shirt, pants, hair, shoes, chain, 1, true);
        // Row 3: Right
        drawCharSide(g, 0, S * 3, skin, shirt, pants, hair, shoes, chain, 0, false);
        drawCharSide(g, S, S * 3, skin, shirt, pants, hair, shoes, chain, -1, false);
        drawCharSide(g, S * 2, S * 3, skin, shirt, pants, hair, shoes, chain, 1, false);

        // Terrain Tiles (Row 4)
        drawConcrete(g, 0, S * 4);
        drawGrass(g, S, S * 4);
        drawShawarmaStand(g, S * 2, S * 4);
        drawRoad(g, S * 3, S * 4);
        drawSand(g, S * 4, S * 4);
        drawWater(g, S * 5, S * 4);
        drawWall(g, S * 6, S * 4);
        drawCurb(g, S * 7, S * 4);

        // Cars (Row 5)
        // White Car (Left, Right, Up, Down)
        drawCar(g, 0, S * 5, Color.WHITE, 0); // Down
        drawCar(g, S, S * 5, Color.WHITE, 1); // Up
        drawCar(g, S * 2, S * 5, Color.WHITE, 2); // Left
        drawCar(g, S * 3, S * 5, Color.WHITE, 3); // Right
        // Red Mazda 3 (Left, Right, Up, Down) - common in Israel
        drawCar(g, S * 4, S * 5, new Color(0xD0, 0x20, 0x20), 0); // Down
        drawCar(g, S * 5, S * 5, new Color(0xD0, 0x20, 0x20), 1); // Up
        drawCar(g, S * 6, S * 5, new Color(0xD0, 0x20, 0x20), 2); // Left
        drawCar(g, S * 7, S * 5, new Color(0xD0, 0x20, 0x20), 3); // Right

        // NPCs (Row 6)
        // Savta (Old Lady) - Floral dress, white hair
        Color savtaSkin = new Color(0xE0, 0xC0, 0xA0);
        Color savtaDress = new Color(0x60, 0x20, 0x80); // Purple floral
        Color savtaHair = new Color(0xE0, 0xE0, 0xE0); // White
        drawNPCDown(g, 0, S * 6, savtaSkin, savtaDress, savtaHair, 0); // Down
        drawNPCDown(g, S, S * 6, savtaSkin, savtaDress, savtaHair, 1); // Walk1
        drawNPCDown(g, S * 2, S * 6, savtaSkin, savtaDress, savtaHair, -1); // Walk2

        // Kid (Child) - Blue shirt, shorts, cap
        Color kidSkin = new Color(0xD4, 0xA8, 0x78);
        Color kidShirt = new Color(0x20, 0x60, 0xD0);
        Color kidShorts = new Color(0xD0, 0xA0, 0x60); // Khaki
        Color kidCap = new Color(0xD0, 0x20, 0x20); // Red cap
        drawKidDown(g, S * 3, S * 6, kidSkin, kidShirt, kidShorts, kidCap, 0);
        drawKidDown(g, S * 4, S * 6, kidSkin, kidShirt, kidShorts, kidCap, 1);
        drawKidDown(g, S * 5, S * 6, kidSkin, kidShirt, kidShorts, kidCap, -1);

        // Building Props (Row 7)
        drawWindow(g, 0, S * 7);
        drawDoor(g, S, S * 7);
        drawACUnit(g, S * 2, S * 7); // Classic Israeli AC box
        drawBench(g, S * 3, S * 7);
        drawStreetLight(g, S * 4, S * 7);

        g.dispose();

        String dir = System.getProperty("user.dir");
        File out = new File(dir, "src/main/resources/textures/sheets/main_sheet.png");
        out.getParentFile().mkdirs();
        ImageIO.write(sheet, "png", out);
        System.out.println("Sprite sheet generated: " + out.getAbsolutePath());
    }

    // --- Player Drawing Methods (Refactored to be generic for Player) ---
    private static void drawCharDown(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color hair, Color shoes, Color chain, int step) {
        g.setColor(hair);
        fill(g, ox + 10, oy + 2, 12, 6); // Gel hair
        g.setColor(new Color(0x40, 0x30, 0x20));
        fill(g, ox + 13, oy + 3, 3, 1); // Shine
        g.setColor(skin);
        fill(g, ox + 10, oy + 8, 12, 7);
        fill(g, ox + 8, oy + 9, 2, 4);
        fill(g, ox + 22, oy + 9, 2, 4);
        g.setColor(Color.BLACK);
        fill(g, ox + 13, oy + 12, 2, 2);
        fill(g, ox + 19, oy + 12, 2, 2);
        g.setColor(shirt);
        fill(g, ox + 10, oy + 15, 12, 8);
        g.setColor(skin);
        fill(g, ox + 6, oy + 15, 4, 6);
        fill(g, ox + 22, oy + 15, 4, 6);
        g.setColor(chain);
        fill(g, ox + 13, oy + 16, 6, 1);
        fill(g, ox + 14, oy + 17, 4, 1);
        fill(g, ox + 15, oy + 18, 2, 1);
        g.setColor(pants);
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX, oy + 23, 5, 6);
        fill(g, rLegX, oy + 23, 5, 6);
        g.setColor(shoes);
        fill(g, lLegX, oy + 29, 5, 2);
        fill(g, rLegX, oy + 29, 5, 2);
    }

    private static void drawCharUp(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color hair, Color shoes, Color chain, int step) {
        g.setColor(hair);
        fill(g, ox + 10, oy + 2, 12, 12);
        g.setColor(new Color(0x40, 0x30, 0x20));
        fill(g, ox + 14, oy + 4, 4, 1);
        g.setColor(skin);
        fill(g, ox + 8, oy + 9, 2, 4);
        fill(g, ox + 22, oy + 9, 2, 4);
        g.setColor(shirt);
        fill(g, ox + 10, oy + 15, 12, 8);
        g.setColor(skin);
        fill(g, ox + 6, oy + 15, 4, 6);
        fill(g, ox + 22, oy + 15, 4, 6);
        g.setColor(chain);
        fill(g, ox + 13, oy + 15, 6, 1);
        g.setColor(pants);
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX, oy + 23, 5, 6);
        fill(g, rLegX, oy + 23, 5, 6);
        g.setColor(shoes);
        fill(g, lLegX, oy + 29, 5, 2);
        fill(g, rLegX, oy + 29, 5, 2);
    }

    private static void drawCharSide(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color hair, Color shoes, Color chain, int step, boolean left) {
        int faceDir = left ? -1 : 1;
        int cx = ox + 16;
        g.setColor(hair);
        fill(g, cx - 5, oy + 2, 10, 6);
        g.setColor(skin);
        fill(g, cx - 5, oy + 8, 10, 7);
        g.setColor(Color.BLACK);
        fill(g, cx + faceDir * 2, oy + 11, 2, 2);
        g.setColor(shirt);
        fill(g, cx - 4, oy + 15, 8, 8);
        g.setColor(skin);
        int armX = cx + faceDir * 5;
        int armSwing = step * 2;
        fill(g, armX, oy + 15 + armSwing, 3, 6);
        g.setColor(chain);
        fill(g, cx + faceDir * 1, oy + 16, 3, 1);
        fill(g, cx + faceDir * 2, oy + 17, 1, 1);
        g.setColor(pants);
        int legSep = step * 2;
        fill(g, cx - 4, oy + 23 - Math.abs(legSep), 4, 6 + Math.abs(legSep));
        fill(g, cx + 1, oy + 23, 4, 6);
        g.setColor(shoes);
        fill(g, cx - 4, oy + 29, 4, 2);
        fill(g, cx + 1, oy + 29, 4, 2);
    }

    // --- NPCs ---
    private static void drawNPCDown(Graphics2D g, int ox, int oy, Color skin, Color dress, Color hair, int step) {
        g.setColor(hair); // Bun
        fill(g, ox + 10, oy + 2, 12, 10);
        g.setColor(skin);
        fill(g, ox + 10, oy + 10, 12, 7);
        g.setColor(Color.BLACK); // Glasses
        g.drawRect(ox + 11, oy + 12, 4, 2);
        g.drawRect(ox + 17, oy + 12, 4, 2);
        g.drawLine(ox + 15, oy + 13, ox + 17, oy + 13);
        g.setColor(dress);
        fill(g, ox + 8, oy + 17, 16, 12); // Dress body
        g.setColor(skin);
        fill(g, ox + 6, oy + 17, 2, 6); // Arms
        fill(g, ox + 24, oy + 17, 2, 6);
        g.setColor(new Color(0x30, 0x10, 0x10)); // Shoes
        fill(g, ox + 10, oy + 29, 5, 2);
        fill(g, ox + 17, oy + 29, 5, 2);
    }

    private static void drawKidDown(Graphics2D g, int ox, int oy, Color skin, Color shirt, Color shorts, Color cap,
            int step) {
        int scale = 24; // Smaller
        int offY = 8;
        int offX = 4;

        g.setColor(cap);
        fill(g, ox + offX + 8, oy + offY, 8, 4);
        g.setColor(skin);
        fill(g, ox + offX + 8, oy + offY + 4, 8, 6);
        g.setColor(Color.BLACK);
        fill(g, ox + offX + 10, oy + offY + 6, 1, 1);
        fill(g, ox + offX + 14, oy + offY + 6, 1, 1);
        g.setColor(shirt);
        fill(g, ox + offX + 6, oy + offY + 10, 12, 8);
        g.setColor(shorts);
        fill(g, ox + offX + 8, oy + offY + 18, 3, 4);
        fill(g, ox + offX + 13, oy + offY + 18, 3, 4);
        g.setColor(Color.BLUE); // Sneakers
        fill(g, ox + offX + 8, oy + offY + 22, 3, 2);
        fill(g, ox + offX + 13, oy + offY + 22, 3, 2);
    }

    // --- Vehicles ---
    private static void drawCar(Graphics2D g, int ox, int oy, Color color, int dir) {
        // 0=Down, 1=Up, 2=Left, 3=Right
        g.setColor(color);
        if (dir == 0 || dir == 1) { // Vertical
            g.fillRoundRect(ox + 6, oy + 2, 20, 28, 4, 4); // Body
            g.setColor(new Color(0x20, 0x20, 0x20)); // Windows
            g.fillRect(ox + 8, oy + 8, 16, 6); // Front/Back window
            g.fillRect(ox + 8, oy + 18, 16, 6);
            g.setColor(Color.BLACK); // Wheels (barely visible)
            fill(g, ox + 4, oy + 6, 2, 6);
            fill(g, ox + 26, oy + 6, 2, 6);
            fill(g, ox + 4, oy + 20, 2, 6);
            fill(g, ox + 26, oy + 20, 2, 6);
            // Headlights/Taillights
            if (dir == 0) { // Down (Front)
                g.setColor(Color.YELLOW);
                fill(g, ox + 6, oy + 26, 4, 2);
                fill(g, ox + 22, oy + 26, 4, 2);
            } else { // Up (Rear)
                g.setColor(Color.RED);
                fill(g, ox + 6, oy + 2, 4, 2);
                fill(g, ox + 22, oy + 2, 4, 2);
            }
        } else { // Horizontal
            g.fillRoundRect(ox + 2, oy + 8, 28, 16, 4, 4);
            g.setColor(new Color(0x20, 0x20, 0x20));
            g.fillRect(ox + 8, oy + 10, 6, 12);
            g.fillRect(ox + 18, oy + 10, 6, 12);
            g.setColor(Color.BLACK); // Wheels
            fill(g, ox + 6, oy + 6, 6, 2);
            fill(g, ox + 20, oy + 6, 6, 2);
            fill(g, ox + 6, oy + 24, 6, 2);
            fill(g, ox + 20, oy + 24, 6, 2);
            // Lights
            if (dir == 3) { // Right (Front)
                g.setColor(Color.YELLOW);
                fill(g, ox + 28, oy + 10, 2, 4);
                fill(g, ox + 28, oy + 18, 2, 4);
            } else { // Left (Front)
                g.setColor(Color.YELLOW);
                fill(g, ox + 2, oy + 10, 2, 4);
                fill(g, ox + 2, oy + 18, 2, 4);
            }
        }
    }

    // --- Props ---
    private static void drawWindow(Graphics2D g, int ox, int oy) {
        // Fits on wall tile
        g.setColor(new Color(0xC8, 0xB8, 0x98)); // Wall bg
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x60, 0x80, 0xA0)); // Darker glass
        fill(g, ox + 6, oy + 6, 20, 20);
        g.setColor(new Color(0xE0, 0xE0, 0xE0)); // Frame
        g.drawRect(ox + 6, oy + 6, 19, 19);
        g.drawLine(ox + 16, oy + 6, ox + 16, oy + 25);
        g.drawLine(ox + 6, oy + 16, ox + 25, oy + 16);
    }

    private static void drawDoor(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xC8, 0xB8, 0x98));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x50, 0x30, 0x10)); // Dark Wood
        fill(g, ox + 8, oy + 4, 16, 28);
        g.setColor(new Color(0x80, 0x50, 0x20)); // Frame
        g.drawRect(ox + 8, oy + 4, 15, 27);
        g.setColor(Color.YELLOW); // Knob
        fill(g, ox + 20, oy + 18, 2, 2);
    }

    private static void drawACUnit(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xC8, 0xB8, 0x98));
        g.fillRect(ox, oy, S, S);
        g.setColor(Color.WHITE); // AC Box
        fill(g, ox + 4, oy + 10, 24, 14);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(ox + 4, oy + 10, 23, 13);
        g.setColor(Color.BLACK); // Fan/Grill
        fill(g, ox + 16, oy + 14, 8, 8);
        for (int i = 0; i < 4; i++) { // Vents
            g.drawLine(ox + 6, oy + 14 + i * 2, ox + 12, oy + 14 + i * 2);
        }
    }

    private static void drawBench(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E)); // Concrete bg
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x6A, 0x4A, 0x2A)); // Wood
        fill(g, ox + 2, oy + 12, 28, 6); // Seat
        fill(g, ox + 2, oy + 6, 28, 4); // Back
        g.setColor(Color.BLACK); // Legs
        fill(g, ox + 4, oy + 18, 2, 4);
        fill(g, ox + 26, oy + 18, 2, 4);
    }

    private static void drawStreetLight(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x20, 0x20, 0x20)); // Pole base
        fill(g, ox + 14, oy + 10, 4, 22);
        g.setColor(Color.ORANGE); // Light reflection
        fill(g, ox + 12, oy + 20, 8, 8);
    }

    // --- Terrain (Existing) ---
    private static void drawConcrete(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0xAA, 0xAA, 0xAA));
        fill(g, ox + 2, oy + 2, 8, 6);
        fill(g, ox + 18, oy + 14, 10, 8);
        g.setColor(new Color(0x88, 0x88, 0x88));
        fill(g, ox + 12, oy + 4, 6, 4);
        fill(g, ox + 4, oy + 20, 8, 6);
        g.setColor(new Color(0x5A, 0x5A, 0x5A));
        g.drawLine(ox + 6, oy + 10, ox + 22, oy + 12);
        g.drawLine(ox + 14, oy + 8, ox + 16, oy + 24);
    }

    private static void drawGrass(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xC4, 0xA8, 0x6B));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x8B, 0x9A, 0x5A));
        fill(g, ox + 2, oy + 4, 4, 3);
        fill(g, ox + 14, oy + 2, 6, 3);
        fill(g, ox + 24, oy + 8, 5, 3);
        fill(g, ox + 6, oy + 18, 4, 3);
        fill(g, ox + 20, oy + 22, 6, 3);
        g.setColor(new Color(0x6B, 0x8B, 0x4A));
        fill(g, ox + 8, oy + 10, 3, 2);
        fill(g, ox + 18, oy + 16, 4, 2);
    }

    private static void drawShawarmaStand(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x8B, 0x5E, 0x3C));
        fill(g, ox + 4, oy + 6, 24, 20);
        g.setColor(new Color(0xC8, 0x9E, 0x6A));
        fill(g, ox + 6, oy + 8, 20, 16);
        g.setColor(new Color(0x8B, 0x4A, 0x2A));
        fill(g, ox + 12, oy + 10, 8, 12);
        g.setColor(new Color(0xC0, 0xC0, 0xC0));
        fill(g, ox + 15, oy + 8, 2, 16);
        g.setColor(new Color(0xFF, 0x8C, 0x00));
        fill(g, ox + 10, oy + 14, 2, 2);
        fill(g, ox + 20, oy + 14, 2, 2);
        g.setColor(new Color(0x4A, 0x2E, 0x1A));
        g.drawRect(ox + 4, oy + 6, 23, 19);
    }

    private static void drawRoad(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x3A, 0x3A, 0x3A));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x44, 0x44, 0x44));
        fill(g, ox + 4, oy + 6, 10, 4);
        fill(g, ox + 18, oy + 20, 8, 4);
        g.setColor(new Color(0xE8, 0xC8, 0x30)); // Yellow markings
        fill(g, ox + 14, oy + 2, 4, 8);
        fill(g, ox + 14, oy + 18, 4, 8);
    }

    private static void drawSand(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xE8, 0xD5, 0xA0));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0xD4, 0xC0, 0x8A));
        fill(g, ox + 4, oy + 8, 6, 3);
        fill(g, ox + 20, oy + 4, 5, 3);
        fill(g, ox + 10, oy + 22, 8, 3);
        g.setColor(new Color(0xF0, 0xE0, 0xC0));
        fill(g, ox + 6, oy + 14, 2, 2);
        fill(g, ox + 22, oy + 18, 2, 2);
    }

    private static void drawWater(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x2A, 0x6E, 0x9E));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x3A, 0x80, 0xB0));
        fill(g, ox + 2, oy + 6, 12, 2);
        fill(g, ox + 18, oy + 14, 10, 2);
        fill(g, ox + 6, oy + 22, 14, 2);
        g.setColor(new Color(0x5A, 0xA0, 0xC8));
        fill(g, ox + 4, oy + 5, 4, 1);
        fill(g, ox + 20, oy + 13, 4, 1);
    }

    private static void drawWall(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xC8, 0xB8, 0x98));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x5A, 0x7A, 0x9A));
        fill(g, ox + 4, oy + 4, 10, 8);
        fill(g, ox + 18, oy + 4, 10, 8);
        g.setColor(new Color(0xA0, 0x90, 0x70));
        g.drawRect(ox + 4, oy + 4, 9, 7);
        g.drawRect(ox + 18, oy + 4, 9, 7);
        g.setColor(new Color(0xB0, 0xA0, 0x80));
        fill(g, ox, oy + 16, S, 2);
        g.setColor(new Color(0x6A, 0x5A, 0x4A));
        fill(g, ox + 12, oy + 18, 8, 14);
    }

    private static void drawCurb(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xB0, 0xB0, 0xB0));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x8A, 0x8A, 0x8A));
        fill(g, ox, oy + 12, S, 4);
        g.setColor(new Color(0xC0, 0xC0, 0xC0));
        fill(g, ox, oy, S, 4);
        g.setColor(new Color(0x70, 0x70, 0x70));
        g.drawLine(ox, oy + 14, ox + 31, oy + 14);
    }

    private static void fill(Graphics2D g, int x, int y, int w, int h) {
        g.fillRect(x, y, w, h);
    }
}
