package me.shanks.hayabusa.impl.core;

import me.shanks.hayabusa.api.event.bus.instance.Bus;
import me.shanks.hayabusa.impl.core.transformer.HayabusaTransformer;
import me.shanks.hayabusa.impl.core.util.MixinHelper;
import me.shanks.hayabusa.impl.util.misc.FileUtil;
import me.shanks.hayabusa.tweaker.HayabusaTweaker;
import me.shanks.hayabusa.tweaker.TweakerCore;
import me.shanks.hayabusa.vanilla.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hayabusas CoreMod.
 * {@link HayabusaTweaker}
 */
@SuppressWarnings("unused")
public class Core implements TweakerCore
{
    /** Logger for the Core. */
    public static final Logger LOGGER = LogManager.getLogger("Hayabusa-Core");

    @Override
    public void init(ClassLoader pluginClassLoader)
    {
        LOGGER.info("Initializing Hayabusas Core.");
        LOGGER.info("Found Environment: " + Environment.getEnvironment());

        Path path = Paths.get("hayabusa");
        FileUtil.createDirectory(path);
        FileUtil.getDirectory(path, "plugins");

        MixinHelper helper = MixinHelper.getHelper();

        MixinBootstrap.init();
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.DEFAULT)
                .setSide(MixinEnvironment.Side.CLIENT);
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.PREINIT)
                .setSide(MixinEnvironment.Side.CLIENT);
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.INIT)
                .setSide(MixinEnvironment.Side.CLIENT);
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.DEFAULT)
                .setSide(MixinEnvironment.Side.CLIENT);
        String extraMixin;
        if (Environment.hasForge())
        {
            LOGGER.info("Forge detected!");
            extraMixin = "mixins.forge.json";
        }
        else
        {
            LOGGER.info("No Forge!");
            extraMixin = "mixins.vanilla.json";
        }

        Mixins.addConfiguration(extraMixin);

        helper.addConfigExclusion("mixins.hayabusa.json");
        Mixins.addConfiguration("mixins.hayabusa.json");
        String obfuscationContext = "searge";
        if (Environment.getEnvironment() == Environment.VANILLA)
        {
            obfuscationContext = "notch";
        }

        MixinEnvironment.getDefaultEnvironment()
                .setObfuscationContext(obfuscationContext);
    }

    @Override
    public String[] getTransformers()
    {
        return new String[] { HayabusaTransformer.class.getName() };
    }
}
