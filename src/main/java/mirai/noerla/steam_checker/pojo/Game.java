package mirai.noerla.steam_checker.pojo;

import mirai.noerla.steam_checker.JavaPluginMain;
import mirai.noerla.steam_checker.consts.CountryConsts;
import mirai.noerla.steam_checker.crawler.SteamCrawler;
import mirai.noerla.steam_checker.utils.ExchangeUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class Game {

    private String id;

    private String name;

    private Map<String, String> price;

    /**
     * success:货币符号+价格
     * fail:查询失败
     */
    private String lowestPrice;

    public String getName() {
        return name;
    }

    public Map<String, String> getPrice() {
        return price;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public Game(String inputName) {
        SteamCrawler steamCrawler = SteamCrawler.getInstance();
        final GameInfo gameInfo = steamCrawler.getInfoByInputName(inputName);
        this.id = gameInfo.getId();

//        String priceCN = steamCrawler.getPrice(id, CountryConsts.CN);
//        String priceHK = steamCrawler.getPrice(id, CountryConsts.HK);
//        String priceAR = steamCrawler.getPrice(id, CountryConsts.AR);
//        String priceRU = steamCrawler.getPrice(id, CountryConsts.RU);
//        String priceTRY = steamCrawler.getPrice(id, CountryConsts.TRY);
        FutureTask<String> priceCN = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.CN));
        FutureTask<String> priceHK = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.HK));
        FutureTask<String> priceAR = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.AR));
        FutureTask<String> priceRU = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.RU));
        FutureTask<String> priceTRY = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.TRY));
        Thread th1 = new Thread(priceCN);
        Thread th2 = new Thread(priceHK);
        Thread th3 = new Thread(priceAR);
        Thread th4 = new Thread(priceRU);
        Thread th5 = new Thread(priceTRY);
        th1.start();
        th2.start();
        th3.start();
        th4.start();
        th5.start();

        //如果国区找不到游戏就使用港区
        this.name = gameInfo.getName();

        Map<String, String> priceMap = new LinkedHashMap<>();
        try {
            priceMap.put(CountryConsts.CN, ExchangeUtil.getNumber(priceCN.get()));
            priceMap.put(CountryConsts.HK, ExchangeUtil.toCN(CountryConsts.HK, priceHK.get()));
            priceMap.put(CountryConsts.AR, ExchangeUtil.SpecialToCN(CountryConsts.AR, priceAR.get()));
            priceMap.put(CountryConsts.RU, ExchangeUtil.toCN(CountryConsts.RU, priceRU.get()));
            priceMap.put(CountryConsts.TRY, ExchangeUtil.SpecialToCN(CountryConsts.TRY, priceTRY.get()));
        } catch (Exception e) {
            JavaPluginMain.INSTANCE.getLogger().error(e.getMessage());
            throw new RuntimeException();
        }
        this.price = priceMap;

        this.lowestPrice = steamCrawler.getLowest(this.id);
    }
}
