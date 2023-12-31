package me.shanks.hayabusa.api.module.data;

import me.shanks.hayabusa.api.config.preset.ModulePreset;
import me.shanks.hayabusa.api.setting.Setting;
import me.shanks.hayabusa.api.setting.SettingContainer;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

// TODO: implement abstraction so this can be used for hud elements too!
public abstract class AbstractData<M extends SettingContainer> implements ModuleData<M>
{
    protected final Map<Setting<?>, String> descriptions = new HashMap<>();
    protected final Set<ModulePreset<M>> presets = new LinkedHashSet<>();
    protected final M module;

    public AbstractData(M module)
    {
        this.module = module;
    }

    @Override
    public Map<Setting<?>, String> settingDescriptions()
    {
        return descriptions;
    }

    @Override
    public Collection<ModulePreset<M>> getPresets()
    {
        return presets;
    }

    public void register(String setting, String description)
    {
        register(module.getSetting(setting), description);
    }

    public void register(Setting<?> setting, String description)
    {
        if (setting != null)
        {
            this.descriptions.put(setting, description);
        }
    }

}