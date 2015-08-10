/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.bus;

import com.bachboss.mangadownloader.bus.description.ADefaultBus;
import com.bachboss.mangadownloader.bus.description.IBusOnePage;
import com.bachboss.mangadownloader.entity.Chapter;
import com.bachboss.mangadownloader.entity.Image;
import com.bachboss.mangadownloader.entity.Manga;
import com.bachboss.mangadownloader.entity.Server;
import com.bachboss.mangadownloader.ult.Heuristic;
import com.bachboss.mangadownloader.ult.HtmlUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class VeChai extends ADefaultBus implements IBusOnePage {

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chapter> getAllChapters(Manga manga) throws IOException {
        ArrayList<Chapter> lstChapter = new ArrayList<Chapter>();

        Document doc = getDocument(manga.getUrl());
        Elements xmlNode = doc.select("textarea[id=vcfix]");
        String text = xmlNode.html();
        text = HtmlUtilities.unescapeHtml4(text);
        doc = Jsoup.parse(text);
        xmlNode = doc.select("a[target=_blank]");
        for (Element e : xmlNode) {
            StringBuilder name = new StringBuilder();
            if (Heuristic.doCheck(e, manga, name)) {
                lstChapter.add(new Chapter(-1, name.toString(), e.attr("href"), manga));
            }
        }

        return lstChapter;
    }

    @Override
    public List<Image> getAllImages(Chapter chapter) throws IOException {
        URL url = chapter.getURL();
        String host = url.getHost();
        ArrayList<Image> lstReturn = new ArrayList<Image>();
        Iterator<Element> iElement = null;
        Document doc = getDocument(chapter.getUrl());
        Elements xmlNode;
        if (host.startsWith("vc1")) {
            xmlNode = doc.select("textarea[id=vcfix]");
            String text = xmlNode.html();
            text = HtmlUtilities.unescapeHtml4(text);
            text = Heuristic.repairXML(text);
            doc = Jsoup.parse(text);
            xmlNode = doc.select("img");
            iElement = xmlNode.iterator();
        } else if (host.startsWith("doctruyen")) {
            xmlNode = doc.select("div[class=entry2]").select("img");
            iElement = xmlNode.iterator();
        } else if (host.startsWith("vechai.info")) {
            xmlNode = doc.select("textarea[id=vcfix]");
            String text = xmlNode.html();
            text = HtmlUtilities.unescapeHtml4(text);
            text = Heuristic.repairXML(text);
            doc = Jsoup.parse(text);
            xmlNode = doc.select("img");
            iElement = xmlNode.iterator();
        }
        if (iElement != null) {
            int i = 0;
            while (iElement.hasNext()) {
                i++;
                Element e = iElement.next();
                lstReturn.add(new Image(i, e.attr("src"), chapter));
            }
        }

        return lstReturn;
    }
}
