package mirai.noerla.steam_checker.utils;


import com.alibaba.fastjson.JSONObject;

import java.util.Optional;

import static mirai.noerla.steam_checker.consts.GloalConsts.PRICE_NOT_FOUND;

public class JsonAlysisUtil {

    public static Optional<String> getName(JSONObject jsonObject, String gameId){
        return Optional.ofNullable(jsonObject)
                .map(j -> j.getJSONObject(gameId))
                .map(j -> j.getJSONObject("data"))
                .map(j -> j.get("name"))
                .map(Object::toString);
    }

    public static String getNowPrice(JSONObject jsonObject, String gameId){
        return Optional.ofNullable(jsonObject)
                .map(j -> j.getJSONObject(gameId))
                .map(j -> j.getJSONObject("data"))
                .map(j -> j.getJSONObject("price_overview"))
                .map(j -> (String)j.get("final_formatted"))
                .orElse(PRICE_NOT_FOUND);
    }
}
