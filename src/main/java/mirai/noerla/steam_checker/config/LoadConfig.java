package mirai.noerla.steam_checker.config;

import cn.hutool.core.util.StrUtil;
import mirai.noerla.steam_checker.consts.CountryConsts;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class LoadConfig {

    public static String url = "";

    public static Map<String, BigDecimal> defaultExchange = new HashMap<>();


    public static void load(){
        String key = Config.INSTANCE.getExchangeKey();
        if (StrUtil.isNotEmpty(key)){
            url = "https://v6.exchangerate-api.com/v6/" + key + "/latest/CNY";
        }
        Config.Default aDefault = Config.INSTANCE.getDefault();
        defaultExchange.put(CountryConsts.HK, BigDecimal.valueOf(aDefault.component1()));
        defaultExchange.put(CountryConsts.AR, BigDecimal.valueOf(aDefault.component2()));
        defaultExchange.put(CountryConsts.RU, BigDecimal.valueOf(aDefault.component3()));
        defaultExchange.put(CountryConsts.TRY, BigDecimal.valueOf(aDefault.component4()));
    }
}
