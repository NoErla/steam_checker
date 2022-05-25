package mirai.noerla.steam_checker.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import mirai.noerla.steam_checker.JavaPluginMain;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.Optional;
import java.util.stream.IntStream;

import static mirai.noerla.steam_checker.consts.GloalConsts.*;

public class SteamCrawler {

    private static final SteamCrawler instance = new SteamCrawler();

    private SteamCrawler(){}

    public static SteamCrawler getInstance(){
        return instance;
    }

    public JSONObject getJson(String id, String country) {
        try {
            String url = INFO_LINK + id + FIX + country;
            String body = SSLHelper.getConnection(url)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/json")
                    .execute()
                    .body();
            return JSON.parseObject(body);
        } catch (Exception e) {
            JavaPluginMain.INSTANCE.getLogger().error("第三方价格查询接口调用异常");
            throw new RuntimeException();
        }
    }

    public String getIdByInput(String inputName){
        try{
            String url = SEARCH_ID + inputName;
            String body = SSLHelper.getConnection(url)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/json")
                    .execute()
                    .body();
            final JSONObject data = JSON.parseObject(body);
            final JSONArray games = data.getJSONObject("result").getJSONArray("items");
            for (int i=0,len=games.size();i<len;i++){
                final JSONObject game = games.getJSONObject(i);
                if (!"game".equals(game.get("type"))){
                    continue;
                }
                return game.getJSONObject("info").get("steam_appid").toString();
            }
            throw  new RuntimeException("未找到游戏");
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error(e.getMessage());
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
            JavaPluginMain.INSTANCE.getLogger().error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
