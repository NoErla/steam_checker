package mirai.noerla.steam_checker.pojo;

/**
 * @author Mu Yuchen
 * @date 2022/5/26 10:08
 */
public class GameInfo {

    private String id;

    private String name;

    private String currentPrice;

    private String lowestPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }
}
