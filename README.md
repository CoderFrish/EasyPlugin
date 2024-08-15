# EasyPlugin

## 1,关于该项目
- 介绍：这是一个Kotlin Gradle项目，主要是为了让开发者简单的编写Bukkit插件
- 作者：Frish2021
- Gradle版本：8.5-all

## 2，如何创建插件
在buildSettings拓展里面编写一下一段代码
```groovy
buildSettings {
    create("<你的插件名>") {
        setMain("<你的插件主类>")
        setVersion("<你的插件版本>")
        setWebsite("<你的插件网站>")
        
        // ......等等
    }
}
```

如果你想自定义的话可以使用`addItem`方法去加入.
```groovy
buildSettings {
    create("<你的插件名>") {
        setMain("<你的插件主类>")
        setVersion("<你的插件版本>")
        setWebsite("<你的插件网站>")
        
        setItem("<key值>", "<value值>")
    }
}
```

## 3，如何创建指令
在buildSettings拓展create里面编写一下一段代码
```groovy
buildSettings {
    create("<你的插件名>") {
        setMain("<你的插件主类>")
        setVersion("<你的插件版本>")
        setWebsite("<你的插件网站>")
        
        createCommand("<你的指令名>") {
            // ......一些参数
        }
    }
}
```

## 4，如何创建权限组
与创建指令同理

## 5，如何构建插件
Windows的Cmd或者PowerShell，MACOSX和Linux的bash输入一下指令
```bash
.\gradlew build
```

## 6，问题
#### 要不要创建plugin.yml文件？
> 答：不需要，build的过程中，构建脚本已经帮你生成好了，其配置和以上的脚本配置有关联

#### 如果要编写Spigot插件怎么办？
> 答：buildSettings拓展里面的type值可以设置，里面有四个预设分别是(Spigot, Bukkit, Paper, Folia)，这里推荐Spigot和Paper,当然工程默认是Paper.

#### 为什么要写这玩意
> 答：插件配置太烦了，Minecraft Development插件有不太靠谱。要为自己服务器写插件的，使用就写个东西方便一下自己。
