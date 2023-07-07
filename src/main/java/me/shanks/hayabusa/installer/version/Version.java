package me.shanks.hayabusa.installer.version;

import com.google.gson.JsonObject;

import java.io.File;

public class Version
{
    private final String name;
    private final File file;
    private final JsonObject object;

    public Version(String name, File file, JsonObject object)
    {
        this.name = name;
        this.file = file;
        this.object = object;
    }

    public String getName()
    {
        return name;
    }

    public File getFile()
    {
        return file;
    }

    public JsonObject getJson()
    {
        return object;
    }
}
