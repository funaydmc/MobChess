package tk.funayd.mobchess.impl.game.round;

import org.jetbrains.annotations.Nullable;
import tk.funayd.mobchess.api.game.GameInstance;
import tk.funayd.mobchess.api.game.GameRound;
import tk.funayd.mobchess.api.game.RoundProvider;
import tk.funayd.mobchess.impl.game.phase.CombatPhase;
import tk.funayd.mobchess.impl.game.phase.DraftPhase;

import java.util.List;

public class TestRoundProvider extends RoundProvider {
    @Nullable
    public GameRound createNextRound(GameInstance game) {
        if (game.getRoundCount() > 3) {
            return null;
        }
        var round = new GameRound(game);
        round.getPhaseList().addAll(
                List.of(
                        DraftPhase.create(round),
                        CombatPhase.create(round)
                ));
        round.addModule(InternalRoundModule.class);
        return round;
    }
}
