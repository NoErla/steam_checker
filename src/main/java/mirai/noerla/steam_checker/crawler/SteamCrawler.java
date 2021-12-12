package mirai.noerla.steam_checker.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mirai.noerla.steam_checker.JavaPluginMain;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

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
            String body = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/json")
                    .execute()
                    .body();
            return JSON.parseObject(body);
        } catch (Exception e) {
            JavaPluginMain.INSTANCE.getLogger().error("第三方接口调用异常");
            throw new RuntimeException();
        }
    }

    public String getIdByInput(String inputName){
        try{
            String url = SEARCH_LINK + inputName + "&cc=HK";
            Document document
                    = Jsoup.connect(url)
                    .header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                    .get();
            JXDocument jxd = JXDocument.create(document);
            JXNode jxn = jxd.selNOne("//div[@id='search_resultsRows']/a[1]/@data-ds-appid");
            return jxn.asString();
        } catch (Exception e){
            JavaPluginMain.INSTANCE.getLogger().error("steam接口调用异常");
            throw new RuntimeException();
        }
    }
}