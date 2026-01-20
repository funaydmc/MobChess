package tk.funayd.mobchess;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIPaperConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import tk.funayd.mobchess.impl.command.MobChessCommand;
import tk.funayd.mobchess.impl.game.GameManager;

public final class MobChessPlugin extends JavaPlugin {

    @Getter
    private static MobChessPlugin instance;
    @Getter
    private GameManager gameManager;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIPaperConfig(this).silentLogs(true));
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable();
        gameManager = new GameManager(this);
        new MobChessCommand(this).register();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
