package me.shanks.hayabusa.installer.service;

import me.shanks.hayabusa.impl.util.misc.JsonPathWriter;
import me.shanks.hayabusa.impl.util.misc.StreamUtil;
import me.shanks.hayabusa.installer.InstallerGlobals;
import me.shanks.hayabusa.installer.main.Main;
import me.shanks.hayabusa.installer.main.MinecraftFiles;
import me.shanks.hayabusa.installer.version.Version;
import me.shanks.hayabusa.installer.version.VersionUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class InstallerService
{
    public static final String VANILLA = "hayabusa/vanilla/1.8.9/";
    public static final String FORGE   = "hayabusa/forge/1.8.9/";
    public static final String JAR     = "forge-1.8.9.jar";
    public static final String VJAR    = "vanilla-1.8.9.jar";

    private final Srg2NotchService remapper = new Srg2NotchService();

    public void install(MinecraftFiles files, Version version)
            throws IOException
    {
        boolean hasForge = VersionUtil.hasForge(version);
        boolean hasEarth = VersionUtil.hasHayabusa(version);

        update(files, hasForge);
        InstallerUtil.installLibs(version.getJson());

        if (!hasEarth)
        {
            InstallerUtil.installHayabusa(version.getJson(), hasForge);
            write(version);
        }
    }

    public void update(MinecraftFiles files, boolean forge)
            throws IOException
    {
        String libPath;
        if (forge)
        {
            libPath = files.getLibraries() + FORGE + JAR;
        }
        else
        {
            libPath = files.getLibraries() + VANILLA + VJAR;
        }

        URL us = Main.class.getProtectionDomain().getCodeSource().getLocation();
        URL target = new URL("file:/" + libPath);
        //noinspection ResultOfMethodCallIgnored
        new File(target.getFile()).getParentFile().mkdirs();

        if (forge)
        {
            if (!InstallerGlobals.hasInstalledForge())
            {
                StreamUtil.copy(us, target);
                InstallerGlobals.setForge(true);
            }
        }
        else
        {
            if (!InstallerGlobals.hasInstalledVanilla())
            {
                remapper.remap(us, target);
                InstallerGlobals.setVanilla(true);
            }
        }
    }

    public void uninstall(Version version) throws IOException
    {
        InstallerUtil.uninstallHayabusa(version.getJson());
        InstallerUtil.uninstallLibs(version.getJson());
        write(version);
    }

    public void write(Version version) throws IOException
    {
        JsonPathWriter.write(version.getFile().toPath(), version.getJson());
    }

}