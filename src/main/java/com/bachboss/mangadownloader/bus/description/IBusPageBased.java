/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.bus.description;

import com.bachboss.mangadownloader.bus.exception.HtmlParsingException;
import com.bachboss.mangadownloader.entity.Chapter;
import com.bachboss.mangadownloader.entity.Image;
import com.bachboss.mangadownloader.entity.Page;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;

/**
 *
 * @author Bach
 */
public interface IBusPageBased extends IBus {

//    public Image getImage(Page p) throws IOException, HtmlParsingException;
    public Image getImage(Page p, Document doc) throws IOException, HtmlParsingException;

    public List<Page> getAllPages(Chapter c, Document doc) throws IOException, HtmlParsingException;
//    List<Page> getAllPages(Chapter c) throws IOException, HtmlParsingException;
}
