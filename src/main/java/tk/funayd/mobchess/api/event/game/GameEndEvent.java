package tk.funayd.mobchess.api.event.game;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import tk.funayd.mobchess.api.game.GameInstance;
import tk.funayd.mobchess.api.game.ModuleStopReason;

@Getter
public class GameEndEvent extends GameEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final ModuleStopReason reason;

    public GameEndEvent(GameInstance game, ModuleStopReason reason) {
        super(game);
        this.reason = reason;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
