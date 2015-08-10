/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.faces.implement;

import com.bachboss.mangadownloader.bus.MangaCow;
import com.bachboss.mangadownloader.bus.description.IBus;
import com.bachboss.mangadownloader.faces.AFacadeDefault;
import com.bachboss.mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeMangaCow extends AFacadeDefault {

    public FacadeMangaCow() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new MangaCow();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.Support;
    }

    @Override
    public String getServerName() {
        return "Mngcow";
    }
}
