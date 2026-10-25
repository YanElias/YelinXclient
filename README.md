# YelinX Client 💛

A lightweight client-side mod for **Minecraft Java 1.8.9 + Forge 11.15.1.2318**.

## Visual identity
- Yellow: `#FFD600`
- Graphite: `#202020`
- Soft white: `#F5F5F5`
- No green accent color.

## Features in 0.2.0
- Custom yellow-and-graphite main menu.
- Custom pause menu with Resume, Modules, Options, and Save & Quit to Title.
- Right Shift opens the YelinX quick menu in-game.
- Module screen with live ON/OFF toggles.
- FPS Counter HUD (enabled by default).
- Coordinates HUD (XYZ).
- Keystrokes HUD (WASD, mouse buttons, Space).
- Fullbright module with gamma restoration when leaving a world.
- English and Brazilian Portuguese keybind labels.

## Build on Linux (BigLinux / Arch / Manjaro)
Requirements: JDK 8, `curl`, `unzip`, and internet access on the first build.

```bash
git clone https://github.com/YanElias/YelinXclient.git
cd YelinXclient
chmod +x scripts/build-linux.sh
./scripts/build-linux.sh
```

The script downloads Gradle 2.14.1 into `~/.cache/yelinx/` and runs `setupDecompWorkspace` followed by `build`. If Java 8 is installed outside the common paths, set `JAVA8_HOME=/path/to/jdk8` before running the script.

The JAR should appear in `build/libs/`. Copy it into the `mods` folder for a **Minecraft 1.8.9 Forge** instance in Prism Launcher, then launch the game. Test the main menu, open a world, press Escape for the pause menu, and press Right Shift for the quick menu.

## Not implemented yet
Microsoft account sign-in, account switching, and custom skin importing are not implemented yet. They need separate implementation and testing.

> The new 0.2.0 code has been committed to GitHub, but it has not been compiled or launched in this environment. Run the build above and report any error so compatibility can be verified.
