package tk.funayd.mobchess.impl.command.game;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.game.GameInstance;
import tk.funayd.mobchess.api.game.GameSetup;
import tk.funayd.mobchess.impl.game.GameManager;
import tk.funayd.mobchess.impl.game.round.TestRoundProvider;

public class GameStartCommand extends CommandAPICommand {

    public GameStartCommand(MobChessPlugin plugin) {
        super("start");
        withPermission("mobchess.game.start");
        executes((sender, args) -> {
            GameSetup setup = GameSetup.builder()
                    .roundProvider(new TestRoundProvider())
                    .build();

            GameInstance game = plugin.getGameManager().createGame(setup);
            game.start();

            sender.sendMessage(Component.text()
                    .append(Component.text("[MobChess] ", NamedTextColor.GOLD))
                    .append(Component.text("Game started with ID: ", NamedTextColor.GREEN))
                    .append(Component.text(game.getGameId(), NamedTextColor.AQUA))
                    .build());
        });
    }
}
