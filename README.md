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

### 通知的解剖
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKT0VUZktveVZUUkE/notifications-anatomy-01-states.png" width = "370" height = "220"/>

#### Overview
通知最重要的设计原则是，便于浏览和使用。
#### Primary content
内容是通知的最突出的元素。次要信息，如时间戳，比主内容要小。
#### People
如果通知涉及到发送者，则出现在右边，让它从其他内容中独立出来。
#### Actions
可通过单击指示器图标来显示可扩展的通知。操作在单独的背景颜色和位置上用文本标签显示

#### 头部区域
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKMGppOHRYLUpUQ2M/notifications-anatomy-01-header.png" width="375" height="100"/>

* （1 ：app icon） 应用程序图标是应用程序标识的一个小的二维身份标识。它出现在状态栏的单色中。如果你的应用程序发送了各种各样的通知，你可以用一个反映内容类型的符号替换你的应用程序的身份图标。例如，谷歌现在使用云图标作为天气通知。
* （2 ：app name） 应用程序的名称会自动出现在通知上
* （3 ：Header text (optional)） 如果应用程序从多个源发送通知，如多个帐户的用户的帐户名，则通常只需要标题文本
* （4 ：Timestamp (optional)）默认情况下，没有出现时间戳，但如果发送通知时非常重要，如未接来电的时间，则可以添加时间戳。
* （5 ：Expand indicator）如果通知可以扩展，则出现此情况。

#### 内容区域
<img src="https://storage.googleapis.com/material-design/publish/material_v_12/assets/0BwJzNNZmsTcKZUVhSXE5VVZaQ0k/notifications-anatomy-02-content.png" width="375" height="100"/>






















## 备注

## 建议与意见
欢迎各位朋友提出建议与意见  wuhongqi0012@163.com