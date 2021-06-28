package mirai.noerla.plugin.controller;

import mirai.noerla.plugin.service.SteamService;
import mirai.noerla.plugin.pojo.Game;

public class SteamController {

    private final SteamService steamService = SteamService.getInstance();

    public Game getGameByInput(String input) {
        String inputName = input.replace(".询价 ", "");
        return steamService.getGameByInput(inputName);
    }


}
