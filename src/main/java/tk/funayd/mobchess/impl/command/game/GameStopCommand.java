package tk.funayd.mobchess.impl.command.game;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.game.ModuleStopReason;
import tk.funayd.mobchess.impl.game.GameManager;

public class GameStopCommand extends CommandAPICommand {

    public GameStopCommand(MobChessPlugin plugin) {
        super("stop");
        withPermission("mobchess.game.stop");
        withArguments(new StringArgument("gameId"));
        executes((sender, args) -> {
            String gameId = (String) args.get("gameId");
            GameManager manager = plugin.getGameManager();
            manager.getGame(gameId).ifPresentOrElse(
                    game -> {
                        game.stop(ModuleStopReason.CANCELLED);
                        manager.removeGame(gameId);
                        sender.sendMessage(Component.text()
                                .append(Component.text("[MobChess] ", NamedTextColor.GOLD))
                                .append(Component.text("Game ", NamedTextColor.GREEN))
                                .append(Component.text(gameId, NamedTextColor.AQUA))
                                .append(Component.text(" has been stopped.", NamedTextColor.GREEN))
                                .build());
                    },
                    () -> sender.sendMessage(Component.text()
                            .append(Component.text("[MobChess] ", NamedTextColor.GOLD))
                            .append(Component.text("Game not found: ", NamedTextColor.RED))
                            .append(Component.text(gameId, NamedTextColor.AQUA))
                            .build()));
        });
    }
}
