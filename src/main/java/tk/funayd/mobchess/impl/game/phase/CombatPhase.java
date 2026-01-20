package tk.funayd.mobchess.impl.game.phase;

import tk.funayd.mobchess.api.game.GamePhase;
import tk.funayd.mobchess.api.game.GameRound;
import tk.funayd.mobchess.api.game.PhaseMetadata;

public class CombatPhase {
    public static GamePhase create(GameRound round) {
        var phase = new GamePhase(
                round,
                PhaseMetadata.builder()
                        .name("combat")
                        .timeout(40)
                        .build()
        );
        phase.addModule(InternalPhaseModule.class);
        return phase;
    }
}
