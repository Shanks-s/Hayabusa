package me.shanks.hayabusa.api.config.preset;

import me.shanks.hayabusa.api.setting.Setting;
import me.shanks.hayabusa.api.setting.SettingContainer;

public class DefaultPreset<M extends SettingContainer> extends ModulePreset<M>
{
    public DefaultPreset(M module)
    {
        super("reset", module, "Resets all settings to the default value.");
    }

    @Override
    public void apply()
    {
        for (Setting<?> setting : this.getModule().getSettings())
        {
            setting.reset();
        }
    }

}