package me.shanks.hayabusa.tweaker.launch;

import me.shanks.hayabusa.api.config.Jsonable;

public interface Argument<T> extends Jsonable
{
    T getValue();

}
