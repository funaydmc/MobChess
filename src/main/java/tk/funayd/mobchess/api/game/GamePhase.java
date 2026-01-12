package tk.funayd.mobchess.api.game;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.event.phase.PhaseEndEvent;
import tk.funayd.mobchess.api.event.phase.PhaseStartEvent;

@Getter
public class GamePhase extends GameLifecycle<GamePhase> {
    private final MobChessPlugin plugin;
    private final GameRound round;
    private final Metadata metadata;
    private BukkitTask timeoutTask = null;

    public GamePhase(GameRound gameRound, Metadata metadata) {
        this.round = gameRound;
        this.metadata = metadata;
        this.plugin = gameRound.getPlugin();
    }

    public record Metadata(
            long timeout) {
    }

    @Override
    protected final void onStart() {
        Metadata metadata = getMetadata();
        if (metadata.timeout() > 0) {
            var plugin = getPlugin();
            timeoutTask = Bukkit.getScheduler().runTaskLater(
                    plugin,
                    () -> this.stop(ModuleStopReason.COMPLETED),
                    metadata.timeout());
        }
        Bukkit.getPluginManager().callEvent(new PhaseStartEvent(this));
    }

    @Override
    protected final void onStop(ModuleStopReason reason) {
        if (timeoutTask != null) {
            timeoutTask.cancel();
            timeoutTask = null;
        }
        Bukkit.getPluginManager().callEvent(new PhaseEndEvent(this, reason));
        cleanup();

        if (reason == ModuleStopReason.COMPLETED) {
            round.nextPhase();
        }
    }

    protected void cleanup() {
        // Cleanup logic
    }
}
