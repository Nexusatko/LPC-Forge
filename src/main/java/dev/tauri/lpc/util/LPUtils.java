package dev.tauri.lpc.util;

import dev.tauri.lpc.LPC;
import dev.tauri.lpc.config.LPCConfig;
import net.minecraft.server.level.ServerPlayer;

import java.util.stream.Collectors;

public class LPUtils {
    public static String buildFormat(final ServerPlayer player) {
        final var metaData = LPC.LUCK_PERMS.get().getPlayerAdapter(ServerPlayer.class).getMetaData(player);
        final var group = metaData.getPrimaryGroup();

        var formatOpt = LPCConfig.getGroupFormat(group);
        var format = LPCConfig.chatFormat.get();
        if (formatOpt.isPresent()) {
            format = formatOpt.get();
        }

        final var prefix = metaData.getPrefix();
        final var suffix = metaData.getSuffix();
        final String usernameColor = metaData.getMetaValue("username-color");
        final String messageColor = metaData.getMetaValue("message-color");

        format = format
                .replace("{prefix}", prefix != null ? prefix : "")
                .replace("{suffix}", suffix != null ? suffix : "")
                .replace("{prefixes}", metaData.getPrefixes().keySet().stream()
                        .map(key -> metaData.getPrefixes().get(key))
                        .collect(Collectors.joining()))
                .replace("{suffixes}", metaData.getSuffixes().keySet().stream()
                        .map(key -> metaData.getSuffixes().get(key))
                        .collect(Collectors.joining()))
                .replace("{world}", player.level().dimension().location().toString())
                .replace("{name}", player.getName().getString())
                .replace("{displayname}", player.getDisplayName().getString())
                .replace("{username-color}", usernameColor != null ? usernameColor : "")
                .replace("{message-color}", messageColor != null ? messageColor : "");
        return format;
    }

    public static String getPlayerDisplayName(final ServerPlayer player) {
        final var metaData = LPC.LUCK_PERMS.get().getPlayerAdapter(ServerPlayer.class).getMetaData(player);
        final var prefix = metaData.getPrefix();
        final var suffix = metaData.getSuffix();
        return (prefix == null ? "" : prefix) + player.getName().getString() + (suffix == null ? "" : suffix);
    }

    public static boolean hasPermission(ServerPlayer player, String permission) {
        return LPC.LUCK_PERMS.get().getPlayerAdapter(ServerPlayer.class).getPermissionData(player).checkPermission(permission).asBoolean();
    }

    public static String processMessage(final ServerPlayer player, final String message) {
        if (hasPermission(player, "lpc.colorcodes") && hasPermission(player, "lpc.rgbcodes")) {
            return message;
        } else if (hasPermission(player, "lpc.colorcodes")) {
            return stripHexCodes(message);
        } else {
            return stripColorCodes(stripHexCodes(message));
        }
    }

    public static String stripColorCodes(final String message) {
        return message.replaceAll("&[0-9a-fA-Fk-oK-OrR]", "");
    }

    public static String stripHexCodes(final String message) {
        String result = message.replaceAll("&#[0-9a-fA-F]{6}", "");
        result = result.replaceAll("&x(&[0-9a-fA-F]){6}", "");
        return result;
    }
}
