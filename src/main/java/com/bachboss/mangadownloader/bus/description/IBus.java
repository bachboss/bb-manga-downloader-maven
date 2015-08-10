/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.bus.description;

import com.bachboss.mangadownloader.bus.exception.HtmlParsingException;
import com.bachboss.mangadownloader.entity.Chapter;
import com.bachboss.mangadownloader.entity.Image;
import com.bachboss.mangadownloader.entity.Manga;
import com.bachboss.mangadownloader.entity.Server;
import com.bachboss.mangadownloader.faces.IFacadeMangaServer;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Bach
 */
public interface IBus {

    /**
     *
     * @param s Server
     * @return <code>List<Manga></code>, which should not be null
     * @throws IOException
     * @throws HtmlParsingException
     */
    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException;

    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException;

    public List<Image> getAllImages(Chapter c) throws IOException, HtmlParsingException;

    public Manga getManga(String mangaUrl);

    public Chapter getChapter(String chapterUrl, boolean isGetMangaInformation);

    public IFacadeMangaServer.UrlType getUrlType(String url);
}
