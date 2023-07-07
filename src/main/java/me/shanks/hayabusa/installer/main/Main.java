package me.shanks.hayabusa.installer.main;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * For some reason my Intellij is broken and this runs only with
 * application.runShadow or run with Coverage
 * <p>
 * <p>
 * BUT it doesn't matter! Just don't run the Installer from Source,
 * the remapper won't work from source!
 */
public class Main
{
    public static void main(String[] args) throws Throwable
    {
        URL[] urls;
        ClassLoader bootCl = Main.class.getClassLoader();
        if (bootCl instanceof URLClassLoader)
        {
            URLClassLoader ucl = (URLClassLoader) bootCl;
            urls = ucl.getURLs();
        }
        else
        {
            urls = new URL[] { Main.class.getProtectionDomain()
                                         .getCodeSource()
                                         .getLocation() };
        }

        LibraryClassLoader cl = new LibraryClassLoader(bootCl, urls);
        Thread.currentThread().setContextClassLoader(cl);

        String installer = "me.shanks.hayabusa.installer.HayabusaInstaller";
        Class<?> c = cl.findClass_public(installer);
        Method   m = c.getMethod("launch", cl.getClass(), String[].class);
        Object   o = c.newInstance();
        m.invoke(o, cl, args);
    }

}