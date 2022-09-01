package mirai.noerla.steam_checker;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import mirai.noerla.steam_checker.consts.CountryConsts;
import mirai.noerla.steam_checker.controller.SteamController;
import mirai.noerla.steam_checker.pojo.Game;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;

import java.util.Map;

public class SteamCheckerEventHandler extends SimpleListenerHost {

    /**
     * 监听群临时会话消息
     * @param event
     * @return
     */
    @EventHandler
    public ListeningStatus OnGroupTempMessageEvent(GroupTempMessageEvent event) {
        replyGameInfo(event.getMessage().contentToString(), event);
        return ListeningStatus.LISTENING;
    }

    /**
     * 监听好友
     * @param event
     * @return
     */
    @EventHandler
    public ListeningStatus OnFriendMessageEvent(FriendMessageEvent event) {
        replyGameInfo(event.getMessage().contentToString(), event);
        return ListeningStatus.LISTENING;
    }
    /**
     * 监听群
     * @param event
     * @return
     */
    @EventHandler
    public ListeningStatus OnGroupMessageEvent(GroupMessageEvent event) {
        replyGameInfo(event.getMessage().contentToString(), event);
        return ListeningStatus.LISTENING;
    }

    private void replyGameInfo(String input, MessageEvent messageEvent){
        input = input.toLowerCase().replaceAll("。", ".");
        if(!input.startsWith(".询价 "))
            return;
        try{
            //计时器
            final TimeInterval timer = DateUtil.timer();
            timer.start();
            SteamController steamController = new SteamController();
            Game game = steamController.getGameByInput(input);

            StringBuilder sb = new StringBuilder();
            sb.append("游戏名:").append(game.getName()).append("\n");
            final Map<String, String> prices = game.getPrice();
            sb.append("国区价格(元):").append(prices.get(CountryConsts.CN)).append("\n");
            sb.append("港服价格(元):").append(prices.get(CountryConsts.HK)).append("\n");
            sb.append("阿区价格(元):").append(prices.get(CountryConsts.AR)).append("\n");
            sb.append("俄区价格(元):").append(prices.get(CountryConsts.RU)).append("\n");
            sb.append("土区价格(元):").append(prices.get(CountryConsts.TRY)).append("\n");
            sb.append("国区史低:").append(game.getLowestPrice()).append("\n");
            sb.append("查询耗时:").append(timer.intervalMs());
            messageEvent.getSubject().sendMessage(sb.toString());
        } catch (Exception e){
            messageEvent.getSubject().sendMessage("找不到游戏, 请尝试英文全名");
        }

    }

}
