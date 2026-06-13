package dev.tauri.lpc;

import dev.tauri.lpc.command.LPCCommands;
import dev.tauri.lpc.config.LPCConfig;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.function.Supplier;

@Mod(LPC.MOD_ID)
public class LPC {
    public static final String MOD_ID = "lpc";
    public static final String MOD_NAME = "LuckPerms Chat for Forge";
    public static Logger logger;

    public static String MOD_VERSION = "";
    public static String MOD_VERSION_ONLY = "";
    public static final String MC_VERSION = "1.20.1";
    public static File clientModPath;

    public static final String[] WELCOME_MESS = {
            "=======================================",
            "|  ╭╮╱╱╭━━━┳━━━╮",
            "|  ┃┃╱╱┃╭━╮┃╭━╮┃",
            "|  ┃┃╱╱┃╰━╯┃┃╱╰╯",
            "|  ┃┃╱╭┫╭━━┫┃╱╭╮",
            "|  ┃╰━╯┃┃╱╱┃╰━╯┃",
            "|  ╰━━━┻╯╱╱╰━━━╯",
            "",
            " Authors: Tau'ri Dev team",
            " Version: {version}",
            "======================================="
    };

    public static final Supplier<LuckPerms> LUCK_PERMS = LuckPermsProvider::get;

    public static void displayWelcomeMessage() {
        for (String s : WELCOME_MESS) {
            logger.info(s.replaceAll("\\{version}", LPC.MOD_VERSION));
        }
    }

    public LPC() {
        logger = LoggerFactory.getLogger(MOD_NAME);

        ModList.get().getModContainerById(MOD_ID).ifPresentOrElse(container -> {
            MOD_VERSION_ONLY = container.getModInfo().getVersion().getQualifier();
            MOD_VERSION = MC_VERSION + "-" + MOD_VERSION_ONLY;
            clientModPath = container.getModInfo().getOwningFile().getFile().getFilePath().toFile();
        }, () -> {
        });

        LPC.logger.info("Started loading LPC mod in {}", LPC.clientModPath.getAbsolutePath());
        LPC.logger.info("Loading LPC version {}", LPC.MOD_VERSION);

        LPCConfig.register();
        MinecraftForge.EVENT_BUS.register(this);
        displayWelcomeMessage();
    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) {
        LPCCommands.registerCommands(event);
    }
}
