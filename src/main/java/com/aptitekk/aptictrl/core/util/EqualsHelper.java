/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.util;

import java.lang.reflect.Array;

public class EqualsHelper {

    public static boolean areEquals(Object var1, Object var2) {
        if (var1 == var2)
            return true;

        if (var1 != null && var2 != null) {
            if (var1.equals(var2)) {
                return true;
            } else if (var1.getClass().isArray() && var2.getClass().isArray()) {
                for (int i = 0; i < Array.getLength(var1); i++) {
                    if (!(Array.get(var1, i).equals(Array.get(var2, i)))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static int calculateHashCode(Object... objects) {
        int hashCode = 0;
        for (Object object : objects) {
            hashCode += (object == null ? 0 : object.hashCode());
        }

        return hashCode * 31;
    }

}
