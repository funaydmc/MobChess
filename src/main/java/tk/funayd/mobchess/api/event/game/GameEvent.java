package tk.funayd.mobchess.api.event.game;

import lombok.Getter;
import org.bukkit.event.Event;
import tk.funayd.mobchess.api.game.GameInstance;

@Getter
public abstract class GameEvent extends Event {

    private final GameInstance game;

    public GameEvent(GameInstance game){
        this.game = game;
    }

}
