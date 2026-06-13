package dev.tauri.lpc.command;

import dev.tauri.lpc.command.commands.ClearChatCommand;
import dev.tauri.lpc.command.commands.DebugCommand;
import dev.tauri.lpc.command.commands.ReloadCommand;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.List;

public class LPCCommands {
    @SuppressWarnings("unused")
    public static final List<AbstractCommand> COMMANDS = List.of(
            new ReloadCommand(),
            new ClearChatCommand(),
            new DebugCommand()
    );

    public static void registerCommands(RegisterCommandsEvent event) {
        Command.LPC_COMMAND_BASE.registerCommands(event.getDispatcher());
    }
}
