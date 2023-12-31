package me.shanks.hayabusa.impl.managers.client;

import me.shanks.hayabusa.api.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginDescriptions
{
    private static final Map<Plugin, String> DESCRIPTIONS =
            new ConcurrentHashMap<>();

    public static void register(Plugin plugin, String description)
    {
        DESCRIPTIONS.put(plugin, description);
    }

    public static String getDescription(Plugin plugin)
    {
        return DESCRIPTIONS.get(plugin);
    }

}