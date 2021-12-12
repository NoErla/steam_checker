# steam_checker

基于[mirai](https://github.com/mamoe/mirai)的简单的steam价格查询插件。目前支持国/港/俄/阿/土区的价格查询。

## 使用方式

```xml
。询价 [游戏名]
.询价 [游戏名]
```

## 配置文件

可在/mirai/config/mirai.noerla.steam_checker/Setting.yml文件中更改汇率的获取方式，具体请参照yml文件中的说明

**Setting.yml文件会在你第一次加载该插件的时候自动创建**

## 如何获取

[你可在此处获取最新版本](https://github.com/NoErla/steam_checker/releases)

## 如何更新

1. 删除老版本jar包

2. 下载新版本jar包并放置于/mirai/plugins下

3. 保存/mirai/config/mirai.noerla.steam_checker/Setting.yml中用于汇率查询的key

4. 删除Setting.yml

   - 或者使用如下的文本覆盖原来的文件

     ```yml
     # 汇率接口所用key
     # 如果需要实现自动更新功能，请自行前往https://www.exchangerate-api.com/获得key
     exchangeKey: ''
     # key为空时采取的汇率，HK=香港，RU=俄罗斯，AR=阿根廷,TRY=土耳其
     default: 
       HK: 1.2
       AR: 14.0
       RU: 11.0
       TRY: 2.2
     ```

5. 启动bot，并Setting.yml文件中添加第三步保存的key

6. 重启bot

## 如何汇报bug

请使用github的issue功能，不过由于个人原因，不一定能准时回复，但一定能看到。
