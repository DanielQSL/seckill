package com.qsl.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DanielQSL
 * @date 2018-10-08
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern=Pattern.compile("1\\d{10}");

    public static boolean isMobile(String mobile){
        if (StringUtils.isEmpty(mobile)){
            return false;
        }

        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("13122928891"));
        System.out.println(isMobile("13122928"));

    }
}
