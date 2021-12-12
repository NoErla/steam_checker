package mirai.noerla.steam_checker.service;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SteamServiceTest {



    @Test
    public void getGameNameById(){
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?$");
        Matcher matcher = pattern.matcher("1999 ska");
        System.out.println(matcher.replaceAll(""));
    }
}
