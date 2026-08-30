# Sleep Timer

Sleep Timer is a lightweight client-side Fabric mod that shows when sleeping becomes available. Its compact HUD can display the current in-game time, a countdown until night, and immediate sleep availability during thunderstorms.

## Features

- Countdown until sleeping becomes available
- Optional 24-hour in-game clock
- Thunderstorm sleep detection
- Smooth color transition as bedtime approaches
- Separate colors for daytime, sleep availability, thunderstorms, and inventory display
- Configurable preparation period and seconds display
- Option to show the clock or timer only while the inventory is open
- Percentage-based HUD positioning that stays consistent across screen sizes
- Live position preview while adjusting the X/Y sliders
- Nether and End handling, where the countdown is hidden outside the inventory

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19.3 or newer
- Fabric API
- Java 25 or newer

[Mod Menu](https://modrinth.com/mod/modmenu) and [Cloth Config](https://modrinth.com/mod/cloth-config) are recommended for the in-game configuration screen.

## Installation

1. Install Fabric Loader for Minecraft 26.2.
2. Install Fabric API.
3. Place the Sleep Timer JAR in your Minecraft `mods` folder.
4. To configure the mod in game, also install Mod Menu and Cloth Config.

With Mod Menu installed, open **Mods → Sleep Timer → Configure**.

## Building from source

Clone or download the project, then run:

```bash
./gradlew build
```

The distributable JAR is generated in `build/libs/`. Use the file that does not end in `-sources.jar`.

## License

Sleep Timer is available under the [CC0 1.0 Universal](LICENSE) license.
