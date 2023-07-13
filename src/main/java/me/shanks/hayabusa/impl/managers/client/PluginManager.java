package me.shanks.hayabusa.impl.managers.client;

import me.shanks.hayabusa.api.plugin.Plugin;
import me.shanks.hayabusa.api.plugin.PluginConfig;
import me.shanks.hayabusa.impl.Hayabusa;
import me.shanks.hayabusa.impl.core.Core;
import me.shanks.hayabusa.impl.managers.client.exception.BadPluginException;
import me.shanks.hayabusa.api.config.Jsonable;
import me.shanks.hayabusa.impl.util.misc.ReflectionUtil;
import me.shanks.hayabusa.vanilla.Environment;

import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Manages {@link Plugin}s for Hayabusa.
 */
public class PluginManager
{
    private static final PluginManager INSTANCE = new PluginManager();
    private static final String PATH = "hayabusa/plugins";

    private final Map<PluginConfig, Plugin> plugins = new HashMap<>();
    private final Map<String, PluginConfig> configs = new HashMap<>();
    private final PluginRemapper remapper = new PluginRemapper();
    private ClassLoader classLoader;

    /** Private ctr since this is a Singleton. */
    private PluginManager() { }

    /** @return the Singleton Instance for this Manager. */
    public static PluginManager getInstance()
    {
        return INSTANCE;
    }

    /**
     * Used by {@link Core#init(ClassLoader)}.
     *
     * Scans the "hayabusa/plugins" directory for Plugins.
     * If it can find jarFiles whose Manifest contain a
     * "HayabusaConfig" the jar will be added to the classPath
     * and a {@link PluginConfig} will be created. If the PluginConfig
     * contains a "mixinConfig" entry that MixinConfig will be added by
     * the CoreMod.
     *
     * @param pluginClassLoader the classLoader to load Plugins with.
     */
    public void createPluginConfigs(ClassLoader pluginClassLoader)
    {
        if (!(pluginClassLoader instanceof URLClassLoader))
        {
            throw new IllegalArgumentException("PluginClassLoader was not" +
                    " an URLClassLoader, but: "
                    + pluginClassLoader.getClass().getName());
        }

        this.classLoader = pluginClassLoader;
        Core.LOGGER.info("PluginManager: Scanning for PluginConfigs.");

        File d = new File(PATH);
        Map<String, File> remap = scanPlugins(d.listFiles(), pluginClassLoader);
        remap.keySet().removeAll(configs.keySet());

        try
        {
            File[] remappedPlugins = remapper.remap(remap.values());
            scanPlugins(remappedPlugins, pluginClassLoader);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Map<String, File> scanPlugins(File[] files,
                                          ClassLoader pluginClassLoader)
    {
        Map<String, File> remap = new HashMap<>();

        try
        {
            for (File file : Objects.requireNonNull(files))
            {
                if (file.getName().endsWith(".jar"))
                {
                    Core.LOGGER.info("PluginManager: Scanning "
                            + file.getName());
                    try
                    {
                        scanJarFile(file, pluginClassLoader, remap);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return remap;
    }

    /**
     * Called by {@link Hayabusa}.
     *
     * Instantiates all found Plugins.
     */
    public void instantiatePlugins()
    {
        for (PluginConfig config : configs.values())
        {
            if (plugins.containsKey(config))
            {
                Hayabusa.getLogger().error("Can't register Plugin "
                        + config.getName()
                        + ", a plugin with that name is already registered.");
                continue;
            }

            Hayabusa.getLogger().info("Instantiating: "
                    + config.getName()
                    + ", MainClass: "
                    + config.getMainClass());
            try
            {
                Class<?> clazz = Class.forName(config.getMainClass());
                Constructor<?> constructor = clazz.getConstructor();
                constructor.setAccessible(true);
                Plugin plugin = (Plugin) constructor.newInstance();
                plugins.put(config, plugin);
            }
            catch (Throwable e)
            {
                Hayabusa.getLogger().error("Error instantiating : "
                        + config.getName() + ", caused by:");

                e.printStackTrace();
            }
        }
    }

    private void scanJarFile(File file,
                             ClassLoader pluginClassLoader,
                             Map<String, File> remap)
            throws Exception
    {
        try (JarFile jarFile = new JarFile(file))
        {
            Manifest manifest = jarFile.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            String configName = attributes.getValue("HayabusaConfig");

            if (configName == null)
            {
                throw new BadPluginException(jarFile.getName()
                        + ": Manifest doesn't provide a HayabusaConfig!");
            }

            String vanilla = attributes.getValue("HayabusaVanilla");
            switch (Environment.getEnvironment())
            {
                case VANILLA:
                    if (vanilla == null || vanilla.equals("false"))
                    {
                        Core.LOGGER.info("Found Plugin to remap!");
                        remap.put(configName, file);
                        return;
                    }

                    break;
                case SEARGE:
                case MCP:
                    if (vanilla != null && vanilla.equals("true"))
                    {
                        return;
                    }

                    break;
                default:
            }

            // ._.
            ReflectionUtil.addToClassPath((URLClassLoader) pluginClassLoader, file);

            PluginConfig config = Jsonable.GSON.fromJson(
                    new InputStreamReader(
                            Objects.requireNonNull(
                                    pluginClassLoader.getResourceAsStream(configName))),
                    PluginConfig.class);

            if (config == null)
            {
                throw new BadPluginException(jarFile.getName()
                        + ": Found a PluginConfig, but couldn't instantiate it.");
            }

            Core.LOGGER.info("Found PluginConfig: "
                    + config.getName()
                    + ", MainClass: "
                    + config.getMainClass()
                    + ", Mixins: "
                    + config.getMixinConfig());

            configs.put(configName, config);
        }
    }

    /**
     * @return a set of all found PluginConfigs.
     */
    public Map<String, PluginConfig> getConfigs()
    {
        return configs;
    }

    /**
     * @return a Map of all found Plugins with their names as keys.
     */
    public Map<PluginConfig, Plugin> getPlugins()
    {
        return plugins;
    }

    public ClassLoader getPluginClassLoader()
    {
        return classLoader;
    }
}
