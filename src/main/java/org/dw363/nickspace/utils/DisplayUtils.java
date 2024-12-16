package org.dw363.nickspace.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Vector;

public class DisplayUtils {

    private static final double MAX_DISTANCE = 5.0; // Максимальная дистанция для отображения ника

    public static Player getPlayerLookingAt(Player player) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();
        for (double i = 0; i <= MAX_DISTANCE; i += 0.1) {
            Location checkLocation = eyeLocation.clone().add(direction.clone().multiply(i));
            for (Player target : player.getWorld().getPlayers()) {
                if (target.equals(player)) continue;
                if (target.getBoundingBox().contains(checkLocation.toVector())) {
                    return target;
                }
            }
        }
        return null;
    }

    public static void createTextDisplay(Player viewer, Player target) {
        Location targetLocation = target.getLocation().clone().add(0, 2.2, 0); // Выше головы игрока
        TextDisplay textDisplay = target.getWorld().spawn(targetLocation, TextDisplay.class);
        textDisplay.text(Component.text(target.getName()).color(NamedTextColor.YELLOW));
        textDisplay.setBillboard(Display.Billboard.CENTER);
        textDisplay.addScoreboardTag(viewer.getName() + "-nick");
        textDisplay.setPersistent(false);
    }

    public static boolean hasActiveTextDisplay(Player viewer, Player target) {
        return target.getWorld().getEntitiesByClass(TextDisplay.class).stream()
                .anyMatch(display -> display.getScoreboardTags().contains(viewer.getName() + "-nick"));
    }

    public static void removeTextDisplay(Player viewer) {
        viewer.getWorld().getEntitiesByClass(TextDisplay.class).stream()
                .filter(display -> display.getScoreboardTags().contains(viewer.getName() + "-nick"))
                .forEach(Display::remove);
    }

    public static void removeAllTextDisplaysForPlayer(Player player) {
        Bukkit.getWorlds().forEach(world ->
                world.getEntitiesByClass(TextDisplay.class).stream()
                        .filter(display -> display.getScoreboardTags().contains(player.getName() + "-nick"))
                        .forEach(Display::remove)
        );
    }
}
