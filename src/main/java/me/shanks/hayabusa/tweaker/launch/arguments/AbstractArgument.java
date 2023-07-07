package me.shanks.hayabusa.tweaker.launch.arguments;

import me.shanks.hayabusa.tweaker.launch.Argument;

public abstract class AbstractArgument<T> implements Argument<T>
{
    protected T value;

    public AbstractArgument(T value)
    {
        this.value = value;
    }

    @Override
    public T getValue()
    {
        return value;
    }
}
