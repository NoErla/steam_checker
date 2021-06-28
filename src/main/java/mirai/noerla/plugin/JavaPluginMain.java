package mirai.noerla.plugin;

import mirai.noerla.plugin.controller.SteamController;
import mirai.noerla.plugin.pojo.Game;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.Map;

import static mirai.noerla.plugin.consts.GloalConsts.*;


/**
 * 使用 Java 请把
 * {@code /src/main/resources/META-INF.services/net.mamoe.mirai.console.plugin.jvm.JvmPlugin}
 * 文件内容改成 {@code org.example.mirai.plugin.JavaPluginMain} <br/>
 * 也就是当前主类全类名
 *
 * 使用 Java 可以把 kotlin 源集删除且不会对项目有影响
 *
 * 在 {@code settings.gradle.kts} 里改构建的插件名称、依赖库和插件版本
 *
 * 在该示例下的 {@link JvmPluginDescription} 修改插件名称，id 和版本等
 *
 * 可以使用 {@code src/test/kotlin/RunMirai.kt} 在 IDE 里直接调试，
 * 不用复制到 mirai-console-loader 或其他启动器中调试
 */

public final class JavaPluginMain extends JavaPlugin {
    public static final JavaPluginMain INSTANCE = new JavaPluginMain();

    private JavaPluginMain() {
        super(new JvmPluginDescriptionBuilder("mirai.noerla.plugin", "0.1.0")
                .info("EG")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("日志");
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        eventChannel.subscribeAlways(GroupMessageEvent.class, g -> {
            //监听群消息
            //getLogger().info(g.getMessage().contentToString());
            RunPrefix(g.getMessage().contentToString(), g);

        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, f -> {
            //监听好友消息
            //getLogger().info(f.getMessage().contentToString());
            RunPrefix(f.getMessage().contentToString(), f);
        });
    }

    private void RunPrefix(String input, MessageEvent messageEvent){
        input = input.toLowerCase().replaceAll("。", ".");
        if(input.startsWith(".询价 ")) {
            try{
                SteamController steamController = new SteamController();
                Game game = steamController.getGameByInput(input);

                StringBuilder sb = new StringBuilder();
                sb.append("游戏名:").append(game.getName()).append("\n");
                final Map<String, String> prices = game.getPrice();
                //TODO 用策略模式优化
                sb.append("国区价格(元):").append(prices.get(CHINA)).append("\n");
                sb.append("阿区价格(元):").append(prices.get(AR)).append("\n");
                sb.append("俄区价格(元):").append(prices.get(RU));
                messageEvent.getSubject().sendMessage(sb.toString());
            } catch (Exception e){
                messageEvent.getSubject().sendMessage("找不到游戏, 请尝试英文全名");
            }
        }
    }
}
