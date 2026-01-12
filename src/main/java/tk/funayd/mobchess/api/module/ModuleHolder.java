package tk.funayd.mobchess.api.module;

import tk.funayd.mobchess.api.game.ModuleStopReason;
import tk.funayd.mobchess.misc.log.DebugLogger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ModuleHolder<T extends ModuleHolder<T>> extends Runner {
    private final List<Module<T>> registeredModules = new ArrayList<>();

    public void onEnable() throws Exception {
        registeredModules.forEach(Module::enable);
    }

    public void onDisable(ModuleStopReason reason) {
        registeredModules.reversed().forEach(module -> module.disable(reason));
    }

    public <M extends Module<T>> void addModule(M module) {
        registeredModules.add(module);
        if (isRunning())
            module.enable();
    }

    public <M extends Module<T>> void addModule(Class<M> moduleClass) {
        if ((hasService(moduleClass))) {
            DebugLogger.info(String.format("Module %s đã được đăng ký!", moduleClass.getSimpleName()));
            return;
        }
        try {
            var module = moduleClass.getConstructor(ModuleHolder.class).newInstance(this);
            addModule(module);
        } catch (
            NoSuchMethodException | 
            InstantiationException | 
            IllegalAccessException | 
            InvocationTargetException e
        ) {
            DebugLogger.warn(
                    "Đăng ký module " + moduleClass.getSimpleName() + " thất bại.",
                    e.getMessage());
        }
    }

    public <M extends Module<?>> void removeModule(M module) {
        if (registeredModules.contains(module)) {
            module.disable(ModuleStopReason.CANCELLED);
            registeredModules.remove(module);
        }
    }

    public <M extends Module<?>> void removeModule(Class<M> moduleClass) {
        getService(moduleClass).ifPresent(this::removeModule);
    }

    public <S> boolean hasService(Class<S> service) {
        return registeredModules.stream().anyMatch(service::isInstance);
    }

    public <S> Optional<S> getService(Class<S> serviceClass) {
        return registeredModules.stream()
                .filter(serviceClass::isInstance)
                .map(serviceClass::cast)
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    private T getSelf() {
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getHolderType() {
        return (Class<T>) this.getClass();
    }
}
