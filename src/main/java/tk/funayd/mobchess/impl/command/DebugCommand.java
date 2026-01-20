package tk.funayd.mobchess.impl.command;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.misc.log.DebugLogger;

public class DebugCommand extends CommandAPICommand{

    public DebugCommand(MobChessPlugin plugin) {
        super("debug");
        withPermission("mobchess.debug");
        executes((sender, args) -> {
            DebugLogger.toggle(sender);

            boolean isEnabled = DebugLogger.isListening(sender);
            Component message;

            if (isEnabled) {
                message = Component.text()
                        .append(Component.text("[MobChess] ", NamedTextColor.GOLD))
                        .append(Component.text("Debug mode ", NamedTextColor.WHITE))
                        .append(Component.text("ENABLED", NamedTextColor.GREEN))
                        .build();
            } else {
                message = Component.text()
                        .append(Component.text("[MobChess] ", NamedTextColor.GOLD))
                        .append(Component.text("Debug mode ", NamedTextColor.WHITE))
                        .append(Component.text("DISABLED", NamedTextColor.RED))
                        .build();
            }

            sender.sendMessage(message);
        });
    }
}
