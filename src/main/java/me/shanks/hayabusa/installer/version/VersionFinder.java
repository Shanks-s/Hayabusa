package me.shanks.hayabusa.installer.version;

import com.google.gson.JsonObject;
import me.shanks.hayabusa.api.config.Jsonable;
import me.shanks.hayabusa.installer.main.MinecraftFiles;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VersionFinder
{
    public List<Version> findVersions(MinecraftFiles files)
            throws IOException
    {
        File[] versionFolders = new File(files.getVersions()).listFiles();
        if (versionFolders == null)
        {
            throw new IllegalStateException("Version folder was empty!");
        }

        List<Version> result = new ArrayList<>();
        for (File file : versionFolders)
        {
            if (file.getName().startsWith("1.8.9"))
            {
                if (file.isDirectory())
                {
                    File[] contained = file.listFiles();
                    if (contained != null)
                    {
                        for (File json : contained)
                        {
                            if (json.getName().endsWith("json"))
                            {
                                Version version = readJson(file.getName(), json);
                                result.add(version);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private Version readJson(final String name, final File jsonFile)
            throws IOException
    {
        JsonObject object = Jsonable.PARSER.parse(new InputStreamReader(
                jsonFile.toURI()
                        .toURL()
                        .openStream()))
                        .getAsJsonObject();

        return new Version(name, jsonFile, object);
    }
}