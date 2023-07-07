package me.shanks.hayabusa.installer.version;

import com.google.gson.*;
import me.shanks.hayabusa.impl.util.misc.Jsonable;

public class VersionUtil
{
    public static final String MAIN = "net.minecraft.launchwrapper.Launch";
    public static final String HAYABUSA = "--tweakClass me.shanks.hayabusa.tweaker.HayabusaTweaker";
    public static final String FORGE = "--tweakClass net.minecraftforge.fml.common.launcher.FMLTweaker";
    public static final String ARGS = "minecraftArguments";
    public static final String LIBS = "libraries";

    public static boolean hasHayabusa(Version version)
    {
        return containsArgument(version, "--tweakClass me.shanks.hayabusa.tweaker.HayabusaTweaker");
    }

    public static boolean hasForge(Version version)
    {
        return containsArgument(version, "--tweakClass net.minecraftforge.fml.common.launcher.FMLTweaker");
    }

    public static boolean hasLaunchWrapper(Version version)
    {
        JsonElement element = version.getJson().get("mainClass");
        return element != null && element.getAsString().equals("net.minecraft.launchwrapper.Launch");
    }

    public static boolean containsArgument(Version version, final String tweaker)
    {
        JsonElement element = version.getJson().get("minecraftArguments");
        return element != null && element.getAsString().contains(tweaker);
    }

    public static JsonElement getOrElse(String name,
                                        JsonObject object,
                                        String returnElse)
    {
        JsonElement element = object.get(name);
        if (element == null)
        {
            return Jsonable.PARSER.parse(returnElse);
        }

        return element;
    }
}
