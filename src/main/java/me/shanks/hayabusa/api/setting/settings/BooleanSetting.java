package me.shanks.hayabusa.api.setting.settings;

import com.google.gson.JsonElement;
import me.shanks.hayabusa.api.setting.Setting;
import me.shanks.hayabusa.api.setting.event.SettingResult;

public class BooleanSetting extends Setting<Boolean>
{
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    public BooleanSetting(String nameIn, Boolean initialValue)
    {
        super(nameIn, initialValue);
    }

    @Override
    public void fromJson(JsonElement element)
    {
        setValue(element.getAsBoolean());
    }

    @Override
    public SettingResult fromString(String string)
    {
        if ("true".equalsIgnoreCase(string))
        {
            this.setValue(true);
            return SettingResult.SUCCESSFUL;
        }
        else if ("false".equalsIgnoreCase(string))
        {
            this.setValue(false);
            return SettingResult.SUCCESSFUL;
        }

        return new SettingResult(false, string
                + " is a bad input. Value should be \"true\" or \"false\".");
    }
}
