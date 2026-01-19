package tk.funayd.mobchess.api.module;

import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public abstract class Module<T extends ModuleHolder<T>> extends Runner {

    @Nullable
    private final T holder;

    public Module(@Nullable T holder) {
        this.holder = holder;
    }

}
