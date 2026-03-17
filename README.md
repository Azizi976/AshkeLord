# AshkeLord: Legend of the Golden Chai

A 2D Java game built from scratch using Java AWT/Swing.

## Project Structure 📁

```text
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
```

## Build & Run 🚀

### Prerequisites
- **Java 17+**
- **Gradle** (or make sure you use a built-in wrapper `gradlew`)

### Quick Start
To launch the game from the command line, simply run:
```bash
java -cp "out;src/main/resources" com.ashkelord.main.Launcher
```

*Note: Ensure classes are compiled to `/out` via `javac` beforehand.*

### Building via Gradle
```bash
gradle build
```
The compiled JAR will be generated in `build/libs/`.

## License

All rights reserved.
