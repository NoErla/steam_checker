package mirai.noerla.steam_checker;

import mirai.noerla.steam_checker.config.Config;
import mirai.noerla.steam_checker.config.LoadConfig;
import mirai.noerla.steam_checker.timer.ExchangeScheduler;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;


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
        super(new JvmPluginDescriptionBuilder("mirai.noerla.steam_checker", "0.3.0")
                .author("Noerla")
                .info("steam_checker")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("日志");
        //读取配置文件
        this.reloadPluginConfig(Config.INSTANCE);
        LoadConfig.load();
        //开启定时任务
        new ExchangeScheduler().startScheduler();
        //注册handler
        GlobalEventChannel.INSTANCE.registerListenerHost(new SteamCheckerEventHandler());
    }

}
