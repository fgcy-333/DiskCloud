package com.ruijian.disk.util;

import java.util.ArrayList;
import java.util.List;
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


    /**
     * 将用逗号隔开的字符串转为集合
     *
     * @param str
     * @return
     */
    public static List<Long> putStrToList(String str) throws Exception {
        if (isBlank(str)) {
            throw new Exception("将用逗号隔开的字符串转为集合,字符串为空");
        }
        final ArrayList<Long> longs = new ArrayList<>();
        final String[] splitArr = str.split(",");
        for (String s : splitArr) {
            if (StringUtil.isBlank(s)) {
                continue;
            }
            longs.add(Long.valueOf(s));
        }
        return longs;
    }
}
