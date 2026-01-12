package tk.funayd.mobchess.api.event.round;

import lombok.Getter;
import tk.funayd.mobchess.api.event.game.GameEvent;
import tk.funayd.mobchess.api.game.GameRound;

@Getter
public abstract class RoundEvent extends GameEvent {
    private final GameRound round;

    public RoundEvent(GameRound round) {
        super(round.getGame());
        this.round = round;
    }
}
