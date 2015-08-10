/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.entity;

import com.bachboss.mangadownloader.manager.HttpDownloadManager;
import com.bachboss.mangadownloader.manager.HttpDownloadManager.MyConnection;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public class Image extends HtmlDocument implements Serializable {

    private Chapter chapter;
    private int imgOrder;
    private MyConnection connection;
    private boolean isDownloaded = false;

    @Deprecated
    /**
     * replaced by {@link #Image(int, String, Chapter, String)}
     */
    public Image(int imgOrder, String url, Chapter chapter) {
        this.imgOrder = imgOrder;
        this.chapter = chapter;
        this.url = url;
    }

    public Image(int imgOrder, String url, Chapter chapter, String referer) {
        this(imgOrder, url, chapter);
        try {
            connection = HttpDownloadManager.createConnection(url).referer(referer);
            connection.referer(referer);
        } catch (IOException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int getImgOrder() {
        return imgOrder;
    }

    public void setImgOrder(int imgOrder) {
        this.imgOrder = imgOrder;
    }

    public MyConnection getConnection() throws IOException {
        if (connection == null) {
            connection = HttpDownloadManager.createConnection(url);
        }
        return connection;
    }

    public void setConnection(MyConnection connection) {
        this.connection = connection;
    }

    public boolean isIsDownloaded() {
        return isDownloaded;
    }

    public void setIsDownloaded(boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
    }

    @Override
    public String toString() {
        return this.getUrl();
    }
}