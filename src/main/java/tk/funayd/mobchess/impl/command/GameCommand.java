package tk.funayd.mobchess.impl.command;

import dev.jorel.commandapi.CommandAPICommand;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.impl.command.game.GameListCommand;
import tk.funayd.mobchess.impl.command.game.GameStartCommand;
import tk.funayd.mobchess.impl.command.game.GameStopCommand;
import tk.funayd.mobchess.impl.game.GameManager;

public class GameCommand extends CommandAPICommand {

    public GameCommand(MobChessPlugin plugin) {
        super("game");
        withPermission("mobchess.game");
        withSubcommand(new GameStartCommand(plugin));
        withSubcommand(new GameStopCommand(plugin));
        withSubcommand(new GameListCommand(plugin));
    }
}
