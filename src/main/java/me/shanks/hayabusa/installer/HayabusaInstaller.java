package me.shanks.hayabusa.installer;

import me.shanks.hayabusa.impl.util.thread.SafeRunnable;
import me.shanks.hayabusa.installer.gui.ErrorPanel;
import me.shanks.hayabusa.installer.gui.InstallerFrame;
import me.shanks.hayabusa.installer.gui.VersionPanel;
import me.shanks.hayabusa.installer.main.Library;
import me.shanks.hayabusa.installer.main.LibraryClassLoader;
import me.shanks.hayabusa.installer.main.LibraryFinder;
import me.shanks.hayabusa.installer.main.MinecraftFiles;
import me.shanks.hayabusa.installer.service.InstallerService;
import me.shanks.hayabusa.installer.version.Version;
import me.shanks.hayabusa.installer.version.VersionFinder;

import javax.swing.*;
import java.util.List;

// TODO: allow us to add new profiles etc
/**
 * {@link me.shanks.hayabusa.installer.main.Main#main(String[])}
 */
@SuppressWarnings("unused")
public class HayabusaInstaller implements Installer
{
    private final MinecraftFiles files;
    private final InstallerFrame gui;
    private InstallerService service;

    public HayabusaInstaller()
    {
        this.files = new MinecraftFiles();
        this.gui = new InstallerFrame();
    }

    public void launch(LibraryClassLoader classLoader, String[] args)
    {
        SwingUtilities.invokeLater(gui::display);

        wrapErrorGui(() ->
        {
            files.findFiles(args);

            LibraryFinder libraryFinder = new LibraryFinder();
            for (Library library : libraryFinder.findLibraries(files))
            {
                classLoader.installLibrary(library);
            }

            service = new InstallerService();
            refreshVersions();
        });
    }

    @Override
    public boolean refreshVersions()
    {
        return wrapErrorGui(() ->
        {
            VersionFinder versionFinder = new VersionFinder();
            List<Version> versions = versionFinder.findVersions(files);

            gui.schedule(new VersionPanel(this, versions));
        });
    }

    @Override
    public boolean install(Version version)
    {
        return wrapErrorGui(() ->
        {
            service.install(files, version);
            refreshVersions();
        });
    }

    @Override
    public boolean uninstall(Version version)
    {
        return wrapErrorGui(() ->
        {
            service.uninstall(version);
            refreshVersions();
        });
    }

    @Override
    public boolean update(boolean forge)
    {
        return wrapErrorGui(() ->
        {
            service.update(files, forge);
            refreshVersions();
        });
    }

    private boolean wrapErrorGui(SafeRunnable runnable)
    {
        try
        {
            runnable.runSafely();
            return false;
        }
        catch (Throwable throwable)
        {
            gui.schedule(new ErrorPanel(throwable));
            throwable.printStackTrace();
            return true;
        }
    }

}
