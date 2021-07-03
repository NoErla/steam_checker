package mirai.noerla.steam_checker;

import mirai.noerla.steam_checker.controller.SteamController;
import mirai.noerla.steam_checker.pojo.Game;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;

import java.util.Map;

import static mirai.noerla.steam_checker.consts.GloalConsts.*;

public class SteamCheckerEventHandler extends SimpleListenerHost {

    @EventHandler
    public ListeningStatus onBotGroupRequest(BotInvitedJoinGroupRequestEvent event) {
        //收到邀请自动加入
        event.accept();
        return ListeningStatus.LISTENING;
    }

    @EventHandler
    public ListeningStatus onFriendRequest(NewFriendRequestEvent event) {
        event.accept();
        return ListeningStatus.LISTENING;
    }

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
            SteamController steamController = new SteamController();
            Game game = steamController.getGameByInput(input);

            StringBuilder sb = new StringBuilder();
            sb.append("游戏名:").append(game.getName()).append("\n");
            final Map<String, String> prices = game.getPrice();
            sb.append("国区价格(元):").append(prices.get(CN)).append("\n");
            sb.append("阿区价格(元):").append(prices.get(AR)).append("\n");
            sb.append("俄区价格(元):").append(prices.get(RU));
            messageEvent.getSubject().sendMessage(sb.toString());
        } catch (Exception e){
            messageEvent.getSubject().sendMessage("找不到游戏, 请尝试英文全名");
        }

    }

}
