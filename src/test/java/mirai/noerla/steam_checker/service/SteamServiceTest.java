package mirai.noerla.steam_checker.service;

import org.junit.Test;

public class SteamServiceTest {

    private SteamService steamService = SteamService.getInstance();

    @Test
    public void getGameNameById(){
        try{
            System.out.println(steamService.getGameByInput("dasug f sadan"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
