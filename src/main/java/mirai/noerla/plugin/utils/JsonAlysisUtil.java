package mirai.noerla.plugin.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.util.Optional;

import static mirai.noerla.plugin.consts.GloalConsts.*;

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

    public static String getNowPrice(String gameId, String country){
        return Optional.ofNullable(getJson(gameId, country))
                .map(j -> j.getJSONObject(gameId))
                .map(j -> j.getJSONObject("data"))
                .map(j -> j.getJSONObject("price_overview"))
                .map(j -> (String)j.get("final_formatted"))
                .orElse("免费游玩");
    }

    public static JSONObject getJson(String id, String country) {
        try {
            String url = INFO_LINK + id + FIX + country;
            String body = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/json")
                    .execute()
                    .body();
            return JSON.parseObject(body);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getIdByInput(String inputName){
        try{
            String url = SEARCH_LINK + inputName;
            Document document
                    = Jsoup.connect(url)
                    .header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                    .get();
            JXDocument jxd = JXDocument.create(document);
            JXNode jxn = jxd.selNOne("//div[@id='search_resultsRows']/a[1]/@data-ds-appid");
            return jxn.asString();
        } catch (Exception e){
            return null;
        }

    }
}
