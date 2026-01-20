package tk.funayd.mobchess.impl.command;

import dev.jorel.commandapi.CommandAPICommand;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.impl.game.GameManager;

/**
 * Main command handler for MobChess plugin.
 * All subcommands are registered under /mobchess.
 * 
 * Usage:
 * - /mobchess debug - Toggle debug mode
 * - /mobchess game start - Start a new test game
 * - /mobchess game stop <gameId> - Stop a specific game
 * - /mobchess game list - List all active games
 */
public class MobChessCommand extends CommandAPICommand {
    public MobChessCommand(MobChessPlugin plugin) {
        super("mobchess");
        withPermission("mobchess.admin");
        withSubcommand(new DebugCommand(plugin));
        withSubcommand(new GameCommand(plugin));
    }
}
