package tk.funayd.mobchess.impl.command.game;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.game.GameInstance;
import tk.funayd.mobchess.impl.game.GameManager;

public class GameListCommand extends CommandAPICommand {

    public GameListCommand(MobChessPlugin plugin) {
        super("list");
        withPermission("mobchess.game.list");
        executes((sender, args) -> {
            var games = plugin.getGameManager().getGames();

            if (games.isEmpty()) {
                sender.sendMessage(Component.text()
                        .append(Component.text("[MobChess] ", NamedTextColor.GOLD))
                        .append(Component.text("No active games.", NamedTextColor.YELLOW))
                        .build());
                return;
            }

            sender.sendMessage(Component.text()
                    .append(Component.text("[MobChess] ", NamedTextColor.GOLD))
                    .append(Component.text("Active games (" + games.size() + "):", NamedTextColor.GREEN))
                    .build());

            for (GameInstance game : games) {
                String status = game.isRunning() ? "RUNNING" : "STOPPED";
                NamedTextColor statusColor = game.isRunning() ? NamedTextColor.GREEN : NamedTextColor.RED;

                sender.sendMessage(Component.text()
                        .append(Component.text("  - ", NamedTextColor.GRAY))
                        .append(Component.text(game.getGameId(), NamedTextColor.AQUA))
                        .append(Component.text(" [", NamedTextColor.GRAY))
                        .append(Component.text(status, statusColor))
                        .append(Component.text("] Round: ", NamedTextColor.GRAY))
                        .append(Component.text(String.valueOf(game.getRoundCount()), NamedTextColor.WHITE))
                        .build());
            }
        });
    }
}
