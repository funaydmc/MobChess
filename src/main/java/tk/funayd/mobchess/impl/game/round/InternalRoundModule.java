package tk.funayd.mobchess.impl.game.round;

import tk.funayd.mobchess.api.game.GameRound;
import tk.funayd.mobchess.api.game.ModuleStopReason;
import tk.funayd.mobchess.api.module.Module;
import tk.funayd.mobchess.misc.log.DebugLogger;

public class InternalRoundModule extends Module<GameRound> {
    public InternalRoundModule(GameRound holder) {
        super(holder);
    }

    @Override
    protected void onEnable() throws Exception {
        var roundCount = getHolder().getGame().getRoundCount();
        var gameId = getHolder().getGame().getGameId();
        DebugLogger.info("Round #" + roundCount + " is started in game "+ gameId);
    }

    @Override
    protected void onDisable(ModuleStopReason reason) throws Exception {
        var roundCount = getHolder().getGame().getRoundCount();
        var gameId = getHolder().getGame().getGameId();
        DebugLogger.info("Round #" + roundCount + " is ended in game "+ gameId);
    }
}
