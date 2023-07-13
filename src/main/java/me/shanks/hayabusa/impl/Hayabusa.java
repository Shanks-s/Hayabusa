package me.shanks.hayabusa.impl;

import me.shanks.hayabusa.api.util.interfaces.Globals;
import me.shanks.hayabusa.impl.core.ducks.IMinecraft;
import me.shanks.hayabusa.impl.managers.Managers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class Hayabusa implements Globals
{
    private static final Logger LOGGER = LogManager.getLogger("Hayabusa");
    public static final String NAME = "Hayabusa";
    public static final String VERSION = "1.0.0-74d8448af323";

    public static void preInit()
    {
        /* For Plugins if they need it */
    }

    public static void init()
    {
        LOGGER.info("\n\nInitializing Hayabusa.");
        Display.setTitle("Hayabusa 1.8.9 - " + VERSION);
        Managers.load();
        LOGGER.info("\nHayabusa Initialized.\n");
    }

    public static void postInit()
    {
        /* For Plugins if they need it */
    }

    public static Logger getLogger()
    {
        return LOGGER;
    }

    public static boolean isRunning()
    {
        return ((IMinecraft) mc).isHayabusaRunning();
    }
}
