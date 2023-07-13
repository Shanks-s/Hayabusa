package me.shanks.hayabusa.api.setting.settings;

import com.google.gson.JsonElement;
import me.shanks.hayabusa.api.setting.Setting;
import me.shanks.hayabusa.api.setting.event.SettingResult;
import me.shanks.hayabusa.api.util.bind.Bind;
import org.lwjgl.input.Keyboard;

public class BindSetting extends Setting<Bind>
{
    public BindSetting(String name)
    {
        this(name, Bind.none());
    }

    public BindSetting(String name, Bind initialValue)
    {
        super(name, initialValue);
    }

    @Override
    public void fromJson(JsonElement element)
    {
        this.fromString(element.getAsString());
    }

    @Override
    public SettingResult fromString(String string)
    {
        if ("none".equalsIgnoreCase(string))
        {
            this.value = Bind.none();
        }
        else
        {
            this.setValue(Bind.fromString(string));
        }

        return SettingResult.SUCCESSFUL;
    }

    public void setKey(int key)
    {
        this.value = Bind.fromKey(key);
    }
}