/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.ult;

import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;

/**
 *
 * @author Bach
 */
public class NumberUtilities {

    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    private static final NamedPattern PATTERN_NUMBER = NamedPattern.compile("(\\d+)");

    public static int getNumberInt(String text) throws NumberFormatException {
        NamedMatcher m = PATTERN_NUMBER.matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            throw new NumberFormatException("Can not convert " + text + " to number");
        }
    }

    public static float parseNumberFloat(String text) {
        return Float.parseFloat(text);
    }
}
