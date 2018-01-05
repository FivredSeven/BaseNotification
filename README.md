# BaseNotification
to make a base notification for android

## 通知基础使用如下
     BaseNotificationBuilder builder = new BaseNotificationBuilder(MainActivity.this);
     builder.setTag("tag")
             .setId(1)
             .setTitle("我是一个普通通知的标题")
             .setContent("我是一个普通通知的内容简介")
             .setSmallIcon(R.mipmap.ic_launcher)
             .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
             .setClickIntent(new Intent(this, MainActivity.class))
     NotificationHelper.getInstance().showNotification(builder);

## 更丰富的扩展类型用法都在MainActivity
主要包含通知类型如下：
* 监听自己被清除的通知
* 自定义声音/震动/呼吸灯的通知
* 显示进度条的通知
* 可展开收起的group通知
* 显示大图片的通知
* 显示多行文本的通知
* 可在通知栏直接回复的通知

## 通知设计原则

### 通知的剖析
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKT0VUZktveVZUUkE/notifications-anatomy-01-states.png" width = "600" height = "359"/>

#### Overview
通知最重要的设计原则是，便于浏览和使用。
#### Primary content
内容是通知的最突出的元素。次要信息，如时间戳，比主内容要小。
#### People
如果通知涉及到发送者，则出现在右边，让它从其他内容中独立出来。
#### Actions
可通过单击指示器图标来显示可扩展的通知。操作在单独的背景颜色和位置上用文本标签显示

#### 头部区域
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKMGppOHRYLUpUQ2M/notifications-anatomy-01-header.png" width="600" height="160"/>

<font color=#DC143C >1、app图标</font> 应用程序图标是应用程序标识的一个小的二维身份标识。它出现在状态栏的单色中。如果你的应用程序发送了各种各样的通知，你可以用一个反映内容类型的符号替换你的应用程序的身份图标。例如，谷歌现在使用云图标作为天气通知。

<font color=#DC143C >2、app名称</font> 应用程序的名称会自动出现在通知上

<font color=#DC143C >3、头部文本（可选）</font> 如果应用程序从多个源发送通知，如多个帐户的用户的帐户名，则通常只需要标题文本

<font color=#DC143C >4、时间戳（可选）</font> 默认情况下，没有出现时间戳，但如果发送通知时非常重要，如未接来电的时间，则可以添加时间戳。

<font color=#DC143C >5、可扩展的图标</font> 如果通知可以扩展，则出现此情况。

#### 内容区域
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKZUVhSXE5VVZaQ0k/notifications-anatomy-02-content.png" width="600" height="160"/>

<font color=#DC143C >1、内容标题</font> 通知的简短标题

<font color=#DC143C >2、内容文本</font>

<font color=#DC143C >3、大图标（可选）</font> 可以添加图像以有意义的方式增强通知，例如包含发件人的消息。

#### 操作区域
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKUlVLMmlYT2NHMFk/notifications-anatomy-03-action-area.png" width="600" height="180"/>

当展开时，通知可以在底部显示多达三个操作。
在Android N和以后，操作没有图标显示以容纳更多文本。还应该提供一个图标，因为操作系统早期版本的设备依赖于它，Android Wear和Android Auto也一样。

### 使用
#### 什么时候不该使用通知
通知不应该是与用户的主要通信通道，因为频繁的中断可能引起恼怒。下列情况不值得通知：
* <font color=#DC143C >Don't</font> 不要以通知的形式送节日或生日祝福。
* <font color=#DC143C >Don't</font> 不要在一个任务的中间打断用户，唯一的目的就是询问你是否做得很好。
* <font color=#DC143C >交叉推广</font> 或在通知中宣传另一产品，这是严格禁止的。
* <font color=#DC143C >从未打开</font> app从未被用户打开过
* <font color=#DC143C >没有直接价值</font> 鼓励用户返回应用程序的消息，但没有提供直接的价值，比如“暂时没有看到你”。
* <font color=#DC143C >评级</font> 请求对应用程序进行评级
* <font color=#DC143C >错误状态</font> 应用程序可以在没有用户交互的情况下恢复

#### 通知的要求
前台服务是在后台运行的应用程序进程，而用户并不直接与应用程序交互。因为这些应用程序使用的电池和可能的数据，Android需要用户通过一个非无足轻重的通知知道这些服务类型。
因为用户不能撤销通知，您应该为用户提供一个操作，如果他们不想运行该服务，就停止服务。
在下载应用程序和文件时，Android的下载管理器运行一个前台服务，并显示一个通知，让用户知道下载正在进行中，并有一个<font color=#DC143C >取消选项</font>。

### 行为













## 备注

## 建议与意见
欢迎各位朋友提出建议与意见  wuhongqi0012@163.com