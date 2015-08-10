/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.faces;

import com.bachboss.mangadownloader.bus.description.IBus;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bach
 */
public abstract class AFacadeMangaServer implements IFacadeMangaServer {

    @Override
    public AFacadeMangaServer clone() {
        java.lang.reflect.Constructor[] aC = this.getClass().getConstructors();
        Object returnValue = null;
        for (Constructor constructor : aC) {
            if (constructor.getParameterTypes().length == 0) {
                try {
                    returnValue = (IFacadeMangaServer) constructor.newInstance((Object[]) null);
                } catch (Exception ex) {
                    Logger.getLogger(AFacadeMangaServer.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (returnValue != null) {
                    return (AFacadeMangaServer) returnValue;
                }
            }
        }
        return null;
    }

    protected abstract IBus getCurrentBUS();
}
