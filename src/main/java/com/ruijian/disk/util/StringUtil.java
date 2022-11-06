package com.ruijian.disk.util;

import java.util.UUID;

public class StringUtil {

    public static boolean inNumber(String str) {
        return str != null && str.matches("[0-9]+");
    }

    /**
     * 获取唯一字符串
     *
     * @return
     */
    public static String getUniqueStr() {
        final UUID uuid = UUID.randomUUID();
        final String uu = uuid.toString().substring(10);
        final String time = String.valueOf(System.currentTimeMillis()).substring(3);
        return uu + time;
    }

    public static boolean isBlank(String name) {
        if (name == null || "".equals(name) || "undefined".equals(name) || " ".equals(name) || "null".equals(name)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotBlank(String name) {
        return !isBlank(name);
    }

}
