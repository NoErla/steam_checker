package mirai.noerla.steam_checker.utils;

import mirai.noerla.steam_checker.crawler.ExchangeCrawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mirai.noerla.steam_checker.consts.GloalConsts.PRICE_NOT_FOUND;

public class ExchangeUtil {

    public static String toCN(String country, String money){
        if (PRICE_NOT_FOUND.equals(money))
            return money;
        ExchangeCrawler exchangeCrawler = ExchangeCrawler.getInstance();
        Double result = Math.floor(Double.parseDouble(getNumber(money)) / exchangeCrawler.getDailyExchangeByCountry(country));
        return Integer.toString(result.intValue());
    }

    public static String SpecialToCN(String country, String money){
        if (PRICE_NOT_FOUND.equals(money))
            return money;
        //适应阿根廷比索
        money = money.split(",")[0];
        money = money.replace(".","");
        ExchangeCrawler exchangeCrawler = ExchangeCrawler.getInstance();
        Double result = Math.floor(Double.parseDouble(getNumber(money)) / exchangeCrawler.getDailyExchangeByCountry(country));
        return Integer.toString(result.intValue());
    }

    public static String getNumber(String str){
        if (PRICE_NOT_FOUND.equals(str))
            return str;
        Pattern pattern = Pattern.compile("[^0-9.]");
        Matcher matcher = pattern.matcher(str);
        final Double value = Double.parseDouble(matcher.replaceAll(""));
        return String.valueOf(value.intValue());
    }
}
