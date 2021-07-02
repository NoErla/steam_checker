package mirai.noerla.steam_checker.config;

import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static mirai.noerla.steam_checker.consts.GloalConsts.AR;
import static mirai.noerla.steam_checker.consts.GloalConsts.RU;

public class LoadConfig {

    public static String url = "";

    public static Map<String, BigDecimal> defaultExchange = new HashMap<>();


    public static void load(){
        String key = Config.INSTANCE.getExchangeKey();
        if (StrUtil.isNotEmpty(key)){
            url = "https://v6.exchangerate-api.com/v6/" + key + "/latest/CNY";
        }
        Config.Default aDefault = Config.INSTANCE.getDefault();
        defaultExchange.put(AR, new BigDecimal(aDefault.component1()));
        defaultExchange.put(RU, new BigDecimal(aDefault.component2()));
    }
}
