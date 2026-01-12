package tk.funayd.mobchess.api.game;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RoundProvider {
    /**
     * Trả về Round tiếp theo.
     * @param game GameInstance hiện tại
     * @return GameRound mới, hoặc null nếu muốn kết thúc Game.
     */
    @Nullable
    GameRound createNextRound(GameInstance game);
}