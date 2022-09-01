package mirai.noerla.steam_checker.pojo;

import cn.hutool.core.thread.ExecutorBuilder;
import mirai.noerla.steam_checker.JavaPluginMain;
import mirai.noerla.steam_checker.consts.CountryConsts;
import mirai.noerla.steam_checker.crawler.SteamCrawler;
import mirai.noerla.steam_checker.utils.ExchangeUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

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
        SteamCrawler steamCrawler = SteamCrawler.INSTANCE;
        //获取游戏的id与名字
        final GameInfo gameInfo = steamCrawler.getInfoByInputName(inputName);
        this.id = gameInfo.getId();
        this.name = gameInfo.getName();

        FutureTask<String> priceCN = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.CN));
        FutureTask<String> priceHK = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.HK));
        FutureTask<String> priceAR = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.AR));
        FutureTask<String> priceRU = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.RU));
        FutureTask<String> priceTRY = new FutureTask<>(() -> steamCrawler.getPrice(id, CountryConsts.TRY));
        //创建线程池
        ExecutorService executor = ExecutorBuilder.create()
                .setCorePoolSize(5)
                .setMaxPoolSize(20)
                .setWorkQueue(new LinkedBlockingQueue<>(100))
                .build();
        executor.submit(priceCN);
        executor.submit(priceHK);
        executor.submit(priceAR);
        executor.submit(priceRU);
        executor.submit(priceTRY);

        Map<String, String> priceMap = new LinkedHashMap<>();
        try {
            priceMap.put(CountryConsts.CN, ExchangeUtil.getNumber(priceCN.get()));
            priceMap.put(CountryConsts.HK, ExchangeUtil.toCN(CountryConsts.HK, priceHK.get()));
            priceMap.put(CountryConsts.AR, ExchangeUtil.specialToCN(CountryConsts.AR, priceAR.get()));
            priceMap.put(CountryConsts.RU, ExchangeUtil.toCN(CountryConsts.RU, priceRU.get()));
            priceMap.put(CountryConsts.TRY, ExchangeUtil.specialToCN(CountryConsts.TRY, priceTRY.get()));
        } catch (Exception e) {
            JavaPluginMain.INSTANCE.getLogger().error(e.getMessage());
            throw new RuntimeException();
        }
        this.price = priceMap;

        this.lowestPrice = steamCrawler.getLowest(this.id);
    }
}
