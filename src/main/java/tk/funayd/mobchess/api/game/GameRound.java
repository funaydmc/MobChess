package tk.funayd.mobchess.api.game;

import lombok.Getter;
import org.bukkit.Bukkit;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.event.round.RoundEndEvent;
import tk.funayd.mobchess.api.event.round.RoundStartEvent;
import tk.funayd.mobchess.misc.log.DebugLogger;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a single round within a GameInstance, containing multiple phases.
 * <p>
 * To extend, listen to {@link RoundStartEvent} / {@link RoundEndEvent} instead
 * of overriding.
 */
@Getter
public class GameRound extends GameLifecycle<GameRound> {
    private final MobChessPlugin plugin;
    private final GameInstance game;
    private final List<GamePhase> phaseList;
    private final Deque<GamePhase> phaseQueue = new LinkedList<>();
    private GamePhase activePhase = null;

    public GameRound(GameInstance gameInstance, List<GamePhase> phases) {
        this.game = gameInstance;
        this.phaseList = new ArrayList<>(phases);
        this.plugin = gameInstance.getPlugin();
    }

    final void nextPhase() {
        activePhase = phaseQueue.poll();
        if (activePhase != null) {
            activePhase.start();
        } else {
            this.stop(ModuleStopReason.COMPLETED);
        }
    }

    @Override
    protected final void onStart() {
        phaseQueue.clear();
        phaseQueue.addAll(phaseList);
        Bukkit.getPluginManager().callEvent(new RoundStartEvent(this));
        if (phaseQueue.isEmpty()) {
            DebugLogger.warn("Round rỗng -> Kết thúc.");
            this.stop(ModuleStopReason.COMPLETED);
            return;
        }
        nextPhase();
    }

    @Override
    protected final void onStop(ModuleStopReason reason) {
        phaseQueue.clear();
        if (activePhase != null) {
            activePhase.stop(reason);
            activePhase = null;
        }
        Bukkit.getPluginManager().callEvent(new RoundEndEvent(this, reason));
        cleanup();

        if (reason == ModuleStopReason.COMPLETED) {
            game.onRoundComplete();
        }
    }

    protected void cleanup() {
        // Cleanup logic
    }
}
