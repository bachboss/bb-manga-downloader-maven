/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.ult;

import java.util.UUID;

/**
 *
 * @author Bach
 */
public class DatabaseUtilities {

    public static int getRandomId() {
        return UUID.randomUUID().hashCode();
    }
}
