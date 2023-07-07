package me.shanks.hayabusa.installer.srg2notch;

import java.lang.reflect.Method;

public class MethodMapping
{
    private final String owner;
    private final String name;
    private final String desc;

    public MethodMapping(String owner, String name, String desc)
    {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public String getOwner()
    {
        return owner;
    }

    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }
}
