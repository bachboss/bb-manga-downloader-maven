/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.bus;

import com.bachboss.mangadownloader.bus.description.ABusPageBasedDefaultChapPageImage;
import com.bachboss.mangadownloader.entity.*;
import com.bachboss.mangadownloader.entity.data.MangaDateTime;
import com.bachboss.mangadownloader.ult.DateTimeUtilities;
import com.bachboss.mangadownloader.ult.NumberUtilities;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class MangaReader extends ABusPageBasedDefaultChapPageImage { // Done

    private static final String BASED_URL = "http://www.mangareader.net";
    private static final String URL_LIST_MANGA = "http://www.mangareader.net/alphabetical";
    //
    private static final String DATE_FORMAT_UPLOAD = "MM/dd/yyyy";
    private static final String DEFAULT_TRANS = "MangaReader";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<Manga>();
        Document doc = getDocument(URL_LIST_MANGA);

        Elements xmlNodes = doc.select("div[class=series_alpha] ul[class=series_alpha] li a");

        for (Element e : xmlNodes) {
            Manga m = new Manga(s, e.text(), BASED_URL + e.attr("href"));
            lstReturn.add(m);
        }
        return lstReturn;
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("div[id=chapterlist] tr");
    }

    @Override
    protected Elements getPageQuery(Element htmlTag) {
        return htmlTag.select("div[id=selectpage]").first().select("option");
    }

    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("img[id=img]").first();
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        if (htmlTag.child(0).tagName().equals("th")) {
            return null;
        }
        Element aTag = htmlTag.child(0).select("a").first();
        String strDate = htmlTag.child(1).text();
        MangaDateTime date;
        try {
            date = new MangaDateTime(DateTimeUtilities.getDate(strDate, DATE_FORMAT_UPLOAD));
        } catch (ParseException ex) {
            date = new MangaDateTime(strDate);
        }
        return new Chapter(-1, aTag.text(), BASED_URL + aTag.attr("href"), m, DEFAULT_TRANS, date);
    }

    @Override
    protected Page getPageFromTag(Element htmlTag, Chapter c) {
        return new Page(BASED_URL + htmlTag.attr("value"), c, NumberUtilities.getNumberInt(htmlTag.text()),
                htmlTag.attributes().hasKey("selected"));
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c, Page p) {
        return new Image(p.getPageOrder(), imgNode.attr("src"), c);
    }
}
