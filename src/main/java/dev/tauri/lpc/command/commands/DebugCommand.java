package dev.tauri.lpc.command.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.tauri.lpc.command.AbstractCommand;
import dev.tauri.lpc.command.Command;
import dev.tauri.lpc.util.ComponentUtils;
import dev.tauri.lpc.util.LPUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

public class DebugCommand extends AbstractCommand {
    public DebugCommand() {
        super(Command.LPC_COMMAND_BASE);
    }

    @Override
    public String getName() {
        return "debug";
    }

    @Override
    public String getGeneralUsage() {
        return "debug";
    }

    @Override
    public String getDescription() {
        return "Show resolved prefix/suffix info for a player";
    }

    @Override
    public String getLPPermission() {
        return "lpc.command.debug";
    }

    @Override
    public ArgumentBuilder<CommandSourceStack, ?> registerArguments(ArgumentBuilder<CommandSourceStack, ?> command) {
        return command
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(ctx -> {
                            ctx.getSource().sendSystemMessage(ComponentUtils.toMCComponentColored(LPUtils.getPlayerDisplayName(EntityArgument.getPlayer(ctx, "player"))));
                            return 1;
                        })
                );
    }
}
