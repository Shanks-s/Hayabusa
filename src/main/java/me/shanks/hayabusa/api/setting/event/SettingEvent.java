package me.shanks.hayabusa.api.setting.event;

import me.shanks.hayabusa.api.event.events.Event;
import me.shanks.hayabusa.api.setting.Setting;

public class SettingEvent<T> extends Event
{
    private final Setting<T> setting;
    private T value;

    public SettingEvent(Setting<T> setting, T value)
    {
        this.setting = setting;
        this.value = value;
    }

    public Setting<T> getSetting()
    {
        return setting;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

}