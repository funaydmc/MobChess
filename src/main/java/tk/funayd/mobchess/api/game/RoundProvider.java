package tk.funayd.mobchess.api.game;

import javax.annotation.Nullable;

public abstract class RoundProvider {
    /**
     * Trả về Round tiếp theo.
     * @param game GameInstance hiện tại
     * @return GameRound mới, hoặc null nếu muốn kết thúc Game.
     */
    @Nullable
    protected abstract GameRound createNextRound(GameInstance game);
}