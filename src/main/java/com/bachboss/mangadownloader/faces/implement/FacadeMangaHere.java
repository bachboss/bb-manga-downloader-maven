/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.faces.implement;

import com.bachboss.mangadownloader.bus.MangaHere;
import com.bachboss.mangadownloader.bus.description.IBus;
import com.bachboss.mangadownloader.faces.AFacadeDefault;
import com.bachboss.mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeMangaHere extends AFacadeDefault {

    public FacadeMangaHere() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new MangaHere();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "MangaHere";
    }
}
