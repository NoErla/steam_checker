package mirai.noerla.steam_checker.controller;

import mirai.noerla.steam_checker.pojo.Game;

public class SteamController {

    public Game getGameByInput(String input) {
        String inputName = input.replace(".询价 ", "");
        return new Game(inputName);
    }


}
