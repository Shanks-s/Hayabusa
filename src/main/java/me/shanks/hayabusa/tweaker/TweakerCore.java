package me.shanks.hayabusa.tweaker;

public interface TweakerCore
{
    void init(ClassLoader pluginClassLoader);

    String[] getTransformers();

}