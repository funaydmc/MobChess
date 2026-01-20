package tk.funayd.mobchess.api.module;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Getter
public abstract class Module<T extends ModuleHolder<T>> extends Runner {

    @NotNull
    private final T holder;

    public Module(@NotNull T holder) {
        this.holder = holder;
    }

}
