# Slog4J - 简单日志记录插件

Slog4J(Simple Logger For Java)是一款轻量级的Java日志记录插件，**上手难度极低**，刚接触Java的小白也能轻松使用。

通过使用Slog4J，你能自定义向控制台输出内容的格式，并可以自动在本地创建日志文件以便随时查阅。

你可以将插件应用在绝大部分Java环境中。

**注：**本插件不提供数据统计功能，仅提供日志输出控制。开发本插件目的旨在最简化地记录日志。

## Why Slog4J?

* 三分钟精通
* 在库中引用即可开始使用
* 框架简单，体积小，几乎不占内存
* 使用线程池+缓存，充分利用性能，不会阻塞代码
* 静态语句环境配置，无需建立配置文件，用代码即可实现所有配置
* 分日期存储在本地，方便查阅
* 可自定义日志输出，大量自定义语句可调用
* 支持打印错误报告，将程序崩溃也记录下来，美观方便
* 美化控制台日志输出，彩色控制台输出
* 输出日志至数据库，并使用线程池大大提升效率（可选，需要导入JDBC）
* 使用缓存池式日志输出存储，先记录至内存，再一起Flush输出

## Get started!

只需几步，你就能为你的项目配置Slog4j。

### 下载JAR

直接将已经打包好的JAR文件引入到你的Java项目中：

[点击下载]()

### Slog4J基本使用方法

**请注意：由于Slog4J使用静态语句配置，请将配置命令（例如设置MySQL、设置时间单位）尽量放在主方法，且尽量保证最先被执行。**

#### 缓存

Slog4J使用缓存来存储日志，使用命令：

```$xslt
Slog4J.log("Hello world!");
```
运行命令，你会发现没有运行结果。试试如下命令：

```$xslt
Slog4J.log("Hello world!");
Slog4J.log("Goodbye world!");
Slog4J.flush();
```

得到输出结果：

```$xslt
[?]-[2019-03-28 12:54:15:394]-[0] Hello world!
[?]-[2019-03-28 12:54:15:395]-[1] Goodbye world!
```

此时Slog将缓存中写入的日志全部输出。

#### 输出并自动刷新缓存

你不但可以使用`Slog4J.log("");`与`Slog4J.flush();`相结合对缓存进行输出，还可以使用下面的命令写入日志并立即输出：

```$xslt
Slog4J.logAndFlush("Process is begin, please wait...");
```

得到输出结果：

```$xslt
[?]-[2019-03-28 13:02:51:546]-[0] Process is begin, please wait...
```

#### 输出至本地

Slog4J默认启用了**控制台输出**和**本地输出**。当你刚刚在执行代码时，Slog4J已经将文本保存在了默认的**项目根目录下的Slog4J文件夹**中。

你可以使用命令来获取Slog4J当前日志的输出位置：

```$xslt
System.out.println(Slog4J.getFileAbsolutePath());
```

得到输出结果：

```$xslt
/Users/adler/SL4J/Slog4J/2019-03-28 12H.txt
```

#### 日志输出等级

你可能留意到了，上方的输出结果包含了一个问号`?`，此处我们应该指定该日志的输出等级：

```$xslt
Slog4J.setLevelByString("info");
Slog4J.logAndFlush("Hello world!");
```

得到输出结果：

```$xslt
[INFO]-[2019-03-28 13:07:01:862]-[0] Hello world!
```

此处的`INFO`是彩色的，但由于文字限制无法体现。

你还可以使用命令设置输出等级：

```$xslt
Slog4J.isInfoLevel();
Slog4J.logAndFlush("Hello world!");
```

你还可以将上面两行语句合并为一行：

```$xslt
Slog4J.logAndFlushWithLevel("info","Hello world!");
```

#### 全部输出等级

除此`info`外，我们还提供了以下输出等级：

* BOOT
* LOG
* INFO
* WARN
* IMPORTANT
* ERROR
* CRASH
* DOWN
* SHUTDOWN
* STACKTRACER
* NULL

#### 异常处理输出

Slog4J包装了异常处理的语句，我们可以人为制造一个异常作显示-被除数为0：

```$xslt
try {
    int a = 1/0;
} catch (Exception e) {
    Slog4J.printStackTrace(e);
}
```

得到输出结果：

![](/img/exception.png)

#### 自定义输出格式

我们可以修改输出的格式，使用命令：

```$xslt
Slog4J.setFormat("日期：${time} 等级：${level} 第${count}条 内容：${words}");
Slog4J.logAndFlush("Slog4J output style is customized!");
```

得到输出结果：

```$xslt
日期：2019-03-28 13:20:52:168 等级：? 第0条 内容：Slog4J output style is customized!
```

#### 全部输出格式

目前，全部可用的输出格式变量有：

* `${words}` 日志内容
* `${time}` 当前时间
* `${level}`日志等级
* `${count}` 通过Slog4J输出的第N条日志

#### 设置日期格式

除了默认的日期格式`年-月-日 时:分:秒:毫秒`外，你还可以设置日期的格式：

```$xslt
Slog4J.setTimeUnit(8);
Slog4J.logAndFlushWithLevel("info","Slog4J time unit is customized!");
```

得到输出结果：

```$xslt
[INFO]-[13:28:32]-[0] Slog4J time unit is customized!
```

其中，`setTimeUnit(unit);`就是设置时间单位的方法。

`unit`是范围在**`0-12`**之间的整数。你可以更改数字调试出你需要的结果。