package com.ashkelord.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class SpriteSheetGenerator {

    private static final int S = 32;
    private static final int COLS = 8;
    private static final int ROWS = 17; // Rows 12-16: Bald Player
    
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

        // --- Bald Player (Rows 12-16) ---
        // Row 12: Bald Player Down
        drawBaldCharDown(g, 0, S * 12, skin, shirt, pants, shoes, chain, 0);
        drawBaldCharDown(g, S, S * 12, skin, shirt, pants, shoes, chain, -1);
        drawBaldCharDown(g, S * 2, S * 12, skin, shirt, pants, shoes, chain, 1);
        drawBaldCharDown(g, S * 3, S * 12, skin, shirt, pants, shoes, chain, -2);
        // Row 13: Bald Player Up
        drawBaldCharUp(g, 0, S * 13, skin, shirt, pants, shoes, chain, 0);
        drawBaldCharUp(g, S, S * 13, skin, shirt, pants, shoes, chain, -1);
        drawBaldCharUp(g, S * 2, S * 13, skin, shirt, pants, shoes, chain, 1);
        drawBaldCharUp(g, S * 3, S * 13, skin, shirt, pants, shoes, chain, -2);
        // Row 14: Bald Player Left
        drawBaldCharSide(g, 0, S * 14, skin, shirt, pants, shoes, chain, 0, true);
        drawBaldCharSide(g, S, S * 14, skin, shirt, pants, shoes, chain, -1, true);
        drawBaldCharSide(g, S * 2, S * 14, skin, shirt, pants, shoes, chain, 1, true);
        drawBaldCharSide(g, S * 3, S * 14, skin, shirt, pants, shoes, chain, -2, true);
        // Row 15: Bald Player Right
        drawBaldCharSide(g, 0, S * 15, skin, shirt, pants, shoes, chain, 0, false);
        drawBaldCharSide(g, S, S * 15, skin, shirt, pants, shoes, chain, -1, false);
        drawBaldCharSide(g, S * 2, S * 15, skin, shirt, pants, shoes, chain, 1, false);
        drawBaldCharSide(g, S * 3, S * 15, skin, shirt, pants, shoes, chain, -2, false);
        // Row 16: Bald Spitting
        drawBaldCharSpit(g, 0, S * 16, skin, shirt, pants, shoes, chain, 0); // Down
        drawBaldCharSpit(g, S, S * 16, skin, shirt, pants, shoes, chain, 1); // Up
        drawBaldCharSpit(g, S * 2, S * 16, skin, shirt, pants, shoes, chain, 2); // Left
        drawBaldCharSpit(g, S * 3, S * 16, skin, shirt, pants, shoes, chain, 3); // Right

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
        // Larger spit projectile (Big Loogie) 16-bit detailed
        fillShadow(g, ox + 8, oy + 26, 16, 4); // Shadow on ground

        // Main body Outline
        g.setColor(new Color(0x60, 0x90, 0xC0));
        fill(g, ox + 9, oy + 9, 12, 12);

        // Main body
        g.setColor(new Color(0xE0, 0xF0, 0xFF)); // White-ish
        fill(g, ox + 10, oy + 10, 10, 10);
        
        // Shading/Texture
        g.setColor(new Color(0x80, 0xB0, 0xD0)); // Dark Blue tint
        fill(g, ox + 12, oy + 13, 8, 7);
        fill(g, ox + 16, oy + 11, 4, 9);
        
        // Highlights
        g.setColor(Color.WHITE);
        fill(g, ox + 11, oy + 11, 3, 2);
        fill(g, ox + 11, oy + 13, 1, 3);
        // Secondary reflection
        fill(g, ox + 18, oy + 17, 1, 1);
        
        // Trailing droplets (detailed)
        // Drop 1 outline
        g.setColor(new Color(0x60, 0x90, 0xC0));
        fill(g, ox + 7, oy + 15, 4, 4);
        g.setColor(new Color(0xC0, 0xE0, 0xFF));
        fill(g, ox + 8, oy + 16, 2, 2);
        g.setColor(Color.WHITE);
        fill(g, ox + 8, oy + 16, 1, 1);
        
        // Drop 2 outline
        g.setColor(new Color(0x60, 0x90, 0xC0));
        fill(g, ox + 13, oy + 7, 4, 4);
        g.setColor(new Color(0xC0, 0xE0, 0xFF));
        fill(g, ox + 14, oy + 8, 2, 2);
        g.setColor(Color.WHITE);
        fill(g, ox + 14, oy + 8, 1, 1);
        
        // Drop 3 outline
        g.setColor(new Color(0x60, 0x90, 0xC0));
        fill(g, ox + 21, oy + 11, 4, 4);
        g.setColor(new Color(0xC0, 0xE0, 0xFF));
        fill(g, ox + 22, oy + 12, 2, 2);
        g.setColor(Color.WHITE);
        fill(g, ox + 22, oy + 12, 1, 1);
    }

    // --- Player Drawing Methods (Refactored to be generic for Player) ---
    private static void drawCharDown(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color hair, Color shoes, Color chain, int step) {
        fillShadow(g, ox + 6, oy + 28, 20, 4); // Drop shadow
        
        // Hair (Back bulk)
        g.setColor(Color.BLACK); // Outline
        fill(g, ox + 8, oy + 0, 16, 10);
        g.setColor(hair);
        fill(g, ox + 9, oy + 1, 14, 8); 
        g.setColor(new Color(0x40, 0x30, 0x20));
        fill(g, ox + 13, oy + 3, 3, 2); // Shine
        
        // Head / Face
        g.setColor(Color.BLACK); // Outline
        fill(g, ox + 8, oy + 7, 16, 9);
        g.setColor(skin);
        fill(g, ox + 9, oy + 8, 14, 7); // Face
        // Ears
        fill(g, ox + 7, oy + 9, 2, 4);
        fill(g, ox + 23, oy + 9, 2, 4);
        
        // Shading on face (under hair)
        g.setColor(new Color(skin.getRed()*4/5, skin.getGreen()*4/5, skin.getBlue()*4/5));
        fill(g, ox + 9, oy + 8, 14, 1);
        
        g.setColor(Color.BLACK); // Eyes
        fill(g, ox + 12, oy + 12, 2, 2);
        fill(g, ox + 18, oy + 12, 2, 2);
        
        // Shirt
        g.setColor(Color.BLACK); // Shirt Outline
        fill(g, ox + 8, oy + 15, 16, 9);
        g.setColor(shirt);
        fill(g, ox + 9, oy + 16, 14, 7);
        // Shirt Highlight
        g.setColor(new Color(Math.min(255, shirt.getRed()+30), Math.min(255, shirt.getGreen()+30), Math.min(255, shirt.getBlue()+30)));
        fill(g, ox + 10, oy + 16, 12, 2);
        
        // Arms
        int armL = (step == 1) ? 2 : ((step == -1) ? -2 : 0);
        int armR = (step == -1) ? 2 : ((step == 1) ? -2 : 0);
        g.setColor(Color.BLACK); // Arm outlines
        fill(g, ox + 4, oy + 15 + armL, 6, 8);
        fill(g, ox + 22, oy + 15 + armR, 6, 8);
        g.setColor(skin);
        fill(g, ox + 5, oy + 16 + armL, 4, 6);
        fill(g, ox + 23, oy + 16 + armR, 4, 6);
        
        // Chain
        g.setColor(chain);
        fill(g, ox + 13, oy + 17, 6, 1);
        fill(g, ox + 14, oy + 18, 4, 1);
        fill(g, ox + 15, oy + 19, 2, 1);
        // Chain sparkle
        g.setColor(Color.WHITE);
        fill(g, ox + 15, oy + 17, 1, 1);
        
        // Pants
        g.setColor(Color.BLACK); // Pants outline
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX - 1, oy + 23, 7, 7);
        fill(g, rLegX - 1, oy + 23, 7, 7);
        g.setColor(pants);
        fill(g, lLegX, oy + 23, 5, 7);
        fill(g, rLegX, oy + 23, 5, 7);
        // Pants shadow
        g.setColor(new Color(pants.getRed()*3/4, pants.getGreen()*3/4, pants.getBlue()*3/4));
        fill(g, lLegX + 3, oy + 23, 2, 7);
        fill(g, rLegX + 3, oy + 23, 2, 7);
        
        // Shoes
        g.setColor(Color.BLACK); // Shoe outline
        fill(g, lLegX - 2, oy + 29, 8, 4);
        fill(g, rLegX - 2, oy + 29, 8, 4);
        g.setColor(shoes);
        fill(g, lLegX - 1, oy + 30, 6, 2);
        fill(g, rLegX - 1, oy + 30, 6, 2);
        g.setColor(Color.WHITE); // Shoe highlight
        fill(g, lLegX, oy + 30, 2, 1);
        fill(g, rLegX, oy + 30, 2, 1);
    }

    private static void drawCharUp(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color hair, Color shoes, Color chain, int step) {
        fillShadow(g, ox + 6, oy + 28, 20, 4); // Drop shadow
        
        g.setColor(Color.BLACK); // Head outline
        fill(g, ox + 8, oy + 7, 16, 9);
        g.setColor(skin); // Neck and ears
        fill(g, ox + 7, oy + 9, 2, 4);
        fill(g, ox + 23, oy + 9, 2, 4);
        
        // Hair (covers back of head)
        g.setColor(Color.BLACK); // Hair outline
        fill(g, ox + 8, oy + 1, 16, 15);
        g.setColor(hair); // Full back of hair
        fill(g, ox + 9, oy + 2, 14, 13);
        g.setColor(new Color(0x40, 0x30, 0x20));
        fill(g, ox + 13, oy + 4, 6, 2); // Highlight
        
        // Shirt
        g.setColor(Color.BLACK); // Outline
        fill(g, ox + 8, oy + 15, 16, 9);
        g.setColor(shirt);
        fill(g, ox + 9, oy + 16, 14, 7);
        // Shirt Shadow (back)
        g.setColor(new Color(shirt.getRed()*3/4, shirt.getGreen()*3/4, shirt.getBlue()*3/4));
        fill(g, ox + 9, oy + 16, 14, 2);
        
        // Arms
        int armL = (step == 1) ? 2 : ((step == -1) ? -2 : 0);
        int armR = (step == -1) ? 2 : ((step == 1) ? -2 : 0);
        g.setColor(Color.BLACK); // Arm outlines
        fill(g, ox + 4, oy + 15 + armL, 6, 8);
        fill(g, ox + 22, oy + 15 + armR, 6, 8);
        g.setColor(skin);
        fill(g, ox + 5, oy + 16 + armL, 4, 6);
        fill(g, ox + 23, oy + 16 + armR, 4, 6);
        
        // Pants & Shoes
        g.setColor(Color.BLACK); // Pants outline
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX - 1, oy + 23, 7, 7);
        fill(g, rLegX - 1, oy + 23, 7, 7);
        g.setColor(pants);
        fill(g, lLegX, oy + 23, 5, 7);
        fill(g, rLegX, oy + 23, 5, 7);
        
        g.setColor(Color.BLACK); // Shoe outline
        fill(g, lLegX - 1, oy + 29, 7, 4);
        fill(g, rLegX - 1, oy + 29, 7, 4);
        g.setColor(shoes);
        fill(g, lLegX, oy + 30, 5, 2);
        fill(g, rLegX, oy + 30, 5, 2);
    }

    private static void drawCharSide(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color hair, Color shoes, Color chain, int step, boolean left) {
        fillShadow(g, ox + 6, oy + 28, 20, 4); // Drop shadow
        int faceDir = left ? -1 : 1;
        int cx = ox + 16;
        
        // Hair (Back bulk)
        g.setColor(Color.BLACK); // Hair outline
        fill(g, cx - 6, oy + 1, 12, 11);
        g.setColor(hair);
        fill(g, cx - 5, oy + 2, 10, 9);
        if(!left) fill(g, cx - 5, oy + 8, 4, 3); // Back of hair
        if(left) fill(g, cx + 1, oy + 8, 4, 3);
        
        // Head / Face
        g.setColor(Color.BLACK); // Face Outline
        fill(g, cx - 7, oy + 7, 14, 9);
        g.setColor(skin);
        fill(g, cx - 6, oy + 8, 12, 7);
        // Face Shadow
        g.setColor(new Color(skin.getRed()*4/5, skin.getGreen()*4/5, skin.getBlue()*4/5));
        fill(g, cx - 6, oy + 8, 12, 1);
        
        g.setColor(Color.BLACK);
        fill(g, cx + faceDir * 2, oy + 11, 2, 2); // Eye
        
        // Shirt
        g.setColor(Color.BLACK); // Outline
        fill(g, cx - 6, oy + 15, 12, 9);
        g.setColor(shirt);
        fill(g, cx - 5, oy + 16, 10, 7);
        // Shirt Highlight
        g.setColor(new Color(Math.min(255, shirt.getRed()+30), Math.min(255, shirt.getGreen()+30), Math.min(255, shirt.getBlue()+30)));
        fill(g, cx - 4, oy + 16, 8, 2);
        
        // Arms
        int armSwing = step * 3;
        g.setColor(Color.BLACK); // Arm outline
        fill(g, cx - 3, oy + 15 + armSwing, 6, 9);
        g.setColor(skin);
        fill(g, cx - 2, oy + 16 + armSwing, 4, 7); // Active arm
        
        // Chain
        g.setColor(chain);
        fill(g, cx + faceDir * 1, oy + 16, 2, 1);
        
        // Pants
        int legSep = step * 3;
        g.setColor(Color.BLACK); // Pants outline
        fill(g, cx - 5, oy + 23 - Math.abs(legSep), 6, 7 + Math.abs(legSep)); // Back leg
        fill(g, cx, oy + 23, 6, 7); // Front leg
        
        g.setColor(pants); // Legs with stride
        fill(g, cx - 4, oy + 23 - Math.abs(legSep), 4, 7 + Math.abs(legSep)); // Back leg
        fill(g, cx + 1, oy + 23, 4, 7); // Front leg
        
        // Shoes
        g.setColor(Color.BLACK); // Shoe outline
        fill(g, cx - 5 + Math.max(0, -legSep), oy + 29, 7, 4);
        fill(g, cx + Math.max(0, legSep), oy + 29, 7, 4);
        g.setColor(shoes);
        fill(g, cx - 4 + Math.max(0, -legSep), oy + 30, 5, 2);
        fill(g, cx + 1 + Math.max(0, legSep), oy + 30, 5, 2);
    }

    // --- Bald Player Drawing Methods ---
    private static void drawBaldCharDown(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color shoes, Color chain, int step) {
        fillShadow(g, ox + 6, oy + 28, 20, 4); 
        g.setColor(Color.BLACK); 
        fill(g, ox + 9, oy + 1, 14, 15); // Head Outline
        g.setColor(skin);
        fill(g, ox + 10, oy + 2, 12, 13); // Full head
        g.setColor(new Color(0xE8, 0xC8, 0xA8)); // Shine on bald head
        fill(g, ox + 13, oy + 3, 4, 2);
        fill(g, ox + 14, oy + 5, 2, 1);
        
        g.setColor(skin);
        fill(g, ox + 8, oy + 9, 2, 4); // Ears
        fill(g, ox + 22, oy + 9, 2, 4);
        
        g.setColor(new Color(skin.getRed()/2, skin.getGreen()/2, skin.getBlue()/2));
        fill(g, ox + 14, oy + 15, 4, 1); // Neck shadow
        
        g.setColor(Color.BLACK);
        fill(g, ox + 13, oy + 12, 2, 2);
        fill(g, ox + 19, oy + 12, 2, 2);
        
        g.setColor(new Color(shirt.getRed()/2, shirt.getGreen()/2, shirt.getBlue()/2)); 
        fill(g, ox + 9, oy + 15, 14, 9);
        g.setColor(shirt);
        fill(g, ox + 10, oy + 16, 12, 7);
        
        g.setColor(skin);
        int armL = (step == 1) ? 2 : ((step == -1) ? -2 : 0);
        int armR = (step == -1) ? 2 : ((step == 1) ? -2 : 0);
        fill(g, ox + 5, oy + 16 + armL, 4, 6);
        fill(g, ox + 23, oy + 16 + armR, 4, 6);
        
        g.setColor(chain);
        fill(g, ox + 13, oy + 17, 6, 1);
        fill(g, ox + 14, oy + 18, 4, 1);
        fill(g, ox + 15, oy + 19, 2, 1);
        
        g.setColor(pants);
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX, oy + 23, 5, 7);
        fill(g, rLegX, oy + 23, 5, 7);
        
        g.setColor(shoes);
        fill(g, lLegX - 1, oy + 29, 6, 3);
        fill(g, rLegX - 1, oy + 29, 6, 3);
        g.setColor(Color.WHITE); 
        fill(g, lLegX, oy + 29, 2, 1);
        fill(g, rLegX, oy + 29, 2, 1);
    }

    private static void drawBaldCharUp(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color shoes, Color chain, int step) {
        fillShadow(g, ox + 6, oy + 28, 20, 4); 
        g.setColor(Color.BLACK); 
        fill(g, ox + 9, oy + 1, 14, 15);
        g.setColor(skin);
        fill(g, ox + 10, oy + 2, 12, 13);
        g.setColor(new Color(0xE8, 0xC8, 0xA8)); 
        fill(g, ox + 13, oy + 4, 6, 2);
        
        g.setColor(skin);
        fill(g, ox + 8, oy + 9, 2, 4);
        fill(g, ox + 22, oy + 9, 2, 4);
        
        g.setColor(new Color(shirt.getRed()/2, shirt.getGreen()/2, shirt.getBlue()/2)); 
        fill(g, ox + 9, oy + 15, 14, 9);
        g.setColor(shirt);
        fill(g, ox + 10, oy + 16, 12, 7);
        
        g.setColor(skin);
        int armL = (step == 1) ? 2 : ((step == -1) ? -2 : 0);
        int armR = (step == -1) ? 2 : ((step == 1) ? -2 : 0);
        fill(g, ox + 5, oy + 16 + armL, 4, 6);
        fill(g, ox + 23, oy + 16 + armR, 4, 6);
        
        g.setColor(pants);
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX, oy + 23, 5, 7);
        fill(g, rLegX, oy + 23, 5, 7);
        
        g.setColor(shoes);
        fill(g, lLegX, oy + 29, 5, 3);
        fill(g, rLegX, oy + 29, 5, 3);
    }

    private static void drawBaldCharSide(Graphics2D g, int ox, int oy,
            Color skin, Color shirt, Color pants, Color shoes, Color chain, int step, boolean left) {
        fillShadow(g, ox + 6, oy + 28, 20, 4); 
        int faceDir = left ? -1 : 1;
        int cx = ox + 16;
        g.setColor(Color.BLACK);
        fill(g, cx - 6, oy + 1, 12, 15);
        g.setColor(skin);
        fill(g, cx - 5, oy + 2, 10, 13);
        g.setColor(new Color(0xE8, 0xC8, 0xA8));
        fill(g, cx - 2, oy + 3, 4, 2);
        
        g.setColor(Color.BLACK);
        fill(g, cx + faceDir * 2, oy + 11, 2, 2);
        
        g.setColor(new Color(shirt.getRed()/2, shirt.getGreen()/2, shirt.getBlue()/2)); 
        fill(g, cx - 5, oy + 15, 10, 9);
        g.setColor(shirt);
        fill(g, cx - 4, oy + 16, 8, 7);
        
        g.setColor(skin);
        int armSwing = step * 3;
        fill(g, cx - 2, oy + 16 + armSwing, 4, 7);
        
        g.setColor(chain);
        fill(g, cx + faceDir * 1, oy + 17, 2, 1);
        
        g.setColor(pants);
        int legSep = step * 3;
        fill(g, cx - 4, oy + 23 - Math.abs(legSep), 4, 7 + Math.abs(legSep));
        fill(g, cx + 1, oy + 23, 4, 7);
        
        g.setColor(shoes);
        fill(g, cx - 4 + Math.max(0, -legSep), oy + 29, 5, 3);
        fill(g, cx + 1 + Math.max(0, legSep), oy + 29, 5, 3);
    }

    private static void drawBaldCharSpit(Graphics2D g, int ox, int oy, Color skin, Color shirt, Color pants, Color shoes, Color chain, int dir) {
        if (dir == 0) { // Down
            drawBaldCharDown(g, ox, oy, skin, shirt, pants, shoes, chain, 0);
            g.setColor(new Color(0x80, 0x40, 0x40));
            fill(g, ox + 15, oy + 12, 2, 2);
            g.setColor(new Color(0xE0, 0xF0, 0xFF));
            fill(g, ox + 16, oy + 13, 1, 1);
        } else if (dir == 1) { // Up
            drawBaldCharUp(g, ox, oy, skin, shirt, pants, shoes, chain, 0);
        } else if (dir == 2) { // Left
            drawBaldCharSide(g, ox, oy, skin, shirt, pants, shoes, chain, 0, true);
            g.setColor(new Color(0x80, 0x40, 0x40));
            fill(g, ox + 10, oy + 11, 2, 2);
            g.setColor(new Color(0xE0, 0xF0, 0xFF));
            fill(g, ox + 9, oy + 12, 1, 1);
        } else if (dir == 3) { // Right
            drawBaldCharSide(g, ox, oy, skin, shirt, pants, shoes, chain, 0, false);
            g.setColor(new Color(0x80, 0x40, 0x40));
            fill(g, ox + 20, oy + 11, 2, 2);
            g.setColor(new Color(0xE0, 0xF0, 0xFF));
            fill(g, ox + 22, oy + 12, 1, 1);
        }
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
        Color shadowColor = new Color(Math.max(0, color.getRed()-50), Math.max(0, color.getGreen()-50), Math.max(0, color.getBlue()-50));
        Color highlightColor = new Color(Math.min(255, color.getRed()+60), Math.min(255, color.getGreen()+60), Math.min(255, color.getBlue()+60));
        
        if (dir == 0 || dir == 1) { // Vertical
            fillShadow(g, ox + 4, oy + 26, 24, 6); // Drop shadow under car
            
            // Wheels
            g.setColor(Color.BLACK);
            fill(g, ox + 3, oy + 6, 2, 6);
            fill(g, ox + 27, oy + 6, 2, 6);
            fill(g, ox + 3, oy + 20, 2, 6);
            fill(g, ox + 27, oy + 20, 2, 6);

            // Car Body Outline
            g.setColor(Color.BLACK);
            g.fillRoundRect(ox + 5, oy + 1, 22, 30, 4, 4);

            // Car Body
            g.setColor(color);
            g.fillRoundRect(ox + 6, oy + 2, 20, 28, 4, 4); 
            
            // Roof / Chassis Shading
            g.setColor(highlightColor);
            g.fillRoundRect(ox + 8, oy + 12, 16, 8, 2, 2); // Roof highlight
            
            g.setColor(new Color(0x11, 0x11, 0x11)); // Windows (Dark Glass)
            g.fillRect(ox + 8, oy + 8, 16, 6); // Front/Back window
            g.fillRect(ox + 8, oy + 18, 16, 6);
            
            // Glass reflections
            g.setColor(new Color(0x66, 0x99, 0xFF, 120));
            g.drawLine(ox + 9, oy + 9, ox + 15, oy + 9);
            g.drawLine(ox + 9, oy + 19, ox + 15, oy + 19);

            // Headlights/Taillights
            if (dir == 0) { // Down (Front)
                g.setColor(new Color(0xFF, 0xEE, 0xAA)); // Warm headlight glow
                fill(g, ox + 7, oy + 26, 4, 3);
                fill(g, ox + 21, oy + 26, 4, 3);
                // Grille
                g.setColor(Color.DARK_GRAY);
                g.fillRect(ox + 12, oy + 27, 8, 2);
            } else { // Up (Rear)
                g.setColor(Color.RED);
                fill(g, ox + 7, oy + 2, 4, 3);
                fill(g, ox + 21, oy + 2, 4, 3);
            }
        } else { // Horizontal
            fillShadow(g, ox + 2, oy + 22, 28, 6); // Drop shadow
            
            // Wheels
            g.setColor(Color.BLACK);
            fill(g, ox + 6, oy + 5, 6, 3);
            fill(g, ox + 20, oy + 5, 6, 3);
            fill(g, ox + 6, oy + 24, 6, 3);
            fill(g, ox + 20, oy + 24, 6, 3);

            // Body Outline
            g.setColor(Color.BLACK);
            g.fillRoundRect(ox + 1, oy + 7, 30, 18, 4, 4);

            // Body
            g.setColor(color);
            g.fillRoundRect(ox + 2, oy + 8, 28, 16, 4, 4);
            
            // Roof Shading
            g.setColor(highlightColor);
            g.fillRoundRect(ox + 10, oy + 10, 12, 12, 2, 2);

            // Windows
            g.setColor(new Color(0x11, 0x11, 0x11));
            g.fillRect(ox + 8, oy + 10, 6, 12);
            g.fillRect(ox + 18, oy + 10, 6, 12);
            
            // Reflections
            g.setColor(new Color(0x66, 0x99, 0xFF, 120));
            g.drawLine(ox + 9, oy + 11, ox + 9, oy + 16);
            g.drawLine(ox + 19, oy + 11, ox + 19, oy + 16);

            // Lights
            if (dir == 3) { // Right (Front)
                g.setColor(new Color(0xFF, 0xEE, 0xAA));
                fill(g, ox + 28, oy + 10, 2, 4);
                fill(g, ox + 28, oy + 18, 2, 4);
                g.setColor(Color.RED); // Tail
                fill(g, ox + 2, oy + 10, 2, 3);
            } else { // Left (Front)
                g.setColor(new Color(0xFF, 0xEE, 0xAA));
                fill(g, ox + 2, oy + 10, 2, 4);
                fill(g, ox + 2, oy + 18, 2, 4);
                g.setColor(Color.RED); // Tail
                fill(g, ox + 28, oy + 10, 2, 3);
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

    // --- Helper Options ---
    private static void fillNoise(Graphics2D g, int x, int y, int w, int h, Color base, int variance) {
        Random rand = new Random(x * 31L + y); // Consistent procedural noise
        for (int iy = 0; iy < h; iy++) {
            for (int ix = 0; ix < w; ix++) {
                int r = Math.max(0, Math.min(255, base.getRed() + (rand.nextInt(variance * 2) - variance)));
                int gg = Math.max(0, Math.min(255, base.getGreen() + (rand.nextInt(variance * 2) - variance)));
                int b = Math.max(0, Math.min(255, base.getBlue() + (rand.nextInt(variance * 2) - variance)));
                g.setColor(new Color(r, gg, b));
                fill(g, x + ix, y + iy, 1, 1);
            }
        }
    }

    private static void fillShadow(Graphics2D g, int x, int y, int w, int h) {
        g.setColor(new Color(0, 0, 0, 80)); 
        g.fillOval(x, y, w, h);
    }

    // --- Terrain (Upgraded) ---
    private static void drawConcrete(Graphics2D g, int ox, int oy) {
        // Base color
        g.setColor(new Color(0x9E, 0x9E, 0x9E));
        g.fillRect(ox, oy, S, S);
        
        // Add subtle gritty noise
        fillNoise(g, ox, oy, S, S, new Color(0x9E, 0x9E, 0x9E), 6); // Add slight grain
        
        // Paving stone pattern (16-bit stylized)
        g.setColor(new Color(0x85, 0x85, 0x85)); // Shadow 
        g.drawLine(ox, oy + 15, ox + 31, oy + 15);
        g.drawLine(ox + 15, oy, ox + 15, oy + 31);
        
        g.setColor(new Color(0xB5, 0xB5, 0xB5)); // Highlight
        g.drawLine(ox, oy + 16, ox + 31, oy + 16);
        g.drawLine(ox + 16, oy, ox + 16, oy + 31);
        
        // Secondary divisions
        g.setColor(new Color(0x8A, 0x8A, 0x8A));
        g.drawLine(ox + 7, oy + 16, ox + 7, oy + 31);
        g.drawLine(ox + 23, oy, ox + 23, oy + 15);
    }

    private static void drawGrass(Graphics2D g, int ox, int oy) {
        // Vibrant 16-bit green base
        g.setColor(new Color(0x55, 0x8B, 0x2F));
        g.fillRect(ox, oy, S, S);
        
        // Background noise
        fillNoise(g, ox, oy, S, S, new Color(0x55, 0x8B, 0x2F), 8);
        
        // Foreground structured tufts
        Color darkGreen = new Color(0x33, 0x69, 0x1E);
        Color lightGreen = new Color(0x7C, 0xB3, 0x42);
        
        // Draw a few distinct tufts
        int[][] tufts = { {4, 8}, {20, 12}, {10, 24}, {26, 28}, {14, 4} };
        for (int[] t : tufts) {
            int tx = ox + t[0];
            int ty = oy + t[1];
            // Shadow
            g.setColor(darkGreen);
            g.drawLine(tx, ty, tx - 2, ty - 3);
            g.drawLine(tx, ty, tx + 2, ty - 4);
            // Highlight
            g.setColor(lightGreen);
            g.drawLine(tx - 1, ty, tx - 3, ty - 3);
            g.drawLine(tx + 1, ty, tx + 3, ty - 4);
        }
    }

    private static void drawShawarmaStand(Graphics2D g, int ox, int oy) {
        fillNoise(g, ox, oy, S, S, new Color(0x9E, 0x9E, 0x9E), 8); // concrete floor
        // Shadow cast
        fillShadow(g, ox + 2, oy + 24, 28, 6);
        // Stand base
        g.setColor(new Color(0x7B, 0x4E, 0x2C));
        fill(g, ox + 4, oy + 6, 24, 20);
        g.setColor(new Color(0x5B, 0x2A, 0x1A));
        g.drawRect(ox + 4, oy + 6, 23, 19);
        // Counter top
        g.setColor(new Color(0xE8, 0xD8, 0xC8)); // marble-ish
        fill(g, ox + 2, oy + 6, 28, 4);
        g.setColor(new Color(0xAA, 0xAA, 0xAA));
        g.drawLine(ox + 2, oy + 10, ox + 29, oy + 10);
        // Spit pole
        g.setColor(new Color(0xC0, 0xC0, 0xC0));
        fill(g, ox + 15, oy + 2, 2, 18);
        // Meat
        g.setColor(new Color(0x8B, 0x4A, 0x2A));
        fill(g, ox + 11, oy + 5, 10, 12);
        g.setColor(new Color(0xA8, 0x6A, 0x3A)); // highlight
        fill(g, ox + 12, oy + 6, 3, 10);
        // Fire Element
        g.setColor(new Color(0xFF, 0x45, 0x00)); // Orange-Red
        fill(g, ox + 8, oy + 6, 2, 12);
        g.setColor(Color.YELLOW);
        fill(g, ox + 9, oy + 8, 1, 8);
    }

    private static void drawRoad(Graphics2D g, int ox, int oy) {
        // Dark asphalt base
        g.setColor(new Color(0x2A, 0x2A, 0x2A));
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0x2A, 0x2A, 0x2A), 4);
        
        // Bold yellow markings (vertical)
        g.setColor(new Color(0xFF, 0xCC, 0x00));
        g.fillRect(ox + 14, oy, 4, 10);
        g.fillRect(ox + 14, oy + 22, 4, 10);
        
        // Asphalt edge wear
        g.setColor(new Color(0x11, 0x11, 0x11));
        g.drawLine(ox, oy, ox, oy + 31);
        g.drawLine(ox + 31, oy, ox + 31, oy + 31);
    }

    private static void drawHorizontalRoad(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x2A, 0x2A, 0x2A));
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0x2A, 0x2A, 0x2A), 4);
        
        // Bold yellow markings (horizontal)
        g.setColor(new Color(0xFF, 0xCC, 0x00));
        g.fillRect(ox, oy + 14, 10, 4);
        g.fillRect(ox + 22, oy + 14, 10, 4);
        
        // Asphalt edge wear
        g.setColor(new Color(0x11, 0x11, 0x11));
        g.drawLine(ox, oy, ox + 31, oy);
        g.drawLine(ox, oy + 31, ox + 31, oy + 31);
    }

    private static void drawIntersectionRoad(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x2A, 0x2A, 0x2A));
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0x2A, 0x2A, 0x2A), 4);
        
        // Intersection center box
        g.setColor(new Color(0x40, 0x40, 0x40));
        g.fillRect(ox + 8, oy + 8, 16, 16);
    }

    private static void drawLaffa(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E)); // Concrete bg
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0x9E, 0x9E, 0x9E), 6);
        
        fillShadow(g, ox + 2, oy + 26, 28, 4); // Drop shadow
        
        // Dark outline
        g.setColor(new Color(0x8C, 0x68, 0x3A));
        g.fillOval(ox + 1, oy + 3, 30, 26);
        
        g.setColor(new Color(0xD4, 0xA8, 0x6B)); // Dough
        g.fillOval(ox + 2, oy + 4, 28, 24);
        
        // Highlight
        g.setColor(new Color(0xEAD0A4));
        g.drawArc(ox + 3, oy + 5, 26, 22, 45, 180);
        
        // Char marks
        g.setColor(new Color(0x6B, 0x3E, 0x1C));
        fill(g, ox + 8, oy + 10, 3, 2);
        fill(g, ox + 18, oy + 8, 4, 2);
        fill(g, ox + 12, oy + 18, 3, 2);
        fill(g, ox + 22, oy + 16, 2, 3);
        
        // Filling
        g.setColor(new Color(0x4A, 0x8C, 0x3A)); // Green salad
        fill(g, ox + 10, oy + 13, 12, 4);
        g.setColor(new Color(0x2A, 0x5C, 0x1A)); // Shadow in salad
        fill(g, ox + 11, oy + 15, 6, 2);
        
        g.setColor(new Color(0xF0, 0xE8, 0xD0)); // Tahini
        fill(g, ox + 12, oy + 14, 8, 2);
        g.setColor(new Color(0xFF, 0xFA, 0xE8)); // Tahini highlight
        fill(g, ox + 13, oy + 14, 4, 1);
    }

    private static void drawSand(Graphics2D g, int ox, int oy) {
        // Base sand color
        g.setColor(new Color(0xE8, 0xD5, 0xA0));
        g.fillRect(ox, oy, S, S);
        
        // Add subtle noise for sand texture
        fillNoise(g, ox, oy, S, S, new Color(0xE8, 0xD5, 0xA0), 10);
        
        // Darker sand color for ripples
        g.setColor(new Color(0xD4, 0xC0, 0x8A));
        // Soft ripples
        g.drawArc(ox + 4, oy + 4, 12, 8, 0, 180);
        g.drawArc(ox + 16, oy + 18, 14, 6, 0, 180);
        g.drawArc(ox + 2, oy + 24, 10, 4, 0, 180);
        
        // Highlight ripples
        g.setColor(new Color(0xF4, 0xE5, 0xB0));
        g.drawArc(ox + 5, oy + 5, 10, 6, 0, 180);
        g.drawArc(ox + 17, oy + 19, 12, 4, 0, 180);
        
        // A few pebbles/dots
        g.setColor(new Color(0xA8, 0x90, 0x60));
        fill(g, ox + 8, oy + 16, 1, 1);
        fill(g, ox + 24, oy + 6, 2, 1);
        fill(g, ox + 18, oy + 28, 1, 2);
    }

    private static void drawWater(Graphics2D g, int ox, int oy) {
        // Deep blue base
        g.setColor(new Color(0x02, 0x77, 0xBD));
        g.fillRect(ox, oy, S, S);
        
        // Water noise
        fillNoise(g, ox, oy, S, S, new Color(0x02, 0x77, 0xBD), 5);
        
        // Wave shadows (darker blue)
        g.setColor(new Color(0x01, 0x57, 0x9B));
        fill(g, ox + 2, oy + 6, 12, 4);
        fill(g, ox + 18, oy + 14, 10, 4);
        fill(g, ox + 6, oy + 24, 14, 4);
        
        // Wave highlights (cyan/white foam)
        g.setColor(new Color(0x4F, 0xC3, 0xF7));
        fill(g, ox + 2, oy + 5, 12, 1);
        fill(g, ox + 18, oy + 13, 10, 1);
        fill(g, ox + 6, oy + 23, 14, 1);
        
        // Specular bright spots
        g.setColor(Color.WHITE);
        fill(g, ox + 4, oy + 5, 2, 1);
        fill(g, ox + 20, oy + 13, 2, 1);
        fill(g, ox + 12, oy + 23, 3, 1);
        
        // Small secondary ripples
        g.setColor(new Color(0x29, 0xB6, 0xF6));
        fill(g, ox + 24, oy + 4, 4, 1);
        fill(g, ox + 8, oy + 18, 6, 1);
    }

    private static void drawWall(Graphics2D g, int ox, int oy) {
        // Concrete wall base (greyish yellow)
        g.setColor(new Color(0xC8, 0xB8, 0x98));
        g.fillRect(ox, oy, S, S);
        
        // Wall texture noise
        fillNoise(g, ox, oy, S, S, new Color(0xC8, 0xB8, 0x98), 8);
        
        // Brick / Panel divisions
        g.setColor(new Color(0xA0, 0x90, 0x70)); // Darker lines
        g.drawRect(ox + 4, oy + 4, 10, 8);
        g.drawRect(ox + 18, oy + 4, 10, 8);
        g.drawRect(ox + 4, oy + 16, 24, 12); // Long bottom panel
        
        // Division Highlights
        g.setColor(new Color(0xE0, 0xD0, 0xB0));
        g.drawLine(ox + 5, oy + 5, ox + 13, oy + 5);
        g.drawLine(ox + 19, oy + 5, ox + 27, oy + 5);
        g.drawLine(ox + 5, oy + 17, ox + 27, oy + 17);
        
        // Dark grunge at the bottom edge
        g.setColor(new Color(0x80, 0x70, 0x50, 150));
        fill(g, ox, oy + 28, S, 4);
    }

    private static void drawCurb(Graphics2D g, int ox, int oy) {
        // Curb base color (light concrete)
        g.setColor(new Color(0xB0, 0xB0, 0xB0));
        g.fillRect(ox, oy, S, S);
        
        fillNoise(g, ox, oy, S, S, new Color(0xB0, 0xB0, 0xB0), 8);
        
        // Curb edge/step down
        g.setColor(new Color(0x8A, 0x8A, 0x8A));
        fill(g, ox, oy + 14, S, 6); // Step shadow area
        
        // Deep shadow line where curb meets asphalt
        g.setColor(new Color(0x50, 0x50, 0x50));
        g.drawLine(ox, oy + 16, ox + 31, oy + 16);
        
        // Highlight for the curb rim (catching light)
        g.setColor(new Color(0xD8, 0xD8, 0xD8));
        g.drawLine(ox, oy + 13, ox + 31, oy + 13);
        
        // Red and White curb markings (Israel typical)
        g.setColor(new Color(0xD0, 0x20, 0x20)); // Red
        fill(g, ox + 4, oy + 14, 8, 2);
        fill(g, ox + 20, oy + 14, 8, 2);
        
        g.setColor(new Color(0xF0, 0xF0, 0xF0)); // White
        fill(g, ox + 12, oy + 14, 8, 2);
    }

    private static void drawPalmTree(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E)); // Concrete bg
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0x9E, 0x9E, 0x9E), 6);
        fillShadow(g, ox + 6, oy + 26, 20, 6); // Shadow
        
        // Trunk
        g.setColor(new Color(0x4A, 0x2E, 0x1B)); // Dark trunk outline
        fill(g, ox + 13, oy + 12, 6, 20);
        g.setColor(new Color(0x61, 0x41, 0x26)); // Trunk base
        fill(g, ox + 14, oy + 12, 4, 20);
        g.setColor(new Color(0x82, 0x5D, 0x3D)); // Trunk highlight
        fill(g, ox + 14, oy + 12, 1, 20);
        
        // Fronds
        g.setColor(new Color(0x1B, 0x38, 0x16)); // Dark frond outline/shadow
        g.fillOval(ox + 3, oy + 1, 26, 14);
        g.fillOval(ox + 1, oy + 5, 30, 10);
        
        g.setColor(new Color(0x2D, 0x5A, 0x27)); // Green fronds
        g.fillOval(ox + 4, oy + 2, 24, 12);
        g.fillOval(ox + 2, oy + 6, 28, 8);
        
        g.setColor(new Color(0x4A, 0x82, 0x39)); // Frond highlight
        g.drawArc(ox + 4, oy + 2, 24, 12, 45, 90);
    }

    private static void drawSabraCactus(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xC4, 0xA8, 0x6B)); // Sand bg
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0xC4, 0xA8, 0x6B), 8);
        fillShadow(g, ox + 6, oy + 26, 20, 5); // Shadow
        
        // Cactus Base Drop Shadow
        g.setColor(new Color(0x2B, 0x3D, 0x16));
        g.fillOval(ox + 9, oy + 13, 14, 18); // Outlines
        g.fillOval(ox + 5, oy + 7, 12, 12);
        g.fillOval(ox + 15, oy + 9, 10, 10);
        
        g.setColor(new Color(0x55, 0x6B, 0x2F)); // Olive Green
        g.fillOval(ox + 10, oy + 14, 12, 16); // Center
        g.fillOval(ox + 6, oy + 8, 10, 10); // Paddle
        g.fillOval(ox + 16, oy + 10, 8, 8); // Paddle
        
        // Highlight
        g.setColor(new Color(0x7A, 0x93, 0x48));
        g.fillArc(ox + 10, oy + 14, 12, 16, 90, 180);
        g.fillArc(ox + 6, oy + 8, 10, 10, 90, 180);
        g.fillArc(ox + 16, oy + 10, 8, 8, 90, 180);

        g.setColor(Color.WHITE); // Spines (dots)
        fill(g, ox + 10, oy + 15, 1, 1);
        fill(g, ox + 18, oy + 12, 1, 1);
        fill(g, ox + 8, oy + 10, 1, 1);
        fill(g, ox + 14, oy + 20, 1, 1);
        fill(g, ox + 22, oy + 14, 1, 1);
    }

    private static void drawTrashCan(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E));
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0x9E, 0x9E, 0x9E), 6);
        fillShadow(g, ox + 6, oy + 28, 20, 4);

        // Body outline/shadow
        g.setColor(Color.BLACK);
        fill(g, ox + 7, oy + 9, 18, 22);

        g.setColor(new Color(0xFF, 0x66, 0x00)); // Israeli Orange Bin
        fill(g, ox + 8, oy + 10, 16, 20);
        
        // Shadow/highlight
        g.setColor(new Color(0xCC, 0x44, 0x00)); // Shadow
        fill(g, ox + 20, oy + 10, 4, 20);
        g.setColor(new Color(0xFF, 0x99, 0x33)); // Highlight
        fill(g, ox + 8, oy + 10, 2, 20);

        g.setColor(Color.BLACK);
        g.drawRect(ox + 8, oy + 10, 15, 19);
        fill(g, ox + 10, oy + 14, 12, 4); // Opening (deeper)
        
        // Frame ridges
        g.setColor(new Color(0xCC, 0x44, 0x00));
        g.drawLine(ox + 8, oy + 20, ox + 23, oy + 20);
        g.drawLine(ox + 8, oy + 25, ox + 23, oy + 25);
    }

    private static void drawPlasticChair(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0x9E, 0x9E, 0x9E));
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0x9E, 0x9E, 0x9E), 6);
        fillShadow(g, ox + 6, oy + 26, 20, 6);

        // Seat Outline
        g.setColor(new Color(0x88, 0x88, 0x88));
        fill(g, ox + 7, oy + 13, 18, 4);
        g.setColor(Color.WHITE);
        fill(g, ox + 8, oy + 14, 16, 2); // Seat
        
        // Back outline
        g.setColor(new Color(0x88, 0x88, 0x88));
        fill(g, ox + 7, oy + 5, 18, 10);
        g.setColor(Color.WHITE);
        fill(g, ox + 8, oy + 6, 16, 8); // Back
        
        // Slits in the back
        g.setColor(new Color(0xAA, 0xAA, 0xAA));
        fill(g, ox + 10, oy + 8, 2, 4);
        fill(g, ox + 15, oy + 8, 2, 4);
        fill(g, ox + 20, oy + 8, 2, 4);

        // Legs
        g.setColor(new Color(0x88, 0x88, 0x88));
        fill(g, ox + 7, oy + 16, 4, 12); // Legs Outline
        fill(g, ox + 21, oy + 16, 4, 12);
        
        g.setColor(Color.WHITE);
        fill(g, ox + 8, oy + 16, 2, 12); // Legs
        fill(g, ox + 22, oy + 16, 2, 12);
        
        // Armrests
        g.setColor(Color.WHITE);
        fill(g, ox + 6, oy + 10, 2, 6);
        fill(g, ox + 24, oy + 10, 2, 6);
    }

    private static void drawSheshbesh(Graphics2D g, int ox, int oy) {
        // Table bg
        g.setColor(new Color(0xCC, 0xAB, 0x8B)); // Wood table
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0xCC, 0xAB, 0x8B), 5);
        fillShadow(g, ox + 2, oy + 28, 28, 4);
        
        g.setColor(new Color(0x3A, 0x2A, 0x1A)); // Dark Wooden frame outline
        g.fillRect(ox + 3, oy + 3, 26, 26);
        
        g.setColor(new Color(0x6A, 0x4A, 0x2A)); // Wooden frame
        g.fillRect(ox + 4, oy + 4, 24, 24);
        
        // Inner shadow
        g.setColor(new Color(0x50, 0x30, 0x1A));
        g.fillRect(ox + 5, oy + 5, 22, 22);

        g.setColor(new Color(0xE8, 0xD5, 0xA0)); // Board
        fill(g, ox + 6, oy + 6, 20, 20);
        
        // Board divider
        g.setColor(new Color(0x6A, 0x4A, 0x2A));
        fill(g, ox + 15, oy + 6, 2, 20);

        g.setColor(new Color(0x8B, 0x4A, 0x2A)); // Triangles
        for (int i = 0; i < 4; i++) {
            int offset = (i >= 2) ? 2 : 0;
            int tx = ox + 6 + i * 4 + offset;
            g.fillPolygon(new int[]{tx, tx + 4, tx + 2}, new int[]{oy + 6, oy + 6, oy + 14}, 3);
            g.fillPolygon(new int[]{tx, tx + 4, tx + 2}, new int[]{oy + 26, oy + 26, oy + 18}, 3);
        }
        g.setColor(new Color(0x4A, 0x2A, 0x1A)); // Alternating triangles
        for (int i = 0; i < 4; i++) {
            int offset = (i >= 2) ? 2 : 0;
            int tx = ox + 8 + i * 4 + offset; // Shifted
            if (tx + 4 <= ox + 26) {
                g.fillPolygon(new int[]{tx, tx + 4, tx + 2}, new int[]{oy + 6, oy + 6, oy + 14}, 3);
                g.fillPolygon(new int[]{tx, tx + 4, tx + 2}, new int[]{oy + 26, oy + 26, oy + 18}, 3);
            }
        }
        
        // Pieces
        g.setColor(Color.WHITE);
        g.fillOval(ox + 7, oy + 7, 3, 3);
        g.fillOval(ox + 7, oy + 11, 3, 3);
        g.fillOval(ox + 22, oy + 23, 3, 3);
        
        g.setColor(Color.BLACK);
        g.fillOval(ox + 22, oy + 7, 3, 3);
        g.fillOval(ox + 10, oy + 23, 3, 3);
    }

    private static void drawBougainvillea(Graphics2D g, int ox, int oy) {
        g.setColor(new Color(0xC8, 0xB8, 0x98)); // Wall bg
        g.fillRect(ox, oy, S, S);
        fillNoise(g, ox, oy, S, S, new Color(0xC8, 0xB8, 0x98), 6);
        
        // Shadow on wall
        g.setColor(new Color(0x00, 0x00, 0x00, 50));
        fill(g, ox + 6, oy + 6, 24, 12);
        
        // Dark Leaves (Shadow)
        g.setColor(new Color(0x1D, 0x3A, 0x17));
        fill(g, ox + 3, oy + 3, 26, 14);
        
        g.setColor(new Color(0x2D, 0x5A, 0x27)); // Green leaves
        fill(g, ox + 4, oy + 4, 24, 12);
        // Leaf Highlights
        g.setColor(new Color(0x4A, 0x82, 0x39));
        fill(g, ox + 5, oy + 5, 4, 2);
        fill(g, ox + 15, oy + 6, 5, 2);
        
        // Magenta flowers
        Color darkMagenta = new Color(0x99, 0x10, 0x40);
        Color magenta = new Color(0xE9, 0x1E, 0x63);
        Color lightMagenta = new Color(0xFF, 0x60, 0x90);
        
        for (int i = 0; i < 5; i++) {
            int fx = ox + 6 + i * 4;
            int fy = oy + 6 + (i % 2) * 4;
            g.setColor(darkMagenta);
            g.fillOval(fx - 1, fy - 1, 8, 8);
            g.setColor(magenta);
            g.fillOval(fx, fy, 6, 6);
            g.setColor(lightMagenta);
            g.fillOval(fx + 1, fy + 1, 2, 2);
        }
    }

    private static void drawSoldierDown(Graphics2D g, int ox, int oy, Color skin, Color uniform, Color boots, int step) {
        fillShadow(g, ox + 6, oy + 28, 20, 4); // Added drop shadow (same as other characters)

        g.setColor(new Color(0x1A, 0x1A, 0x1A)); // Beret outline/shadow
        fill(g, ox + 17, oy + 5, 6, 4);
        g.setColor(new Color(0x8B, 0x00, 0x00)); // Paratrooper red beret? Or black? Let's stick with dark grey/green or Red. Original was black.
        fill(g, ox + 18, oy + 6, 4, 3);
        
        // Head outline
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 7, 14, 10);
        g.setColor(skin);
        fill(g, ox + 10, oy + 8, 12, 8);
        
        // Face Shadow
        g.setColor(new Color(skin.getRed()*4/5, skin.getGreen()*4/5, skin.getBlue()*4/5));
        fill(g, ox + 10, oy + 8, 12, 2); // Under hat

        // Eyes
        g.setColor(Color.BLACK);
        fill(g, ox + 13, oy + 12, 2, 2);
        fill(g, ox + 17, oy + 12, 2, 2);

        // Uniform Top Outline
        g.setColor(Color.BLACK);
        fill(g, ox + 7, oy + 15, 18, 12);
        g.setColor(uniform);
        fill(g, ox + 8, oy + 16, 16, 10); // Torso
        
        // Uniform shading
        g.setColor(new Color(Math.max(0, uniform.getRed()-30), Math.max(0, uniform.getGreen()-30), Math.max(0, uniform.getBlue()-30)));
        fill(g, ox + 8, oy + 16, 16, 2); // Collar shadow
        fill(g, ox + 14, oy + 16, 4, 10); // Center line/buttons
        
        // Arms
        int armL = (step == 1) ? 2 : ((step == -1) ? -2 : 0);
        int armR = (step == -1) ? 2 : ((step == 1) ? -2 : 0);
        
        g.setColor(Color.BLACK); // Outline
        fill(g, ox + 5, oy + 15 + armL, 4, 8);
        fill(g, ox + 23, oy + 15 + armR, 4, 8);
        g.setColor(skin);
        fill(g, ox + 6, oy + 16 + armL, 2, 6); // Arms
        fill(g, ox + 24, oy + 16 + armR, 2, 6);

        // Pants
        g.setColor(Color.BLACK); // Outline
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        fill(g, lLegX - 1, oy + 25, 7, 6);
        fill(g, rLegX - 1, oy + 25, 7, 6);
        
        g.setColor(uniform); // Pants
        fill(g, lLegX, oy + 26, 5, 4);
        fill(g, rLegX, oy + 26, 5, 4);
        
        // Boots
        g.setColor(Color.BLACK); // Boot outline
        fill(g, lLegX - 2, oy + 29, 9, 3);
        fill(g, rLegX - 2, oy + 29, 9, 3);
        
        g.setColor(boots); // Or Red boots
        fill(g, lLegX - 1, oy + 30, 7, 2);
        fill(g, rLegX - 1, oy + 30, 7, 2);
        
        g.setColor(new Color(0xAA, 0xAA, 0xAA)); // Boot Highlight
        fill(g, lLegX, oy + 30, 2, 1);
        fill(g, rLegX, oy + 30, 2, 1);
    }

    private static void drawTracksuitArsDown(Graphics2D g, int ox, int oy, Color skin, Color tracksuit, Color stripe, int step) {
        fillShadow(g, ox + 6, oy + 28, 20, 4); 
        
        // Head outline
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 3, 14, 13);
        
        g.setColor(new Color(0x1A, 0x0E, 0x00)); // Buzz cut hair
        fill(g, ox + 10, oy + 4, 12, 5);
        
        g.setColor(skin); // Face
        fill(g, ox + 10, oy + 9, 12, 6);
        // Eyes
        g.setColor(Color.BLACK);
        fill(g, ox + 13, oy + 12, 2, 2);
        fill(g, ox + 17, oy + 12, 2, 2);
        
        // Jacket
        g.setColor(Color.BLACK); // Outline
        fill(g, ox + 7, oy + 14, 18, 11);
        g.setColor(tracksuit);
        fill(g, ox + 8, oy + 15, 16, 9); // Jacket
        
        // Shadow on jacket
        g.setColor(new Color(Math.max(0, tracksuit.getRed()-30), Math.max(0, tracksuit.getGreen()-30), Math.max(0, tracksuit.getBlue()-30)));
        fill(g, ox + 8, oy + 15, 16, 2);
        
        g.setColor(stripe); // Adidas-like stripes
        fill(g, ox + 10, oy + 15, 2, 9); // Stripe
        fill(g, ox + 20, oy + 15, 2, 9);
        
        // Gold chain
        g.setColor(new Color(0x60, 0x40, 0x05)); // Shadow of chain
        fill(g, ox + 12, oy + 17, 8, 2);
        g.setColor(new Color(0xD4, 0xA0, 0x17)); // Gold chain
        fill(g, ox + 12, oy + 16, 8, 2);
        fill(g, ox + 14, oy + 18, 4, 2); // Pendant
        
        // Pants
        int lLegX = ox + 10 + (step == -1 ? -2 : 0);
        int rLegX = ox + 17 + (step == 1 ? 2 : 0);
        
        g.setColor(Color.BLACK); // Pants outline
        fill(g, lLegX - 1, oy + 23, 7, 8);
        fill(g, rLegX - 1, oy + 23, 7, 8);
        
        g.setColor(tracksuit); // Pants
        fill(g, lLegX, oy + 24, 5, 6);
        fill(g, rLegX, oy + 24, 5, 6);
        
        g.setColor(stripe);
        fill(g, lLegX + 2, oy + 24, 2, 6); // Striped pants
        fill(g, rLegX + 1, oy + 24, 2, 6);
        
        // Shoes
        g.setColor(Color.BLACK);
        fill(g, lLegX - 2, oy + 29, 9, 3);
        fill(g, rLegX - 2, oy + 29, 9, 3);
        
        g.setColor(Color.WHITE); // White sneakers
        fill(g, lLegX - 1, oy + 30, 7, 2);
        fill(g, rLegX - 1, oy + 30, 7, 2);
    }

    
    private static void drawLiranDown(Graphics2D g, int ox, int oy) {
        fillShadow(g, ox + 6, oy + 28, 20, 4);
        
        // Head outline
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 1, 14, 15);
        
        // Liran: The Final Boss
        // Spiky Hair
        g.setColor(LIRAN_HAIR);
        fill(g, ox + 10, oy + 2, 12, 6);
        fill(g, ox + 14, oy + 1, 4, 3); // Spikes
        fill(g, ox + 11, oy, 2, 4);
        fill(g, ox + 19, oy + 1, 2, 3);
        
        // Skin
        g.setColor(LIRAN_SKIN);
        fill(g, ox + 10, oy + 8, 12, 7);
        // Jaw outline shadow
        g.setColor(new Color(LIRAN_SKIN.getRed()*4/5, LIRAN_SKIN.getGreen()*4/5, LIRAN_SKIN.getBlue()*4/5));
        fill(g, ox + 10, oy + 14, 12, 1);
        
        // Sunglasses (Detailed)
        g.setColor(Color.BLACK);
        fill(g, ox + 10, oy + 10, 6, 4); // Left lens outline
        fill(g, ox + 16, oy + 10, 6, 4); // Right lens outline
        g.setColor(new Color(0x33, 0x33, 0x33));
        fill(g, ox + 11, oy + 11, 4, 2);
        fill(g, ox + 17, oy + 11, 4, 2);
        // Reflection
        g.setColor(Color.WHITE);
        fill(g, ox + 14, oy + 11, 1, 1);
        fill(g, ox + 20, oy + 11, 1, 1);
        
        // Shirt (Tight Black Tee)
        g.setColor(Color.BLACK); // Outline
        fill(g, ox + 8, oy + 14, 16, 11);
        g.setColor(LIRAN_SHIRT);
        fill(g, ox + 9, oy + 15, 14, 9);
        // Muscle highlights on shirt
        g.setColor(new Color(0x33, 0x33, 0x33));
        fill(g, ox + 11, oy + 16, 4, 4); // Pecs
        fill(g, ox + 17, oy + 16, 4, 4);
        
        // Arms (Muscular)
        g.setColor(Color.BLACK); // Outline
        fill(g, ox + 5, oy + 14, 5, 9);
        fill(g, ox + 22, oy + 14, 5, 9);
        
        g.setColor(LIRAN_SKIN);
        fill(g, ox + 6, oy + 15, 3, 7);
        fill(g, ox + 23, oy + 15, 3, 7);
        
        // Tattoos? (Dark lines)
        g.setColor(new Color(0x22, 0x11, 0x11));
        fill(g, ox + 6, oy + 17, 3, 2);
        fill(g, ox + 23, oy + 18, 3, 2);
        
        // Heavy Gold Chain
        g.setColor(new Color(0x44, 0x33, 0x00));
        fill(g, ox + 12, oy + 17, 8, 4); // Shadow
        g.setColor(GOLD);
        fill(g, ox + 12, oy + 16, 8, 2);
        fill(g, ox + 14, oy + 18, 4, 3); // Big medallion
        g.setColor(Color.WHITE); // sparkle
        fill(g, ox + 15, oy + 19, 2, 1);
        
        // Pants
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 23, 7, 9);
        fill(g, ox + 16, oy + 23, 7, 9);
        
        g.setColor(LIRAN_PANTS);
        fill(g, ox + 10, oy + 24, 5, 7);
        fill(g, ox + 17, oy + 24, 5, 7);
        // Pants wrinkles
        g.setColor(new Color(0x20, 0x20, 0x40));
        fill(g, ox + 10, oy + 26, 5, 2);
        fill(g, ox + 17, oy + 28, 5, 2);
        
        // Shoes (Fancy White Sneakers)
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 30, 7, 3);
        fill(g, ox + 16, oy + 30, 7, 3);
        
        g.setColor(Color.WHITE);
        fill(g, ox + 10, oy + 31, 5, 2);
        fill(g, ox + 17, oy + 31, 5, 2);
        // Sneaker details
        g.setColor(Color.RED);
        fill(g, ox + 13, oy + 31, 2, 1);
        fill(g, ox + 17, oy + 31, 2, 1);
    }

    // --- Easter Egg Drawing Methods ---

    private static void drawRustyBike(Graphics2D g, int ox, int oy) {
        // Rusty brown electric bike frame - broken and abandoned
        Color rust = new Color(0x8B, 0x4C, 0x24);
        Color darkRust = new Color(0x5C, 0x33, 0x17);
        Color tire = new Color(0x20, 0x20, 0x20);
        Color flatTire = new Color(0x40, 0x40, 0x40);
        Color battery = new Color(0x33, 0x33, 0x33);

        fillShadow(g, ox + 2, oy + 24, 28, 6);

        // Tires outline and shape
        g.setColor(Color.BLACK);
        fill(g, ox + 2, oy + 19, 12, 10); // Back
        fill(g, ox + 19, oy + 19, 12, 10); // Front
        fill(g, ox + 4, oy + 23, 8, 8); // Flat bulge
        fill(g, ox + 21, oy + 23, 8, 8);

        // Inside Tire
        g.setColor(tire);
        fill(g, ox + 3, oy + 20, 10, 8);
        fill(g, ox + 20, oy + 20, 10, 8);
        g.setColor(flatTire);
        fill(g, ox + 5, oy + 22, 6, 4);
        fill(g, ox + 22, oy + 22, 6, 4);

        // Spokes (faded)
        g.setColor(new Color(0x60, 0x60, 0x60));
        fill(g, ox + 7, oy + 21, 2, 6); // Just a cross
        fill(g, ox + 5, oy + 23, 6, 2);
        fill(g, ox + 24, oy + 21, 2, 6);
        fill(g, ox + 22, oy + 23, 6, 2);

        // Frame outline
        g.setColor(Color.BLACK);
        fill(g, ox + 7, oy + 13, 18, 5); // top tube
        fill(g, ox + 7, oy + 13, 5, 12); // seat tube
        fill(g, ox + 20, oy + 13, 5, 12); // head tube
        fill(g, ox + 11, oy + 16, 12, 4); // down tube

        // Frame body
        g.setColor(rust);
        fill(g, ox + 8, oy + 14, 16, 3);
        fill(g, ox + 8, oy + 14, 3, 10);
        fill(g, ox + 21, oy + 14, 3, 10);
        fill(g, ox + 12, oy + 17, 10, 2);

        // Rust spots
        g.setColor(darkRust);
        fill(g, ox + 10, oy + 15, 2, 1);
        fill(g, ox + 18, oy + 15, 2, 1);
        fill(g, ox + 14, oy + 18, 2, 1);
        fill(g, ox + 9, oy + 20, 2, 2);
        fill(g, ox + 22, oy + 20, 2, 2);

        // Handlebars
        g.setColor(Color.BLACK);
        fill(g, ox + 19, oy + 9, 8, 4);
        g.setColor(rust);
        fill(g, ox + 22, oy + 10, 2, 5);
        fill(g, ox + 20, oy + 10, 6, 2);

        // Seat
        g.setColor(Color.BLACK);
        fill(g, ox + 5, oy + 10, 8, 5);
        g.setColor(darkRust);
        fill(g, ox + 6, oy + 11, 6, 3);
        // Highlight
        g.setColor(new Color(0x8B, 0x5A, 0x33));
        fill(g, ox + 7, oy + 11, 4, 1);

        // Empty battery slot
        g.setColor(Color.BLACK);
        fill(g, ox + 10, oy + 5, 12, 8);
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
        Color sprayDarkRed = new Color(0x90, 0x10, 0x20); // new darker shade

        // Wall background
        g.setColor(wallColor);
        fill(g, ox, oy, 32, 32);
        fillNoise(g, ox, oy, S, S, wallColor, 8);
        
        // Wall texture lines (bricks/cracks)
        g.setColor(wallDark);
        fill(g, ox, oy + 8, 32, 1);
        fill(g, ox, oy + 16, 32, 1);
        fill(g, ox, oy + 24, 32, 1);
        fill(g, ox + 16, oy, 1, 8);
        fill(g, ox + 8, oy + 8, 1, 8);
        fill(g, ox + 24, oy + 8, 1, 8);
        fill(g, ox + 16, oy + 16, 1, 8);
        
        // Highlights on bricks
        g.setColor(new Color(0xB0, 0xA8, 0x98));
        fill(g, ox, oy + 9, 32, 1);
        fill(g, ox + 1, oy + 17, 32, 1);
        fill(g, ox + 1, oy + 25, 32, 1);

        // Spray-painted shadow/outer edge
        g.setColor(sprayDarkRed);
        fill(g, ox + 7, oy + 5, 20, 16); // Rough bounding box

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
        fill(g, ox + 18, oy + 8, 2, 1);

        // "S+L" text inside heart
        g.setColor(Color.BLACK);
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
        g.setColor(sprayDarkRed);
        fill(g, ox + 15, oy + 21, 1, 3);
        fill(g, ox + 9, oy + 16, 1, 3);
        g.setColor(sprayRed);
        fill(g, ox + 15, oy + 22, 1, 2);
        fill(g, ox + 9, oy + 17, 1, 1);

        // Scrawled "FINISHED" at bottom
        g.setColor(new Color(0x33, 0x11, 0x11));
        fill(g, ox + 3, oy + 27, 26, 1);
        fill(g, ox + 5, oy + 26, 2, 1);
        fill(g, ox + 11, oy + 26, 2, 1);
    }

    private static void drawAbuRafi(Graphics2D g, int ox, int oy) {
        fillShadow(g, ox + 6, oy + 28, 20, 5); // Add shadow

        // Old wise man with white keffiyeh, brown robe, and walking cane
        Color skin = new Color(0xC0, 0x99, 0x70);
        Color robe = new Color(0x6B, 0x4E, 0x31); // brown robe
        Color keffiyeh = new Color(0xF0, 0xF0, 0xE8); // white headwear
        Color keffiyehBand = new Color(0x22, 0x22, 0x22); // black agal
        Color cane = new Color(0x8B, 0x6D, 0x44);
        Color beard = new Color(0xD0, 0xD0, 0xD0);

        // Keffiyeh outline
        g.setColor(Color.BLACK);
        fill(g, ox + 6, oy, 20, 11);

        // Keffiyeh (headwear - draped)
        g.setColor(keffiyeh);
        fill(g, ox + 9, oy + 1, 14, 8);
        fill(g, ox + 7, oy + 5, 4, 6);  // left drape
        fill(g, ox + 21, oy + 5, 4, 6); // right drape
        
        // Keffiyeh shading
        g.setColor(new Color(0xD0, 0xD0, 0xC8));
        fill(g, ox + 9, oy + 5, 2, 6); // Inner shadow
        fill(g, ox + 21, oy + 5, 2, 6);

        // Face outline
        g.setColor(Color.BLACK);
        fill(g, ox + 10, oy + 5, 12, 8);
        
        // Face
        g.setColor(skin);
        fill(g, ox + 11, oy + 6, 10, 6);
        // Eyes
        g.setColor(Color.BLACK);
        fill(g, ox + 13, oy + 8, 2, 2);
        fill(g, ox + 18, oy + 8, 2, 2);
        
        // Agal (black band)
        g.setColor(keffiyehBand);
        fill(g, ox + 10, oy + 4, 12, 2);
        // Agal highlight
        g.setColor(new Color(0x44, 0x44, 0x44));
        fill(g, ox + 11, oy + 4, 10, 1);

        // White beard outline
        g.setColor(new Color(0x90, 0x90, 0x90));
        fill(g, ox + 10, oy + 10, 12, 6);
        fill(g, ox + 12, oy + 14, 8, 4);

        // White beard
        g.setColor(beard);
        fill(g, ox + 11, oy + 11, 10, 4);
        fill(g, ox + 13, oy + 15, 6, 2);

        // Robe outline
        g.setColor(Color.BLACK);
        fill(g, ox + 8, oy + 13, 16, 14);

        // Robe (long brown)
        g.setColor(robe);
        fill(g, ox + 9, oy + 14, 14, 12);
        // Robe shading
        g.setColor(new Color(0x55, 0x3D, 0x25));
        fill(g, ox + 10, oy + 18, 2, 8);
        fill(g, ox + 14, oy + 16, 4, 10);
        fill(g, ox + 20, oy + 18, 2, 8);

        // Arms outline
        g.setColor(Color.BLACK);
        fill(g, ox + 5, oy + 15, 6, 8);
        fill(g, ox + 21, oy + 15, 6, 8);

        // Arms
        g.setColor(robe);
        fill(g, ox + 6, oy + 16, 4, 6);
        fill(g, ox + 22, oy + 16, 4, 6);
        
        // Hands
        g.setColor(skin);
        fill(g, ox + 6, oy + 22, 4, 2);
        fill(g, ox + 22, oy + 22, 4, 2);

        // Walking cane outline
        g.setColor(Color.BLACK);
        fill(g, ox + 25, oy + 15, 4, 16);
        fill(g, ox + 24, oy + 14, 6, 4);

        // Walking cane
        g.setColor(cane);
        fill(g, ox + 26, oy + 16, 2, 14);
        fill(g, ox + 25, oy + 15, 4, 2); // handle

        // Sandals outline
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 25, 7, 6);
        fill(g, ox + 16, oy + 25, 7, 6);

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
        fillShadow(g, ox + 6, oy + 28, 20, 5); // Add shadow

        // Israeli barber: blue apron, styled dark hair, scissors in hand
        Color skin = new Color(0xD4, 0xA8, 0x78);
        Color apron = new Color(0x30, 0x60, 0xB0); // Blue barber apron
        Color hair = new Color(0x1A, 0x0E, 0x04);
        Color scissors = new Color(0xC0, 0xC0, 0xC0);

        // Head outline
        g.setColor(Color.BLACK);
        fill(g, ox + 8, oy + 0, 16, 14);

        // Hair (styled pompadour)
        g.setColor(hair);
        fill(g, ox + 10, oy + 1, 12, 7);
        fill(g, ox + 9, oy + 3, 2, 4);  // side
        fill(g, ox + 22, oy + 3, 2, 4); // side
        // pompadour bump
        fill(g, ox + 12, oy, 8, 3);
        
        // Hair highlight
        g.setColor(new Color(0x3A, 0x2E, 0x24));
        fill(g, ox + 13, oy + 1, 4, 2);

        // Face
        g.setColor(skin);
        fill(g, ox + 10, oy + 6, 12, 7);
        // Eyes
        g.setColor(Color.BLACK);
        fill(g, ox + 13, oy + 8, 2, 2);
        fill(g, ox + 18, oy + 8, 2, 2);
        // Mustache
        fill(g, ox + 13, oy + 11, 7, 2);

        // Body outline
        g.setColor(Color.BLACK);
        fill(g, ox + 8, oy + 12, 16, 15);

        // Blue Apron
        g.setColor(apron);
        fill(g, ox + 9, oy + 13, 14, 13);
        // Apron shading
        g.setColor(new Color(0x20, 0x40, 0x80));
        fill(g, ox + 9, oy + 13, 14, 2);
        // Apron pocket
        g.setColor(new Color(0x25, 0x50, 0x95));
        fill(g, ox + 12, oy + 17, 8, 4);

        // Arms outline
        g.setColor(Color.BLACK);
        fill(g, ox + 4, oy + 13, 7, 8);
        fill(g, ox + 21, oy + 13, 7, 8);

        // Arms
        g.setColor(skin);
        fill(g, ox + 5, oy + 14, 5, 6);
        fill(g, ox + 22, oy + 14, 5, 6);

        // Scissors in right hand outline
        g.setColor(Color.BLACK);
        fill(g, ox + 23, oy + 11, 4, 10);

        // Scissors in right hand
        g.setColor(scissors);
        fill(g, ox + 24, oy + 12, 2, 8);
        fill(g, ox + 26, oy + 14, 2, 4);
        g.setColor(new Color(0x90, 0x90, 0x90));
        fill(g, ox + 23, oy + 15, 1, 2);

        // Shoes
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 26, 7, 5);
        fill(g, ox + 16, oy + 26, 7, 5);
        
        g.setColor(new Color(0x33, 0x33, 0x33));
        fill(g, ox + 10, oy + 27, 5, 3);
        fill(g, ox + 17, oy + 27, 5, 3);
    }

    private static void drawAviGymBoss(Graphics2D g, int ox, int oy) {
        fillShadow(g, ox + 6, oy + 28, 20, 5); // Add shadow

        // Big muscular dude: red tank top, gold chain, shaved head
        Color skin = new Color(0xCC, 0x99, 0x66);
        Color tank = new Color(0xD0, 0x20, 0x20); // Red tank top
        Color chain = new Color(0xD4, 0xA0, 0x17);

        // Head outline
        g.setColor(Color.BLACK);
        fill(g, ox + 8, oy + 1, 16, 12);

        // Shaved head
        g.setColor(skin);
        fill(g, ox + 9, oy + 2, 14, 10);
        // Stubble shadow
        g.setColor(new Color(0xB0, 0x80, 0x55));
        fill(g, ox + 10, oy + 2, 12, 3);
        
        // Head highlight
        g.setColor(new Color(0xDD, 0xAA, 0x77));
        fill(g, ox + 12, oy + 4, 8, 2);

        // Eyes (angry) outline already done
        g.setColor(Color.BLACK);
        fill(g, ox + 12, oy + 5, 3, 2);
        fill(g, ox + 18, oy + 5, 3, 2);
        // Angry eyebrows
        fill(g, ox + 11, oy + 4, 4, 1);
        fill(g, ox + 18, oy + 4, 4, 1);
        // Mouth (grimace)
        fill(g, ox + 14, oy + 9, 5, 1);

        // Thick neck outline
        g.setColor(Color.BLACK);
        fill(g, ox + 11, oy + 10, 10, 5);

        // Thick neck
        g.setColor(skin);
        fill(g, ox + 12, oy + 11, 8, 3);

        // Gold chain shadow
        g.setColor(new Color(0x50, 0x30, 0x1A));
        fill(g, ox + 11, oy + 13, 10, 2);
        // Gold chain
        g.setColor(chain);
        fill(g, ox + 11, oy + 12, 10, 2);
        fill(g, ox + 13, oy + 14, 6, 2); // Medallion
        g.setColor(Color.WHITE);
        fill(g, ox + 14, oy + 15, 2, 1);

        // Red tank top outline
        g.setColor(Color.BLACK);
        fill(g, ox + 5, oy + 13, 22, 12);
        
        // Red tank top (wide shoulders = big muscles)
        g.setColor(tank);
        fill(g, ox + 6, oy + 14, 20, 10);
        // Tank top muscle shading
        g.setColor(new Color(0x90, 0x10, 0x10));
        fill(g, ox + 11, oy + 14, 10, 4); // Chest separation

        // Muscle arms outline
        g.setColor(Color.BLACK);
        fill(g, ox + 2, oy + 13, 7, 10);
        fill(g, ox + 23, oy + 13, 7, 10);
        fill(g, ox + 1, oy + 21, 7, 5);
        fill(g, ox + 24, oy + 21, 7, 5);

        // Muscle bulge arms
        g.setColor(skin);
        fill(g, ox + 3, oy + 14, 5, 8);  // Left bicep
        fill(g, ox + 24, oy + 14, 5, 8); // Right bicep
        
        // Bicep shading
        g.setColor(new Color(0xAA, 0x77, 0x44));
        fill(g, ox + 4, oy + 15, 3, 2);
        fill(g, ox + 25, oy + 15, 3, 2);

        // Forearms
        g.setColor(skin);
        fill(g, ox + 2, oy + 22, 5, 3);
        fill(g, ox + 25, oy + 22, 5, 3);

        // Shorts outline
        g.setColor(Color.BLACK);
        fill(g, ox + 7, oy + 23, 18, 6);

        // Shorts
        g.setColor(new Color(0x22, 0x22, 0x22));
        fill(g, ox + 8, oy + 24, 16, 4);

        // Shoes outline
        g.setColor(Color.BLACK);
        fill(g, ox + 8, oy + 27, 8, 5);
        fill(g, ox + 16, oy + 27, 8, 5);

        // Shoes
        g.setColor(new Color(0xE0, 0xE0, 0xE0));
        fill(g, ox + 9, oy + 28, 6, 3);
        fill(g, ox + 17, oy + 28, 6, 3);
        g.setColor(Color.WHITE);
        fill(g, ox + 10, oy + 28, 4, 1);
        fill(g, ox + 18, oy + 28, 4, 1);
    }

    private static void drawYotamTelAvivian(Graphics2D g, int ox, int oy) {
        fillShadow(g, ox + 6, oy + 28, 20, 5); // Add shadow

        // Hipster Tel-Avivian: man bun, round glasses, beige tote bag
        Color skin = new Color(0xE0, 0xC0, 0xA0);
        Color shirt = new Color(0xF0, 0xF0, 0xE0); // Off-white linen
        Color pants = new Color(0x50, 0x50, 0x50);
        Color hair = new Color(0x4A, 0x2E, 0x14);
        Color tote = new Color(0xD4, 0xC0, 0x90);

        // Hair Outline
        g.setColor(Color.BLACK);
        fill(g, ox + 12, oy - 1, 8, 5);
        fill(g, ox + 9, oy + 2, 14, 7);

        // Man bun on top
        g.setColor(hair);
        fill(g, ox + 13, oy, 6, 4);
        // Hair sides
        fill(g, ox + 10, oy + 3, 12, 5);

        // Face outline
        g.setColor(Color.BLACK);
        fill(g, ox + 10, oy + 5, 12, 9);

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

        // Shirt outline
        g.setColor(Color.BLACK);
        fill(g, ox + 8, oy + 12, 16, 12);
        
        // Tote bag outline
        fill(g, ox + 3, oy + 12, 8, 15);

        // Off-white linen shirt
        g.setColor(shirt);
        fill(g, ox + 9, oy + 13, 14, 10);
        // Shirt creases
        g.setColor(new Color(0xD0, 0xD0, 0xC0));
        fill(g, ox + 10, oy + 15, 2, 6);
        fill(g, ox + 14, oy + 14, 2, 8);
        fill(g, ox + 18, oy + 15, 2, 6);

        // Arms outline
        g.setColor(Color.BLACK);
        fill(g, ox + 5, oy + 13, 6, 8);
        fill(g, ox + 21, oy + 13, 6, 8);

        // Arms
        g.setColor(skin);
        fill(g, ox + 6, oy + 14, 4, 6);
        fill(g, ox + 22, oy + 14, 4, 6);

        // Tote bag (left shoulder)
        g.setColor(tote);
        fill(g, ox + 4, oy + 13, 6, 13);
        g.setColor(new Color(0xC0, 0xA8, 0x78));
        fill(g, ox + 5, oy + 13, 1, 13); // strap
        // Tote shadow
        g.setColor(new Color(0xA8, 0x90, 0x58));
        fill(g, ox + 7, oy + 18, 3, 8);

        // Dark pants outline
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 22, 7, 7);
        fill(g, ox + 16, oy + 22, 7, 7);

        // Dark pants
        g.setColor(pants);
        fill(g, ox + 10, oy + 23, 5, 6);
        fill(g, ox + 17, oy + 23, 5, 6);

        // Sandals outline
        g.setColor(Color.BLACK);
        fill(g, ox + 9, oy + 27, 7, 5);
        fill(g, ox + 16, oy + 27, 7, 5);

        // Sandals
        g.setColor(new Color(0x8B, 0x6D, 0x44));
        fill(g, ox + 10, oy + 28, 5, 3);
        fill(g, ox + 17, oy + 28, 5, 3);
    }

    private static void drawEBikeBattery(Graphics2D g, int ox, int oy) {
        // Electric bike battery: black rectangle with yellow lightning bolt
        Color body = new Color(0x2A, 0x2A, 0x2A);
        Color bodyLight = new Color(0x40, 0x40, 0x40);
        Color accent = new Color(0x3A, 0x3A, 0x3A);
        Color bolt = new Color(0xFF, 0xD7, 0x00); // Golden bolt
        Color glow = new Color(0xFF, 0xE0, 0x40, 100);
        Color terminal = new Color(0xC0, 0xC0, 0xC0);
        
        fillShadow(g, ox + 6, oy + 26, 20, 6);

        // Glow effect
        g.setColor(glow);
        fill(g, ox + 4, oy + 3, 24, 26);

        // Battery body outline
        g.setColor(Color.BLACK);
        fill(g, ox + 5, oy + 4, 22, 24);

        // Battery body
        g.setColor(body);
        fill(g, ox + 6, oy + 5, 20, 22);
        
        // Edge highlight
        g.setColor(bodyLight);
        fill(g, ox + 7, oy + 6, 4, 20);
        
        // Terminal nub (top)
        g.setColor(Color.BLACK);
        fill(g, ox + 12, oy + 2, 8, 4);
        g.setColor(terminal);
        fill(g, ox + 13, oy + 3, 6, 2);

        // Accent lines
        g.setColor(Color.BLACK);
        fill(g, ox + 6, oy + 8, 20, 2);
        fill(g, ox + 6, oy + 23, 20, 2);
        g.setColor(accent);
        fill(g, ox + 7, oy + 9, 18, 1);
        fill(g, ox + 7, oy + 24, 18, 1);

        // Lightning bolt ⚡
        g.setColor(bolt);
        fill(g, ox + 17, oy + 11, 4, 2);
        fill(g, ox + 15, oy + 13, 4, 2);
        fill(g, ox + 13, oy + 15, 8, 2);
        fill(g, ox + 15, oy + 17, 4, 2);
        fill(g, ox + 13, oy + 19, 4, 2);
        fill(g, ox + 11, oy + 21, 4, 2);
        // Bolt highlight
        g.setColor(Color.WHITE);
        fill(g, ox + 16, oy + 12, 1, 1);
        fill(g, ox + 14, oy + 14, 1, 1);
        fill(g, ox + 12, oy + 16, 2, 1);

        // "36V" text area
        g.setColor(new Color(0x60, 0x60, 0x60));
        fill(g, ox + 10, oy + 20, 12, 3);
    }

    private static void drawStrongHoldWax(Graphics2D g, int ox, int oy) {
        // Hair wax jar: purple jar with lid and label
        Color jar = new Color(0x6A, 0x1B, 0x9A); // Purple
        Color jarLight = new Color(0x8E, 0x3E, 0xBE);
        Color jarDark = new Color(0x4A, 0x12, 0x6B);
        Color lid = new Color(0x22, 0x22, 0x22);
        Color label = new Color(0xF0, 0xF0, 0xE0);
        Color labelText = new Color(0x22, 0x22, 0x22);

        fillShadow(g, ox + 4, oy + 26, 24, 5); // Add shadow

        // Jar body Outline
        g.setColor(Color.BLACK);
        fill(g, ox + 7, oy + 9, 18, 18);
        fill(g, ox + 6, oy + 11, 20, 14);

        // Jar body (rounded rectangle)
        g.setColor(jar);
        fill(g, ox + 8, oy + 10, 16, 16);
        fill(g, ox + 7, oy + 12, 18, 12);
        
        // Shadow
        g.setColor(jarDark);
        fill(g, ox + 20, oy + 12, 5, 12);
        
        // Highlight
        g.setColor(jarLight);
        fill(g, ox + 9, oy + 11, 4, 14);

        // Lid Outline
        g.setColor(Color.BLACK);
        fill(g, ox + 6, oy + 6, 20, 6);
        fill(g, ox + 8, oy + 4, 16, 4);

        // Lid
        g.setColor(lid);
        fill(g, ox + 7, oy + 7, 18, 4);
        fill(g, ox + 9, oy + 5, 14, 3);
        
        // Lid reflection
        g.setColor(new Color(0x55, 0x55, 0x55));
        fill(g, ox + 9, oy + 8, 3, 2);

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

        // Floor / Reflection
        g.setColor(new Color(0x6A, 0x1B, 0x9A, 50));
        fill(g, ox + 8, oy + 27, 16, 2);
    }

    private static void fill(Graphics2D g, int x, int y, int w, int h) {
        g.fillRect(x, y, w, h);
    }
}
