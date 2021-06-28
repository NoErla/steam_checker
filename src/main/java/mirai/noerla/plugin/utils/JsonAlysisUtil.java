package mirai.noerla.plugin.utils;


import com.alibaba.fastjson.JSONObject;

import java.util.Optional;

public class JsonAlysisUtil {

    public static String getName(JSONObject jsonObject, String gameId){
        return jsonObject
                .getJSONObject(gameId)
                .getJSONObject("data")
                .get("name")
                .toString();
    }

    public static String getNowPrice(JSONObject jsonObject, String gameId){
        return Optional.ofNullable(jsonObject)
                .map(j -> j.getJSONObject(gameId))
                .map(j -> j.getJSONObject("data"))
                .map(j -> j.getJSONObject("price_overview"))
                .map(j -> (String)j.get("final_formatted"))
                .orElse("免费游玩");
    }
}
