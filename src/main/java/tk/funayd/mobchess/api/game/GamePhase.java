package tk.funayd.mobchess.api.game;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.event.phase.PhaseEndEvent;
import tk.funayd.mobchess.api.event.phase.PhaseStartEvent;

/**
 * Represents a single phase within a GameRound.
 * <p>
 * To extend, listen to {@link PhaseStartEvent} / {@link PhaseEndEvent} instead
 * of overriding.
 * <p>
 * Supports pause/resume, skip, and remaining time tracking.
 */
@Getter
public final class GamePhase extends GameLifecycle<GamePhase> {
    private final MobChessPlugin plugin;
    private final GameRound round;
    private final PhaseMetadata metadata;

    // Timer state
    private BukkitTask timerTask = null;
    /**
     * -- GETTER --
     *  Get remaining ticks until phase auto-completes.
     *  Returns 0 if no timeout or phase has ended.
     */
    private long remainingTicks = 0;
    private boolean paused = false;

    public GamePhase(GameRound gameRound, PhaseMetadata metadata) {
        this.round = gameRound;
        this.metadata = metadata;
        this.plugin = gameRound.getPlugin();
    }

    /**
     * Convenience method to access GameInstance from Phase.
     */
    public GameInstance getGame() {
        return round.getGame();
    }

    // ==================== Timer API ====================

    /**
     * Pause the phase timer. The phase will not auto-complete while paused.
     * Does nothing if already paused or no timeout.
     */
    public void pause() {
        if (paused || timerTask == null)
            return;
        paused = true;
        timerTask.cancel();
        timerTask = null;
    }

    /**
     * Resume the phase timer after pausing.
     * Does nothing if not paused or no remaining time.
     */
    public void resume() {
        if (!paused || remainingTicks <= 0)
            return;
        paused = false;
        startTimer(remainingTicks);
    }

    /**
     * Skip this phase immediately. Equivalent to completing it early.
     */
    public void skip() {
        if (!isRunning())
            return;
        this.stop(ModuleStopReason.COMPLETED);
    }

    // ==================== Lifecycle ====================

    @Override
    protected void onStart() {
        long timeout = metadata.getTimeout();
        if (timeout > 0) {
            remainingTicks = timeout;
            startTimer(timeout);
        }
        Bukkit.getPluginManager().callEvent(new PhaseStartEvent(this));
    }

    @Override
    protected void onStop(ModuleStopReason reason) {
        cancelTimer();
        remainingTicks = 0;
        paused = false;

        Bukkit.getPluginManager().callEvent(new PhaseEndEvent(this, reason));
        cleanup();

        if (reason == ModuleStopReason.COMPLETED) {
            round.nextPhase();
        }
    }

    private void cleanup() {
        // Cleanup logic for subclasses
    }

    // ==================== Internal ====================

    private void startTimer(long ticks) {
        timerTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (paused)
                return;
            remainingTicks--;
            if (remainingTicks <= 0) {
                this.stop(ModuleStopReason.COMPLETED);
            }
        }, 1L, 1L);
    }

    private void cancelTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}
