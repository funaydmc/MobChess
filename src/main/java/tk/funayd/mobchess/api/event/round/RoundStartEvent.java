package tk.funayd.mobchess.api.event.round;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import tk.funayd.mobchess.api.game.GameRound;

/**
 * Event fired when a GameRound starts.
 * <p>
 * Note: {@code @Getter} generates static {@code getHandlerList()} for Bukkit.
 */
@Getter
public class RoundStartEvent extends RoundEvent {
    private static final HandlerList handlerList = new HandlerList();

    public RoundStartEvent(GameRound round) {
        super(round);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
