/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.faces.implement;

import com.bachboss.mangadownloader.bus.VeChai;
import com.bachboss.mangadownloader.bus.description.IBus;
import com.bachboss.mangadownloader.faces.AFacadeDefault;
import com.bachboss.mangadownloader.faces.SupportType;

/**
 *
 * @author Bach
 */
public class FacadeVeChai extends AFacadeDefault {

    public FacadeVeChai() {
    }

    @Override
    protected IBus getCurrentBUS() {
        return new VeChai();
    }

    @Override
    public SupportType getSupportType() {
        return SupportType.PartlySupport;
    }

    @Override
    public String getServerName() {
        return "VeChai";
    }
}
