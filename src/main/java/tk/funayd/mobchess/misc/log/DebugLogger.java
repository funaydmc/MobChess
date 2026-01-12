package tk.funayd.mobchess.misc.log;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DebugLogger {
    private static final List<CommandSender> audiences = new ArrayList<>();

    public static boolean isListening(CommandSender sender) {
        return audiences.contains(sender);
    }

    public static void enable(CommandSender sender) {
        if (isListening(sender)) return;
        audiences.add(sender);
    }

    public static void disable(CommandSender sender) {
        if (!isListening(sender)) return;
        audiences.remove(sender);
    }

    public static void toggle(CommandSender sender) {
        if (isListening(sender)) {
            audiences.remove(sender);
        } else {
            audiences.add(sender);
        }
    }

    public static void info(String... message) {
        log(NamedTextColor.WHITE, message);
    }

    public static void important(String... message) {
        log(NamedTextColor.BLUE, message);
    }

    public static void warn(String... message) {
        log(NamedTextColor.YELLOW, message);
    }

    public static void error(String... message) {
        log(NamedTextColor.RED, message);
    }

    public static void log(TextColor color, String... message) {
        if (audiences.isEmpty()) return;
        String callerClassName = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(f -> {
                            String fullClassName = f.getClassName();
                            String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
                            return simpleClassName + ":" + f.getLineNumber();
                        })
                        .orElse("Unknown"));
        audiences.forEach(sender -> {
            for (String msg : message) {
                Component cpn = Component.text(String.format("[MobChess-%s] %s", callerClassName, msg));
                sender.sendMessage(cpn.color(color));
            }
        });
    }
}
