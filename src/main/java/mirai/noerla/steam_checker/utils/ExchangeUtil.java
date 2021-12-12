package mirai.noerla.steam_checker.utils;

import mirai.noerla.steam_checker.consts.CountryConsts;
import mirai.noerla.steam_checker.crawler.ExchangeCrawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mirai.noerla.steam_checker.consts.GloalConsts.*;

public class ExchangeUtil {

    public static String toCN(String country, String money){
        if (PRICE_NOT_FOUND.equals(money))
            return money;
        //适应阿根廷比索
        String[] prices = money.split(",");
        ExchangeCrawler exchangeCrawler = ExchangeCrawler.getInstance();
        double result = Math.floor(Double.parseDouble(getNumber(prices[0])) / exchangeCrawler.getDailyExchangeByCountry(country));
        return Double.toString(result);
    }

    public static String getNumber(String str){
        if (PRICE_NOT_FOUND.equals(str))
            return str;
        Pattern pattern = Pattern.compile("[^0-9.]");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }

}
