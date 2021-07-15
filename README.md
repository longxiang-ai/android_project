# Homework1
第一次作业在文件夹demo中，其中实现了ImageView, TextView, EditText, CheckBox, Button 以及Switch 共6种控件
其中，点击按钮 ChangeVisibility可以改变字节跳动图片的可见性（点击一下消失再点击一下出现）
点击Click Counter可以记录点击该按钮的次数，但清零功能暂未实现
切换开关Big Model状态，能够改变中间TextView的显示字体大小
Password栏可以输入密码（暂时没有完成登录验证的功能）
打包的apk文件在demo中

# Homework2

该程序主要实现了四个功能，分别由主activity的四个按钮支持

1. 跳转到Practice界面，该页面有toast的测试
2. 跳转打开百度页面
3. 跳转到拨号（需提供拨号权限）
4. 跳转到排行榜界面

由于水平有限，本人在仔细研究RecyclerViewActivity后，参照demo工程实现了一个排行榜。该排行榜仅在给的示例demo界面的基础上，增加了一行新的数据属性type。type由“新”和“热”两个字组成，确认在显示的时候会固定在最后热度的左边。

打包生成的.apk文件也已在homework2目录下。

# Homework3

已经根据作业要求实现如下的三个功能

1. 在CH3EX1 activity中，实现seekBar对lottie动画播放进度的操纵，以及通过CheckBox控制播放模式
2. 在CH3EX2 activity中，实现彩虹字体，scaleX,scaleY以及alpha的规则属性动画
3. 在CH3EX3 activity中，实现带有tap的fragment变化，其中动画会在出现后五秒钟时渐弱淡出，相应的聊天框会在五秒钟时淡入。由于聊天框部分的设计比较困难，所以这里复用了Homework2中的recyclerview进行了实现。

打包生成的.apk文件已经放在homework3目录下。

# Homework4

已经根据作业要求实现如下的六个功能

1. 绘制时钟界面，包括时针、分针、秒针
2. 时针、分针、秒针需要跳动
3. 时针、分针、秒针的粗细、长短不同，并设置秒针颜色为红色
4. 绘制表盘上的数字
5. 用点绘制表盘刻度
6. 在表盘上半方添加文字，显示实时时间(xx:yy:zz)形式

打包生成的.apk文件已经放在homework4目录下。
