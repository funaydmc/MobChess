package tk.funayd.mobchess;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIPaperConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobChessPlugin extends JavaPlugin {

    @Getter
    private static MobChessPlugin instance;


    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIPaperConfig(this).silentLogs(true));
        // TODO: Register commands here
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
