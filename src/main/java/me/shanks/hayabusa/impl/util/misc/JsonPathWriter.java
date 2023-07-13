package me.shanks.hayabusa.impl.util.misc;

import com.google.gson.JsonObject;
import me.shanks.hayabusa.api.config.Jsonable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonPathWriter
{
    public static void write(Path path, JsonObject object) throws IOException
    {
        String json = Jsonable.GSON.toJson(object);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(path))))
        {
            writer.write(json);
        }
    }

}