package me.shanks.hayabusa.installer.srg2notch.remappers;

import me.shanks.hayabusa.installer.srg2notch.Mapping;
import org.objectweb.asm.tree.ClassNode;

public interface Remapper
{
    void remap(ClassNode cn, Mapping mapping);

}