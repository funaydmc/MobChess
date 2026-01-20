package tk.funayd.mobchess.impl.game.phase;

import tk.funayd.mobchess.api.game.GamePhase;
import tk.funayd.mobchess.api.game.ModuleStopReason;
import tk.funayd.mobchess.api.module.Module;
import tk.funayd.mobchess.misc.log.DebugLogger;

public class InternalPhaseModule extends Module<GamePhase> {
    public InternalPhaseModule(GamePhase holder) {
        super(holder);
    }

    @Override
    protected void onEnable() throws Exception {
        var phaseName = getHolder().getMetadata().getName();
        var gameId = getHolder().getGame().getGameId();
        DebugLogger.info("Phase " + phaseName+" is started in game "+gameId);
    }

    @Override
    protected void onDisable(ModuleStopReason reason) throws Exception {
        var phaseName = getHolder().getMetadata().getName();
        var gameId = getHolder().getGame().getGameId();
        DebugLogger.info("Phase " + phaseName+" is ended in game "+gameId);
    }
}
