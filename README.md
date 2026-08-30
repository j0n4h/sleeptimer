# Sleep Timer

A small Fabric client mod for Minecraft 26.2 that shows a compact sleep reminder in the HUD.

It helps you quickly see:
- when you can sleep
- how long until sleep is available
- whether thunderstorms allow sleeping
- an optional in-game clock

## Current status

This project is a working Fabric mod and builds successfully with Gradle. It is client-side only and does not change gameplay.

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19.3+
- Fabric API
- Java 25+

Optional for config UI:
- [Mod Menu](https://modrinth.com/mod/modmenu)
- [Cloth Config](https://modrinth.com/mod/cloth-config)

## Install

1. Install Fabric for Minecraft 26.2.
2. Install Fabric API.
3. Put the mod JAR in your `mods` folder.
4. If you want the in-game config screen, also install Mod Menu and Cloth Config.

## Build from source

```bash
./gradlew build
```

The JAR is created in `build/libs/`.

## License

This project is licensed under the [MIT](LICENSE) license.
