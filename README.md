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

<font color=#DC143C >`1、app图标`</font> 应用程序图标是应用程序标识的一个小的二维身份标识。它出现在状态栏的单色中。如果你的应用程序发送了各种各样的通知，你可以用一个反映内容类型的符号替换你的应用程序的身份图标。例如，谷歌现在使用云图标作为天气通知。

<font color=#DC143C >`2、app名称`</font> 应用程序的名称会自动出现在通知上

<font color=#DC143C >`3、头部文本（可选）`</font> 如果应用程序从多个源发送通知，如多个帐户的用户的帐户名，则通常只需要标题文本

<font color=#DC143C >`4、时间戳（可选）`</font> 默认情况下，没有出现时间戳，但如果发送通知时非常重要，如未接来电的时间，则可以添加时间戳。

<font color=#DC143C >`5、可扩展的图标`</font> 如果通知可以扩展，则出现此情况。

#### 内容区域
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKZUVhSXE5VVZaQ0k/notifications-anatomy-02-content.png" width="600" height="160"/>

<font color=#DC143C >`1、内容标题`</font> 通知的简短标题

<font color=#DC143C >`2、内容文本`</font>

<font color=#DC143C >`3、大图标（可选）`</font> 可以添加图像以有意义的方式增强通知，例如包含发件人的消息。

#### 操作区域
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKUlVLMmlYT2NHMFk/notifications-anatomy-03-action-area.png" width="600" height="180"/>

当展开时，通知可以在底部显示多达三个操作。
在Android N和以后，操作没有图标显示以容纳更多文本。还应该提供一个图标，因为操作系统早期版本的设备依赖于它，Android Wear和Android Auto也一样。

### 使用
#### 什么时候不该使用通知
通知不应该是与用户的主要通信通道，因为频繁的中断可能引起恼怒。下列情况不值得通知：
* <font color=#DC143C >`Don't`</font> 不要以通知的形式送节日或生日祝福。
* <font color=#DC143C >`Don't`</font> 不要在一个任务的中间打断用户，唯一的目的就是询问你是否做得很好。
* <font color=#DC143C >`交叉推广`</font> 或在通知中宣传另一产品，这是严格禁止的。
* <font color=#DC143C >`从未打开`</font> app从未被用户打开过
* <font color=#DC143C >`没有直接价值`</font> 鼓励用户返回应用程序的消息，但没有提供直接的价值，比如“暂时没有看到你”。
* <font color=#DC143C >`评级`</font> 请求对应用程序进行评级
* <font color=#DC143C >`错误状态`</font> 应用程序可以在没有用户交互的情况下恢复

#### 通知的要求
前台服务是在后台运行的应用程序进程，而用户并不直接与应用程序交互。因为这些应用程序使用的电池和可能的数据，Android需要用户通过一个非无足轻重的通知知道这些服务类型。
因为用户不能撤销通知，您应该为用户提供一个操作，如果他们不想运行该服务，就停止服务。
在下载应用程序和文件时，Android的下载管理器运行一个前台服务，并显示一个通知，让用户知道下载正在进行中，并有一个<font color=#DC143C >`取消选项`</font>。

### 行为
#### 通知栏到达
当通知到达时，它会被添加到通知抽屉中。根据您设置的参数和设备的当前状态，通知也可以：
* 在状态栏中用图标表示
* 发出声音或震动
* 在当前屏幕上展示以吸引用户的注意力

用户可以选择更改您设置的通知行为。

当通知到达时，图标通常会出现在状态栏中。这向用户发出通知，通知抽屉里有东西要看。

<img src="https://github.com/FivredSeven/BaseNotification/blob/master/app/localgif/%E6%A8%AA%E5%B9%85%E9%80%9A%E7%9F%A5.gif" />

#### 通知抽屉
Android中的通知抽屉通常显示反向时间顺序通知，受影响的调整：
* 应用程序声明的通知优先级或重要性
* 通知是否最近以声音或振动通知用户
* 任何人附上通知和他们是否是明星接触
* 通知是否是一个重要的正在进行的活动，例如正在进行中的电话或音乐播放。

从Android O操作系统开始，Android系统可以通过添加强调或减弱改变在列表的顶部和底部的一些通知的出现，帮助用户扫描内容。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKZy1YYTV3VWQzVUE/notifications-behavior-03-drawer.png" />

在这个通知抽屉中，刚刚到达的文本出现在顶部。底部有一个关于天气的低优先级通知。

旧通知 ： 通知抽屉应及时向用户显示当前时刻的相关信息。如果先前发送的通知不再相关，您可以自动删除它，这样用户就不会看到它了。

在这个通知抽屉中，刚刚到达的文本出现在顶部。底部有一个关于天气的低优先级通知。

#### 通知的作用
用户可以通过以下方式与通知交互：

<font color=#DC143C >`1、导航到目的地`</font>

若要导航，用户可以单击通知。如果通知出现在锁定的屏幕上，用户将需要双击它，然后输入他们的PIN、模式或密码。

当用户点击一个通知时，他们应该被带到你的应用程序中直接与该通知相关的屏幕，让他们立即采取行动。例如，如果通知说这是他们在两人游戏中的回合，点击通知应该直接把他们带到那个游戏中。

<font color=#DC143C >`2、查看扩展视图`</font>

如果提供，扩展指示器出现在表头中。用户可以点击指示器或扫掉通知主体以扩展它。

<img src="https://github.com/FivredSeven/BaseNotification/blob/master/app/localgif/%E5%A4%A7%E5%9B%BE%E9%80%9A%E7%9F%A5.gif" />

<font color=#DC143C >`3、关闭（如果允许的话）`</font>

用户可以将通知左滑或者右滑，正在进行中的通知表明背景中的连续过程，如音乐播放，可能不会被一个滑动通知取消。

<img src="https://github.com/FivredSeven/BaseNotification/blob/master/app/localgif/%E9%80%9A%E7%9F%A5%E6%B8%85%E9%99%A4.gif" />

<font color=#DC143C >`4、在将来控制类似的通知`</font>

可以通过以下方式访问通知控件：
* 触摸并持有个别通知
* 通知左滑或右滑，然后点击设置图标

显示的控件取决于Android版本，以及该应用程序是否有通知的通道（从Android O开始）。

<img src="https://github.com/FivredSeven/BaseNotification/blob/master/app/localgif/%E9%80%9A%E7%9F%A5%E5%BF%AB%E9%80%9F%E8%AE%BE%E7%BD%AE.gif" width = "600" height = "420"/>

#### 多种类的通知
对于生成相同类型的多个通知的应用程序，Android提供了两种不同的方法来表示它们：总结和捆绑。

在折叠视图和扩展视图中显示了多个通知。
#### 总结
您可以创建一个汇总所有通知的通知，而不是显示多个通知。例如，一个消息应用程序可能有一个摘要通知，它在扩展时表示“3个新消息”，它可以为每个消息显示一个代码段。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKbHNsWE53UWRDeGs/notifications-guidelines-05-summary-1.png" width = "600" height = "359"/>

#### 分组
应用程序可以根据层次结构呈现多个通知：
* 父通知显示其子通知的摘要。
* 如果父通知由用户扩展，则显示所有子通知。
* 可以扩展子通知以显示其全部内容。

没有重复标题信息呈现子通知。例如，如果子通知具有与其父相同的应用程序图标，则子标题不包含图标。

如果单独出现，子通知应该是可以理解的，因为系统可能在他们到达时将它们显示在组外。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKeXpNbmZVV2Nwemc/notifications-guidelines-05-summary-2.png" width = "600" height = "510"/>

#### 扩展视图
您可以通过扩展视图显示通知中的更多信息，而不偏离通知。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKUGczcGJEd3RuZkk/notifications-behavior-06-expand.png" width = "600" height = "359"/>

不要包含重复通知主体上的敲击行为的文本操作。

<font color=#DC143C >`Don't`</font>

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0Bzhp5Z4wHba3Ulo5OWtmM3J0Qlk/notifications-guidelines-13-action-dont.png" width = "600" height = "190"/>

<font color=#DC143C >`Do`</font>

<img src="当Android的时钟应用程序有计时器运行时，这个通知允许用户暂停或直接从通知中添加一分钟。"/>
#### 能够输入操作的通知
您可以让用户直接键入通知中。用户可以输入少量的文字，这样的回复短信或写一个简短的便条。

对于长表单输入，导航用户到您的应用程序，在那里他们受益于更多的空间来查看和编辑文本。

如果您正在为消息应用程序使用此功能，请在用户发送应答后保持通知，并等到会话达到正常状态后自动删除。

<img src="https://github.com/FivredSeven/BaseNotification/blob/master/app/localgif/%E5%BF%AB%E9%80%9F%E5%9B%9E%E5%A4%8D%E7%9A%84%E9%80%9A%E7%9F%A5.gif"/>

Android的消息应用程序的用户可以直接回复任何消息而不离开通知。

### 通知的类型
通知被认为是事务性的或非事务性的。

#### 事务
事务性通知提供用户在特定时间必须接收的内容，以便执行下列操作之一：
* 使人与人的互动
* 在日常生活中更好地发挥作用
* 控制或解析瞬态设备状态

<table border="1">
<tr>
<td>事务通知可以帮助用户…</td>
<td>实例</td>
</tr>
<tr>
<td>使人与人的互动</td>
<td>来电或短信</td>
</tr>
<tr>
<td></td>
<td>双人游戏中用户回合的通知</td>
</tr>
<tr>
<td>在日常生活中更好地发挥作用</td>
<td>即将举行的日历活动</td>
</tr>
<tr>
<td></td>
<td>用户设置的提醒或警报</td>
</tr>
<tr>
<td></td>
<td>航班延误</td>
</tr>
<tr>
<td></td>
<td>交付订单</td>
</tr>
<tr>
<td>监视、控制或解析临时设备状态</td>
<td>音乐播放</td>
</tr>
<tr>
<td></td>
<td>逐轮导航</td>
</tr>
<tr>
<td></td>
<td>秒表运行</td>
</tr>
<tr>
<td></td>
<td>截图摄</td>
</tr>
<tr>
<td></td>
<td>运行在后台的应用程序</td>
</tr>
</table>

#### 非事务
如果上述情况没有一个描述您的通知，那么它是非事务性的。

非事务性通知应该是可选的，因为它们可能不会吸引所有用户。您可以通过以下两种方式之一使它们成为可选的：

<font color=#DC143C >`选择退出`</font> 默认情况下，用户接收选择退出通知，但可能会关闭设置而停止接收它们。

避免选择退出的方法，除非您的通知符合以下两个标准：
* 它们为用户提供具体的价值。
* 它们清楚地涉及到用户的上下文（例如当前位置、当前日期或时间、过去的历史或表达的兴趣）。

<table border="1">
<tr>
<td>通知示例…</td>
<td>避免在这里使用退出通知的原因</td>
</tr>
<tr>
<td>关于如何使用应用程序的随机提示</td>
<td>提供具体的用户值，但不是上下文。</td>
</tr>
<tr>
<td>对应用程序最近使用的内容的快速提示</td>
<td>上下文，但不能提供具体的用户价值</td>
</tr>
<tr>
</table>

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0Bzhp5Z4wHba3S1JWc3NkTVpjVk0/notifications-guidelines-03-optin.png" width = "600" height = "190"/>

这个来自YouTube的通知采用了退出的方法。它提供了两个上下文（用户订阅的YouTube频道）和值（从该频道获得的新视频）。“选项”行动将用户，他们可以选择退出该频道未来的通知。

<font color=#DC143C >`选择加入`</font> 用户只需在应用程序中打开一个设置，就可以接收选择通知。

加入选择入路比较保守。因为用户显式地选择接收这些通知，所以他们很可能会很高兴看到它们。但是，他们必须访问设置以了解如何接收它们。你可以告知用户这些通知来自于你的应用程序的其他地方，如入职流程或一卡。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0Bzhp5Z4wHba3MHNWeE80eGt5UGs/notifications-guidelines-04-optout.png" width = "600" height = "359"/>

这个应用程序采用选择入法。应用程序顶部的一张卡让用户知道他们可以接收突发新闻报道的通知。如果用户想选择，他们选择“是”，否则，他们选择“不谢谢”，然后选择退出。这个选项也可以在应用程序设置中找到。

### 设置

#### Channels in Android O
当你把应用程序升级到Android O时，你将需要为你的通知定义一个通道，一个是你想要发送的每一个通知类型。

用户在Android O中使用频道控制应用程序通知。如果用户不想从应用程序中获得某种通知，他们可以阻止该通道，而不是所有通知。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKaU5rVHZyT2R4eFE/notifications-settings-01-channels.png" width = "361" height = "638"/>

这个应用程序有三个通道，可以在UI中称为“类别”。

`渠道的重要性水平`

对于每个定义的通道，您将为它分配一个重要级别。从Android O开始，重要级别控制每个通道的行为（取代优先级）。
重要级别有以下限制：

* 您分配的重要级别将是通道的默认值。用户可以在Android环境中更改频道的重要级别。
* 一旦你选择了一个重要的等级，你就只能局限于如何改变它：你只能降低重要性，而且只有当用户没有明确改变它的时候。

通道的重要性应该考虑用户的时间和注意力。当一个不重要的通知被伪装成紧急情况时，它会产生不必要的警报。

<table border="1">
<tr>
<td>重要性</td>
<td>行为</td>
<td>使用</td>
<td>实例</td>
</tr>
<tr>
<td>高</td>
<td>发出声音并出现在屏幕上</td>
<td>用户必须知道或立即行动的时间关键信息</td>
<td>短信，警报，电话</td>
</tr>
<tr>
<td>默认</td>
<td>发出声音</td>
<td>应该在用户最方便的时候看到的信息，但不要打断他们正在做的事情。</td>
<td>交通警报，任务提醒</td>
</tr>
<tr>
<td>低</td>
<td>没有声音或视觉中断</td>
<td>可以等待或不是与用户特别相关的非必需信息</td>
<td>附近的景点，天气，宣传内容</td>
</tr>
</table>

`定义渠道`

要定义您的通道，请记录您要发送的所有通知。将这些通知分组为具有以下共同点的集合：
* 标的物。单个主题可以简洁地描述所有这些通知，如“下载”。
* 期望重要性水平。由于信道中的通知共享一个重要级别，因此它们应该从用户的角度进行类似的重要级别的交互。

`分组渠道`

您可以将频道分组，让用户更容易扫描Android设置中的频道列表。
只创建通道分组：
* 你有超过10个频道
* 您的应用程序支持多个用户帐户（如个人帐户和业务帐户），允许用户在帐户上拥有相同的通道名称和功能。

`将你的应用程序设置链接到Android频道设置`

使应用程序的设置与Android通道中的设置保持一致。如果你的应用程序提供了不同类型的通知控件，用户可以直接选择适当的Android通道设置来进行更改。

您还可以将频道设置屏幕链接到应用程序的设置。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKekZ1UWZWRGFXQ0E/notifications-settings-03b-applink.png" width = "600" height = "530"/>

在这个应用程序中，用户可以指定通信偏好。其中一个首选项是航班更新通知，它链接到一个称为“航班更新”的频道的系统设置屏幕。该屏幕包括通过“应用程序中的其他设置”返回的链接。

`通道和前台服务`

在Android中，前台服务通知信道的默认重要性水平必须至少importance_low使它显示在状态栏图标。

用不太突出的importance_min水平渠道将触发从Android在importance_low额外的通知，说明该应用程序是使用电池。

#### 无通道优先级
对于尚未升级到Android O的应用程序，您需要为每个单独的通知分配一个优先级级别。某些优先级别提供播放声音的选项。

`优先级别`

为了影响如何将每个通知传递给用户，请将其分配给优先级级别。优先级越高，越会被中断。例如，不管用户在做什么，max和高优先级的通知都会偷看用户当前的屏幕。有疑问时，选择较低优先级。

`通知声音`

指定默认（或更高）优先级的通知可以在发出通知时播放声音（使用您提供的声音文件）。但是，通知应该只使用声音：
* 它帮助用户维护对时间敏感的社会期望，如来电或即将召开的工作会议。
* 它帮助用户在日常生活中，如让他们知道航班延误。
* 用户已明确要求此通知到达时发出声音（默认情况下此选项已关闭）

#### 预先定义的类别
`无论您是否使用通道，都将每个通知分配给最合适的预定义类别。Android可以使用这些信息进行排名和过滤决策。`

<table border="1">
<tr>
<td>类别</td>
<td>描述</td>
</tr>
<tr>
<td>CATEGORY_CALL</td>
<td>呼入（语音或视频）或类似的同步通信请求</td>
</tr>
<tr>
<td>CATEGORY_MESSAGE</td>
<td>incoming直接消息（短信、即时消息等）。</td>
</tr>
<tr>
<td>CATEGORY_EMAIL</td>
<td>异步批量消息（电子邮件）</td>
</tr>
<tr>
<td>CATEGORY_EVENT</td>
<td>日历事件</td>
</tr>
<tr>
<td>CATEGORY_PROMO</td>
<td>促销或广告</td>
</tr>
<tr>
<td>CATEGORY_ALARM</td>
<td>闹钟或定时器</td>
</tr>
<tr>
<td>CATEGORY_PROGRESS</td>
<td>长时间后台操作的进展</td>
</tr>
<tr>
<td>CATEGORY_SOCIAL</td>
<td>社交网络或共享更新</td>
</tr>
<tr>
<td>CATEGORY_ERROR</td>
<td>后台操作或身份验证状态出错</td>
</tr>
<tr>
<td>CATEGORY_TRANSPORT</td>
<td>回放媒体传输控制</td>
</tr>
<tr>
<td>CATEGORY_SYSTEM</td>
<td>系统或设备状态更新。预留给系统使用。</td>
</tr>
<tr>
<td>CATEGORY_SERVICE</td>
<td>运行后台服务的指示</td>
</tr>
<tr>
<td>CATEGORY_RECOMMENDATION</td>
<td>针对某一事物的具体、及时的建议。例如，一个新闻应用程序可能推荐一个用户可能希望下次阅读的新闻故事。</td>
</tr>
<tr>
<td>CATEGORY_STATUS</td>
<td>关于设备或上下文状态的正在进行的信息</td>
</table>

### 锁屏
当用户的屏幕被锁定时，用户可以选择显示通知。这些通知可能隐藏应用程序标记为敏感的任何内容。Android评估每个通知的可见性级别，以确定哪些可以安全地显示。

#### 锁定屏幕上的敏感内容
由于通知是在锁定屏幕上可见的，因此用户隐私是一个重要考虑因素。对于您创建的每个通知，可见性级别设置为公共、私有或机密。

`公共` 通知在安全锁定屏幕上是完全可见的，而`私密`通知是隐藏的。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKTUVyR2hFcHZkcHc/notifications-behavior-04-lockscreen1.png" width = "360" height = "630"/>

`私人` 通知落在中间：它们只显示基本信息，包括发布它及其图标的应用程序的名称。代替常规内容——隐藏的内容——你可以选择显示不显示个人信息的文本，例如“2条新消息”。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKTi0zbDNNNXpCMjQ/notifications-behavior-05-lockscreen2.png" width = "360" height = "630"/>


### 风格
#### 简洁明了的文字
Android将内容标题一行（即使扩大）。

内容标题应该：
* 低于30个字符长
* 包含最重要的信息
* 避免变量——除非它们包含一个数字或短文本字符串，或在文本之前。
* 排除已经出现在标头中的应用程序的名称。

`Don't` 内容标题显示应用程序名称，它与标题区域冗余，并使用可用字符。

内容文本应该：
* 避免超过40个字符限制
* 不要重复内容标题中的内容

`Do` 内容标题显示最重要的信息。

#### 大图标
在Android N中，大图标只用于特定情况，在这种情况下，图像有意义地增强通知的内容，包括：
* `通信` 从另一个人身上，例如发送消息的人的形象
* `内容的来源` 如果它不同于发送通知的应用程序，例如来自用户订阅的YouTube频道的徽标。
* `有意义的符号` 关于通知，如用于指示方向的箭头符号。

当显示一个人时，大图标应该是圆形的，但在所有其他情况下都是方形的。

`Don't` 大图标不是用于品牌。
`Do` 大图标是指以有意义的方式加强通知的内容，例如连接到消息通知的人的照片。


### 模板
谷歌在Android应用程序中使用了以下通知模板，您可以对其应用程序进行调整。

#### 标准模板
标准模板适用于大多数通知，允许简洁的文本、大图标（在适用时）和动作。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKQTlSWGZCeXE4Tzg/notifications-templates-01-standard.png" width = "600" height = "359"/>

折叠和扩展通知的标准模板

#### 大文本模板
当显示长文本时，应使用此模板。它允许用户在通知扩展时预览更多文本。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKLWNJejI2b2NmZ1U/notifications-templates-02-big-text.png" width = "600" height = "460"/>

折叠和扩展通知的大文本模板

#### 大图片模板
当通知包含图片时，应使用此模板。大图标提供图片的缩略图，用户可以通过扩展通知获得更大的预览。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKRnFhVzBDdm5pRGM/notifications-templates-03-big-picture.png" width = "600" height = "500"/>

折叠和扩展通知的大图片模板

#### 有进度的模板
此模板应该用于用户发起的活动，这些活动需要时间完成，并且可以在任何时候取消。（不可撤销的活动不需要通知。）

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKYzRta2tPbmF6VkU/notifications-templates-04-progress.png" width = "600" height = "359"/>

折叠和扩展通知的进度模板

#### 媒体模板
此模板允许用户控制当前正在从应用程序播放的媒体。
* `折叠视图` 显示多达三个动作，大图标可以显示相关的图像，如相册封面
* `展开视图` 显示五个较大图像的操作，如果没有显示图像，则显示六个操作。从所提供的图像颜色自动通知的背景和其他元素的颜色。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKZTNRY29yZVQwQkk/notifications-templates-05-media.png" width = "600" height = "359"/>

#### 短信模板
此模板用于实时通信。您还可以向用户提供直接在通知中键入答复的功能。

<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKREFiTEhKdXI3N0k/notifications-templates-06-messaging.png" width = "600" height = "359"/>

用于折叠和扩展通知的消息模板












## 备注

暂时还没想到要补充的，之后再写

## 建议与意见
欢迎各位朋友提出建议与意见  wuhongqi0012@163.com