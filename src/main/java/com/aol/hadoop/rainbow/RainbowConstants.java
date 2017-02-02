package com.aol.hadoop.rainbow;

import java.nio.charset.Charset;

/**
 *
 */
public class RainbowConstants {

    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final int defaultMinLength = 1;
    public static final int defaultMaxLength = 5;
    public static final int defaultHashDepth = 1;
//    public static final int defaultMappers = 500;
//    public static final int defaultReducers = 100;
    public static final int defaultMappers = 12;
    public static final int defaultReducers = 3;
    public static final String defaultAlgorithm = "MD5";
//    public static final String defaultCharset
//            = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789!@#$%^&*(){}[]-=_+`~;:/?,.<>'\\\"";
    public static final String defaultCharset
            = "abcdefghijklmnopqrstuvwxyz0123456789";

    private RainbowConstants() {
    }

}
