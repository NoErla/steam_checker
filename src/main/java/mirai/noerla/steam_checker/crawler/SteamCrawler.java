package mirai.noerla.steam_checker.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import mirai.noerla.steam_checker.JavaPluginMain;
import mirai.noerla.steam_checker.consts.GloalConsts;
import mirai.noerla.steam_checker.pojo.GameInfo;
import mirai.noerla.steam_checker.utils.JsonAlysisUtil;

import java.util.Optional;

import static mirai.noerla.steam_checker.consts.GloalConsts.*;

public enum SteamCrawler {

    INSTANCE;

    public String getPrice(String id, String country) {
        try {
            String url = INFO_LINK + id + FIX + country;
            String body = SSLHelper.getConnection(url)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/json")
                    .execute()
                    .body();
            final JSONObject data = JSON.parseObject(body);
            return JsonAlysisUtil.getNowPrice(data, id);
        } catch (Exception e) {
            JavaPluginMain.INSTANCE.getLogger().error("第三方价格查询接口调用异常:"+id+","+country+","+e.getMessage());
            return PRICE_NOT_FOUND;
        }
    }

    public GameInfo getInfoByInputName(String inputName){
        try{
            String url = SEARCH_ID + inputName;
            String body = SSLHelper.getConnection(url)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/json")
                    .execute()
                    .body();
            final JSONObject data = JSON.parseObject(body);
            final JSONArray games = data.getJSONObject("result").getJSONArray("items");
            //寻找第一个类型为游戏的信息
            for (int i=0,len=games.size();i<len;i++){
                final JSONObject game = games.getJSONObject(i);
                if (!"game".equals(game.get("type"))){
                    continue;
                }
                GameInfo result = new GameInfo();
                result.setId(game.getJSONObject("info").get("steam_appid").toString());
                result.setName(game.getJSONObject("info").getString("name"));
                result.setCurrentPrice(game.getJSONObject("info").getJSONObject("price").getString("current"));
                final String lowestPrice = Optional.ofNullable(game.getJSONObject("info").getJSONObject("price").getString("lowest_price_raw")).orElse("无");
                result.setLowestPrice(lowestPrice);
                return result;
            }
            throw  new RuntimeException("未找到游戏");
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error("游戏名{"+inputName+"}查询异常："+e.getMessage());
            throw new RuntimeException();
        }
    }

    public String getLowest(String appId){
        try{
            String url = HISTORY_LINK + appId;
            String body = SSLHelper.getConnection(url)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/json")
                    .execute()
                    .body();
            final JSONObject data = JSON.parseObject(body);
            final Object price = data.getJSONObject("result").getJSONObject("price").get("lowest_price");
            if (price == null) return "无";
            return price.toString();
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error("史低查询异常"+e.getMessage());
            return PRICE_NOT_FOUND;
        }
    }
}
