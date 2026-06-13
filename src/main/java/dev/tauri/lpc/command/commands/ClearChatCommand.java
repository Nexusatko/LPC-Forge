package dev.tauri.lpc.command.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.tauri.lpc.command.AbstractCommand;
import dev.tauri.lpc.command.Command;
import dev.tauri.lpc.config.LPCConfig;
import dev.tauri.lpc.util.ComponentUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class ClearChatCommand extends AbstractCommand {
    public ClearChatCommand() {
        super(Command.LPC_COMMAND_BASE);
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getGeneralUsage() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clear chat for all players";
    }

    @Override
    public String getLPPermission() {
        return "lpc.command.clear";
    }

    @Override
    public ArgumentBuilder<CommandSourceStack, ?> registerArguments(ArgumentBuilder<CommandSourceStack, ?> command) {
        return command.executes(ctx -> {
            for (var i = 0; i < 100; i++) {
                ctx.getSource().getServer().getPlayerList().getPlayers().forEach(p -> p.sendSystemMessage(Component.empty()));
            }
            ctx.getSource().getServer().getPlayerList().broadcastSystemMessage(ComponentUtils.toMCComponent(ComponentUtils.colorFormat(LPCConfig.clearMessage.get())), false);
            return 1;
        });
    }
}
