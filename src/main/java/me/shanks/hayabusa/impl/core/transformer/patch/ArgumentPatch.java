package me.shanks.hayabusa.impl.core.transformer.patch;

import org.objectweb.asm.tree.ClassNode;

public abstract class ArgumentPatch extends FinishingPatch
{
    private final String argument;

    public ArgumentPatch(String name, String transformed, String argument)
    {
        super(name, transformed);
        this.argument = argument;
    }

    protected abstract void applyPatch(ClassNode node);

    @Override
    public void apply(ClassNode node)
    {
        applyPatch(node);
    }
}
