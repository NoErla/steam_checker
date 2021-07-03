package mirai.noerla.steam_checker.pojo;

import java.util.Map;

public class Game {

    private String id;

    private String name;

    private Map<String, String> price;

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

    public Map<String, String> getPrice() {
        return price;
    }

    public void setPrice(Map<String, String> price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public Game(String id, String name, Map<String, String> price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Game() {
    }
}
