package me.shanks.hayabusa.impl.core.transformer;

import me.shanks.hayabusa.impl.core.Core;
import me.shanks.hayabusa.impl.core.transformer.patch.HayabusaPatcher;
import me.shanks.hayabusa.impl.core.transformer.patch.patches.EntityPatch;
import me.shanks.hayabusa.impl.core.transformer.patch.patches.EnumFacingPatch;
import me.shanks.hayabusa.impl.core.util.MixinHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;

@IFMLLoadingPlugin.SortingIndex(Integer.MAX_VALUE)
public class HayabusaTransformer implements IClassTransformer
{
    private boolean changingPriority = true;
    private int reentrancy;

    public HayabusaTransformer()
    {
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.DEFAULT)
                .addTransformerExclusion(HayabusaTransformer.class.getName());
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.PREINIT)
                .addTransformerExclusion(HayabusaTransformer.class.getName());
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.INIT)
                .addTransformerExclusion(HayabusaTransformer.class.getName());
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.DEFAULT)
                .addTransformerExclusion(HayabusaTransformer.class.getName());

        PatchManager patches = HayabusaPatcher.getInstance();
        patches.addPatch(new EntityPatch());
        patches.addPatch(new EnumFacingPatch());
        loadReentrantClasses();

        Core.LOGGER.info("Transformer instantiated.");
    }

    @Override
    public byte[] transform(String name,
                            String transformed,
                            byte[] b)
    {
        reentrancy++;
        if (reentrancy > 1)
        {
            Core.LOGGER.warn(
                    "Transformer is reentrant on class: "
                            + name + " : " + transformed + ".");
        }

        if (changingPriority)
        {
            try
            {
                MixinHelper.getHelper().establishDominance();
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
        }

        if (transformed.equals("net.minecraft.client.entity.EntityPlayerSP"))
        {
            Core.LOGGER.info("Done changing MixinPriority.");
            changingPriority = false;
        }

        byte[] r = HayabusaPatcher.getInstance().transform(name, transformed, b);
        reentrancy--;
        return r;
    }

    private void loadReentrantClasses()
    {
        try
        {
            Class.forName("me.shanks.hayabusa.impl.core.util.AsmUtil");
            Class.forName("me.shanks.hayabusa.impl.util.misc.ReflectionUtil");
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
