package com.qsl.seckill;

import org.junit.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author DanielQSL
 * @date 2018/12/3
 */
public class EnumTest {

    @Test
    public void TestSeason() {
        LocalDateTime time = LocalDateTime.now();
         System.out.println(Clock.systemUTC());
//        LocalDate time1 = LocalDate.of(2017, 1, 12).format("yyyy/MM/dd");
//        System.out.println(time1);
        System.out.println(Season.Spring.getCode());
        System.out.println(Season.Spring.getName());


    }
}
