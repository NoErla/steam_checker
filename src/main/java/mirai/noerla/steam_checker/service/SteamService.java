package mirai.noerla.steam_checker.service;

import com.alibaba.fastjson.JSONObject;
import mirai.noerla.steam_checker.network.SteamCrawler;
import mirai.noerla.steam_checker.pojo.Game;
import mirai.noerla.steam_checker.utils.ExchangeUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static mirai.noerla.steam_checker.consts.GloalConsts.*;
import static mirai.noerla.steam_checker.utils.JsonAlysisUtil.*;

public class SteamService {

    private static SteamService instance = new SteamService();

    private SteamService(){}

    public static SteamService getInstance(){
        return instance;
    }

    private final SteamCrawler steamCrawler = SteamCrawler.getInstance();

    public Game getGameByInput(String inputName){
        Game game = new Game();
        String id = steamCrawler.getIdByInput(inputName);

        if (id == null)
            throw new RuntimeException();

        game.setId(id);

        JSONObject gameCNJson = steamCrawler.getJson(id, CN);
        JSONObject gameARJson = steamCrawler.getJson(id, AR);
        JSONObject gameRUJson = steamCrawler.getJson(id, RU);

        game.setName(getName(gameCNJson, id));

        Map<String, String> priceMap = new LinkedHashMap<>();
        priceMap.put(CN, ExchangeUtil.getNumber(getNowPrice(gameCNJson, id)));
        priceMap.put(AR, ExchangeUtil.ARtoCN(getNowPrice(gameARJson, id)));
        priceMap.put(RU, ExchangeUtil.RUtoCN(getNowPrice(gameRUJson, id)));

        game.setPrice(priceMap);
        return game;
    }
}