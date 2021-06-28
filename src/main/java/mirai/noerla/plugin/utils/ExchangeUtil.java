package mirai.noerla.plugin.utils;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO 更为精确的汇率转换
public class ExchangeUtil {

    public static String RUtoCN(String money){
        if ("免费游玩".equals(money))
            return money;
        Integer v = (int)Math.floor(Integer.parseInt(getNumber(money)) * 0.09);
        return String.valueOf(v);
    }

    public static String ARtoCN(String money){
        if ("免费游玩".equals(money))
            return money;
        String[] prices = money.split(",");
        Integer v = (int)Math.floor(Integer.parseInt(getNumber(prices[0])) * 0.07);//直接省略逗号后面的
        return String.valueOf(v);
    }


    public static String getNumber(String str){
        if ("免费游玩".equals(str))
            return str;
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }
}
