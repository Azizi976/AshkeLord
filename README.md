# AshkeLord: Legend of the Golden Chai

A 2D Java game built from scratch using Java AWT/Swing.

## Project Structure

```
src/
├── main/
│   ├── java/com/ashkelord/
│   │   ├── core/          — Core utilities and systems
│   │   ├── entities/      — Entity hierarchy (creatures, NPCs, behaviors)
│   │   ├── gfx/           — Rendering, sprites, camera
│   │   ├── input/         — Keyboard & mouse handling
│   │   ├── audio/         — Sound & music management
│   │   ├── ui/            — HUD, menus, dialogue
│   │   ├── items/         — Game items
│   │   ├── quests/        — Quest system
│   │   ├── states/        — Game states (menu, gameplay, etc.)
│   │   ├── tiles/         — Tile system
│   │   ├── worlds/        — World & level management
│   │   └── main/          — Entry point (Launcher, Game, Display)
│   └── resources/
│       ├── textures/      — Sprite sheets & images
│       ├── sounds/        — Audio files
│       ├── maps/          — Level & world data
│       └── fonts/         — Custom fonts
└── test/
    └── java/com/ashkelord/ — Unit tests
```

## Build & Run

### Prerequisites
- Java 17+
- Gradle (or use the Gradle wrapper)

### Run the game
```bash
gradle run
```

### Build a JAR
```bash
gradle build
```

The compiled JAR will be in `build/libs/`.

## License

All rights reserved.
