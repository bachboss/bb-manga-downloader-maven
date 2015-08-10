/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.bus;

import com.bachboss.mangadownloader.bus.description.ABusPageBasedDefaultChapPageImage;
import com.bachboss.mangadownloader.bus.exception.HtmlParsingException;
import com.bachboss.mangadownloader.entity.Chapter;
import com.bachboss.mangadownloader.entity.Image;
import com.bachboss.mangadownloader.entity.Manga;
import com.bachboss.mangadownloader.entity.Page;
import com.bachboss.mangadownloader.entity.Server;
import com.bachboss.mangadownloader.entity.data.MangaDateTime;
import com.bachboss.mangadownloader.ult.DateTimeUtilities;
import com.bachboss.mangadownloader.ult.NumberUtilities;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class TenManga extends ABusPageBasedDefaultChapPageImage {

    private static final String DEFAULT_TRANS = "Not Supported";
    private static final String BASED_URL = "http://www.tenmanga.com";
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

    // TODO: Later !
    @Override
    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) throws HtmlParsingException {
        return htmlTag.select("body div.main div.main_left_out div.left_top div.cmtList div.chapter_list table tbody tr:gt(0)");
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) throws HtmlParsingException {
        String displayName = htmlTag.select("td:eq(0)").text();
        String url = BASED_URL + htmlTag.select("td:eq(0) a").first().attr("href");
        String strDate = htmlTag.select("td:eq(2)").text();
        strDate = strDate.substring(strDate.indexOf(',') + 1).trim();
        MangaDateTime date;
        try {
            Date d = DateTimeUtilities.getDate(strDate, DATE_FORMAT_UPLOAD);
            date = new MangaDateTime(d);
        } catch (Exception ex) {
            date = new MangaDateTime(strDate);
        }
        Chapter c = new Chapter(-1F, displayName, url, m, DEFAULT_TRANS, date);
        return c;
    }

    @Override
    protected Elements getPageQuery(Element htmlTag) throws HtmlParsingException {
        return htmlTag.select("select#page").first().select("option");
    }

    @Override
    protected Page getPageFromTag(Element htmlTag, Chapter c) throws HtmlParsingException {
        String url = htmlTag.attr("value");
        int order = NumberUtilities.getNumberInt(htmlTag.text());
        Page p = new Page(url, c, order);
        return p;
    }

    @Override
    protected Element getImageQuery(Element imgNode) throws HtmlParsingException {
        return imgNode.select("img#comicpic").first();
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c, Page p) throws HtmlParsingException {
        return new Image(p.getPageOrder(), imgNode.attr("src"), c);
    }
}
