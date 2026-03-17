package com.ashkelord.states;

import com.ashkelord.main.Game;
import com.ashkelord.gfx.Renderer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Cinematic epilogue — classic RPG-style ending screens after defeating Liran.
 * Mirrors PrologueState's typewriter + gold theme aesthetic.
 */
public class EpilogueState extends State {

    // --- Color Palette (Golden Hour / Victory) ---
    private static final Color BG_COLOR = new Color(0x1a, 0x1a, 0x2e);
    private static final Color GOLD = new Color(0xd4, 0xa0, 0x17);
    private static final Color GOLD_DIM = new Color(0xd4, 0xa0, 0x17, 120);
    private static final Color TEXT_COLOR = new Color(0xe0, 0xe0, 0xe0);
    private static final Color PROMPT_COLOR = new Color(0xff, 0xd7, 0x00);
    private static final Color SHADOW = new Color(0, 0, 0, 200);
    private static final Color BG_OVERLAY = new Color(0, 0, 0, 70);
    private static final Color VICTORY_GOLD = new Color(0xff, 0xd7, 0x00, 200);

    // --- Fonts ---
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 36);
    private static final Font SUBTITLE_FONT = new Font("Serif", Font.ITALIC, 18);
    private static final Font BODY_FONT = new Font("Monospaced", Font.PLAIN, 16);
    private static final Font PROMPT_FONT = new Font("Monospaced", Font.BOLD, 14);
    private static final Font CREDITS_FONT = new Font("Monospaced", Font.PLAIN, 13);

    // --- Narrative Slides ---
    private final String[][] slides = {
            {
                    "Victory",
                    "The Shark falls to his knees.",
                    "",
                    "His spit dries on the concrete.",
                    "The Marina goes silent.",
                    "",
                    "Nadav stands tall under the golden sunset."
            },
            {
                    "The Golden Amba",
                    "The legendary Golden Amba gleams",
                    "in Nadav's hands.",
                    "",
                    "Its warmth spreads through the air.",
                    "The scent of cumin and fenugreek",
                    "fills the streets of Ashkelon."
            },
            {
                    "A City Reborn",
                    "Word travels fast through the shchuna.",
                    "",
                    "Miri raises her laffa in salute.",
                    "Abu Rafi nods with a knowing smile.",
                    "Tzion gives everyone free haircuts.",
                    "",
                    "Even Yotam from Tel Aviv stays."
            },
            {
                    "The Prince Returns",
                    "Shirel texts him that night.",
                    "",
                    "'I made a mistake.'",
                    "",
                    "Nadav reads it, smiles,",
                    "and blocks her number.",
                    "",
                    "He has a city to run."
            },
            {
                    "AshkeLord",
                    "From the dusty corners of the periphery,",
                    "a legend was born.",
                    "",
                    "Not with money. Not with violence.",
                    "But with honor, shawarma,",
                    "and the sickest fade in all of Israel.",
                    "",
                    "Long live the AshkeLord."
            },
            {
                    "Credits",
                    "Thank you for playing!",
                    "",
                    "AshkeLord",
                    "A 2D RPG built from scratch in Java",
                    "",
                    "Made with passion and pita.",
                    "",
                    "              Yalla, bye."
            }
    };

    // --- State ---
    private int currentSlide = 0;
    private int typewriterIndex = 0;
    private int totalCharsInSlide = 0;
    private long lastCharTime = 0;
    private static final long CHAR_DELAY_MS = 40; // Slightly slower for dramatic effect

    private boolean slideFullyRevealed = false;
    private long promptBlinkTimer = 0;
    private boolean promptVisible = true;

    private boolean enterWasPressed = false;

    private BufferedImage bgImage;

    public EpilogueState(Game game) {
        super(game);
        // Stop gameplay music before the ending cinematic
        com.ashkelord.audio.AudioManager.getInstance().stopMusic();
        loadBackground();
        prepareSlide();
    }

    private void loadBackground() {
        try {
            // Try epilogue-specific bg, fall back to prologue bg
            bgImage = ImageIO.read(getClass().getResourceAsStream("/textures/epilogue_bg.jpg"));
        } catch (Exception e) {
            try {
                bgImage = ImageIO.read(getClass().getResourceAsStream("/textures/prologue_bg.jpg"));
            } catch (Exception e2) {
                System.err.println("Warning: Could not load epilogue background. Using solid color.");
                bgImage = null;
            }
        }
    }

    private void prepareSlide() {
        typewriterIndex = 0;
        slideFullyRevealed = false;
        lastCharTime = System.currentTimeMillis();

        totalCharsInSlide = 0;
        String[] slide = slides[currentSlide];
        for (int i = 1; i < slide.length; i++) {
            totalCharsInSlide += slide[i].length();
        }
    }

    @Override
    public void tick() {
        long now = System.currentTimeMillis();

        // Typewriter animation
        if (!slideFullyRevealed) {
            if (now - lastCharTime >= CHAR_DELAY_MS) {
                typewriterIndex++;
                lastCharTime = now;
                if (typewriterIndex >= totalCharsInSlide) {
                    slideFullyRevealed = true;
                }
            }
        }

        // Blinking prompt
        promptBlinkTimer++;
        if (promptBlinkTimer % 30 == 0) {
            promptVisible = !promptVisible;
        }

        // Enter key handling with debounce
        boolean enterPressed = game.getKeyManager().enter;
        if (enterPressed && !enterWasPressed) {
            if (!slideFullyRevealed) {
                typewriterIndex = totalCharsInSlide;
                slideFullyRevealed = true;
            } else {
                currentSlide++;
                if (currentSlide >= slides.length) {
                    // Epilogue finished — return to title / close game
                    System.out.println("=== THE END ===");
                    System.exit(0);
                    return;
                }
                prepareSlide();
            }
        }
        enterWasPressed = enterPressed;
    }

    @Override
    public void render(Renderer r) {
        Graphics g = r.getRawGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background
        if (bgImage != null) {
            int zoomPx = (int) (bgImage.getWidth() * 0.075);
            int zoomPy = (int) (bgImage.getHeight() * 0.075);
            g2.drawImage(bgImage,
                    0, 0, game.width, game.height,
                    zoomPx, zoomPy,
                    bgImage.getWidth() - zoomPx, bgImage.getHeight() - zoomPy,
                    null);
            g2.setColor(BG_OVERLAY);
            g2.fillRect(0, 0, game.width, game.height);
        } else {
            g2.setColor(BG_COLOR);
            g2.fillRect(0, 0, game.width, game.height);
        }

        // Decorative gold line at top
        g2.setColor(GOLD);
        g2.fillRect(0, 0, game.width, 3);

        String[] slide = slides[currentSlide];
        String title = slide[0];

        // --- Render Title ---
        g2.setFont(TITLE_FONT);
        FontMetrics titleFm = g2.getFontMetrics();
        int titleX = (game.width - titleFm.stringWidth(title)) / 2;
        int titleY;

        if (currentSlide == 0) {
            titleY = game.height / 3;
        } else {
            titleY = 80;
        }

        // Title shadow
        g2.setColor(SHADOW);
        g2.drawString(title, titleX + 2, titleY + 2);
        // Title gold
        g2.setColor(GOLD);
        g2.drawString(title, titleX, titleY);

        // --- Victory subtitle on first slide ---
        if (currentSlide == 0) {
            g2.setFont(SUBTITLE_FONT);
            FontMetrics subFm = g2.getFontMetrics();
            String subtitle = "The Shark Has Been Defeated";
            int subX = (game.width - subFm.stringWidth(subtitle)) / 2;
            g2.setColor(VICTORY_GOLD);
            g2.drawString(subtitle, subX, titleY + 40);
        }

        // --- Render Body Text (typewriter) ---
        g2.setFont(BODY_FONT);
        FontMetrics bodyFm = g2.getFontMetrics();
        int lineHeight = bodyFm.getHeight() + 6;
        int startY = (currentSlide == 0) ? titleY + 80 : 130;
        int charsRemaining = typewriterIndex;

        int bodyStartIdx = (currentSlide == 0) ? 2 : 1;
        for (int i = bodyStartIdx; i < slide.length; i++) {
            String line = slide[i];
            int lineY = startY + (i - bodyStartIdx) * lineHeight;

            if (charsRemaining <= 0)
                break;

            String visiblePart;
            if (charsRemaining >= line.length()) {
                visiblePart = line;
                charsRemaining -= line.length();
            } else {
                visiblePart = line.substring(0, charsRemaining);
                charsRemaining = 0;
            }

            int lineX = (game.width - bodyFm.stringWidth(line)) / 2;

            // Shadow
            g2.setColor(SHADOW);
            g2.drawString(visiblePart, lineX + 1, lineY + 1);
            // Text
            g2.setColor(TEXT_COLOR);
            g2.drawString(visiblePart, lineX, lineY);
        }

        // --- Prompt ---
        if (slideFullyRevealed && promptVisible) {
            g2.setFont(PROMPT_FONT);
            FontMetrics promptFm = g2.getFontMetrics();
            String prompt;
            if (currentSlide == slides.length - 1) {
                prompt = "[ Press ENTER to close ]";
            } else {
                prompt = "[ Press ENTER to continue ]";
            }
            int promptX = (game.width - promptFm.stringWidth(prompt)) / 2;
            int promptY = game.height - 60;

            g2.setColor(PROMPT_COLOR);
            g2.drawString(prompt, promptX, promptY);
        }

        // Decorative gold line at bottom
        g2.setColor(GOLD);
        g2.fillRect(0, game.height - 3, game.width, 3);
    }
}
