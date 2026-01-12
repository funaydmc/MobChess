package tk.funayd.mobchess.api.event.round;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import tk.funayd.mobchess.api.game.GameRound;

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
