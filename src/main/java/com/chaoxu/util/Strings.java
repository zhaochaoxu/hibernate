package com.chaoxu.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public class Strings {

    public static String toUTF8(String str) {
        if (StringUtils.isNotEmpty(str)) {
            try {
                return new String(str.getBytes("ISO8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("字符串转码异常");
            }
        }
        return "";
    }

    public static String toPinyin(String str) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);//将文字转换成小写
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//设置拼音音调（不要）
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);//用 V 表示 U

        try {
            return PinyinHelper.toHanyuPinyinString(str,outputFormat,"");
        } catch (BadHanyuPinyinOutputFormatCombination ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
