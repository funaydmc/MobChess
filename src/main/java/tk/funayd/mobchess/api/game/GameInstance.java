package tk.funayd.mobchess.api.game;

import lombok.Getter;
import org.bukkit.Bukkit;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.event.game.GameEndEvent;
import tk.funayd.mobchess.api.event.game.GameStartEvent;
import tk.funayd.mobchess.misc.log.DebugLogger;

@Getter
public class GameInstance extends GameLifecycle<GameInstance> {
    private final String gameId;
    private final MobChessPlugin plugin;
    private final GameSetup setup;
    private GameRound activeRound;

    /**
     * Số lượng round đã được chơi (bắt đầu từ 0, tăng khi mỗi round mới bắt đầu).
     * Hữu ích cho RoundProvider để quyết định logic tạo round tiếp theo.
     */
    private int roundCount = 0;

    public GameInstance(MobChessPlugin plugin, String gameId, GameSetup setup) {
        this.plugin = plugin;
        this.gameId = gameId;
        this.setup = setup;
    }

    public RoundProvider getRoundProvider() {
        return setup.getRoundProvider();
    }

    @Override
    protected final void onStart() {
        DebugLogger.info("GameInstance " + gameId + " khởi động...");

        // Register all modules from setup
        setup.getModules().forEach(this::addModule);

        Bukkit.getPluginManager().callEvent(new GameStartEvent(this));
        nextRound();
    }

    @Override
    protected final void onStop(ModuleStopReason reason) {
        if (activeRound != null) {
            activeRound.stop(reason);
            activeRound = null;
        }
        Bukkit.getPluginManager().callEvent(new GameEndEvent(this, reason));
        cleanup();
        DebugLogger.info("GameInstance " + gameId + " đã dừng. Lý do: " + reason);
    }

    final void onRoundComplete() {
        if (!isRunning())
            return;
        Bukkit.getScheduler().runTask(plugin, this::nextRound);
    }

    private void nextRound() {
        if (!isRunning())
            return;
        RoundProvider roundProvider = setup.getRoundProvider();
        if (roundProvider == null) {
            DebugLogger.error("Không tìm thấy RoundProvider! Dừng game.");
            this.stop(ModuleStopReason.ERROR);
            return;
        }

        GameRound nextRound = roundProvider.createNextRound(this);
        if (nextRound == null) {
            DebugLogger.info("Không còn Round nào tiếp theo. Kết thúc Game.");
            this.stop(ModuleStopReason.COMPLETED);
            return;
        }

        this.activeRound = nextRound;
        this.roundCount++;
        DebugLogger.info("Bắt đầu Round #" + roundCount + ": " + nextRound.getClass().getSimpleName());

        activeRound.start();
    }

    protected void cleanup() {
        // Cleanup logic
    }
}
