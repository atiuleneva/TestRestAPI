package org.atiuleneva.utils;

import java.util.regex.Pattern;

public class RegExpSet {
    public static final Pattern ImageHash = Pattern.compile("^[a-zA-Z0-9]{7}$");
    public static final Pattern ImageDeleteHash = Pattern.compile("^[a-zA-Z0-9]{15}$");

}
