# Homework8

本程序已经实现当前project要求的所有功能

* 使⽤系统相机拍照
  * 解决权限申请
  * 使⽤应⽤私有存储
  * 图⽚显示⽅向正确
*  使⽤系统相机录制视频
  * 解决权限申请
  * 使⽤应⽤私有存储
  * 相机拍摄后在⻚⾯上播放
  * 点击暂停，再次点击恢复播放
* ⾃定义拍照和录制视频
  * 使⽤ Camera、SurfaceView 实时显示摄像头图像
  * 使⽤ Camera 拍摄照⽚并展示
  * 使⽤ MediaRecorder 录制视频并展示

打包生成的.apk文件已经放在homework8目录下。

# Homework7

本程序已经实现当前project要求的所有功能(包括可选功能)

* 加载中、加载失败时有占位图
* 实现图片圆角功能
* 渐变展示
* 在图片展示的activity中，增加输入图片uri地址的功能，如在edittext中填了内容，则会尝试打开该地址，否则会打开默认的图片
* 播放、暂停功能
* 播放进度条展示（包括时间显示）
* 进度条可以点击、滑动，跳转到指定位置
* 在视频展示的activity中，增加输入视频uri地址的功能，如在edittext中填了内容，点击播放按钮后，则会尝试打开该地址，否则会打开默认的视频
* 横竖屏切换、横屏时展示全屏模式，且不会重启activity或暂停播放
* 将app注册为播放器类型(Action为ACTION_VIEW，Data为Uri，Type为其MIME类型)，点击打开系统视频⽂件时，可以选择使⽤⾃制播放器，播放本地视频文件时，采用的播放标签为"播放本地视频"，目前经过测试已经可以打开大部分本地的视频文件，但少数.mp4文件未能正常打开，暂未找到原因

打包生成的.apk文件已经放在homework7目录下。

# Homework6

本程序已经实现当前project要求的所有功能

* 为 To-do List的场景建立一个数据库，完成数据库表的设计和创建
* 进入主页后，从数据库中查询出所有的数据，并以列表形式呈现出来
* 点击加号后跳转到一个新页面，输入任意内容，点击确认后把内容插入数据库，返回主页并更新主页数据
* 点击每个checkbox，能把该条note设置为"已完成"，并更新数据和ui，会将ui显示为删除样式
* 点击每个x能把该条note删除，并更新数据库和ui
* 增加优先级选项，不同优先级之前可用背景颜色区分出来并将优先级高的置于列表最前面，其中背景颜色高优先级为黄色，中优先级为蓝绿色，低优先级为白色。
* 自动保存草稿信息，将草稿信息存储在**文件**中，退出activity或应用后，均能恢复上次的输入内容以及之前选择的优先级

打包生成的.apk文件已经放在homework6目录下。

# Homework5

目前已经完成了除了TODO7以外的其它功能，在TODO6中使用异步的retrofit方式完成，但TODO7中参数格式调整仍未完成，遇到一些困难还未解决，在时间不够的情况下只能暂时放弃了。

打包生成的.apk文件已经放在homework5目录下。

# Homework4

已经根据作业要求实现如下的六个功能

1. 绘制时钟界面，包括时针、分针、秒针
2. 时针、分针、秒针需要跳动
3. 时针、分针、秒针的粗细、长短不同，并设置秒针颜色为红色
4. 绘制表盘上的数字
5. 用点绘制表盘刻度
6. 在表盘上半方添加文字，显示实时时间(xx:yy:zz)形式

打包生成的.apk文件已经放在homework4目录下。

# Homework3

已经根据作业要求实现如下的三个功能

1. 在CH3EX1 activity中，实现seekBar对lottie动画播放进度的操纵，以及通过CheckBox控制播放模式
2. 在CH3EX2 activity中，实现彩虹字体，scaleX,scaleY以及alpha的规则属性动画
3. 在CH3EX3 activity中，实现带有tap的fragment变化，其中动画会在出现后五秒钟时渐弱淡出，相应的聊天框会在五秒钟时淡入。由于聊天框部分的设计比较困难，所以这里复用了Homework2中的recyclerview进行了实现。

打包生成的.apk文件已经放在homework3目录下。

# Homework2

该程序主要实现了四个功能，分别由主activity的四个按钮支持

1. 跳转到Practice界面，该页面有toast的测试
2. 跳转打开百度页面
3. 跳转到拨号（需提供拨号权限）
4. 跳转到排行榜界面

由于水平有限，本人在仔细研究RecyclerViewActivity后，参照demo工程实现了一个排行榜。该排行榜仅在给的示例demo界面的基础上，增加了一行新的数据属性type。type由“新”和“热”两个字组成，确认在显示的时候会固定在最后热度的左边。

打包生成的.apk文件也已在homework2目录下。

# Homework1

第一次作业在文件夹demo中，其中实现了ImageView, TextView, EditText, CheckBox, Button 以及Switch 共6种控件
其中，点击按钮 ChangeVisibility可以改变字节跳动图片的可见性（点击一下消失再点击一下出现）
点击Click Counter可以记录点击该按钮的次数，但清零功能暂未实现
切换开关Big Model状态，能够改变中间TextView的显示字体大小
Password栏可以输入密码（暂时没有完成登录验证的功能）
打包的apk文件在demo中
