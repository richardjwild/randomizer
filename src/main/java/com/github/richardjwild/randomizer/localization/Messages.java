package com.github.richardjwild.randomizer.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    public static String getMessage(String key, String... arguments) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        String s = messages.getString(key);
        int i = 0;
        for (String arg: arguments) {
            s = s.replace("{" + i++ + "}", arg);
        }
        return s;
    }
}
