/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.bus;

import com.bachboss.mangadownloader.bus.description.ADefaultBus;
import com.bachboss.mangadownloader.bus.description.IBusOnePage;
import com.bachboss.mangadownloader.bus.exception.HtmlParsingException;
import com.bachboss.mangadownloader.entity.Chapter;
import com.bachboss.mangadownloader.entity.Image;
import com.bachboss.mangadownloader.entity.Manga;
import com.bachboss.mangadownloader.entity.Server;
import com.bachboss.mangadownloader.entity.data.MangaDateTime;
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
public class MangaStream extends ADefaultBus implements IBusOnePage {

    private static final String BASED_URL = "http://mangastream.com";
    private static final String URL_LIST_MANGA = "http://mangastream.com/manga";
    private static final String DEFAULT_TRANS = "MangaStream";

    private List<Manga> getMangas(Server s, String mangaName) throws IOException {
        List<Manga> lstReturn = new ArrayList<Manga>();
        Document doc = getDocument(URL_LIST_MANGA);
        Elements xmlNodes = doc.select("td strong");
        for (Element e : xmlNodes) {
            String mN = e.text();
            if (mangaName == null || mN.equalsIgnoreCase(mangaName)) {
                Manga m = new Manga(s, mN, URL_LIST_MANGA);
                Element e2 = e.nextElementSibling();
                do {
                    if (e2.tagName().equals("a")) {
                        // This host does not have upload time
                        Chapter c = new Chapter(-1, e2.text(), BASED_URL + e2.attr("href"), m,
                                DEFAULT_TRANS, MangaDateTime.NOT_AVAIABLE);
                        m.addChapter(c);
                    }
                    e2 = e2.nextElementSibling();
                } while (e2 != null && !e2.tagName().equals("strong"));
                lstReturn.add(m);
                if (mangaName != null) {
                    return lstReturn;
                }
            }
        }
        return lstReturn;
    }

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        return getMangas(s, null);
    }

    @Override
    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException {
        if (m.getListChapter().isEmpty()) {
            List<Manga> lstManga = getMangas(m.getServer(), m.getMangaName());
            if (lstManga.isEmpty()) {
                throw new HtmlParsingException(m);
            } else {
                return lstManga.get(0).getListChapter();
            }
        } else {
            return m.getListChapter();
        }
    }

    @Override
    public List<Image> getAllImages(Chapter c) throws IOException {
        // Get the image in first page, and others except the 1st one here !
        List<Image> lstImage = new ArrayList<Image>();
        Document doc = getDocument(c.getUrl());
        Elements xmlNodes = doc.select("div[id=controls] a").not("a[href~=javascript]");
        for (Element e : xmlNodes) {
            try {
                String url = BASED_URL + e.attr("href");
                int order = Integer.parseInt(e.html());
                Image img;
                if (order == 1) {
                    img = getImageFromPage(doc, order);
                } else {
                    img = getImageFromPage(url, order);
                }
                lstImage.add(img);
            } catch (NumberFormatException ex) {
            }
        }
        return lstImage;
    }

    private Image getImageFromPage(Document doc, int order) {
        Element e = doc.select("div[id=page] img").first();
        Image img = new Image(order, e.attr("src"), null);
        return img;
    }

    private Image getImageFromPage(String pageUrl, int order) throws IOException {
        Document doc = getDocument(pageUrl);
        return getImageFromPage(doc, order);
    }
}
