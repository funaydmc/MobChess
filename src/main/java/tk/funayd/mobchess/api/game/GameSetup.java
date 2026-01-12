package tk.funayd.mobchess.api.game;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import tk.funayd.mobchess.api.module.Module;

import java.util.List;

/**
 * Configuration class for initializing a new GameInstance.
 * Contains all necessary information to start a game.
 */
@Getter
@Builder
public class GameSetup {
    private final RoundProvider roundProvider;

    @Singular
    private final List<Module<GameInstance>> modules;
}
