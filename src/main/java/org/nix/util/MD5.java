package org.nix.util;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Date;

/**
 * 该加密方法和前端加密方法一直，可以互相验证加密结果
 * @see /view/app/user/js/md5.js 前端加密代码类
 * Create by zhangpe0312@qq.com on 2017/12/28.
 */
public class MD5 {

    private MD5() {
    }

    /**
     * 16进制的字符数组
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};
    //加密后输出编码格式
    private static String encoding = "utf-8";
    //输出后是否大写
    private static Boolean uppercase = false;

    /**
     * 加密
     * @param object
     * @return
     */
    public static String encryption(String object) {
       return MD5Encode(object,encoding,uppercase);
    }

    /**
     * @param source    需要加密的原字符串
     * @param encoding  指定编码类型
     * @param uppercase 是否转为大写字符串
     * @return
     */
    public static String MD5Encode(String source, String encoding, boolean uppercase) {
        String result = null;
        try {
            result = source;
            // 获得MD5摘要对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要信息
            messageDigest.update(result.getBytes(encoding));
            // messageDigest.digest()获得16位长度
            result = byteArrayToHexString(messageDigest.digest());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uppercase ? result.toUpperCase() : result;
    }

    /**
     * 转换字节数组为16进制字符串
     *
     * @param bytes 字节数组
     * @return
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte tem : bytes) {
            stringBuilder.append(byteToHexString(tem));
        }
        return stringBuilder.toString();
    }

    /**
     * 转换byte到16进制
     *
     * @param b 要转换的byte
     * @return 16进制对应的字符
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Boolean getUppercase() {
        return uppercase;
    }

    public void setUppercase(Boolean uppercase) {
        this.uppercase = uppercase;
    }

    public static void main(String[] args) {
        String str = "";
        str = encryption(str);
        System.out.println(str);
        System.out.println(str.length());


        Date date = new Date();
        System.out.println(date.getTime());
    }

}
