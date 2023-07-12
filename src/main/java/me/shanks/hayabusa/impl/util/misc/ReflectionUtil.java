package me.shanks.hayabusa.impl.util.misc;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ReflectionUtil
{
    public static void addToClassPath(URLClassLoader classLoader, File file)
            throws Exception
    {
        URL url = file.toURI().toURL();
        addToClassPath(classLoader, url);
    }

    public static void addToClassPath(URLClassLoader classLoader, URL url)
            throws Exception
    {
        Method method = URLClassLoader
                .class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(classLoader, url);
    }

}
