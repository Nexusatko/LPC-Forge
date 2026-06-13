package dev.tauri.lpc.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.regex.Pattern;

public class ComponentUtils {

    // Regex for format &xrrggbb
    private static final Pattern HEX_SHORT = Pattern.compile("&#([0-9a-fA-F]{6})");

    // Regex for BungeeCord format &x&r&r&g&g&b&b
    private static final Pattern HEX_LONG = Pattern.compile("&x&([0-9a-fA-F])&([0-9a-fA-F])&([0-9a-fA-F])&([0-9a-fA-F])&([0-9a-fA-F])&([0-9a-fA-F])");

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component colorFormat(String input) {
        if (input == null) input = "";
        return mm.deserialize(StringUtils.legacyColor(convertHex(input)))
                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public static net.minecraft.network.chat.Component toMCComponent(Component component) {
        return net.minecraft.network.chat.Component.Serializer.fromJson(GsonComponentSerializer.gson().serialize(component));
    }

    public static String convertHex(String input) {
        if (input == null) return null;

        var shortMatcher = HEX_SHORT.matcher(input);
        input = shortMatcher.replaceAll("<#$1>");

        var longMatcher = HEX_LONG.matcher(input);
        input = longMatcher.replaceAll("<#$1$2$3$4$5$6>");

        return input;
    }
}
