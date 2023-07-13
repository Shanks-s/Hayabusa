package me.shanks.hayabusa.api.setting.settings;

import com.google.gson.JsonElement;
import me.shanks.hayabusa.api.setting.Setting;
import me.shanks.hayabusa.api.setting.event.SettingResult;
import me.shanks.hayabusa.api.util.EnumHelper;

public class EnumSetting<E extends Enum<E>> extends Setting<E>
{
    private final String concatenated;

    public EnumSetting(String nameIn, E initialValue)
    {
        super(nameIn, initialValue);
        concatenated = concatenateInputs();
    }

    @Override
    public void fromJson(JsonElement element)
    {
        fromString(element.getAsString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public SettingResult fromString(String string)
    {
        Enum<?> entry = EnumHelper.fromString(this.value, string);
        this.setValue((E) entry);
        return SettingResult.SUCCESSFUL;
    }

    private String concatenateInputs()
    {
        StringBuilder builder = new StringBuilder("<");
        Class<? extends Enum<?>> clazz = this.initial.getDeclaringClass();
        for (Enum<?> entry : clazz.getEnumConstants())
        {
            builder.append(entry.name()).append(", ");
        }

        builder.replace(builder.length() - 2, builder.length(), ">");
        return builder.toString();
    }

}