package me.shanks.hayabusa.impl.core.mixins;

import me.shanks.hayabusa.impl.Hayabusa;
import me.shanks.hayabusa.impl.core.ducks.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Queue;
import java.util.concurrent.FutureTask;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMinecraft
{
    private static boolean isHayabusaRunning = true;

    @Shadow
    @Final
    private static Logger logger;

    @Shadow
    @Final
    private Queue<FutureTask<?>> scheduledTasks;

    private int gameLoop = 0;

    @Override
    @Accessor(value = "metadataSerializer_")
    public abstract IMetadataSerializer getMetadataSerializer();

    @Override
    public int getGameLoop()
    {
        return gameLoop;
    }

    @Override
    public boolean isHayabusaRunning()
    {
        return isHayabusaRunning;
    }

    @Override
    @SuppressWarnings("SynchronizeOnNonFinalField")
    public void runScheduledTasks()
    {
        synchronized (this.scheduledTasks)
        {
            while (!this.scheduledTasks.isEmpty())
            {
                Util.runTask(this.scheduledTasks.poll(), logger);
            }
        }
    }

    @Inject(
            method = "startGame",
            at = @At(value = "HEAD"))
    private void initHook_2(CallbackInfo info)
    {
        Hayabusa.preInit();
    }

    @Inject(
            method = "startGame",
            at = @At(value = "TAIL"))
    private void initHook(CallbackInfo info)
    {
        Hayabusa.init();
        Hayabusa.postInit();
    }
}
