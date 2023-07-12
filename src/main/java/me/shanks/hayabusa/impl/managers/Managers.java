package me.shanks.hayabusa.impl.managers;

import me.shanks.hayabusa.api.plugin.Plugin;
import me.shanks.hayabusa.impl.Hayabusa;
import me.shanks.hayabusa.impl.managers.client.PluginManager;

/**
 * The internals of the client.
 */
public class Managers
{
    /** Loads all Managers, Shouldn't be called more than once. */
    public static void load()
    {
        Hayabusa.getLogger().info("Subscribing Managers.");

        PluginManager.getInstance().instantiatePlugins();
        for (Plugin plugin : PluginManager.getInstance().getPlugins().values())
        {
            plugin.load();
        }
    }
}
