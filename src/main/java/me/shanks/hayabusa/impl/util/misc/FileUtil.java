package me.shanks.hayabusa.impl.util.misc;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class FileUtil
{
    @SuppressWarnings("UnusedReturnValue")
    public static Path getDirectory(Path parent, String...paths)
    {
        if (paths.length < 1)
        {
            return parent;
        }

        Path dir = lookupPath(parent, paths);
        createDirectory(dir);
        return dir;
    }

    public static Path lookupPath(Path root, String...paths)
    {
        return Paths.get(root.toString(), paths);
    }

    public static void createDirectory(Path dir)
    {
        try
        {
            if (!Files.isDirectory(dir))
            {
                if (Files.exists(dir))
                {
                    Files.delete(dir);
                }

                Files.createDirectories(dir);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}