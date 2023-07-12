package me.shanks.hayabusa.impl;

import me.shanks.hayabusa.impl.managers.Managers;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class Hayabusa
{
    private static final Logger LOGGER = LogManager.getLogger("Hayabusa");
    public static final String NAME = "Hayabusa";
    public static final String VERSION = "1.0.0-build-c205ef01";

    public void preInit()
    {

    }

    public void init()
    {
        LOGGER.info("\n\nInitializing Hayabusa.");
        Display.setTitle("Hayabusa 1.8.9 - " + VERSION);
        Managers.load();
        LOGGER.info("\nHayabusa Initialized.\n");
    }

    public void postInit()
    {

    }

    public static Logger getLogger()
    {
        return LOGGER;
    }
}
