/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.bus;

import com.bachboss.mangadownloader.bus.description.ABusPageBasedDefaultChapPageImage;
import com.bachboss.mangadownloader.entity.*;
import com.bachboss.mangadownloader.entity.data.MangaDateTime;
import com.bachboss.mangadownloader.ult.NumberUtilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class EatManga extends ABusPageBasedDefaultChapPageImage {  // Done
    // TODO: Convert Time Stamp

    private static final String BASED_URL = "http://eatmanga.com";
    private static final String URL_LIST_MANGA = "http://eatmanga.com/Manga-Scan/";
    // 
    private static final String DEFAULT_TRANS = "EatManga";

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        List<Manga> lstReturn = new ArrayList<Manga>();
        Document doc = getDocument(URL_LIST_MANGA);

        Elements xmlNodes = doc.select("table[id=updates] tr th");

        for (Element e : xmlNodes) {
            Element node = e.select("a").first();
            if (node != null) {
                Manga m = new Manga(s, node.text(), BASED_URL + node.attr("href"));
                lstReturn.add(m);
            }
        }
        return lstReturn;
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("table[id=updates] th[class=title] a");
    }

    @Override
    protected Elements getPageQuery(Element htmlTag) {
        return htmlTag.select("select[id=pages]").first().select("option");
    }

    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("img[alt]").select("[id*=image]").first();
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        String url = htmlTag.attr("href");
        if (url.contains("upcoming")) {
            return null;
        }
        MangaDateTime date;
        date = new MangaDateTime(htmlTag.parent().nextElementSibling().text());

        return new Chapter(-1, htmlTag.text(), BASED_URL + url, m,
                DEFAULT_TRANS, date);
    }

    @Override
    protected Page getPageFromTag(Element htmlTag, Chapter c) {
        return new Page(BASED_URL + htmlTag.attr("value"), c,
                NumberUtilities.getNumberInt(htmlTag.text()),
                htmlTag.attributes().hasKey("selected"));
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c, Page p) {
        Element node = imgNode.select("img[alt]").select("[id*=image]").first();
        if (node != null) {
            return new Image(p.getPageOrder(), imgNode.attr("src"), c);
        } else {
            return null;
        }
    }
}
