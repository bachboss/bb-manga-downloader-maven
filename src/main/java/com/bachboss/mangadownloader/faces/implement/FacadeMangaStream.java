/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.faces.implement;

import com.bachboss.mangadownloader.bus.MangaStream;
import com.bachboss.mangadownloader.bus.description.IBus;
import com.bachboss.mangadownloader.faces.AFacadeDefault;
import com.bachboss.mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeMangaStream extends AFacadeDefault {

    public FacadeMangaStream() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new MangaStream();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Scanner;
    }

    @Override
    public String getServerName() {
        return "MangaStream";
    }
}
