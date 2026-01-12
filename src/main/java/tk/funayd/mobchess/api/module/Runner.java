package tk.funayd.mobchess.api.module;

import lombok.Getter;
import tk.funayd.mobchess.api.game.ModuleStopReason;
import tk.funayd.mobchess.misc.log.DebugLogger;

@Getter
public abstract class Runner {

    private boolean running = false;

    protected abstract void onEnable() throws Exception;
    protected abstract void onDisable(ModuleStopReason reason) throws Exception;

    public final void enable() {
        if (running) return;
        try {
            onEnable();
            running = true;
        } catch (Exception e) {
            DebugLogger.error(
                    "Lỗi khi bật module",
                    e.getMessage()
            );
            disable(ModuleStopReason.ERROR);
        }
    }
    public final void disable(ModuleStopReason reason) {
        if (!running) return;
        running = false;
        try {
            onDisable(reason);
        } catch (Exception e) {
            DebugLogger.error(
                    "Lỗi khi tắt module",
                    e.getMessage()
            );
        }
    }

}
