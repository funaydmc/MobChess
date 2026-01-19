package tk.funayd.mobchess.api.event.phase;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import tk.funayd.mobchess.api.game.GamePhase;
import tk.funayd.mobchess.api.game.ModuleStopReason;

/**
 * Event fired when a GamePhase ends.
 * <p>
 * Note: {@code @Getter} generates static {@code getHandlerList()} for Bukkit.
 */
@Getter
public class PhaseEndEvent extends PhaseEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final ModuleStopReason reason;

    public PhaseEndEvent(GamePhase phase, ModuleStopReason reason) {
        super(phase);
        this.reason = reason;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
