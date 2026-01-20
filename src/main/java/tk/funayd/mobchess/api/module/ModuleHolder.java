package tk.funayd.mobchess.api.module;

import tk.funayd.mobchess.api.game.ModuleStopReason;
import tk.funayd.mobchess.misc.log.DebugLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
            var constructor = findCompatibleConstructor(moduleClass);
            var module = constructor.newInstance(this);
            addModule(module);
        } catch (
                NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            DebugLogger.warn(
                    "Đăng ký module " + moduleClass.getSimpleName() + " thất bại.",
                    e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private <M extends Module<T>> Constructor<M> findCompatibleConstructor(Class<M> moduleClass)
            throws NoSuchMethodException {
        return (Constructor<M>) Arrays.stream(moduleClass.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 1)
                .filter(c -> c.getParameterTypes()[0].isAssignableFrom(this.getClass()))
                .findFirst()
                .orElseThrow(() -> new NoSuchMethodException(
                        "No compatible constructor found for " + moduleClass.getName() + " accepting "
                                + this.getClass().getName()));
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

    /**
     * Helper method to cast this instance to type T.
     * Used to bypass compiler warnings when working with self-referencing generics.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private T getSelf() {
        return (T) this;
    }

    /**
     * Helper method to get the runtime class of this holder.
     * Used for reflection operations with generic types.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private Class<T> getHolderType() {
        return (Class<T>) this.getClass();
    }
}
