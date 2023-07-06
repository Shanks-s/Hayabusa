package me.shanks.hayabusa.api.plugin;

/**
 * Plugins can be used to add functionality, modules or commands
 * to Hayabusa. Plugins can contain Mixins and a MixinConfig and
 * are located in the hayabusa/plugins folder. A Plugin should
 * be a jar file. Dependencies like Mixin don't need to be included
 * as they are already included in the Hayabusa jar.
 *
 * TODO: CorePlugin implementing IClassTransformer for ASM if requested?
 * TODO: unload plugins
 */
public interface Plugin
{
    /**
     * Loads this Plugin.
     */
    void load();

}
