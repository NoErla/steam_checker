package mirai.noerla.steam_checker.network;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mirai.noerla.steam_checker.JavaPluginMain;
import mirai.noerla.steam_checker.config.Config;
import mirai.noerla.steam_checker.config.LoadConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static mirai.noerla.steam_checker.consts.GloalConsts.*;

public class ExchangeCrawler {

    public Map<String, BigDecimal> dailyExchange;

    private static ExchangeCrawler instance = new ExchangeCrawler();

    private ExchangeCrawler(){}

    public static ExchangeCrawler getInstance(){
        return instance;
    }

    /**
     * 每日更新汇率
     */
    public void getExchangeDaily(){
        try{
            if (StrUtil.isEmpty(LoadConfig.url)){
                getDefaultExchange();
            } else {
                Element body = Jsoup.connect(LoadConfig.url).ignoreContentType(true).get().body();
                JSONObject json = JSON.parseObject(body.text());
                Optional.ofNullable(json)
                        .map(j -> j.getJSONObject("conversion_rates"))
                        .ifPresent(j -> {
                            dailyExchange.put(AR, (BigDecimal) j.get("ARS"));
                            dailyExchange.put(RU, (BigDecimal)j.get("RUB"));
                        });
            }
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error("汇率查询出错，使用默认汇率");
            getDefaultExchange();
        }
    }

    public void getDefaultExchange(){
        dailyExchange = LoadConfig.defaultExchange;
    }

    public Double getDailyExchangeByCountry(String country){
        if (!dailyExchange.containsKey(country)){
            JavaPluginMain.INSTANCE.getLogger().error("无此国家：" + country);
            throw new IllegalArgumentException();
        }
        return dailyExchange.get(country).doubleValue();
    }
}
