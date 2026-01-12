package tk.funayd.mobchess.api.event.round;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import tk.funayd.mobchess.api.game.GameRound;
import tk.funayd.mobchess.api.game.ModuleStopReason;

@Getter
public class RoundEndEvent extends RoundEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final ModuleStopReason reason;

    public RoundEndEvent(GameRound round, ModuleStopReason reason) {
        super(round);
        this.reason = reason;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
