package tk.funayd.mobchess.api.event.phase;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import tk.funayd.mobchess.api.game.GamePhase;

/**
 * Event fired when a GamePhase starts.
 * <p>
 * Note: {@code @Getter} generates static {@code getHandlerList()} for Bukkit.
 */
@Getter
public class PhaseStartEvent extends PhaseEvent {
    private static final HandlerList handlerList = new HandlerList();

    public PhaseStartEvent(GamePhase phase) {
        super(phase);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
