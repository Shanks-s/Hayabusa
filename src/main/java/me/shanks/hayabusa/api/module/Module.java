package me.shanks.hayabusa.api.module;

import me.shanks.hayabusa.api.config.preset.ModulePreset;
import me.shanks.hayabusa.api.event.bus.api.Listener;
import me.shanks.hayabusa.api.event.bus.api.Subscriber;
import me.shanks.hayabusa.api.event.bus.instance.Bus;
import me.shanks.hayabusa.api.module.data.DefaultData;
import me.shanks.hayabusa.api.module.data.ModuleData;
import me.shanks.hayabusa.api.module.util.Category;
import me.shanks.hayabusa.api.setting.Setting;
import me.shanks.hayabusa.api.setting.SettingContainer;
import me.shanks.hayabusa.api.setting.settings.BindSetting;
import me.shanks.hayabusa.api.setting.settings.BooleanSetting;
import me.shanks.hayabusa.api.setting.settings.EnumSetting;
import me.shanks.hayabusa.api.util.bind.Bind;
import me.shanks.hayabusa.api.util.bind.Toggle;
import me.shanks.hayabusa.api.util.interfaces.Globals;
import me.shanks.hayabusa.api.util.interfaces.Nameable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A Module.
 */
public class Module extends SettingContainer
    implements Globals, Subscriber, Nameable
{
    /** Listeners for the EventBus. */
    protected final List<Listener<?>> listeners = new ArrayList<>();
    private final AtomicBoolean enableCheck = new AtomicBoolean();
    private final AtomicBoolean inOnEnable  = new AtomicBoolean();

    private final Setting<Bind> bind =
            register(new BindSetting("Bind", Bind.none()));
    private final Setting<Boolean> enabled =
            register(new BooleanSetting("Enabled", false));
    private final Setting<Toggle> bindMode =
            register(new EnumSetting<>("Toggle", Toggle.Normal));

    private final Category category;
    private final String name;
    private ModuleData data;

    public Module(String name, Category category)
    {
        this.name = name;
        this.category = category;
        this.data = new DefaultData<>(this);
        this.enabled.addObserver(event ->
        {
            if (event.isCancelled())
            {
                return;
            }

            enableCheck.set(event.getValue());
            if (event.getValue() && !Bus.EVENT_BUS.isSubscribed(this))
            {
                inOnEnable.set(true);
                onEnable();
                inOnEnable.set(false);
                if (enableCheck.get())
                {
                    Bus.EVENT_BUS.subscribe(this);
                }
            }
            else if (!event.getValue()
                    && (Bus.EVENT_BUS.isSubscribed(this) || inOnEnable.get()))
            {
                Bus.EVENT_BUS.unsubscribe(this);
                onDisable();
            }
        });
    }

    @Override
    public String getName()
    {
        return name;
    }

    public final void toggle()
    {
        if (isEnabled())
        {
            disable();
        }
        else
        {
            enable();
        }
    }

    public final void enable()
    {
        if (!isEnabled())
        {
            enabled.setValue(true);
        }
    }

    public final void disable()
    {
        if (isEnabled())
        {
            enabled.setValue(false);
        }
    }

    public final void load()
    {
        if (isEnabled() && !Bus.EVENT_BUS.isSubscribed(this))
        {
            Bus.EVENT_BUS.subscribe(this);
        }

        onLoad();
    }

    public boolean isEnabled()
    {
        return enableCheck.get();
    }

    public Category getCategory()
    {
        return category;
    }

    public ModuleData getData()
    {
        return data;
    }

    public void setData(ModuleData data)
    {
        if (data != null)
        {
            this.data = data;
        }
    }

    public Bind getBind()
    {
        return bind.getValue();
    }

    public void setBind(Bind bind)
    {
        this.bind.setValue(bind);
    }

    public Toggle getBindMode()
    {
        return bindMode.getValue();
    }

    protected void onEnable()
    {
        /* Implemented by the module */
    }

    protected void onDisable()
    {
        /* Implemented by the module */
    }

    protected void onLoad()
    {
        /* Implemented by the module */
    }

    @Override
    public Collection<Listener<?>> getListeners()
    {
        return listeners;
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }
        else if (o instanceof Module)
        {
            String name = this.name;
            return name != null && name.equals(((Module) o).name);
        }

        return false;
    }
}
