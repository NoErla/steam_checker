package mirai.noerla.plugin.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mirai.noerla.plugin.JavaPluginMain;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static mirai.noerla.plugin.consts.GloalConsts.*;

public class ExchangeCrawler {

    //自己申请key
    private static final String url = "https://v6.exchangerate-api.com/v6/key/latest/CNY";

    public static Map<String, BigDecimal> exchangeMap = new HashMap<>();
    {
        exchangeMap.put(AR, new BigDecimal(14));
        exchangeMap.put(RU, new BigDecimal(11));
    }

    public static void getExchange(){
        try{
            Element body = Jsoup.connect(url).ignoreContentType(true).get().body();
            JSONObject json = JSON.parseObject(body.text());
            Optional.ofNullable(json)
                    .map(j -> j.getJSONObject("conversion_rates"))
                    .ifPresent(j -> {
                        exchangeMap.put(AR, (BigDecimal) j.get("ARS"));
                        exchangeMap.put(RU, (BigDecimal)j.get("RUB"));
                    });
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error("汇率查询出错，使用默认汇率");
            exchangeMap.put(AR, new BigDecimal(14));
            exchangeMap.put(RU, new BigDecimal(11));
        }
    }

    public static Double getExchange(String country){
        return exchangeMap.get(country).doubleValue();
    }
}
