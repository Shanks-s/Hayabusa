package me.shanks.hayabusa.tweaker.launch.arguments;

import com.google.gson.JsonElement;

public class LongArgument extends AbstractArgument<Long>
{
    public LongArgument(Long value)
    {
        super(value);
    }

    @Override
    public void fromJson(JsonElement element)
    {
        value = element.getAsLong();
    }

    @Override
    public String toJson()
    {
        return value.toString();
    }
}
