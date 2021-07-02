package mirai.noerla.steam_checker.controller;

import mirai.noerla.steam_checker.service.SteamService;
import mirai.noerla.steam_checker.pojo.Game;

public class SteamController {

    private final SteamService steamService = SteamService.getInstance();

    public Game getGameByInput(String input) {
        String inputName = input.replace(".询价 ", "");
        return steamService.getGameByInput(inputName);
    }


}
