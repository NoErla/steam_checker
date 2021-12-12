plugins {
    val kotlinVersion = "1.5.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("net.mamoe.mirai-console") version "2.7-M2"
}

group = "org.example"
version = "0.2.0"

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    mavenCentral()
}
dependencies{
    implementation("junit:junit:4.13.1")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("cn.wanghaomiao:JsoupXpath:2.4.3")
    implementation("org.codehaus.jackson:jackson-core-asl:1.9.13")
    implementation("com.alibaba:fastjson:1.2.76")
    implementation("cn.hutool:hutool-core:5.7.4")
    implementation("org.quartz-scheduler:quartz:2.3.2")
}