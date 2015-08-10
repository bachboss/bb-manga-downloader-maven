/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.bus;

import com.bachboss.mangadownloader.bus.description.ABusPageBasedDefaultChapImage;
import com.bachboss.mangadownloader.entity.*;
import com.bachboss.mangadownloader.ult.NumberUtilities;
import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Hentai2Read extends ABusPageBasedDefaultChapImage {

    private static final NamedPattern PATTERN_CHAPTER = NamedPattern.compile("(?<cN>\\d+)");
    private static final NamedPattern PATTERN_URL = NamedPattern.compile("location.href='(?<url>.*)'.*this.value");

    @Override
    public List<Manga> getAllMangas(Server s) throws IOException {
        // TODO: MUST DO THIS, LATER !
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Elements getChapterQuery(Element htmlTag) {
        return htmlTag.select("html body.page div.wrap div#sct_main div#sct_ctt_ara.fl div.con div.wpm_pag div.left div.box div.text ul li a");
    }

    @Override
    protected Element getImageQuery(Element imgNode) {
        return imgNode.select("div.prw a img[alt][src]").first();
    }

    @Override
    protected Chapter getChapterFromTag(Element htmlTag, Manga m) {
        return new Chapter(getChapterNumber(htmlTag.text()), htmlTag.text(), htmlTag.attr("href"), m);
    }

    @Override
    protected Image getImageFromTag(Element imgNode, Chapter c, Page p) {
        return new Image(p.getPageOrder(), imgNode.attr("src"), c, c.getUrl());
    }

    @Override
    public List<Page> getAllPages(Chapter chapter, Document doc) throws IOException {
        List<Page> lstPage = new ArrayList<Page>();
        Element xmlNode = doc.select("select[class=cbo_wpm_pag]").first();
        String url = xmlNode.attr("onchange");
        NamedMatcher m = PATTERN_URL.matcher(url);
        if (m.find()) {
            url = m.group(1);
        } else {
            return lstPage;
        }
        Elements xmlNodes = xmlNode.select("option");
        for (Element e : xmlNodes) {
            Page p = new Page(url + e.attr("value") + "/", chapter, NumberUtilities.getNumberInt(e.text()),
                    e.attributes().hasKey("selected"));
            lstPage.add(p);
        }

        return lstPage;
    }

    private float getChapterNumber(String name) {
        NamedMatcher m = PATTERN_CHAPTER.matcher(name);
        if (m.find()) {
            try {
                return Float.parseFloat(m.group(1));
            } catch (Exception ex) {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
