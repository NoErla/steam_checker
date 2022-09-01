package mirai.noerla.steam_checker.utils;

import mirai.noerla.steam_checker.crawler.ExchangeCrawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mirai.noerla.steam_checker.consts.GloalConsts.PRICE_NOT_FOUND;

public class ExchangeUtil {

    /**
     * 默认的货币转换
     * @param country
     * @param money
     * @return
     */
    public static String toCN(String country, String money){
        if (PRICE_NOT_FOUND.equals(money))
            return money;
        ExchangeCrawler exchangeCrawler = ExchangeCrawler.INSTANCE;
        Double result = Math.floor(Double.parseDouble(getNumber(money)) / exchangeCrawler.getTodayExchangeByCountry(country));
        return Integer.toString(result.intValue());
    }

    /**
     * 一些特别的货币转换
     * todo 合并为一种转换方法
     * @param country
     * @param money
     * @return
     */
    public static String specialToCN(String country, String money){
        if (PRICE_NOT_FOUND.equals(money))
            return money;
        //适应阿根廷比索
        money = money.split(",")[0];
        money = money.replace(".","");
        ExchangeCrawler exchangeCrawler = ExchangeCrawler.INSTANCE;
        Double result = Math.floor(Double.parseDouble(getNumber(money)) / exchangeCrawler.getTodayExchangeByCountry(country));
        return Integer.toString(result.intValue());
    }

    /**
     * str只保留数字和小数点
     * fixme 有的货币带,
     * @param str
     * @return
     */
    public static String getNumber(String str){
        if (PRICE_NOT_FOUND.equals(str))
            return str;
        Pattern pattern = Pattern.compile("[^0-9.]");
        Matcher matcher = pattern.matcher(str);
        final Double value = Double.parseDouble(matcher.replaceAll(""));
        return String.valueOf(value.intValue());
    }
}
