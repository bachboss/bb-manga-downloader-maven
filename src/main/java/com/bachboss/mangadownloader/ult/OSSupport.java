/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.ult;

import java.io.File;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Bach
 */
public class OSSupport {

    private static final OS CURRENT_OS;

    static {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            CURRENT_OS = OS.WINDOWS;
        } else if ((os.contains("mac"))) {
            CURRENT_OS = OS.MAC_OS;
        } else if ((os.contains("nix") || os.contains("nux"))) {
            CURRENT_OS = OS.UNIX;
        } else if (os.contains("sunos")) {
            CURRENT_OS = OS.SOLARIS;
        } else {
            CURRENT_OS = OS.UNDETECTED;
        }
        System.out.println(os + "\t" + CURRENT_OS);
    }

    public static synchronized OS getOS() {
        return CURRENT_OS;
    }

    public static File getDefaultOutputFolder() {
        return FileUtils.getFile(FileUtils.getUserDirectory(), "Download", "Manga");
    }

    public enum OS {

        WINDOWS, MAC_OS, UNIX, SOLARIS, UNDETECTED
    }
}
