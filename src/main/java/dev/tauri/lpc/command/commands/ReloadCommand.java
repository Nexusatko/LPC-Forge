package dev.tauri.lpc.command.commands;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.tauri.lpc.command.AbstractCommand;
import dev.tauri.lpc.command.Command;
import dev.tauri.lpc.config.LPCConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.fml.loading.FMLPaths;

public class ReloadCommand extends AbstractCommand {
    public ReloadCommand() {
        super(Command.LPC_COMMAND_BASE);
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getGeneralUsage() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the mod config";
    }

    @Override
    public String getLPPermission() {
        return "lpc.command.reload";
    }

    @Override
    public ArgumentBuilder<CommandSourceStack, ?> registerArguments(ArgumentBuilder<CommandSourceStack, ?> command) {
        return command.executes(ctx -> {
            var configPath = FMLPaths.CONFIGDIR.get().resolve("lpc.toml");
            var fileConfig = CommentedFileConfig.builder(configPath)
                    .sync()
                    .autoreload()
                    .build();

            fileConfig.load();
            LPCConfig.BUILT_CONFIG.get().acceptConfig(fileConfig);
            baseCommand.sendSuccessMess(ctx.getSource(), "Configs reloaded!");
            return 1;
        });
    }
}
