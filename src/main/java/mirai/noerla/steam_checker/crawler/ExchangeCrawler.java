package mirai.noerla.steam_checker.crawler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mirai.noerla.steam_checker.JavaPluginMain;
import mirai.noerla.steam_checker.config.LoadConfig;
import mirai.noerla.steam_checker.consts.CountryConsts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ExchangeCrawler {

    INSTANCE;

    private Map<String, BigDecimal> todayExchange = new HashMap<>();

    /**
     * 更新汇率
     */
    public void updateExchange(){
        try{
            if (StrUtil.isEmpty(LoadConfig.url)){
                JavaPluginMain.INSTANCE.getLogger().info("使用默认汇率");
                todayExchange = LoadConfig.defaultExchange;
            } else {
                JavaPluginMain.INSTANCE.getLogger().info("使用第三方汇率接口");
                Element body = Jsoup.connect(LoadConfig.url).ignoreContentType(true).get().body();
                JSONObject data = JSON.parseObject(body.text());
                Optional.ofNullable(data)
                        .map(j -> j.getJSONObject("conversion_rates"))
                        .ifPresent(j -> {
                            todayExchange.put(CountryConsts.AR, (BigDecimal) j.get("ARS"));
                            todayExchange.put(CountryConsts.RU, (BigDecimal)j.get("RUB"));
                            todayExchange.put(CountryConsts.HK, (BigDecimal)j.get("HKD"));
                            todayExchange.put(CountryConsts.TRY, (BigDecimal)j.get("TRY"));
                        });
            }
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error("汇率查询出错，使用默认汇率");
            todayExchange = LoadConfig.defaultExchange;
        }
    }

    /**
     * 获取货币对RMB的当前汇率
     * @param country
     * @return
     */
    public Double getTodayExchangeByCountry(String country){
        if (!todayExchange.containsKey(country)){
            JavaPluginMain.INSTANCE.getLogger().error("无此国家：" + country);
            throw new IllegalArgumentException();
        }
        return todayExchange.get(country).doubleValue();
    }
}
