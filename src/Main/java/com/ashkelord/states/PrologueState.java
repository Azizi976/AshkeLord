package com.ashkelord.states;

import com.ashkelord.main.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Cinematic prologue intro — classic RPG-style click-through narrative screens.
 * Tells the story of Nadav "The Prince" Biton before gameplay begins.
 */
public class PrologueState extends State {

    // --- Color Palette (Golden Hour / Urban Gritty) ---
    private static final Color BG_COLOR = new Color(0x1a, 0x1a, 0x2e); // Dark midnight blue
    private static final Color GOLD = new Color(0xd4, 0xa0, 0x17); // Golden hour
    private static final Color GOLD_DIM = new Color(0xd4, 0xa0, 0x17, 120); // Faded gold
    private static final Color TEXT_COLOR = new Color(0xe0, 0xe0, 0xe0); // Light grey body text
    private static final Color PROMPT_COLOR = new Color(0xff, 0xd7, 0x00); // Bright gold prompt
    private static final Color SHADOW = new Color(0, 0, 0, 200); // Text shadow
    private static final Color BG_OVERLAY = new Color(0, 0, 0, 70); // Light overlay on image

    // --- Fonts ---
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 36);
    private static final Font SUBTITLE_FONT = new Font("Serif", Font.ITALIC, 18);
    private static final Font BODY_FONT = new Font("Monospaced", Font.PLAIN, 16);
    private static final Font PROMPT_FONT = new Font("Monospaced", Font.BOLD, 14);

    // --- Narrative Slides ---
    private final String[][] slides = {
            // Each slide: { title, line1, line2, line3, ... }
            {
                    "AshkeLord",
                    "The Legend of the Golden Chai",
                    "",
                    "A tale of honor, heartbreak,",
                    "and the quest for the ultimate drip."
            },
            {
                    "The Streets of Ashkelon",
                    "In the sun-baked streets of Ashkelon,",
                    "honor is everything.",
                    "",
                    "Where concrete meets the sea,",
                    "legends are forged in gold chains",
                    "and the roar of Honda Civics."
            },
            {
                    "The Prince",
                    "Nadav \"The Prince\" Biton.",
                    "",
                    "Once the proudest rider in the neighborhood.",
                    "His electric bike gleamed under the sunset.",
                    "His girl, Shirel, was the envy of every corner."
            },
            {
                    "The Darkest Morning",
                    "But one morning, everything changed.",
                    "",
                    "His electric bike's battery... stolen.",
                    "And Shirel? Gone.",
                    "",
                    "She left him for his arch-nemesis..."
            },
            {
                    "The Shark",
                    "Liran \"The Shark\" - ruler of the Marina.",
                    "",
                    "With his blacked-out Honda Civic",
                    "and his iron grip on the waterfront,",
                    "he took everything from Nadav.",
                    "",
                    "Everything."
            },
            {
                    "Your Destiny Awaits",
                    "Reclaim your honor.",
                    "Earn your Street Cred.",
                    "Defeat The Shark.",
                    "",
                    "Become the AshkeLord.",
                    "",
                    "                    ...Yalla, let's go."
            }
    };

    // --- State ---
    private int currentSlide = 0;
    private int typewriterIndex = 0; // Characters revealed so far
    private int totalCharsInSlide = 0; // Total characters in current slide body
    private long lastCharTime = 0; // Timestamp for typewriter pacing
    private static final long CHAR_DELAY_MS = 35; // Milliseconds per character

    private boolean slideFullyRevealed = false;
    private long promptBlinkTimer = 0;
    private boolean promptVisible = true;

    private boolean enterWasPressed = false; // Debounce for enter key

    // Background image
    private BufferedImage bgImage;

    public PrologueState(Game game) {
        super(game);
        loadBackground();
        prepareSlide();
    }

    private void loadBackground() {
        try {
            bgImage = ImageIO.read(getClass().getResourceAsStream("/textures/prologue_bg.jpg"));
        } catch (Exception e) {
            System.err.println("Warning: Could not load prologue background. Using solid color.");
            bgImage = null;
        }
    }

    private void prepareSlide() {
        typewriterIndex = 0;
        slideFullyRevealed = false;
        lastCharTime = System.currentTimeMillis();

        // Count total body characters (skip title at index 0)
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
                // Skip typewriter — reveal full text instantly
                typewriterIndex = totalCharsInSlide;
                slideFullyRevealed = true;
            } else {
                // Advance to next slide
                currentSlide++;
                if (currentSlide >= slides.length) {
                    // Prologue finished — transition to gameplay
                    State.setState(new GameState(game));
                    return;
                }
                prepareSlide();
            }
        }
        enterWasPressed = enterPressed;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background image (scaled to fill) + dark overlay for text readability
        if (bgImage != null) {
            // Zoom in ~10%: crop center of source image for tighter framing
            int zoomPx = (int) (bgImage.getWidth() * 0.075); // 7.5% from each side = 15% zoom
            int zoomPy = (int) (bgImage.getHeight() * 0.075);
            g2.drawImage(bgImage,
                    0, 0, game.width, game.height, // destination (full screen)
                    zoomPx, zoomPy, // source top-left (cropped)
                    bgImage.getWidth() - zoomPx, bgImage.getHeight() - zoomPy, // source bottom-right
                    null);
            g2.setColor(BG_OVERLAY);
            g2.fillRect(0, 0, game.width, game.height);
        } else {
            g2.setColor(BG_COLOR);
            g2.fillRect(0, 0, game.width, game.height);
        }

        // Decorative gold line at top
        g2.setColor(GOLD_DIM);
        g2.fillRect(0, 0, game.width, 3);

        String[] slide = slides[currentSlide];
        String title = slide[0];

        // --- Render Title (always fully visible) ---
        g2.setFont(TITLE_FONT);
        FontMetrics titleFm = g2.getFontMetrics();
        int titleX = (game.width - titleFm.stringWidth(title)) / 2;
        int titleY;

        if (currentSlide == 0) {
            // First slide: title centered vertically, larger presence
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

        // --- Subtitle on first slide ---
        if (currentSlide == 0) {
            g2.setFont(SUBTITLE_FONT);
            FontMetrics subFm = g2.getFontMetrics();
            String subtitle = slide[1];
            int subX = (game.width - subFm.stringWidth(subtitle)) / 2;
            g2.setColor(GOLD_DIM);
            g2.drawString(subtitle, subX, titleY + 40);
        }

        // --- Render Body Text (typewriter effect) ---
        g2.setFont(BODY_FONT);
        FontMetrics bodyFm = g2.getFontMetrics();
        int lineHeight = bodyFm.getHeight() + 6;
        int startY = (currentSlide == 0) ? titleY + 80 : 130;
        int charsRemaining = typewriterIndex;

        int bodyStartIdx = (currentSlide == 0) ? 2 : 1; // Skip subtitle on first slide
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
            // Adjust for partially revealed lines
            if (!visiblePart.equals(line)) {
                lineX = (game.width - bodyFm.stringWidth(line)) / 2;
            }

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
                prompt = "[ Press ENTER to begin your journey ]";
            } else {
                prompt = "[ Press ENTER to continue ]";
            }
            int promptX = (game.width - promptFm.stringWidth(prompt)) / 2;
            int promptY = game.height - 60;

            g2.setColor(PROMPT_COLOR);
            g2.drawString(prompt, promptX, promptY);
        }

        // Decorative gold line at bottom
        g2.setColor(GOLD_DIM);
        g2.fillRect(0, game.height - 3, game.width, 3);
    }
}
