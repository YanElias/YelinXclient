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
- JDK 8
- Minecraft Forge 1.8.9 (11.15.1.2318)
- Gradle compatible with ForgeGradle 2.1 (typically Gradle 2.14.1)

## Build
Use a compatible Gradle 2.x installation and run:

```bash
gradle setupDecompWorkspace
gradle build
```

The built mod JAR will be in `build/libs/`.

> This is an early foundation. Features will be added incrementally and tested for compatibility and performance.
