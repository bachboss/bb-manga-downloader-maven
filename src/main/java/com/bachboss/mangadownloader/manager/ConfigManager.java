/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.manager;

import com.bachboss.mangadownloader.ult.OSSupport;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Bach
 */
public class ConfigManager {

    private static final ConfigManager cI = new ConfigManager();
    private Properties softwareConfig;
    private static final String CONFIG_PATH = "config.properties";

    private ConfigManager() {
        softwareConfig = new Properties();
    }

    public static ConfigManager getCurrentInstance() {
        return cI;
    }

    public static void loadOnStartUp() {
        Properties p = cI.softwareConfig;
        try {
            File configFile = new File(CONFIG_PATH);
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            p.load(new FileInputStream(CONFIG_PATH));
        } catch (Exception ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        synchronized (cI) {
            // Load Proxy
            if (cI.getIsUsingProxy()) {
                HttpDownloadManager.config.setProxy(cI.getProxyAddress(), cI.getProxyPort());
            }
            // Load Save Path
            {
                String outputFolder = cI.getOutputFolder();
                if (outputFolder != null) {
                    FileManager.setDownloadFolder(new File(outputFolder));
                }
            }
        }
    }

    public void save() throws FileNotFoundException, IOException {
        softwareConfig.store(new FileOutputStream(CONFIG_PATH), null);
    }

    //<editor-fold>
    public String getProperty(String propertyName) throws ConfigNotFoundException {
        String returnValue = softwareConfig.getProperty(propertyName);
        if (returnValue == null) {
            throw new ConfigNotFoundException("Config " + propertyName + " not found");

        } else {
            return returnValue;
        }
    }

    public boolean getBooleanProperty(String propertyName) throws ConfigNotFoundException {
        return ConfigManager.getYesNo(softwareConfig.getProperty(propertyName));
    }

    public void setProperty(String propertyName, String value) {
        softwareConfig.setProperty(propertyName, value);
    }

    private static boolean getYesNo(String value) {
        if ("yes".equals(value)) {
            return true;
        }
        return false;
    }

    private static String getYesNoString(boolean value) {
        if (value) {
            return "yes";
        } else {
            return "no";
        }
    }

    private void setYesNo(String key, boolean value) {
        if (value) {
            setProperty(key, "yes");
        } else {
            setProperty(key, "no");
        }
    }

//    private void setYesNo(String key, String value) {
//        setYesNo(key, getYesNo(value));
//    }
    public String getConfig(Config config) {
        try {
            return getProperty(config.getConfigName());
        } catch (ConfigNotFoundException ex) {
            return config.getDefaultValue();
        }
    }

    /**
     *
     * @param <T>
     * @param config
     * @param o
     * @return null type o is not defined
     */
    public <T> T getConfig(Config config, T o) {
        if (o instanceof String) {
            return (T) getConfig(config);
        } else if (o instanceof Integer) {
            return (T) (Integer.valueOf(getConfig(config)));
        } else if (o instanceof Boolean) {
            return (T) (Boolean.valueOf(getYesNo(getConfig(config))));
        } else {
            return null;
        }
    }

    public void setConfig(Config config, String value) {
        setProperty(config.getConfigName(), value);
    }

    public void setConfig(Config config, int value) {
        setConfig(config, String.valueOf(value));
    }

    public void setConfig(Config config, boolean value) {
        setConfig(config, getYesNoString(value));
    }
    //</editor-fold>

    public String getProxyAddress() {
        return getConfig(Config.ProxyAddress);
    }

    public void setProxyAddress(String address) {
        setConfig(Config.ProxyAddress, address);
    }

    public int getProxyPort() {
        return getConfig(Config.ProxyPort, Integer.MAX_VALUE);
    }

    public void setProxyPort(int port) {
        setConfig(Config.ProxyPort, port);
    }

    public String getOutputFolder() {
        File f = new File(getConfig(Config.OutputFolder));
        if (!f.exists()) {
            boolean isCreated = f.mkdir();
            if (!isCreated) {
                return Config.OutputFolder.getDefaultValue();
            }
        } else if (f.isDirectory()) {
            return f.getAbsolutePath();
        }
        return Config.OutputFolder.getDefaultValue();
    }

    public void setOutputFolder(String outputFolder) {
        setConfig(Config.OutputFolder, outputFolder);
    }

    public boolean getIsUsingProxy() {
        return ConfigManager.getYesNo(getConfig(Config.UseProxy));
    }

    public void setIsUsingProxy(boolean isUseProxy) {
        setYesNo(Config.UseProxy.getConfigName(), isUseProxy);
    }

    public String getWatcherFile() {
        File f = new File(getConfig(Config.WatcherFile));
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                return Config.WatcherFile.getDefaultValue();
            }
        }
        return f.getAbsolutePath();
    }

    public String getCacheFolder() {
        return getConfig(Config.CacheFolder);
    }

    public boolean isAdult() {
        return getYesNo(getConfig(Config.Aldult));
    }

    public boolean isZip() {
        return getYesNo(getConfig(Config.Zip));
    }

    public void setZip(boolean isZip) {
        setConfig(Config.Zip, isZip);
    }

    public boolean isDeleteAfterZip() {
        return getConfig(Config.DeleteAfterZip, Boolean.FALSE);
    }

    public void setDeleteAfterZip(boolean isDelete) {
        setConfig(Config.DeleteAfterZip, isDelete);
    }

    public boolean isGenerateHtml() {
        return getConfig(Config.GenerateHtml, Boolean.TRUE);
    }

    public void setGenerateHtml(boolean isGenerate) {
        setConfig(Config.GenerateHtml, isGenerate);
    }

    public boolean isGenerateHtmlManifest() {
        return getConfig(Config.GenerateHtmlManifest, Boolean.TRUE);
    }

    public void setGenerateHtmlManifest(boolean isGenerate) {
        setConfig(Config.GenerateHtmlManifest, isGenerate);
    }

    public boolean isHttpdServer() {
        return getConfig(Config.HttpdServer, Boolean.TRUE);
    }

    public void setHttpdServer(boolean isTurnOn) {
        setConfig(Config.HttpdServer, isTurnOn);
    }

    public int getHttpdServerPort() {
        return getConfig(Config.HttpdServerPort, Integer.MAX_VALUE);
    }

    public void setHttpdServerPort(int port) {
        setConfig(Config.HttpdServerPort, port);
    }

    public void setCheckUpdateOnStartUp(boolean value) {
        setConfig(Config.UpdateOnStartUp, value);
    }

    public boolean isCheckUpdateOnStartUp() {
        return getConfig(Config.UpdateOnStartUp, Boolean.FALSE);
    }

    public int getMaxiumDownloadInQueue() {
        return getConfig(Config.MaxiumDownloadInQueue, Integer.MAX_VALUE);
    }

    public void setMaxiumDownloadInQueue(int max) {
        setConfig(Config.MaxiumDownloadInQueue, max);
    }

    public String getLastDownloadMangaName() {
        return getConfig(Config.LastDownloadMangaName);
    }

    public void setLastDownloadMangaName(String name) {
        setConfig(Config.LastDownloadMangaName, name);
    }

    public String getLastDownloadMangaUrl() {
        return getConfig(Config.LastDownloadMangaUrl);
    }

    public void setLastDownloadMangaUrl(String url) {
        setConfig(Config.LastDownloadMangaUrl, url);
    }

    public int getMaxiumDownloadImage() {
        return getConfig(Config.MaxiumDownloadImage, Integer.MAX_VALUE);
    }

    public void setMaxiumDownloadImage(int max) {
        setConfig(Config.MaxiumDownloadImage, max);
    }

    public String getBookmarkFile() {
        File f = new File(getConfig(Config.BookmarkFile));
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                return Config.BookmarkFile.getDefaultValue();
            }
        }
        return f.getAbsolutePath();
    }

    public void setBookmarkFile(String file) {
        setConfig(Config.BookmarkFile, file);
    }

    public String getHistoryFile() {
        File f = new File(getConfig(Config.HistoryFile));
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                return Config.HistoryFile.getDefaultValue();
            }
        }
        return f.getAbsolutePath();
    }

    public void setHistoryFile(String file) {
        setConfig(Config.HistoryFile, file);
    }

    public static class ConfigNotFoundException extends Exception {

        public ConfigNotFoundException(String message) {
            super(message);
        }
    }

    public static enum Config {

        ProxyAddress("proxyAddress", ""),
        ProxyPort("proxyPort", "0"),
        OutputFolder("outputFolder", OSSupport.getDefaultOutputFolder().getAbsolutePath()),
        UseProxy("useProxy", "no"),
        WatcherFile("watcherFile", "watcher.xml"),
        CacheFolder("cacheFolder", "Cache"),
        Aldult("abc", "no"),
        Zip("zip", "no"),
        DeleteAfterZip("deleteAfterZip", "no"),
        GenerateHtml("generateHtml", "no"),
        GenerateHtmlManifest("generateHtmlManifest", "no"),
        HttpdServer("httpdServer", "no"),
        HttpdServerPort("httpdServerPort", "7070"),
        UpdateOnStartUp("updateOnStartup", "yes"),
        MaxiumDownloadInQueue("maxiumDownloadInQueue", "5"),
        MaxiumDownloadImage("maxiumDownloadImage", "3"),
        LastDownloadMangaUrl("lastDownloadMangaUrl", "http://kissmanga.com/Manga/Hentai-Ouji-to-Warawanai-Neko"),
        LastDownloadMangaName("lastDownloadMangaName", "Hentai Ouji to Warawanai Neko"),
        BookmarkFile("bookmarkfile", "bookmark.xml"),
        HistoryFile("historyfile", "history.xml");

        private Config(String configName, String defaultValue) {
            this.configName = configName;
            this.defaultValue = defaultValue;
        }
        private final String configName;
        private final String defaultValue;

        public String getConfigName() {
            return configName;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }
}
