package tk.funayd.mobchess.api.game;

import lombok.Getter;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.module.ModuleHolder;

@Getter
public abstract class GameLifecycle<T extends GameLifecycle<T>> extends ModuleHolder<T> {

    /**
     * Lấy plugin instance bằng cách traverse hierarchy để tìm GameInstance.
     * Hữu ích cho việc schedule task, register listener, v.v.
     *
     * @return MobChessPlugin instance
     * @throws IllegalStateException Nếu không tìm thấy GameInstance trong hierarchy
     */
    protected abstract MobChessPlugin getPlugin();

    /**
     * Logic khởi động nội bộ (được định nghĩa bởi lớp con)
     * Ví dụ: Phase spawn quái, Round load phase, Instance load round.
     *
     * @throws Exception Nếu có lỗi, hệ thống sẽ tự động dừng với lý do ERROR.
     */
    protected abstract void onStart() throws Exception;

    /**
     * Logic dừng nội bộ (được định nghĩa bởi lớp con)
     *
     * @param reason Lý do dừng
     */
    protected abstract void onStop(ModuleStopReason reason);

    @Override
    public final void onEnable() throws Exception {
        super.onEnable();
        onStart();
    }

    @Override
    public final void onDisable(ModuleStopReason reason) {
        onStop(reason);
        super.onDisable(reason);
    }

    public final void start() {
        enable();
    }

    protected final void stop(ModuleStopReason moduleStopReason) {
        disable(moduleStopReason);
    }
}
