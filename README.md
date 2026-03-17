# AshkeLord: Legend of the Golden Chai

A 2D Java game built from scratch using Java AWT/Swing without any external library dependencies.

## 📖 Storyline
The game follows **Nadav**, a hero from Ashkelon whose life got turned upside down: his electric bike was stolen, and his girlfriend **Shirel** left him for **Liran "The Shark"**, the big boss of the periphery who rules the city.
To restore his lost honor and win back Shirel's love, Nadav embarks on a journey across Ashkelon, trying to gather "Street Creds" and become the **AshkeLord**.

Nadav's journey is divided into several main Quests:
- **The Lost Gel:** Tzion the Barber lost his Strong Hold Wax to Avi, the gym bully. Nadav must intimidate Avi and return the gel to Tzion in exchange for a fresh haircut (going bald).
- **Escape from Periphery:** Escorting Yotam, the nerdy Tel-Avivian, through the "Keter Chair Maze". Nadav must protect Yotam and evade tracksuited "Arsim" trying to rob him. Reward: Oat Milk Cortado that gives a speed boost.
- **The Sacred Shawarma:** Miri demands Nadav find the legendary "Golden Amba" located at the Ashkelon Port in exchange for a heavy Laffa to restore his strength.
- **The Final Spit:** The ultimate showdown against Liran The Shark. A boss fight in a closed arena for a spitting duel, to earn the Golden Amba and the title of "AshkeLord".

## 🎮 Gameplay & Controls
- **Movement:** `W, A, S, D` keys or Arrow Keys (`Up, Down, Left, Right`).
- **Interaction (Talking to NPCs & Advancing dialog):** `Enter` key.
- **Primary Attack (Spit / Melee):** `Space` key.
- **Secondary Attack:** `Shift` key.
- **Special Attack (Unlocked later - e.g. Techno Music AoE damage):** `Q` key.

### Core Mechanics:
- **Evolution System:** At a certain point, as Nadav gathers enough "Street Creds" and finds special items (like an E-Bike Battery), he evolves from a "Baby Ars" to an "Intermediate Ars". Each evolution changes his appearance, grants a permanent speed multiplier, and unlocks new attacks.
- **Inventory:** An RPG-style inventory system to store and access quest items and rewards collected along the way (managed by `Tab` or active during runtime).
- **Stats:** The game tracks stats like Street Creds, Nervim (Nerves), and Charisma, which are crucial metrics for interacting with characters and progressing the storyline.

## 📂 Project Structure
The project is built on an efficient architecture providing full control without external helpers:

```text
src/
├── main/
│   ├── java/com/ashkelord/
│   │   ├── core/          — Global utilities and helper functions.
│   │   ├── entities/      — Entity hierarchy for the Player, NPCs (Yotam, Miri, Avi), enemies (LiranBoss), and QuestItems.
│   │   │   ├── behaviors/ — Evolution states and attack forms for the player.
│   │   │   ├── creatures/ — Mobile entities (Player, NPCs, Bosses).
│   │   │   └── projectiles/ — Ranged attacks (e.g. Spit).
│   │   ├── gfx/           — Graphic rendering, cropping, and generative creation of 16-bit style sprite sheets, plus game camera math.
│   │   ├── input/         — Keyboard state management and tracking (`KeyManager`).
│   │   ├── combat/        — Combat system (handling melee, ranged, Area of Effect (AoE) attacks, and cooldowns).
│   │   ├── inventory/     — Player inventory management.
│   │   ├── audio/         — Core sound effects and background music streaming (`AudioManager`).
│   │   ├── ui/            — User interface including the HUD, menus, and rich in-game dialog boxes (`DialogBox`).
│   │   ├── items/         — Collectible game items.
│   │   ├── quests/        — Flexible quest system tracking storyline progression.
│   │   ├── states/        — System states: Menu (MenuState), gameplay (GameState), Prologue, and Epilogue.
│   │   ├── tiles/         — Map tile logic. Precise assembly of street terrain types.
│   │   ├── worlds/        — World management and level loading via text files (world_maze, world_boss, etc).
│   │   └── main/          — Game entry points: Launcher (base), Game (running the core loop), and Display (JFrame window).
│   └── resources/
│       ├── textures/      — Image assets and sprite sheets.
│       ├── sounds/        — Audio files.
│       ├── maps/          — Text files defining the layout of world elements for each map.
│       └── fonts/         — Embedded retro custom fonts.
```

## 🚀 How to Run

### Prerequisites
- **Java 17** or higher.
- **Gradle** (or use the included wrapper `gradlew`).

### Building via Gradle
To re-package and compile the project, it is highly recommended to use Gradle via the command line (Terminal/CMD):
```bash
gradle build
```
This action will create the final runnable JAR build in `build/libs/`.

### Quick Start (from compiled source)
If you skip the build process and just want to run directly (assuming classes are successfully compiled into `/out`), run:
```bash
java -cp "out;src/main/resources" com.ashkelord.main.Launcher
```
*(Important note for Linux / macOS users: Use a colon `:` instead of a semicolon `;` as the classpath separator, i.e. `out:src/main/resources`).*

## License
All rights reserved.
