# YelinX Client 💛

A lightweight, yellow-and-graphite Minecraft Java client mod for **Minecraft 1.8.9 + Forge**.

## Visual identity
- Yellow accent: `#FFD600`
- Graphite background: `#202020`
- Soft white text: `#F5F5F5`
- No green accent color.

## Current foundation
- Forge mod entry point and client-side event setup
- Custom YelinX menu opened with Right Shift
- Simple FPS HUD overlay
- Minimal ForgeGradle 2.1 project structure

## Requirements
- JDK 8 (ForgeGradle 2.1 is too old for Java 17/21/25)
- Minecraft Forge 1.8.9 (11.15.1.2318)
- Internet connection on the first build, to download Gradle and Minecraft/Forge dependencies

## Build on Linux (BigLinux / Arch / Manjaro)
From the repository folder, run:

```bash
chmod +x scripts/build-linux.sh
./scripts/build-linux.sh
```

The script searches common locations for Java 8, downloads Gradle 2.14.1 into `~/.cache/yelinx/` if needed, prepares ForgeGradle, and builds the project. You can set `JAVA8_HOME=/path/to/jdk8` if Java 8 is installed somewhere else.

The built mod JAR will be in `build/libs/`. Copy it to the `mods` folder of a Minecraft 1.8.9 Forge profile in Prism Launcher, then launch the game and press **Right Shift** to open the menu.

## Accounts and custom skins
Account switching and skin importing are being added as separate features. Microsoft login must use a supported authentication flow; never enter your Microsoft password into an unofficial in-game form. A local skin override is client-side and does not upload a skin to your Microsoft/Minecraft profile.

> This is an early foundation. The build script has been added, but the project has **not yet been compiled in this environment**. The first local build is still needed to verify compatibility.
