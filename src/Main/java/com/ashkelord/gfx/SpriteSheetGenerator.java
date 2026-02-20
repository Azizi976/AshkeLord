package com.ashkelord.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SpriteSheetGenerator {

    private static final int S = 32;
    private static final int COLS = 8;
    private static final int ROWS = 12; // Row 11: Spitting logic
    
    // Liran Colors
    private static final Color LIRAN_SKIN = new Color(0xD4, 0xA8, 0x78);
    private static final Color LIRAN_SHIRT = new Color(0x10, 0x10, 0x10); // Black
    private static final Color LIRAN_PANTS = new Color(0x30, 0x30, 0x50); // Dark Blue Jeans
    private static final Color LIRAN_HAIR = new Color(0x20, 0x10, 0x05); // Dark Brown
    private static final Color GOLD = new Color(0xD4, 0xA0, 0x17);
    


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

        // ... (existing calls 0-10) ...
        // Row 0: Down
        drawCharDown(g, 0, 0, skin, shirt, pants, hair, shoes, chain, 0);
        drawCharDown(g, S, 0, skin, shirt, pants, hair, shoes, chain, -1);
        drawCharDown(g, S * 2, 0, skin, shirt, pants, hair, shoes, chain, 1);
        drawCharDown(g, S * 3, 0, skin, shirt, pants, hair, shoes, chain, -2);
        // Row 1: Up
        drawCharUp(g, 0, S, skin, shirt, pants, hair, shoes, chain, 0);
        drawCharUp(g, S, S, skin, shirt, pants, hair, shoes, chain, -1);
        drawCharUp(g, S * 2, S, skin, shirt, pants, hair, shoes, chain, 1);
        drawCharUp(g, S * 3, S, skin, shirt, pants, hair, shoes, chain, -2);
        // Row 2: Left
        drawCharSide(g, 0, S * 2, skin, shirt, pants, hair, shoes, chain, 0, true);
        drawCharSide(g, S, S * 2, skin, shirt, pants, hair, shoes, chain, -1, true);
        drawCharSide(g, S * 2, S * 2, skin, shirt, pants, hair, shoes, chain, 1, true);
        drawCharSide(g, S * 3, S * 2, skin, shirt, pants, hair, shoes, chain, -2, true);
        // Row 3: Right
        drawCharSide(g, 0, S * 3, skin, shirt, pants, hair, shoes, chain, 0, false);
        drawCharSide(g, S, S * 3, skin, shirt, pants, hair, shoes, chain, -1, false);
        drawCharSide(g, S * 2, S * 3, skin, shirt, pants, hair, shoes, chain, 1, false);
        drawCharSide(g, S * 3, S * 3, skin, shirt, pants, hair, shoes, chain, -2, false);

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
        drawCar(g, 0, S * 5, Color.WHITE, 0); 
        drawCar(g, S, S * 5, Color.WHITE, 1); 
        drawCar(g, S * 2, S * 5, Color.WHITE, 2); 
        drawCar(g, S * 3, S * 5, Color.WHITE, 3); 
        drawCar(g, S * 4, S * 5, new Color(0xD0, 0x20, 0x20), 0);
        drawCar(g, S * 5, S * 5, new Color(0xD0, 0x20, 0x20), 1);
        drawCar(g, S * 6, S * 5, new Color(0xD0, 0x20, 0x20), 2);
        drawCar(g, S * 7, S * 5, new Color(0xD0, 0x20, 0x20), 3);

        // NPCs (Row 6)
        Color savtaSkin = new Color(0xE0, 0xC0, 0xA0);
        Color savtaDress = new Color(0x60, 0x20, 0x80); 
        Color savtaHair = new Color(0xE0, 0xE0, 0xE0); 
        drawNPCDown(g, 0, S * 6, savtaSkin, savtaDress, savtaHair, 0); 
        drawNPCDown(g, S, S * 6, savtaSkin, savtaDress, savtaHair, 1); 
        drawNPCDown(g, S * 2, S * 6, savtaSkin, savtaDress, savtaHair, -1); 

        Color kidSkin = new Color(0xD4, 0xA8, 0x78);
        Color kidShirt = new Color(0x20, 0x60, 0xD0);
        Color kidShorts = new Color(0xD0, 0xA0, 0x60); 
        Color kidCap = new Color(0xD0, 0x20, 0x20); 
        drawKidDown(g, S * 3, S * 6, kidSkin, kidShirt, kidShorts, kidCap, 0);
        drawKidDown(g, S * 4, S * 6, kidSkin, kidShirt, kidShorts, kidCap, 1);
        drawKidDown(g, S * 5, S * 6, kidSkin, kidShirt, kidShorts, kidCap, -1);
        drawMiriKapara(g, S * 6, S * 6);
        drawGoldenAmba(g, S * 7, S * 6);

        // Building Props (Row 7)
        drawWindow(g, 0, S * 7);
        drawDoor(g, S, S * 7);
        drawACUnit(g, S * 2, S * 7); 
        drawBench(g, S * 3, S * 7);
        drawStreetLight(g, S * 4, S * 7);
        drawIntersectionRoad(g, S * 5, S * 7);
        drawWoodTile(g, S * 6, S * 7);
        drawOatMilkCortado(g, S * 7, S * 7);
        
        // Ashkelon Specific Props (Row 8)
        drawPalmTree(g, 0, S * 8);
        drawSabraCactus(g, S, S * 8);
        drawTrashCan(g, S * 2, S * 8);
        drawPlasticChair(g, S * 3, S * 8);
        drawSheshbesh(g, S * 4, S * 8);
        drawBougainvillea(g, S * 5, S * 8);
        drawHorizontalRoad(g, S * 6, S * 8);
        drawLaffa(g, S * 7, S * 8);
        
        // Ashkelon Specific NPCs (Row 9)
        Color soldierSkin = new Color(0xD4, 0xA8, 0x78);
        Color soldierUniform = new Color(0x37, 0x41, 0x2E); 
        Color soldierBoots = Color.BLACK;
        drawSoldierDown(g, 0, S * 9, soldierSkin, soldierUniform, soldierBoots, 0);
        drawSoldierDown(g, S, S * 9, soldierSkin, soldierUniform, soldierBoots, 1);
        drawSoldierDown(g, S * 2, S * 9, soldierSkin, soldierUniform, soldierBoots, -1);
        
        Color arsSkin = new Color(0xCC, 0x99, 0x66);
        Color tracksuit = Color.WHITE;
        Color stripe = Color.BLACK;
        drawTracksuitArsDown(g, S * 3, S * 9, arsSkin, tracksuit, stripe, 0);
        drawTracksuitArsDown(g, S * 4, S * 9, arsSkin, tracksuit, stripe, 1);
        drawTracksuitArsDown(g, S * 5, S * 9, arsSkin, tracksuit, stripe, -1);
        
        // Liran Boss (Row 9, Slot 6)
        drawLiranDown(g, S * 6, S * 9);

        // Easter Egg Sprites (Row 10)
        drawRustyBike(g, 0, S * 10);
        drawGraffitiWall(g, S, S * 10);
        drawAbuRafi(g, S * 2, S * 10);
        drawTzionBarber(g, S * 3, S * 10);
        drawAviGymBoss(g, S * 4, S * 10);
        drawYotamTelAvivian(g, S * 5, S * 10);
        drawEBikeBattery(g, S * 6, S * 10);
        drawStrongHoldWax(g, S * 7, S * 10);

        // Row 11: Spitting Sprites
        // Slot 0: Spit Down (Nadav)
        drawCharSpit(g, 0, S * 11, skin, shirt, pants, hair, shoes, chain, 0);
        // Slot 1: Spit Up (Nadav)
        drawCharSpit(g, S, S * 11, skin, shirt, pants, hair, shoes, chain, 1);
        // Slot 2: Spit Left (Nadav)
        drawCharSpit(g, S * 2, S * 11, skin, shirt, pants, hair, shoes, chain, 2);
        // Slot 3: Spit Right (Nadav)
        drawCharSpit(g, S * 3, S * 11, skin, shirt, pants, hair, shoes, chain, 3);
        // Slot 4: Spit Projectile
        drawSpitProjectile(g, S * 4, S * 11);

        g.dispose();

        String dir = System.getProperty("user.dir");
        File out = new File(dir, "src/main/resources/textures/sheets/main_sheet.png");
        out.getParentFile().mkdirs();
        ImageIO.write(sheet, "png", out);
        System.out.println("Sprite sheet generated: " + out.getAbsolutePath());
    }

    // --- Spitting Animation ---
    private static void drawCharSpit(Graphics2D g, int ox, int oy, Color skin, Color shirt, Color pants, Color hair, Color shoes, Color chain, int dir) {
        // Based on stand pose but with modification
        // Dir: 0=Down, 1=Up, 2=Left, 3=Right
        
        // Base Body
        if (dir == 0) { // Down
            drawCharDown(g, ox, oy, skin, shirt, pants, hair, shoes, chain, 0);
            // Mouth Open
            g.setColor(new Color(0x80, 0x40, 0x40)); // Dark red mouth
            fill(g, ox + 15, oy + 12, 2, 2);
            // Spit droplet forming
            g.setColor(new Color(0xE0, 0xF0, 0xFF)); // White-ish blue
            fill(g, ox + 16, oy + 13, 1, 1);
        } else if (dir == 1) { // Up
            drawCharUp(g, ox, oy, skin, shirt, pants, hair, shoes, chain, 0);
            // Head tilt back? Just slight offset
            // Hard to see mouth from back, maybe just head recoil
            // We'll leave it subtle or add a pixel
        } else if (dir == 2) { // Left
            drawCharSide(g, ox, oy, skin, shirt, pants, hair, shoes, chain, 0, true);
            // Mouth pixel
            g.setColor(new Color(0x80, 0x40, 0x40));
            fill(g, ox + 10, oy + 11, 2, 2); // Mouth area on left profile
            // spit
            g.setColor(new Color(0xE0, 0xF0, 0xFF));
            fill(g, ox + 9, oy + 12, 1, 1);
        } else if (dir == 3) { // Right
            drawCharSide(g, ox, oy, skin, shirt, pants, hair, shoes, chain, 0, false);
            // Mouth pixel
            g.setColor(new Color(0x80, 0x40, 0x40));
            fill(g, ox + 20, oy + 11, 2, 2); // Mouth area on right profile
            // spit
            g.setColor(new Color(0xE0, 0xF0, 0xFF));
            fill(g, ox + 22, oy + 12, 1, 1);
        }
    }
    
    private static void drawSpitProjectile(Graphics2D g, int ox, int oy) {
        // Larger spit projectile (Big Loogie)
        // Main body
        g.setColor(new Color(0xE0, 0xF0, 0xFF)); // White-ish
        fill(g, ox + 10, oy + 10, 10, 10);
        // Shading/Texture
        g.setColor(new Color(0xA0, 0xC0, 0xE0)); // Blue tint
        fill(g, ox + 12, oy + 12, 6, 6);
        // Highlights
        g.setColor(Color.WHITE);
        fill(g, ox + 11, oy + 11, 2, 2);
        // Trailing droplets
        g.setColor(new Color(0xC0, 0xE0, 0xFF));
        fill(g, ox + 8, oy + 16, 2, 2);
        fill(g, ox + 14, oy + 8, 2, 2);
        fill(g, ox + 22, oy + 12, 2, 2);
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
        g.setColor(new Color(0xE8, 0xC8, 0x30)); // Yellow markings (vertical)
        fill(g, ox + 14, oy + 2, 4, 8);
        fill(g, ox + 14, oy + 18, 4, 8);
    }

    private static void drawHorizontalRoad(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x3A, 0x3A, 0x3A));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x44, 0x44, 0x44));
        fill(g, ox + 6, oy + 4, 4, 10);
        fill(g, ox + 20, oy + 18, 4, 8);
        g.setColor(new Color(0xE8, 0xC8, 0x30)); // Yellow markings (horizontal)
        fill(g, ox + 2, oy + 14, 8, 4);
        fill(g, ox + 18, oy + 14, 8, 4);
    }

    private static void drawIntersectionRoad(Graphics2D g, int ox, int oy) {
        // Plain asphalt with no lane markings — used where roads cross
        g.setColor(new Color(0x3A, 0x3A, 0x3A));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x44, 0x44, 0x44));
        fill(g, ox + 4, oy + 4, 6, 6);
        fill(g, ox + 18, oy + 20, 8, 6);
        fill(g, ox + 22, oy + 6, 4, 4);
        fill(g, ox + 8, oy + 22, 6, 4);
    }

    private static void drawLaffa(Graphics2D g, int ox, int oy) {
        // Transparent background
        // Laffa is a large round flatbread, golden-brown with char marks
        g.setColor(new Color(0xD4, 0xA8, 0x6B)); // Golden dough
        g.fillOval(ox + 2, oy + 4, 28, 24);
        g.setColor(new Color(0xC0, 0x96, 0x5A)); // Darker crust edge
        g.drawOval(ox + 2, oy + 4, 28, 24);
        // Char marks / bubbles
        g.setColor(new Color(0x8B, 0x5E, 0x3C));
        fill(g, ox + 8, oy + 10, 3, 2);
        fill(g, ox + 18, oy + 8, 4, 2);
        fill(g, ox + 12, oy + 18, 3, 2);
        fill(g, ox + 22, oy + 16, 2, 3);
        // Filling peek (green + white = salad + tahini)
        g.setColor(new Color(0x4A, 0x8C, 0x3A)); // Green filling
        fill(g, ox + 10, oy + 13, 12, 3);
        g.setColor(new Color(0xF0, 0xE8, 0xD0)); // Tahini
        fill(g, ox + 12, oy + 14, 8, 1);
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

    private static void drawPalmTree(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E)); // Concrete bg
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x61, 0x41, 0x26)); // Trunk
        fill(g, ox + 14, oy + 12, 4, 20);
        g.setColor(new Color(0x2D, 0x5A, 0x27)); // Fronds
        g.fillOval(ox + 4, oy + 2, 24, 12);
        g.fillOval(ox + 2, oy + 6, 28, 8);
    }

    private static void drawSabraCactus(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xC4, 0xA8, 0x6B)); // Grass/Sand bg
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x55, 0x6B, 0x2F)); // Olive Green
        g.fillOval(ox + 10, oy + 14, 12, 16); // Center
        g.fillOval(ox + 6, oy + 8, 10, 10); // Paddle
        g.fillOval(ox + 16, oy + 10, 8, 8); // Paddle
        g.setColor(Color.WHITE); // Spines (dots)
        fill(g, ox + 10, oy + 15, 1, 1);
        fill(g, ox + 18, oy + 12, 1, 1);
        fill(g, ox + 8, oy + 10, 1, 1);
    }

    private static void drawTrashCan(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E));
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0xFF, 0x66, 0x00)); // Israeli Orange Bin
        fill(g, ox + 8, oy + 10, 16, 20);
        g.setColor(Color.BLACK);
        g.drawRect(ox + 8, oy + 10, 15, 19);
        fill(g, ox + 10, oy + 14, 12, 2); // Opening
    }

    private static void drawPlasticChair(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E));
        g.fillRect(ox, oy, S, S);
        g.setColor(Color.WHITE);
        fill(g, ox + 8, oy + 14, 16, 2); // Seat
        fill(g, ox + 8, oy + 6, 16, 8); // Back
        fill(g, ox + 8, oy + 16, 2, 12); // Legs
        fill(g, ox + 22, oy + 16, 2, 12);
    }

    private static void drawSheshbesh(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x6A, 0x4A, 0x2A)); // Wooden frame
        g.fillRect(ox + 4, oy + 4, 24, 24);
        g.setColor(new Color(0xE8, 0xD5, 0xA0)); // Board
        fill(g, ox + 6, oy + 6, 20, 20);
        g.setColor(new Color(0x8B, 0x4A, 0x2A)); // Triangles
        for (int i = 0; i < 4; i++) {
            int tx = ox + 6 + i * 5;
            g.fillPolygon(new int[]{tx, tx + 4, tx + 2}, new int[]{oy + 6, oy + 6, oy + 14}, 3);
            g.fillPolygon(new int[]{tx, tx + 4, tx + 2}, new int[]{oy + 26, oy + 26, oy + 18}, 3);
        }
    }

    private static void drawBougainvillea(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xC8, 0xB8, 0x98)); // Wall bg
        g.fillRect(ox, oy, S, S);
        g.setColor(new Color(0x2D, 0x5A, 0x27)); // Green leaves
        fill(g, ox + 4, oy + 4, 24, 12);
        g.setColor(new Color(0xE9, 0x1E, 0x63)); // Magenta flowers
        for (int i = 0; i < 5; i++) {
            g.fillOval(ox + 6 + i * 4, oy + 6 + (i % 2) * 4, 6, 6);
        }
    }

    private static void drawSoldierDown(Graphics2D g, int ox, int oy, Color skin, Color uniform, Color boots, int step) {
        g.setColor(new Color(0x1A, 0x1A, 0x1A)); // Beret (usually tucked)
        fill(g, ox + 18, oy + 6, 4, 3);
        g.setColor(skin);
        fill(g, ox + 10, oy + 8, 12, 8);
        g.setColor(uniform);
        fill(g, ox + 8, oy + 16, 16, 10); // Torso
        g.setColor(skin);
        fill(g, ox + 6, oy + 16, 2, 6); // Arms
        fill(g, ox + 24, oy + 16, 2, 6);
        g.setColor(uniform); // Pants
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX, oy + 26, 5, 4);
        fill(g, rLegX, oy + 26, 5, 4);
        g.setColor(boots);
        fill(g, lLegX, oy + 30, 5, 2);
        fill(g, rLegX, oy + 30, 5, 2);
    }

    private static void drawTracksuitArsDown(Graphics2D g, int ox, int oy, Color skin, Color tracksuit, Color stripe, int step) {
        g.setColor(Color.BLACK); // Buzz cut hair
        fill(g, ox + 10, oy + 4, 12, 5);
        g.setColor(skin);
        fill(g, ox + 10, oy + 9, 12, 6);
        g.setColor(tracksuit);
        fill(g, ox + 8, oy + 15, 16, 9); // Jacket
        g.setColor(stripe);
        fill(g, ox + 10, oy + 15, 1, 9); // Stripe
        fill(g, ox + 21, oy + 15, 1, 9);
        g.setColor(new Color(0xD4, 0xA0, 0x17)); // Gold chain
        fill(g, ox + 12, oy + 16, 8, 1);
        g.setColor(tracksuit); // Pants
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX, oy + 24, 5, 6);
        fill(g, rLegX, oy + 24, 5, 6);
        g.setColor(stripe);
        fill(g, lLegX + 2, oy + 24, 1, 6);
        fill(g, rLegX + 2, oy + 24, 1, 6);
    }

    
    private static void drawLiranDown(Graphics2D g, int ox, int oy) {
        // Liran: The Final Boss
        // Spiky Hair
        g.setColor(LIRAN_HAIR);
        fill(g, ox + 10, oy + 2, 12, 6);
        fill(g, ox + 14, oy + 1, 4, 2); // Spikes
        
        // Skin
        g.setColor(LIRAN_SKIN);
        fill(g, ox + 10, oy + 8, 12, 7);
        
        // Sunglasses
        g.setColor(Color.BLACK);
        fill(g, ox + 11, oy + 11, 4, 2);
        fill(g, ox + 17, oy + 11, 4, 2);
        g.drawLine(ox + 15, oy + 12, ox + 17, oy + 12);
        
        // Shirt (Tight Black Tee)
        g.setColor(LIRAN_SHIRT);
        fill(g, ox + 9, oy + 15, 14, 9);
        
        // Arms (Muscular)
        g.setColor(LIRAN_SKIN);
        fill(g, ox + 6, oy + 15, 3, 7);
        fill(g, ox + 23, oy + 15, 3, 7);
        
        // Heavy Gold Chain
        g.setColor(GOLD);
        fill(g, ox + 12, oy + 16, 8, 2);
        fill(g, ox + 14, oy + 18, 4, 2);
        
        // Pants
        g.setColor(LIRAN_PANTS);
        fill(g, ox + 10, oy + 24, 5, 7);
        fill(g, ox + 17, oy + 24, 5, 7);
        
        // Shoes (Fancy White Sneakers)
        g.setColor(Color.WHITE);
        fill(g, ox + 10, oy + 31, 5, 1);
        fill(g, ox + 17, oy + 31, 5, 1);
    }

    // --- Easter Egg Drawing Methods ---

    private static void drawRustyBike(Graphics2D g, int ox, int oy) {
        // Rusty brown electric bike frame - broken and abandoned
        Color rust = new Color(0x8B, 0x4C, 0x24);
        Color darkRust = new Color(0x5C, 0x33, 0x17);
        Color tire = new Color(0x30, 0x30, 0x30);
        Color flatTire = new Color(0x50, 0x50, 0x50);
        Color battery = new Color(0x44, 0x44, 0x44); // empty battery slot

        // Back wheel (flat - oval instead of circle)
        g.setColor(tire);
        fill(g, ox + 3, oy + 20, 10, 8);
        g.setColor(flatTire);
        fill(g, ox + 5, oy + 22, 6, 4);

        // Front wheel (flat)
        g.setColor(tire);
        fill(g, ox + 20, oy + 20, 10, 8);
        g.setColor(flatTire);
        fill(g, ox + 22, oy + 22, 6, 4);

        // Frame - rusty diagonal tube
        g.setColor(rust);
        fill(g, ox + 8, oy + 14, 16, 3); // top tube
        fill(g, ox + 8, oy + 14, 3, 10); // seat tube
        fill(g, ox + 21, oy + 14, 3, 10); // head tube
        fill(g, ox + 12, oy + 17, 10, 2); // down tube

        // Rust spots
        g.setColor(darkRust);
        fill(g, ox + 10, oy + 15, 2, 1);
        fill(g, ox + 18, oy + 15, 2, 1);
        fill(g, ox + 14, oy + 18, 2, 1);

        // Handlebars
        g.setColor(rust);
        fill(g, ox + 22, oy + 10, 2, 5);
        fill(g, ox + 20, oy + 10, 6, 2);

        // Seat
        g.setColor(darkRust);
        fill(g, ox + 6, oy + 11, 6, 3);

        // Empty battery slot (the stolen part!) - outlined box with X
        g.setColor(battery);
        fill(g, ox + 11, oy + 6, 10, 6);
        g.setColor(new Color(0xFF, 0x44, 0x44)); // red X = missing!
        fill(g, ox + 13, oy + 7, 2, 4);
        fill(g, ox + 17, oy + 7, 2, 4);
        fill(g, ox + 15, oy + 9, 2, 1);

        // Ground dust
        g.setColor(new Color(0xA0, 0x90, 0x70, 120));
        fill(g, ox + 1, oy + 28, 30, 3);
    }

    private static void drawGraffitiWall(Graphics2D g, int ox, int oy) {
        // Concrete wall with spray-painted heart and "S+L"
        Color wallColor = new Color(0xA0, 0x98, 0x88);
        Color wallDark = new Color(0x88, 0x80, 0x70);
        Color sprayRed = new Color(0xE0, 0x20, 0x30);
        Color sprayPink = new Color(0xFF, 0x66, 0x88);
        Color sprayBlack = new Color(0x22, 0x22, 0x22);

        // Wall background
        g.setColor(wallColor);
        fill(g, ox, oy, 32, 32);
        // Wall texture lines
        g.setColor(wallDark);
        fill(g, ox, oy + 8, 32, 1);
        fill(g, ox, oy + 16, 32, 1);
        fill(g, ox, oy + 24, 32, 1);
        fill(g, ox + 16, oy, 1, 8);
        fill(g, ox + 8, oy + 8, 1, 8);
        fill(g, ox + 24, oy + 8, 1, 8);
        fill(g, ox + 16, oy + 16, 1, 8);

        // Spray-painted heart shape (center)
        g.setColor(sprayRed);
        fill(g, ox + 8, oy + 6, 6, 4);   // left bump
        fill(g, ox + 18, oy + 6, 6, 4);  // right bump
        fill(g, ox + 7, oy + 9, 18, 4);  // middle
        fill(g, ox + 9, oy + 13, 14, 3); // lower
        fill(g, ox + 11, oy + 16, 10, 2);
        fill(g, ox + 13, oy + 18, 6, 2);
        fill(g, ox + 15, oy + 20, 2, 1);

        // Inner heart highlight
        g.setColor(sprayPink);
        fill(g, ox + 10, oy + 8, 3, 2);

        // "S+L" text inside heart
        g.setColor(sprayBlack);
        // S
        fill(g, ox + 10, oy + 11, 3, 1);
        fill(g, ox + 10, oy + 12, 1, 1);
        fill(g, ox + 10, oy + 13, 3, 1);
        fill(g, ox + 12, oy + 14, 1, 1);
        fill(g, ox + 10, oy + 15, 3, 1);
        // +
        fill(g, ox + 15, oy + 13, 1, 3);
        fill(g, ox + 14, oy + 14, 3, 1);
        // L
        fill(g, ox + 18, oy + 11, 1, 5);
        fill(g, ox + 18, oy + 15, 3, 1);

        // Drip effect from heart
        g.setColor(sprayRed);
        fill(g, ox + 15, oy + 21, 1, 2);
        fill(g, ox + 9, oy + 16, 1, 2);

        // Scrawled "FINISHED" at bottom
        g.setColor(sprayBlack);
        fill(g, ox + 3, oy + 27, 26, 1);
        fill(g, ox + 5, oy + 26, 2, 1);
        fill(g, ox + 11, oy + 26, 2, 1);
    }

    private static void drawAbuRafi(Graphics2D g, int ox, int oy) {
        // Old wise man with white keffiyeh, brown robe, and walking cane
        Color skin = new Color(0xC0, 0x99, 0x70);
        Color robe = new Color(0x6B, 0x4E, 0x31); // brown robe
        Color keffiyeh = new Color(0xF0, 0xF0, 0xE8); // white headwear
        Color keffiyehBand = new Color(0x22, 0x22, 0x22); // black agal
        Color cane = new Color(0x8B, 0x6D, 0x44);
        Color beard = new Color(0xD0, 0xD0, 0xD0);

        // Keffiyeh (headwear - draped)
        g.setColor(keffiyeh);
        fill(g, ox + 9, oy + 1, 14, 8);
        fill(g, ox + 7, oy + 5, 4, 6);  // left drape
        fill(g, ox + 21, oy + 5, 4, 6); // right drape
        // Agal (black band)
        g.setColor(keffiyehBand);
        fill(g, ox + 10, oy + 4, 12, 2);

        // Face
        g.setColor(skin);
        fill(g, ox + 11, oy + 6, 10, 6);
        // Eyes
        g.setColor(Color.BLACK);
        fill(g, ox + 13, oy + 8, 2, 2);
        fill(g, ox + 18, oy + 8, 2, 2);

        // White beard
        g.setColor(beard);
        fill(g, ox + 11, oy + 11, 10, 4);
        fill(g, ox + 13, oy + 15, 6, 2);

        // Robe (long brown)
        g.setColor(robe);
        fill(g, ox + 9, oy + 14, 14, 12);
        // Robe shading
        g.setColor(new Color(0x55, 0x3D, 0x25));
        fill(g, ox + 10, oy + 18, 2, 8);
        fill(g, ox + 20, oy + 18, 2, 8);

        // Arms
        g.setColor(robe);
        fill(g, ox + 6, oy + 16, 4, 6);
        fill(g, ox + 22, oy + 16, 4, 6);
        // Hands
        g.setColor(skin);
        fill(g, ox + 6, oy + 22, 4, 2);
        fill(g, ox + 22, oy + 22, 4, 2);

        // Walking cane
        g.setColor(cane);
        fill(g, ox + 26, oy + 16, 2, 14);
        fill(g, ox + 25, oy + 15, 4, 2); // handle

        // Sandals
        g.setColor(new Color(0x7A, 0x5C, 0x3C));
        fill(g, ox + 10, oy + 26, 5, 4);
        fill(g, ox + 17, oy + 26, 5, 4);
    }

    // --- Missing Sprites (formerly placeholders) ---

    private static void drawMiriKapara(Graphics2D g, int ox, int oy) {
        // Miri: Purple dress, white hair bun, glasses, warm smile
        Color skin = new Color(0xE0, 0xC0, 0xA0);
        Color dress = new Color(0x60, 0x20, 0x80); // Purple
        Color hair = new Color(0xE0, 0xE0, 0xE0); // White
        Color apron = new Color(0xF0, 0xE0, 0xD0); // Cream apron

        // White hair bun
        g.setColor(hair);
        fill(g, ox + 11, oy, 10, 5);
        fill(g, ox + 10, oy + 3, 12, 4);

        // Face
        g.setColor(skin);
        fill(g, ox + 11, oy + 6, 10, 7);

        // Glasses
        g.setColor(Color.BLACK);
        fill(g, ox + 12, oy + 8, 3, 2); // left lens
        fill(g, ox + 17, oy + 8, 3, 2); // right lens
        fill(g, ox + 15, oy + 9, 2, 1); // bridge

        // Warm smile
        g.setColor(new Color(0xC0, 0x60, 0x60));
        fill(g, ox + 14, oy + 11, 4, 1);

        // Purple dress
        g.setColor(dress);
        fill(g, ox + 9, oy + 13, 14, 12);
        // Floral pattern dots
        g.setColor(new Color(0xD0, 0x60, 0xA0));
        fill(g, ox + 11, oy + 15, 2, 2);
        fill(g, ox + 17, oy + 18, 2, 2);
        fill(g, ox + 13, oy + 21, 2, 2);

        // Cream apron
        g.setColor(apron);
        fill(g, ox + 12, oy + 17, 8, 8);

        // Arms
        g.setColor(skin);
        fill(g, ox + 6, oy + 14, 4, 5);
        fill(g, ox + 22, oy + 14, 4, 5);

        // Shoes
        g.setColor(new Color(0x5A, 0x3A, 0x22));
        fill(g, ox + 10, oy + 25, 5, 4);
        fill(g, ox + 17, oy + 25, 5, 4);
    }

    private static void drawGoldenAmba(Graphics2D g, int ox, int oy) {
        // Golden amba jar: glowing golden jar with shimmer
        Color glow = new Color(0xFF, 0xE0, 0x40, 80);
        Color jar = new Color(0xDA, 0xA5, 0x20); // Goldenrod
        Color jarDark = new Color(0xB8, 0x86, 0x0B);
        Color liquid = new Color(0xFF, 0xD7, 0x00);
        Color lid = new Color(0x40, 0x40, 0x40);
        Color shine = Color.WHITE;

        // Outer glow
        g.setColor(glow);
        fill(g, ox + 4, oy + 4, 24, 24);

        // Jar body
        g.setColor(jar);
        fill(g, ox + 8, oy + 8, 16, 18);
        fill(g, ox + 7, oy + 10, 18, 14);

        // Dark side shading
        g.setColor(jarDark);
        fill(g, ox + 20, oy + 10, 5, 14);

        // Liquid inside
        g.setColor(liquid);
        fill(g, ox + 10, oy + 12, 10, 10);

        // Lid
        g.setColor(lid);
        fill(g, ox + 9, oy + 6, 14, 4);

        // Shimmer highlights
        g.setColor(shine);
        fill(g, ox + 11, oy + 10, 2, 6);
        fill(g, ox + 14, oy + 9, 1, 3);

        // "AMBA" label area
        g.setColor(new Color(0xFF, 0xF0, 0xD0));
        fill(g, ox + 10, oy + 18, 10, 4);
        g.setColor(jarDark);
        fill(g, ox + 11, oy + 19, 8, 2); // text line
    }

    private static void drawWoodTile(Graphics2D g, int ox, int oy) {
        // Wooden planks tile
        Color wood = new Color(0x8B, 0x6D, 0x44);
        Color woodDark = new Color(0x6B, 0x4D, 0x2E);
        Color woodLight = new Color(0xA5, 0x85, 0x5A);
        Color grain = new Color(0x7A, 0x5C, 0x38);

        // Base
        g.setColor(wood);
        fill(g, ox, oy, 32, 32);

        // Plank lines (horizontal)
        g.setColor(woodDark);
        fill(g, ox, oy + 7, 32, 1);
        fill(g, ox, oy + 15, 32, 1);
        fill(g, ox, oy + 23, 32, 1);
        fill(g, ox, oy + 31, 32, 1);

        // Wood grain streaks
        g.setColor(grain);
        fill(g, ox + 5, oy + 2, 8, 1);
        fill(g, ox + 18, oy + 10, 6, 1);
        fill(g, ox + 3, oy + 18, 10, 1);
        fill(g, ox + 20, oy + 26, 7, 1);

        // Light highlights
        g.setColor(woodLight);
        fill(g, ox + 2, oy + 1, 12, 1);
        fill(g, ox + 14, oy + 9, 8, 1);
        fill(g, ox + 6, oy + 17, 6, 1);
        fill(g, ox + 22, oy + 25, 4, 1);

        // Nail dots
        g.setColor(new Color(0x55, 0x55, 0x55));
        fill(g, ox + 3, oy + 4, 1, 1);
        fill(g, ox + 28, oy + 4, 1, 1);
        fill(g, ox + 3, oy + 12, 1, 1);
        fill(g, ox + 28, oy + 12, 1, 1);
        fill(g, ox + 3, oy + 20, 1, 1);
        fill(g, ox + 28, oy + 20, 1, 1);
        fill(g, ox + 3, oy + 28, 1, 1);
        fill(g, ox + 28, oy + 28, 1, 1);
    }

    private static void drawOatMilkCortado(Graphics2D g, int ox, int oy) {
        // Oat milk cortado in a takeaway cup
        Color cup = new Color(0xF0, 0xE8, 0xD8); // Cream
        Color cupDark = new Color(0xD0, 0xC0, 0xA8);
        Color lid = new Color(0x4A, 0x3A, 0x2A); // Brown lid
        Color sleeve = new Color(0xA8, 0x8B, 0x6A); // Cardboard sleeve
        Color coffee = new Color(0x6F, 0x4E, 0x37);
        Color steam = new Color(0xE0, 0xE0, 0xE0, 120);

        // Steam wisps
        g.setColor(steam);
        fill(g, ox + 14, oy + 1, 2, 3);
        fill(g, ox + 17, oy + 2, 2, 2);
        fill(g, ox + 12, oy + 3, 2, 2);

        // Lid
        g.setColor(lid);
        fill(g, ox + 9, oy + 5, 14, 4);
        // Sip hole
        g.setColor(coffee);
        fill(g, ox + 14, oy + 6, 3, 2);

        // Cup body (tapered)
        g.setColor(cup);
        fill(g, ox + 9, oy + 9, 14, 18);
        // Darker side
        g.setColor(cupDark);
        fill(g, ox + 20, oy + 9, 3, 18);

        // Cardboard sleeve
        g.setColor(sleeve);
        fill(g, ox + 9, oy + 14, 14, 6);

        // "OAT" text on sleeve
        g.setColor(new Color(0x4A, 0x3A, 0x2A));
        fill(g, ox + 11, oy + 16, 2, 2); // O
        fill(g, ox + 14, oy + 16, 2, 2); // A
        fill(g, ox + 17, oy + 16, 2, 2); // T

        // Cup bottom
        g.setColor(cupDark);
        fill(g, ox + 10, oy + 27, 12, 2);

        // Table surface
        g.setColor(new Color(0x8B, 0x6D, 0x44));
        fill(g, ox + 4, oy + 28, 24, 3);
    }

    // --- Quest NPC and Item Drawing Methods ---

    private static void drawTzionBarber(Graphics2D g, int ox, int oy) {
        // Israeli barber: blue apron, styled dark hair, scissors in hand
        Color skin = new Color(0xD4, 0xA8, 0x78);
        Color apron = new Color(0x30, 0x60, 0xB0); // Blue barber apron
        Color hair = new Color(0x1A, 0x0E, 0x04);
        Color scissors = new Color(0xC0, 0xC0, 0xC0);

        // Hair (styled pompadour)
        g.setColor(hair);
        fill(g, ox + 10, oy + 1, 12, 7);
        fill(g, ox + 9, oy + 3, 2, 4);  // side
        fill(g, ox + 22, oy + 3, 2, 4); // side
        // pompadour bump
        fill(g, ox + 12, oy, 8, 3);

        // Face
        g.setColor(skin);
        fill(g, ox + 11, oy + 6, 10, 7);
        // Eyes
        g.setColor(Color.BLACK);
        fill(g, ox + 13, oy + 8, 2, 2);
        fill(g, ox + 18, oy + 8, 2, 2);
        // Mustache
        fill(g, ox + 13, oy + 11, 7, 2);

        // Blue Apron
        g.setColor(apron);
        fill(g, ox + 9, oy + 13, 14, 13);
        // Apron pocket
        g.setColor(new Color(0x25, 0x50, 0x95));
        fill(g, ox + 12, oy + 17, 8, 4);

        // Arms
        g.setColor(skin);
        fill(g, ox + 5, oy + 14, 5, 6);
        fill(g, ox + 22, oy + 14, 5, 6);

        // Scissors in right hand
        g.setColor(scissors);
        fill(g, ox + 24, oy + 12, 2, 8);
        fill(g, ox + 26, oy + 14, 2, 4);
        g.setColor(new Color(0x90, 0x90, 0x90));
        fill(g, ox + 23, oy + 15, 1, 2);

        // Shoes
        g.setColor(Color.BLACK);
        fill(g, ox + 10, oy + 26, 5, 4);
        fill(g, ox + 17, oy + 26, 5, 4);
    }

    private static void drawAviGymBoss(Graphics2D g, int ox, int oy) {
        // Big muscular dude: red tank top, gold chain, shaved head
        Color skin = new Color(0xCC, 0x99, 0x66);
        Color tank = new Color(0xD0, 0x20, 0x20); // Red tank top
        Color chain = new Color(0xD4, 0xA0, 0x17);

        // Shaved head
        g.setColor(skin);
        fill(g, ox + 9, oy + 2, 14, 10);
        // Stubble shadow
        g.setColor(new Color(0xB0, 0x80, 0x55));
        fill(g, ox + 10, oy + 2, 12, 2);

        // Eyes (angry)
        g.setColor(Color.BLACK);
        fill(g, ox + 12, oy + 5, 3, 2);
        fill(g, ox + 18, oy + 5, 3, 2);
        // Angry eyebrows
        fill(g, ox + 11, oy + 4, 4, 1);
        fill(g, ox + 18, oy + 4, 4, 1);
        // Mouth (grimace)
        fill(g, ox + 14, oy + 9, 5, 1);

        // Thick neck
        g.setColor(skin);
        fill(g, ox + 12, oy + 11, 8, 3);

        // Gold chain
        g.setColor(chain);
        fill(g, ox + 11, oy + 12, 10, 2);

        // Red tank top (wide shoulders = big muscles)
        g.setColor(tank);
        fill(g, ox + 6, oy + 14, 20, 10);
        // Muscle bulge arms
        g.setColor(skin);
        fill(g, ox + 3, oy + 14, 5, 8);  // Left bicep
        fill(g, ox + 24, oy + 14, 5, 8); // Right bicep
        // Forearms
        fill(g, ox + 2, oy + 22, 5, 3);
        fill(g, ox + 25, oy + 22, 5, 3);

        // Shorts
        g.setColor(Color.BLACK);
        fill(g, ox + 8, oy + 24, 16, 4);

        // Shoes
        g.setColor(new Color(0xE0, 0xE0, 0xE0));
        fill(g, ox + 9, oy + 28, 6, 3);
        fill(g, ox + 17, oy + 28, 6, 3);
    }

    private static void drawYotamTelAvivian(Graphics2D g, int ox, int oy) {
        // Hipster Tel-Avivian: man bun, round glasses, beige tote bag
        Color skin = new Color(0xE0, 0xC0, 0xA0);
        Color shirt = new Color(0xF0, 0xF0, 0xE0); // Off-white linen
        Color pants = new Color(0x50, 0x50, 0x50);
        Color hair = new Color(0x4A, 0x2E, 0x14);
        Color tote = new Color(0xD4, 0xC0, 0x90);

        // Man bun on top
        g.setColor(hair);
        fill(g, ox + 13, oy, 6, 4);
        // Hair sides
        fill(g, ox + 10, oy + 3, 12, 5);

        // Face
        g.setColor(skin);
        fill(g, ox + 11, oy + 6, 10, 7);
        // Round glasses
        g.setColor(Color.BLACK);
        fill(g, ox + 12, oy + 7, 4, 3); // left lens outline
        fill(g, ox + 17, oy + 7, 4, 3); // right lens outline
        g.setColor(new Color(0xCC, 0xDD, 0xFF)); // lens tint
        fill(g, ox + 13, oy + 8, 2, 1);
        fill(g, ox + 18, oy + 8, 2, 1);
        // Bridge
        g.setColor(Color.BLACK);
        fill(g, ox + 16, oy + 8, 1, 1);
        // Beard (small)
        g.setColor(hair);
        fill(g, ox + 12, oy + 11, 8, 2);

        // Off-white linen shirt
        g.setColor(shirt);
        fill(g, ox + 9, oy + 13, 14, 10);
        // Arms
        g.setColor(skin);
        fill(g, ox + 6, oy + 14, 4, 6);
        fill(g, ox + 22, oy + 14, 4, 6);

        // Tote bag (left shoulder)
        g.setColor(tote);
        fill(g, ox + 4, oy + 13, 6, 13);
        g.setColor(new Color(0xC0, 0xA8, 0x78));
        fill(g, ox + 5, oy + 13, 1, 13); // strap

        // Dark pants
        g.setColor(pants);
        fill(g, ox + 10, oy + 23, 5, 6);
        fill(g, ox + 17, oy + 23, 5, 6);

        // Sandals
        g.setColor(new Color(0x8B, 0x6D, 0x44));
        fill(g, ox + 10, oy + 28, 5, 3);
        fill(g, ox + 17, oy + 28, 5, 3);
    }

    private static void drawEBikeBattery(Graphics2D g, int ox, int oy) {
        // Electric bike battery: black rectangle with yellow lightning bolt
        Color body = new Color(0x2A, 0x2A, 0x2A);
        Color accent = new Color(0x3A, 0x3A, 0x3A);
        Color bolt = new Color(0xFF, 0xD7, 0x00); // Golden bolt
        Color glow = new Color(0xFF, 0xE0, 0x40, 100);
        Color terminal = new Color(0xC0, 0xC0, 0xC0);

        // Glow effect
        g.setColor(glow);
        fill(g, ox + 4, oy + 3, 24, 26);

        // Battery body
        g.setColor(body);
        fill(g, ox + 6, oy + 5, 20, 22);
        // Terminal nub (top)
        g.setColor(terminal);
        fill(g, ox + 13, oy + 3, 6, 3);

        // Accent lines
        g.setColor(accent);
        fill(g, ox + 7, oy + 6, 18, 1);
        fill(g, ox + 7, oy + 25, 18, 1);

        // Lightning bolt ⚡
        g.setColor(bolt);
        fill(g, ox + 17, oy + 8, 4, 2);
        fill(g, ox + 15, oy + 10, 4, 2);
        fill(g, ox + 13, oy + 12, 8, 2);
        fill(g, ox + 15, oy + 14, 4, 2);
        fill(g, ox + 13, oy + 16, 4, 2);
        fill(g, ox + 11, oy + 18, 4, 2);

        // "36V" text area
        g.setColor(new Color(0x80, 0x80, 0x80));
        fill(g, ox + 10, oy + 22, 12, 3);
    }

    private static void drawStrongHoldWax(Graphics2D g, int ox, int oy) {
        // Hair wax jar: purple jar with lid and label
        Color jar = new Color(0x6A, 0x1B, 0x9A); // Purple
        Color jarLight = new Color(0x8E, 0x3E, 0xBE);
        Color lid = new Color(0x22, 0x22, 0x22);
        Color label = new Color(0xF0, 0xF0, 0xE0);
        Color labelText = new Color(0x22, 0x22, 0x22);

        // Jar body (rounded rectangle)
        g.setColor(jar);
        fill(g, ox + 8, oy + 10, 16, 16);
        fill(g, ox + 7, oy + 12, 18, 12);
        // Highlight
        g.setColor(jarLight);
        fill(g, ox + 9, oy + 11, 4, 14);

        // Lid
        g.setColor(lid);
        fill(g, ox + 7, oy + 7, 18, 4);
        fill(g, ox + 9, oy + 5, 14, 3);

        // Label
        g.setColor(label);
        fill(g, ox + 10, oy + 15, 12, 8);
        // "WAX" text
        g.setColor(labelText);
        fill(g, ox + 11, oy + 16, 2, 5); // W left
        fill(g, ox + 13, oy + 19, 1, 2); // W mid
        fill(g, ox + 14, oy + 16, 2, 5); // W right
        fill(g, ox + 17, oy + 16, 4, 1); // A top
        fill(g, ox + 17, oy + 19, 4, 1); // A mid
        fill(g, ox + 17, oy + 17, 1, 4); // A left
        fill(g, ox + 20, oy + 17, 1, 4); // A right

        // Table surface
        g.setColor(new Color(0x8B, 0x6D, 0x44));
        fill(g, ox + 4, oy + 26, 24, 4);
    }

    private static void fill(Graphics2D g, int x, int y, int w, int h) {
        g.fillRect(x, y, w, h);
    }
}
