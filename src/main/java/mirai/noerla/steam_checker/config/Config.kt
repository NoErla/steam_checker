package mirai.noerla.steam_checker.config
import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object Config : ReadOnlyPluginConfig("Settings") {
    @ValueDescription(
        """
        汇率接口所用key
        如果需要实现自动更新功能，请自行前往https://www.exchangerate-api.com/获得key
        """)
    val exchangeKey by value("")

    @ValueDescription("key为空时采取的汇率，HK=香港，RU=俄罗斯，AR=阿根廷,TRY=土耳其")
    val default by value(Default())

    @Serializable
    data class Default(
        val HK: Double = 1.2,
        val AR: Double = 14.0,
        val RU: Double = 11.0,
        val TRY: Double = 2.4
    )
}