package com.ruijian.disk.util;

public class StringUtil {
    public static boolean inNumber(String str) {
        return str != null && str.matches("[0-9]+");
    }

}
