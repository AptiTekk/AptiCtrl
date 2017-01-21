/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.util;

import java.time.format.DateTimeFormatter;

public class TimeCommons {

    public static final DateTimeFormatter STANDARD_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
    public static final DateTimeFormatter FRIENDLY_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy h:mm a");

}
