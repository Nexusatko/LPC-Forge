package dev.tauri.lpc.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.tauri.lpc.util.LPUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.List;

public abstract class AbstractCommand {

    public final Command baseCommand;

    public AbstractCommand(Command baseCommand) {
        this.baseCommand = baseCommand;
        baseCommand.registerSubCommand(this);
    }

    public final ArgumentBuilder<CommandSourceStack, ?> register(String command) {
        return registerArguments(Commands.literal(command).requires(cs -> cs.hasPermission(getPermissions()) || (cs.getPlayer() != null && LPUtils.hasPermission(cs.getPlayer(), getLPPermission()))));
    }

    public List<String> getAliases(){
        return List.of();
    }

    public abstract String getName();

    public abstract String getGeneralUsage();

    public abstract String getDescription();

    public int getPermissions() {
        return 2;
    }

    public String getLPPermission() {
        return "*";
    }

    public abstract ArgumentBuilder<CommandSourceStack, ?> registerArguments(ArgumentBuilder<CommandSourceStack, ?> command);
}
