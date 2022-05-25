plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.10.0"
}

group = "org.example"
version = "0.2.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        name = "ktor-eap"
    }
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        name = "ktor-eap"
    }
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