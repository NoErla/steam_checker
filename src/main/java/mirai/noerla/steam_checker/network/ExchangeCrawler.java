package mirai.noerla.steam_checker.network;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mirai.noerla.steam_checker.JavaPluginMain;
import mirai.noerla.steam_checker.config.Config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static mirai.noerla.steam_checker.consts.GloalConsts.*;

public class ExchangeCrawler {

    private String key = Config.INSTANCE.getExchangeKey();

    private String suffix = "/latest/CNY";

    private String prefix = "https://v6.exchangerate-api.com/v6/";


    public Map<String, BigDecimal> exchangeMap = new HashMap<>();

    private static ExchangeCrawler instance = new ExchangeCrawler();

    private ExchangeCrawler(){}

    public static ExchangeCrawler getInstance(){
        return instance;
    }

    public void getExchange(){
        try{
            if (StrUtil.isEmpty(key)){
                getDefaultExchange();
            } else {
                String url = prefix + key + suffix;
                Element body = Jsoup.connect(url).ignoreContentType(true).get().body();
                JSONObject json = JSON.parseObject(body.text());
                Optional.ofNullable(json)
                        .map(j -> j.getJSONObject("conversion_rates"))
                        .ifPresent(j -> {
                            exchangeMap.put(AR, (BigDecimal) j.get("ARS"));
                            exchangeMap.put(RU, (BigDecimal)j.get("RUB"));
                        });
            }
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error("汇率查询出错，使用默认汇率");
            getDefaultExchange();
        }
    }

    public void getDefaultExchange(){
        Config.Default aDefault = Config.INSTANCE.getDefault();
        exchangeMap.put(AR, new BigDecimal(aDefault.component1()));
        exchangeMap.put(RU, new BigDecimal(aDefault.component2()));
    }

    public Double getExchange(String country){
        return exchangeMap.get(country).doubleValue();
    }
}
