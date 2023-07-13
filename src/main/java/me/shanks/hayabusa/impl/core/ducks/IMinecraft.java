package me.shanks.hayabusa.impl.core.ducks;

import net.minecraft.client.resources.data.IMetadataSerializer;

/**
 * Duck interface for {@link net.minecraft.client.Minecraft}.
 */
public interface IMinecraft
{
    /** @return the current gameloop, will be incremented every gameloop. */
    int getGameLoop();

    /** @return <tt>true</tt> if Hayabusa is running. */
    boolean isHayabusaRunning();

    /** Polls all scheduled tasks and runs them. */
    void runScheduledTasks();

    /** @return Minecraft's MetadataSerializer. */
    IMetadataSerializer getMetadataSerializer();
}
