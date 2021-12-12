package mirai.noerla.steam_checker.pojo;

import com.alibaba.fastjson.JSONObject;
import mirai.noerla.steam_checker.consts.CountryConsts;
import mirai.noerla.steam_checker.crawler.SteamCrawler;
import mirai.noerla.steam_checker.utils.ExchangeUtil;
import mirai.noerla.steam_checker.utils.JsonAlysisUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static mirai.noerla.steam_checker.utils.JsonAlysisUtil.getName;
import static mirai.noerla.steam_checker.utils.JsonAlysisUtil.getNowPrice;

public class Game {

    private String id;

    private String name;

    private Map<String, String> price;

    public String getName() {
        return name;
    }

    public Map<String, String> getPrice() {
        return price;
    }

    public Game(String inputName) {
        SteamCrawler steamCrawler = SteamCrawler.getInstance();
        this.id = steamCrawler.getIdByInput(inputName);

        JSONObject gameCNJson = steamCrawler.getJson(id, CountryConsts.CN);
        JSONObject gameHKJson = steamCrawler.getJson(id, CountryConsts.HK);
        JSONObject gameARJson = steamCrawler.getJson(id, CountryConsts.AR);
        JSONObject gameRUJson = steamCrawler.getJson(id, CountryConsts.RU);
        JSONObject gameTRYJson = steamCrawler.getJson(id, CountryConsts.TRY);

        //如果国区找不到游戏就使用港区
        this.name = JsonAlysisUtil.getName(gameCNJson, id)
                .orElse(JsonAlysisUtil.getName(gameHKJson, id).get());

        Map<String, String> priceMap = new LinkedHashMap<>();
        priceMap.put(CountryConsts.CN, ExchangeUtil.getNumber(getNowPrice(gameCNJson, id)));
        priceMap.put(CountryConsts.HK, ExchangeUtil.toCN(CountryConsts.HK, getNowPrice(gameHKJson, id)));
        priceMap.put(CountryConsts.AR, ExchangeUtil.toCN(CountryConsts.AR, getNowPrice(gameARJson, id)));
        priceMap.put(CountryConsts.RU, ExchangeUtil.toCN(CountryConsts.RU, getNowPrice(gameRUJson, id)));
        priceMap.put(CountryConsts.TRY, ExchangeUtil.toCN(CountryConsts.TRY, getNowPrice(gameTRYJson, id)));
        this.price = priceMap;
    }
}
