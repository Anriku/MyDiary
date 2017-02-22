# MyDiary<br>

## 寒假考核心得:<br>
这次的寒假考核，我选择了做MyDiary这个软件。开始把《第一行代码》基本上看完了，就准备去做软件了，然而真正去做一个软件的的时候才发现还有好多东西要去学，
最开始看看这些软件，简直是一头雾水，这个该怎么实现，那个该怎么实现，哎，我是不是读了假书，怎么书上还有这么多的东西没有。但是一步一步地对这些东西进行实
现，网上查看他人的博客，还是能做，而且越来越觉得Android是一个博大精深的东西，它所包含的东西绝不是一本书就能讲透的，最关键的是它还在不断的成长，成长得
越来越好，越来越人性化。下面是对寒假做的这个软件的功能和用到的知识点进行的一些简要分析。分析可能得并不完全。分析的过程我没有从主界面开始一步步分析，而
是从关键的地方开始进行分析。<br>

# 功能:<br>
* 1.写日记<br>
  * 记录日记的时间、日期。<br>
  * 记录日记的天气、心情。<br>
  * 记录日记的标题及内容。<br>
  * 对日记进行CRUD操作。<br>
  * 点击RecyclerView的item可以进行查看。<br>
  * 长按item可以进行日记的修改，以及删除。<br>
* 2.实现密码保护<br>
  * 密码的存，删，以及简单的找回密码功能。<br>
* 3.头像的设置<br>
  * 实现照相和从相册中取得两种头像的改换方式。<br>
* 4.对昵称以及信仰的修改<br>
  * 对昵称和信仰进行修改以及存储。
* 5.在主界面做了个轮播图<br>
  * 可以进行长按来通过从相册中选取图片来改变轮播的图片，添加属于自己的照片墙。<br>
* 6.在设置中应用了一些简单的设置<br>
  * 对启动页的经典语录进行了设置。<br>
  * 一共有三个地方会用到密码，考虑到密码多了会让人输着烦，于是在设置中进行了密码的选择，选择哪些地方出现密码。<br>
  * 对主界面的轮播图时间进行了自定义。<br>
  
# 用到的知识点:<br>
* 1.日记方面:<br>
  * ViewPager+RadioGroup+Fragment进行组合。RadioGroup通过selector设置了选中和没选中的不同颜色以及通过其中Shape的设置设置成了一个ios类型的<br>
  Button。<br>
  * 通过Spinner进行天气以及心情的图标选择。Spinner通过自定义一个布局并在Adapter中的getView()方法中进行实例化其中的控件来实现图片的选择。<br>
  * 在项目的碎片中进行RecyclerView的使用并实现item的点击查看以及长按进行修改和删除，在查看的过程从从一个activity到另一个activity，要进行存储<br>
  的数据传递这里通过Bundle类的putSerializable()来进行对象的传递。<br>
  * 通过Calendar进行时间、日期的获取,由于多处会用到所以自定义了一个类。<br>
  * 由于在项目碎片中进行修改以及删除后UI不能立即更新。最初在碎片的onCreateView中用RecyclerView的Adapter来notififyDataSetChange()。但还是不能立即
  更新，于是想了想Fragment的生命周期在onResume中进行同样的操作，但还是不行。由于碎片依赖于Activity，于是，想在Activity中kill掉那个Fragment来进行
  从新开始Fragment，这样弄了下还是不行。实在那它没辙了，于是在网上找了找，发现在Fragment依赖的Acitivity中的onResume()方法中进行ViewPager的
  Adapter的notifyDataSetChange(),这样删除立即进行UI的更新，但是修改又不行了。于是继续在网上看了看他们的解决办法，发现在RecyclerView的Adapter中进
  行ViewPager的Apater的notifyDataSetChange能行于是就这样做了,确实行得通。只是我把ViewPager和Adapter设置成了static，这样好像会破坏实例域的安全，
  不知道是不是。本来不想这样的，但用了下前面Bundle的方法来传，但好像还是不得行，才这么做的。啊!心好累，真是个让人苦笑不得的debug过程。<br>
  * 对日记通过LitePal进行CRUD操作<br>
* 2.密码方面:<br>
  * 通过SharedPreferences进行密码的存储，以及读取。<br>
  * 万一忘了密码设置了一个找回密码的方式，由于没有通过网络来实现找回密码的方式，所以还是有可能把问题也忘了，这样密码无法找回就很尴尬了。<br>
* 3.轮播图方面:<br>
  * ViewPager中每个是一个ImageView，这个比较简单和上面的ViewPager中每个存放一个Fragment差不多,只不过一个用的是PagerAdapter一个用的是<br>
  FragmentPagerAdapter。<br>
  * 在drawable中通过xml文件来定义了两个小圆点，一个是被选中的点，一个是普通情况下。<br>
  * 通过ScheduleExecutorService类来实现定时功能。要让其在前台不断更新就将其在onStart的方法中，使用ScheduleExecutorService的<br>
  scheduleAtFixedRate()方法在一个子线程中进行该显示的位置的处理，UI的更新通过Handler来进行更新。<br>
  * 对ViewPager的每个图片设置了长按点击事件来进行图片的切换，切换后同LitePal来进行图片的存储。只不过最开始傻乎乎的把Bitmap对象拿进去存了。就这样问
  题又来了，“我的天，饶了我吧，为什么又闪了”。网上查了一下，哦，原来LitePal不支持图片的存储。那不能图片不能存呀，我要持久化呀。后来又查了下才发现，
  LitePal支持把图片转化为byte数组来进行存储。<br>
  * 从相册中选去图片进行处理。这个后面在头像选择中一起说。<br>
* 4.头像的选择:<br>
  * 进行照相选择头像并进行处理后生成头像。这里通过Uri的获取实现了Android 7.0后通过FileProvider和之前通过Uri.parseFromFile()两种方法获取<br>
  * 进行从相册中选择图片，从相册中选图片加入了运行时权限。并实现了Android 4.4前后两种不同的获取图片路径的方法。在图片获取后，一般图片还好说。但是,照
  片就太大了会造成手机的卡顿，于是乎，就要对图片进行压缩。最开始通过对图片的质量进行压缩，但是，不管怎么弄都不行，差点还以为不是压缩的问题，结果进行
  比例压缩就行了。但还是不懂为什么按质量进行压缩就不行。哎!<br>
  * 这里也通过LitePal对图片进行了储存，以及重启软件的恢复。<br>
  * 图片的选择以及前面的图片选择进行了一下封装，写成了一个ImageUtils的类，可能封装得不怎么好。<br>
* 5.昵称和信仰方面:<br>
  * 通过SharedPreferences对其进行存储和恢复。<br>
* 6.设置方面:<br>
  * 通过SharedPreferences对启动页经典语录进行修改和存储。密码选择的存储和下面轮播图时间的存储由于都是一些比较简单的东西所以都采用<br>
  了SharedPreferences来进行存储。<br>
* 7.主界面方面:<br>
  * 主要才用了DrawerLayout为主体来进行功能的展开。<br>
  * 这里面又有了一些坑，开始傻傻的直接用findViewById()来获取NavigationView中HeaderView的控件，结果等我把一切都弄好了，一点头像为什么不行呀，呵
  呵，大哥别吓我了，又点点昵称。哎!还是不行，查查才发现NavigationView中的控件要通过NavigtionView的实例进行getHeaderView()来实例化一个HeaderView,
  再通过这个View来findViewById()来实例化里面的控件。这和Dialog通过自定义Layout来实例化里面的控件是一样的。<br>
  * 通过覆盖onKeyDown()方法并结合Handler来进行了按两次Back键退出应用，防止用户按错。<br>
