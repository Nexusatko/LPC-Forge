package dev.tauri.lpc.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class LPCConfig {
    public static final AtomicReference<ForgeConfigSpec> BUILT_CONFIG = new AtomicReference<>();
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder().comment(
            "LPC Configuration",
            "https://luckperms.net/wiki/Prefixes,-Suffixes-&-Meta",
            "",
            "Placeholders:",
            "{message} - the chat message",
            "{name} - the player's name",
            "{displayname} - the player's display name / nickname",
            "{world} - the world name of the player's current world",
            "{prefix} - the player's highest priority prefix",
            "{suffix} - the player's highest priority suffix",
            "{prefixes} - the player's prefixes sorted by the highest priority",
            "{suffixes} - the player's suffixes sorted by the highest priority",
            "{username-color} - the player's or the group's username color",
            "{message-color} - the player's or the group's message color",
            "",
            "Color codes: Use & for colors (&a, &b, etc.) and &#rrggbb for hex colors.",
            "Permissions: lpc.reload, lpc.clearchat, lpc.debug, lpc.colorcodes, lpc.rgbcodes",
            "",
            "Commands:",
            "  /lpc reload - Reload configuration",
            "  /lpc clear - Clear chat for all players",
            "  /lpc debug <player> - Show resolved prefix/suffix info for a player",
            "",
            "More information: https://www.spigotmc.org/resources/68965"
    );

    public static final ForgeConfigSpec.ConfigValue<String> chatFormat = BUILDER
            .comment(
                    "",
                    "Examples:",
                    "\"[{world}] {prefix}{name}&r: {message}\"",
                    "\"{prefix}{name}{suffix}&r: {message}\"",
                    "\"{prefix}{username-color}{name}&r: {message-color}{message}\""
            )
            .define("chat-format", "{prefix}{name}{suffix}&r: {message}");

    public static final ForgeConfigSpec.ConfigValue<List<String>> groupFormats = BUILDER
            .comment(
                    "",
                    "Per-group chat format. Overrides chat-format for the specified group.",
                    "Format: group:format"
            )
            .define("group-formats", new ArrayList<>(List.of("admin:{prefix}{name}&r: {message}")));

    public static final ForgeConfigSpec.ConfigValue<String> clearMessage = BUILDER
            .comment("Message shown after /lpc clear")
            .define("clear-chat-message", "&7Chat has been cleared by a staff member.");

    public static Optional<String> getGroupFormat(String group) {
        return groupFormats.get().stream()
                .filter(f -> f.startsWith(group + ":"))
                .map(s -> s.replaceFirst(Pattern.quote(group + ":"), ""))
                .findFirst();
    }

    @SuppressWarnings("deprecated, removal")
    public static void register() {
        BUILT_CONFIG.set(BUILDER.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BUILT_CONFIG.get(), "lpc.toml");
    }
}
