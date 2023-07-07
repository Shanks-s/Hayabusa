package me.shanks.hayabusa.tweaker.launch;

import me.shanks.hayabusa.impl.util.misc.Jsonable;

public interface Argument<T> extends Jsonable
{
    T getValue();

}
