# Slog4J - 简单日志记录插件

![GitHub top language](https://img.shields.io/github/languages/top/AdlerED/Slog4J.svg)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/AdlerED/Slog4J.svg)
![GitHub All Releases](https://img.shields.io/github/downloads/AdlerED/Slog4J/total.svg)
![GitHub](https://img.shields.io/github/license/AdlerED/Slog4J.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/AdlerED/Slog4J.svg)
![GitHub watchers](https://img.shields.io/github/watchers/AdlerED/Slog4J.svg?style=social)

[中文版本](#中文版本)

[English version](#english-version)

# 中文版本

Slog4J(Simple Logger For Java)是一款轻量级的Java日志记录插件，**上手难度极低**，刚接触Java的小白也能轻松使用。  
通过使用Slog4J，你能自定义向控制台输出内容的格式，并可以自动在本地创建日志文件以便随时查阅。  
你可以将插件应用在绝大部分Java环境中。  

**注**：本插件不提供数据统计功能，仅提供日志输出控制。开发本插件目的旨在最简化地记录日志。

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

## 全部命令一览

<a href="#全部命令一览表">如需查阅全部命令，请点我跳转</a>

## Get started!

只需几步，你就能为你的项目配置Slog4j。

### 下载JAR

直接将已经打包好的JAR文件引入到你的Java项目中：

<a href="https://www.stackoverflow.wiki/blog/articles/2019/03/28/1553762166613.html" target="_blank">点击下载</a>

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

#### 停止与开启Slog4J

Slog4J的`输出线程池`默认是开启的。你可能发现了，当你单独执行Slog4J后，程序不会自动停止。这是因为Slog4J的线程仍在运行。

你可以使用：

```$xslt
Slog4J.close();
```

来停止Slog4J的全部线程。

**!!! 强烈建议在即将停止程序时执行Slog4J.close() !!!**

如在Tomcat中部署Slog4J，在停止Tomcat时不执行`Slog4J.close()`可能会无法正常关闭Tomcat。

**执行`close()`后，Slog4J线程不会立即强行停止，而是等待所有日志写入后再停止全部线程。**

如果由于某些原因，你需要强制终止Slog4J的全部线程，可以使用命令：

```$xslt
Slog4J.shutdownAllSlog4JThreadPoolNow();
```

#### 连接MySQL

Slog4J使用`JDBC`进行MySQL操作。推荐使用版本`8.0`，在本项目根目录下已经附件了一个`mysql-connector-java-8.0.11.jar`，你可以直接使用而无需额外下载。

你可以使用下面命令使Slog4J向MySQL中输出：

```$xslt
Slog4J.mysql.setURL("jdbc:mysql://localhost/Users?useSSL=false");
Slog4J.mysql.setUser("root");
Slog4J.mysql.setPassword("toor");
Slog4J.enableMySQLOutput();
```

`setURL`用来设置MySQL的地址，让我们将语句拆分：

```$xslt
jdbc:mysql://
```

由于Slog4J使用`JDBC`进行数据库操作，所以必须包含。

```$xslt
localhost/Users
```

`localhost`是MySQL的地址，`Users`是指定的数据库。

`setUser`是MySQL的用户名，`setPassword`是MySQL的密码。

在设置MySQL的参数并使用`Slog4J.enableMySQLOutput();`启用MySQL后，Slog4J会**自动创建**一个`Slog4J`表，Slog4J会向其中输出所有的日志。

#### 输出功能的开启与关闭

你可以灵活开启关闭Slog4J的三种输出方法：`控制台`、`本地文件`、`MySQL`：

```$xslt
//启用MySQL日志输出
Slog4J.enableMySQLOutput();
//关闭MySQL日志输出
Slog4J.disableMySQLOutput();

//启用本地文件日志输出
Slog4J.enableFileOutput();
//关闭本地文件日志输出
Slog4J.disableFileOutput();

//启用控制台日志输出
Slog4J.enableConsoleOutput();
//关闭控制台日志输出
Slog4J.disableConsoleOutput();
```

Slog4J默认的日志输出策略是：

* 默认**启用**控制台输出
* 默认**启用**本地文件输出
* 默认**关闭**MySQL输出

# 全部命令一览表

`setFormat(String format)`
自定义输出格式

`flush()`和`write()`
将缓存输出

`wipeLogList()`
清空缓存但不输出

`restoreFormatDefault()`
重置输出格式为默认

`setTimeUnit(int unit)`
设置时间格式（0-12）

`disableColor()`
关闭彩色输出

`setLogDir(String logDir)`
设置本地输出文件的目录（默认为相对目录"Slog4J"）

`shutdownAllSlog4JThreadPool()`和`close()`
安全地关闭全部Slog4J线程

`start()`
重新开启Slog4J全部线程，会清空全部缓存

`shutdownAllSlog4JThreadPoolNow()`
强制关闭全部Slog4J线程

`disableConsoleOutput()`
关闭控制台输出

`enableConsoleOutput()`
开启控制台输出（默认已经开启）

`disableFileOutput()`
关闭本地文件输出

`enableFileOutput()`
开启本地文件输出（默认已经开启）

`disableMySQLOutput()`
关闭MySQL输出

`enableMySQLOutput()`
开启MySQL输出

`isBootLevel()`
设置输出级别为BOOT

`isLogLevel()`
设置输出级别为LOG

`isInfoLevel()`
设置输出级别为INFO

`isWarnLevel()`
设置输出级别为WARN

`isImportantLevel()`
设置输出级别为IMPORTANT

`isErrorLevel()`
设置输出级别为ERROR

`isCrashLevel()`
设置输出级别为CRASH

`isDownLevel()`
设置输出级别为DOWN

`isShutdownLevel()`
设置输出级别为SHUTDOWN

`isStacktracerLevel()`
设置输出级别为STACKTRACER

`isNullLevel()`
设置输出级别为NULL

`setLevelByString(String level)`
以传递字符串方式设置输出级别，可用值（不区分大小写）：`BOOT` `LOG` `INFO` `WARN` `IMPORTANT` `ERROR` `CRASH` `DOWN` `SHUTDOWN` `STACKTRACER` `NULL`

`log(String log)`
将日志存到缓存中

`logAndFlush(String log)`
将日志存到缓存中，并输出

`logAndFlushWithLevel(String level, String log)`
将日志存到缓存中，以特定值等级输出。可用值（不区分大小写）：`BOOT` `LOG` `INFO` `WARN` `IMPORTANT` `ERROR` `CRASH` `DOWN` `SHUTDOWN` `STACKTRACER` `NULL`

`printStackTrace(Exception e)`
让Slog4J接管错误，并以设定格式输出

`mysql.setURL(String url)`
设置MySQL地址

`mysql.setUser(String user)`
设置MySQL用户名

`mysql.setPassword(String password)`
设置MySQL密码

`getFileAbsolutePath()`
获取当前日志文件绝对路径

`getSlog4JVersion()`
获取Slog4J版本

`getDeveloperInfo()`
获取开发者信息

# English version

Slog4J (Simple Logger For Java) is a lightweight Java logging plug-in. ** It is extremely difficult to get started. ** Just touching Java can easily be used.
By using Slog4J, you can customize the format of the output to the console and automatically create log files locally for easy access.
You can apply the plugin to most Java environments.

**Note**: This plug-in does not provide data statistics and only provides log output control. The purpose of developing this plugin is to make logging in the most simplified way.

## Why Slog4J?

* Three minutes of mastery
* Reference in the library to get started
* The frame is simple, small, and almost does not occupy memory
* Use thread pool + cache to take full advantage of performance without blocking code
* Static statement environment configuration, no need to create a configuration file, all the configuration can be achieved with code
* Dates are stored locally for easy access
* Customizable log output, a large number of custom statements can be called
* Support printing error report, record the program crash, beautiful and convenient
* Beautify console log output, color console output
* Output logs to the database and use thread pools to greatly improve efficiency (optional, need to import JDBC)
* Use cache pooled log output storage, first record to memory, then together with Flush output

## List of all commands

<a href="#all-command-list">For all commands, please click here to jump</a>

## Get started!

In just a few steps, you can configure Slog4j for your project.

### Download JAR

Directly import the already packaged JAR files into your Java project:

<a href="https://www.stackoverflow.wiki/blog/articles/2019/03/28/1553762166613.html" target="_blank">Click to download</a>

### Slog4J basic usage

**Please note: Since Slog4J uses static statement configuration, please configure the configuration commands (such as setting MySQL, setting the time unit) as much as possible in the main method, and try to ensure that it is executed first. **

#### Cache

Slog4J uses the cache to store logs, using the command:

```$xslt
Slog4J.log("Hello world!");
```
Run the command and you will find that there is no running result. Try the following command:

```$xslt
Slog4J.log("Hello world!");
Slog4J.log("Goodbye world!");
Slog4J.flush();
```

Get the output:

```$xslt
[?]-[2019-03-28 12:54:15:394]-[0] Hello world!
[?]-[2019-03-28 12:54:15:395]-[1] Goodbye world!
```

At this point, Slog will output all the logs written in the cache.

#### Output and automatically flush the cache

Not only can you use `Slog4J.log("");` with `Slog4J.flush();` to output the cache, you can also use the following command to write to the log and output immediately:

```$xslt
Slog4J.logAndFlush("Process is begin, please wait...");
```

Get the output:

```$xslt
[?]-[2019-03-28 13:02:51:546]-[0] Process is begin, please wait...
```

#### Output to local

Slog4J has ** console output** and **local output** enabled by default. When you are just executing the code, Slog4J has saved the text in the Slog4J folder** in the default ** project root directory.

You can use the command to get the output location of the current log of Slog4J:

```$xslt
System.out.println(Slog4J.getFileAbsolutePath());
```

Get the output:

```$xslt
/Users/adler/SL4J/Slog4J/2019-03-28 12H.txt
```

#### Log output level

You may have noticed that the output above contains a question mark `?`, where we should specify the output level of the log:

```$xslt
Slog4J.setLevelByString("info");
Slog4J.logAndFlush("Hello world!");
```

Get the output:

```$xslt
[INFO]-[2019-03-28 13:07:01:862]-[0] Hello world!
```

The `INFO` here is colored, but it cannot be reflected due to text restrictions.

You can also use the command to set the output level:

```$xslt
Slog4J.isInfoLevel();
Slog4J.logAndFlush("Hello world!");
```

You can also combine the above two lines of statements into one line:

```$xslt
Slog4J.logAndFlushWithLevel("info","Hello world!");
```

#### All output levels

In addition to this `info`, we also provide the following output levels:

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

#### Exception processing output

Slog4J wraps the exception handling statement, we can artificially create an exception for display - the dividend is 0:

```$xslt
Try {
    Int a = 1/0;
} catch (Exception e) {
    Slog4J.printStackTrace(e);
}
```

Get the output:

![](/img/exception.png)

#### Custom Output Format

We can modify the format of the output, using the command:

```$xslt
Slog4J.setFormat("Date: ${time} Rank: ${level} ${count} Article: ${words}");
Slog4J.logAndFlush("Slog4J output style is customized!");
```

Get the output:

```$xslt
Date: 2019-03-28 13:20:52:168 Level:? Article 0 Content: Slog4J output style is customized!
```

#### All output formats

Currently, all available output format variables are:

* `${words}` log content
* `${time}` current time
* `${level}` log level
* `${count}` The Nth log output by Slog4J

#### Setting the date format

In addition to the default date format `year-month-day hour:minute:second:millisecond`, you can also set the format of the date:

```$xslt
Slog4J.setTimeUnit(8);
Slog4J.logAndFlushWithLevel("info","Slog4J time unit is customized!");
```

Get the output:

```$xslt
[INFO]-[13:28:32]-[0] Slog4J time unit is customized!
```

Among them, `setTimeUnit(unit);` is the method of setting the time unit.

`unit` is an integer ranging from **`0-12`**. You can change the numbers to debug the results you need.

#### Stop and open Slog4J

Slog4J's `output thread pool` is enabled by default. You may have discovered that when you execute Slog4J separately, the program does not stop automatically. This is because the thread of Slog4J is still running.

you can use:

```$xslt
Slog4J.close();
```

To stop all threads of Slog4J.

**!!! It is highly recommended to execute Slog4J.close() !!!** when the program is about to be stopped.

If Slog4J is deployed in Tomcat, not executing `Slog4J.close()` when stopping Tomcat may not close Tomcat properly.

** After executing `close()`, the Slog4J thread will not forcefully stop immediately, but wait for all logs to be written before stopping all threads. **

If for some reason you need to forcefully terminate all threads of Slog4J, you can use the command:

```$xslt
Slog4J.shutdownAllSlog4JThreadPoolNow();
```

#### Connecting MySQL

Slog4J uses `JDBC` for MySQL operations. It is recommended to use the version `8.0`. A `mysql-connector-java-8.0.11.jar` has been attached to the root directory of this project, you can use it directly without additional download.

You can use the following command to make Slog4J output to MySQL:

```$xslt
Slog4J.mysql.setURL("jdbc:mysql://localhost/Users?useSSL=false");
Slog4J.mysql.setUser("root");
Slog4J.mysql.setPassword("toor");
Slog4J.enableMySQLOutput();
```

`setURL` is used to set the address of MySQL, let us split the statement:

```$xslt
Jdbc:mysql://
```

Since Slog4J uses `JDBC` for database operations, it must be included.

```$xslt
Localhost/Users
```

`localhost` is the address of MySQL, and `Users` is the specified database.

`setUser` is the MySQL username, and `setPassword` is the MySQL password.

After setting the MySQL parameters and using `Slog4J.enableMySQLOutput();` to enable MySQL, Slog4J will automatically create a `Slog4J` table, and Slog4J will output all the logs to it.

#### Turning on and off the output function

You can flexibly turn on the three output methods that turn off Slog4J: `console`, `local file`, `MySQL`:

```$xslt
/ / Enable MySQL log output
Slog4J.enableMySQLOutput();
/ / Close the MySQL log output
Slog4J.disableMySQLOutput();

/ / Enable local file log output
Slog4J.enableFileOutput();
/ / Close the local file log output
Slog4J.disableFileOutput();

/ / Enable console log output
Slog4J.enableConsoleOutput();
/ / Close the console log output
Slog4J.disableConsoleOutput();
```

The default log output strategy for Slog4J is:

* Default **Enable** console output
* Default ** Enabled ** Local File Output
* Default ** off ** MySQL output

# All command list

`setFormat(String format)`
Custom output format

`flush()` and `write()`
Cache output

`wipeLogList()`
Empty the cache but not output

`restoreFormatDefault()`
Reset output format to default

`setTimeUnit(int unit)`
Set the time format (0-12)

`disableColor()`
Turn off color output

`setLogDir(String logDir)`
Set the directory of the local output file (default is the relative directory "Slog4J")

`shutdownAllSlog4JThreadPool()` and `close()`
Safely shut down all Slog4J threads

`start()`
Re-open all Slog4J threads, will clear all caches

`shutdownAllSlog4JThreadPoolNow()`
Force all Slog4J threads to be closed

`disableConsoleOutput()`
Turn off console output

`enableConsoleOutput()`
Turn on console output (it is already enabled by default)

`disableFileOutput()`
Turn off local file output

`enableFileOutput()`
Enable local file output (default is enabled)

`disableMySQLOutput()`
Turn off MySQL output

`enableMySQLOutput()`
Turn on MySQL output

`isBootLevel()`
Set the output level to BOOT

`isLogLevel()`
Set the output level to LOG

`isInfoLevel()`
Set the output level to INFO

`isWarnLevel()`
Set the output level to WARN

`isImportantLevel()`
Set the output level to IMPORTANT

`isErrorLevel()`
Set the output level to ERROR

`isCrashLevel()`
Set the output level to CRASH

`isDownLevel()`
Set the output level to DOWN

`isShutdownLevel()`
Set the output level to SHUTDOWN

`isStacktracerLevel()`
Set the output level to STACKTRACER

`isNullLevel()`
Set the output level to NULL

`setLevelByString(String level)`
Set the output level by passing the string, the available value (not case sensitive): `BOOT` `LOG` `INFO` `WARN` `IMPORTANT` `ERROR` `CRASH` `DOWN` `SHUTDOWN` `STACKTRACER` `NULL `

`log(String log)`
Save the log to the cache

`logAndFlush(String log)`
Save the log to the cache and output

`logAndFlushWithLevel(String level, String log)`
Save the log to the cache and output it at a specific value level. Available values ​​(not case sensitive): `BOOT` `LOG` `INFO` `WARN` `IMPORTANT` `ERROR` `CRASH` `DOWN` `SHUTDOWN` `STACKTRACER` `NULL`

`printStackTrace(Exception e)`
Let Slog4J take over the error and output in the format

`mysql.setURL(String url)`
Set MySQL address

`mysql.setUser(String user)`
Set MySQL username

`mysql.setPassword(String password)`
Set MySQL password

`getFileAbsolutePath()`
Get the absolute path of the current log file

`getSlog4JVersion()`
Get the Slog4J version

`getDeveloperInfo()`
Get developer information
