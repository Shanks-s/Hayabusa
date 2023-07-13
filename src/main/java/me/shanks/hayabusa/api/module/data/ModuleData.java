package me.shanks.hayabusa.api.module.data;

import me.shanks.hayabusa.api.config.preset.ModulePreset;
import me.shanks.hayabusa.api.setting.Setting;
import me.shanks.hayabusa.api.setting.SettingContainer;

import java.util.Collection;
import java.util.Map;

/**
 * Container for a Modules data:
 * -the color of the module.
 * -a description of the module.
 * -descriptions for each setting of the module.
 * -a collection of presets for the module.
 */
public interface ModuleData<M extends SettingContainer>
{
    /**
     * Returns the modules color.
     * The Color is used by the Arraylist for example.
     *
     * @return an ARGB value.
     */
    int getColor();

    /**
     * Returns the modules description.
     * Hopefully detailed!
     *
     * @return a description of the module.
     */
    String getDescription();

    /**
     * Returns Settings belonging to this module
     * mapped to a description. The exact Description map
     * will be returned, meaning that putting in and removing is
     * supported.
     *
     * @return settings with their descriptions.
     */
    Map<Setting<?>, String> settingDescriptions();

    /**
     * Returns a collection of the modules presets.
     * The exact Collection will be returned, meaning adding
     * and removing presets is supported.
     *
     * @return the modules presets.
     */
    Collection<ModulePreset<M>> getPresets();

}