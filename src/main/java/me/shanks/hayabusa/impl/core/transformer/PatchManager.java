package me.shanks.hayabusa.impl.core.transformer;

public interface PatchManager
{
    void addPatch(Patch patch);

    byte[] transform(String name, String transformedName, byte[] bytes);

}
