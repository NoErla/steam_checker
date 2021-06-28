package mirai.noerla.plugin.service;

import com.alibaba.fastjson.JSONObject;
import mirai.noerla.plugin.pojo.Game;
import mirai.noerla.plugin.utils.ExchangeUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static mirai.noerla.plugin.consts.GloalConsts.*;
import static mirai.noerla.plugin.utils.JsonAlysisUtil.*;

public class SteamService {

    private static SteamService instance = new SteamService();

    private SteamService(){}

    public static SteamService getInstance(){
        return instance;
    }

    public Game getGameByInput(String inputName){
        Game game = new Game();
        String id = getIdByInput(inputName);
        if (id == null)
            throw new RuntimeException("找不到游戏, 请尝试英文全名");

        game.setId(id);

        JSONObject gameCNJson = getJson(id, CHINA);

        game.setName(getName(gameCNJson, id));

        Map<String, String> priceMap = new LinkedHashMap<>();
        priceMap.put(CHINA, ExchangeUtil.getNumber(getNowPrice(gameCNJson, id)));
        priceMap.put(AR, ExchangeUtil.ARtoCN(getNowPrice(id, AR)));
        priceMap.put(RU, ExchangeUtil.RUtoCN(getNowPrice(id, RU)));

        game.setPrice(priceMap);
        return game;
    }
}