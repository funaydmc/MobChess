package tk.funayd.mobchess.impl.game;

import lombok.RequiredArgsConstructor;
import tk.funayd.mobchess.MobChessPlugin;
import tk.funayd.mobchess.api.game.GameInstance;
import tk.funayd.mobchess.api.game.GameSetup;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class GameManager {
    private static final Random RANDOM = new Random();

    private final MobChessPlugin plugin;
    private final Map<String, GameInstance> games = new ConcurrentHashMap<>();

    public GameInstance createGame(GameSetup setup) {
        String id = generateUniqueId();
        GameInstance game = new GameInstance(plugin, id, setup);
        games.put(id, game);
        return game;
    }

    public Optional<GameInstance> getGame(String id) {
        return Optional.ofNullable(games.get(id));
    }

    public void removeGame(String id) {
        games.remove(id);
    }

    public Collection<GameInstance> getGames() {
        return games.values();
    }

    private String generateUniqueId() {
        String id;
        do {
            int number = RANDOM.nextInt(900_000) + 100_000; // Range: 100000-999999
            id = String.valueOf(number);
        } while (games.containsKey(id));
        return id;
    }
}
