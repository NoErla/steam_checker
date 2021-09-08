package mirai.noerla.steam_checker.utils;

import mirai.noerla.steam_checker.network.ExchangeCrawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mirai.noerla.steam_checker.consts.GloalConsts.*;

//TODO 更为精确的汇率转换
public class ExchangeUtil {

    public static String RUtoCN(String money){
        if ("免费游玩".equals(money))
            return money;
        ExchangeCrawler exchangeCrawler = ExchangeCrawler.getInstance();
        Integer v = (int)Math.floor(Integer.parseInt(getNumberRU(money)) / exchangeCrawler.getDailyExchangeByCountry(RU));
        return String.valueOf(v);
    }

    public static String ARtoCN(String money){
        if ("免费游玩".equals(money))
            return money;
        String[] prices = money.split(",");
        ExchangeCrawler exchangeCrawler = ExchangeCrawler.getInstance();
        Integer v = (int)Math.floor(Integer.parseInt(getNumberAR(prices[0])) / exchangeCrawler.getDailyExchangeByCountry(AR));//直接省略逗号后面的
        return String.valueOf(v);
    }


    public static String getNumber(String str){
        if ("免费游玩".equals(str))
            return str;
        Pattern pattern = Pattern.compile("[^0-9.]");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }

    public static String getNumberRU(String str){
        return str.substring(0, str.length()-5);
    }

    public static String getNumberAR(String str){
        if ("免费游玩".equals(str))
            return str;
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }
}
