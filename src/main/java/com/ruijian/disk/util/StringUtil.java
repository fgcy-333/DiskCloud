package com.ruijian.disk.util;

import java.util.Random;
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
    public static String getUniqueStr(int num) throws Exception {
        final UUID uuid = UUID.randomUUID();
        final String uu = uuid.toString();
        final String time = String.valueOf(System.currentTimeMillis());
        String s = uu + time;
        int length = s.length();
        if (length < num) {
            throw new Exception("所需位数超出限制");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(length);
            sb.append(s.charAt(nextInt));
        }
        return sb.toString();
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
