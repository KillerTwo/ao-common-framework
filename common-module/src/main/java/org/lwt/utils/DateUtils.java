package org.lwt.utils;




import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author lwt2710@163.com
 * @date 2019-9-9 21:45
 */
public class DateUtils {

    /** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN); // 格式化
        String formatDate = localDateTime.format(formatter);
        return formatDate;
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param dateTime  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param dateTime  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if(dateTime != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return dateTime.format(formatter);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static LocalDateTime stringToDate(String strDate, String pattern) {
        if (StringUtils.isEmpty(strDate)){
            return null;
        }
        return LocalDateTime.parse(strDate,DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 对日期的【秒】进行加/减
     *
     * @param dateTime 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static LocalDateTime addDateSeconds(LocalDateTime dateTime, int seconds) {

        return dateTime.plusSeconds(seconds);
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param dateTime 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static LocalDateTime addDateMinutes(LocalDateTime dateTime, int minutes) {
        return dateTime.plusMinutes(minutes);
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param dateTime 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static LocalDateTime addDateHours(LocalDateTime dateTime, int hours) {
        return dateTime.plusHours(hours);
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param dateTime 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static LocalDateTime addDateDays(LocalDateTime dateTime, int days) {
        return dateTime.plusDays(days);
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param dateTime 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static LocalDateTime addDateWeeks(LocalDateTime dateTime, int weeks) {
        return dateTime.plusWeeks(weeks);
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param dateTime 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static LocalDateTime addDateMonths(LocalDateTime dateTime, int months) {
        return dateTime.plusMonths(months);
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param dateTime 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static LocalDateTime addDateYears(LocalDateTime dateTime, int years) {
        return dateTime.plusYears(years);
    }

    /**
     *  获取UTC时间
     * @return
     */
    public static long millis() {
        return Clock.systemUTC().millis();
    }

    /**
     *  beforeDate在afterDate前面则返回true否则返回false
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public boolean compare(LocalDateTime beforeDate, LocalDateTime afterDate){
        return beforeDate.isBefore(afterDate) ? true : false;
    }

    /**
     *  当前年有多少天
     * @return
     */
    public static int daysOfYear(){
        YearMonth yearMonth = YearMonth.now();
        return yearMonth.lengthOfYear();
    }

    /**
     *  当前月有多少天
     * @return
     */
    public static int daysOfMonth(){
        YearMonth yearMonth = YearMonth.now();
        return yearMonth.lengthOfMonth();
    }

    /**
     *  是否是闰年
     * @param localDate
     * @return
     */
    public static boolean isLeapYear(LocalDate localDate) {
        return localDate.isLeapYear();
    }

    /**
     *  两个日期相差天数
     * @param localDate1
     * @param localDate2
     * @return
     */
    public static long daysOfBetween(LocalDate localDate1, LocalDate localDate2) {
        return localDate2.toEpochDay() - localDate1.toEpochDay();
    }

    /**
     *  两个日期相隔月数
     * @param localDate1
     * @param localDate2
     * @return
     */
    public static long monthsOfBetween(LocalDate localDate1, LocalDate localDate2) {
        return localDate1.until(localDate2, ChronoUnit.MONTHS);
    }

    /**
     *  两个时间相隔的秒数
     * @param time1
     * @param time2
     * @return
     */
    public static long secondOfBetween(LocalDateTime time1, LocalDateTime time2) {
        Instant start = time1.atZone(ZoneId.systemDefault()).toInstant();
        Instant end = time2.atZone(ZoneId.systemDefault()).toInstant();
        Duration duration = Duration.between(start, end);
        return duration.getSeconds();
    }

    /**
     *  两个时间相隔的分
     * @param time1
     * @param time2
     * @return
     */
    public static long minutesOfBetween(LocalDateTime time1, LocalDateTime time2) {
        Instant start = time1.atZone(ZoneId.systemDefault()).toInstant();
        Instant end = time2.atZone(ZoneId.systemDefault()).toInstant();
        Duration duration = Duration.between(start, end);
        return duration.toMinutes();
    }

    /**
     *  两个时间相隔的小时
     * @param time1
     * @param time2
     * @return
     */
    public static long hoursOfBetween(LocalDateTime time1, LocalDateTime time2) {
        Instant start = time1.atZone(ZoneId.systemDefault()).toInstant();
        Instant end = time2.atZone(ZoneId.systemDefault()).toInstant();
        Duration duration = Duration.between(start, end);
        return duration.toHours();
    }

    /**
     *  两个时间相隔的天
     * @param time1
     * @param time2
     * @return
     */
    public static long daysOfBetween(LocalDateTime time1, LocalDateTime time2) {
        Instant start = time1.atZone(ZoneId.systemDefault()).toInstant();
        Instant end = time2.atZone(ZoneId.systemDefault()).toInstant();
        Duration duration = Duration.between(start, end);
        return duration.toDays();
    }

    /**
     *  将分钟数转换成天数
     * @param minites
     * @return
     */
    public static long minitesToDay(long minites) {
        return minites/60/24;
    }


    public static void main(String[] args) {
        LocalDate localDate = LocalDate.of(2019, 11, 11);
        LocalDate localDate1 = LocalDate.of(2011, 11, 11);
        Period period = Period.between(localDate1, localDate);
        System.err.println(period.getDays());
        System.err.println(period.getMonths());
        System.err.println(period.getYears());

        System.err.println(localDate.toEpochDay() - localDate1.toEpochDay());

        System.err.println(daysOfBetween(LocalDateTime.now(), LocalDateTime.of(2016,11,11,12,12,20)));
    }

}
