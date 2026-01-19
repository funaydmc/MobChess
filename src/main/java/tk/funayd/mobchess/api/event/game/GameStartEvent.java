package tk.funayd.mobchess.api.event.game;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import tk.funayd.mobchess.api.game.GameInstance;

/**
 * Event fired when a GameInstance starts.
 * <p>
 * Note: {@code @Getter} generates static {@code getHandlerList()} for Bukkit.
 */
@Getter
public class GameStartEvent extends GameEvent {
    private static final HandlerList handlerList = new HandlerList();

    public GameStartEvent(GameInstance game) {
        super(game);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
