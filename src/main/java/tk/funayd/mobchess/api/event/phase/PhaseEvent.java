package tk.funayd.mobchess.api.event.phase;

import lombok.Getter;
import tk.funayd.mobchess.api.event.game.GameEvent;
import tk.funayd.mobchess.api.game.GamePhase;

@Getter
public abstract class PhaseEvent extends GameEvent {
    private final GamePhase phase;

    public PhaseEvent(GamePhase phase) {
        super(phase.getRound().getGame());
        this.phase = phase;
    }
}
