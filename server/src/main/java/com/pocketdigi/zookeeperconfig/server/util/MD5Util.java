package com.pocketdigi.zookeeperconfig.server.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import org.apache.commons.lang3.StringUtils;

/**
 * @author fhp
 * @date 2019-08-10
 */
public class MD5Util {
    /**
     * 返回32位md5
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String digestStr=new BigInteger(1, md.digest()).toString(16);
            if(digestStr.length()<32) {
                StringBuilder stringBuilder=new StringBuilder(32);
                int zeroLength=32-digestStr.length();
                for(int i=0;i<zeroLength;i++) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(digestStr);
                return stringBuilder.toString();
            }
            return digestStr;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回16位md5
     * @param str
     * @return
     */
    public static String getMD516(String str) {
        String md5 = getMD5(str);
        if(StringUtils.isNotBlank(md5)) {
            return md5.substring(8,24);
        }
        return null;
    }
}
