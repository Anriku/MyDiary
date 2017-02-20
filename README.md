# MyDiary<br>
## 寒假考核心得
这次的寒假考核，我选择了做MyDiary这个软件。感觉真正去做一个软件的的时候还有好多东西要去学，开始拿到这些东西，简直是一头雾水。<br>
但是一步一步地对这些东西进行实现，还是能做，虽然感觉自己做的东西不是很好，但还是觉得有进步了。<br>
# 功能:<br>
* 1.写日记<br>
  * 记录日记的时间、日期。<br>
  * 记录日记的天气、心情。<br>
  * 记录日记的标题及内容。<br>
* 2.存日记<br>
  * 对日记进行CRUD操作。<br>
  * 点击RecyclerView的item可以进行查看。<br>
  * 长按可以进行日记的修改，以及删除。<br>
* 3.实现密码保护<br>
  * 密码的存，删，以及简单的找回密码功能。<br>
* 4.头像的设置<br>
  * 实现照相和从相册中取得两种头像的改换方式。<br>
* 5.对昵称以及信仰的修改<br>
* 6.在主界面做了个轮播图<br>
  * 可以进行长按来通过从相册中选取图片来改变轮播的图片<br>
# 用到的知识点:<br>
* 1.日记方面:<br>
  * ViewPager+TabLayout+Fragment进行组合。<br>
  * 通过Spinner进行天气以及心情的图标选择。<br>
  * 在项目的碎片中进行RecyclerView的使用并实现item的点击以及长按事件。<br>
  * 通过Calendar进行时间、日期的获取,由于多处会用到所以自定义了一个类。<br>
  * 由于在项目碎片中进行修改以及删除后UI不能立即更新。最初在碎片的onCreateView中用ViewPager的Adapter来notififyDataSetChange。<br>
  但还是不能立即更新于是通过生命周期在onResume中进行同样的操作。这样删除立即进行UI的更新，但是修改又不行了。这次实在拿它没折了，<br>
  于是在网上看了看他们的解决办法，发现在RecyclerView的Adapter中进行ViewPager的Apater的notifyDataSetChange能行于是就这样做了。<br>
  只是我通过RecyclerView的new Adapter在构造器中添加ViewPager和Adapter进去却不行，就把ViewPager和Adapter设置成了static，这样<br>
  好像会破坏实例域的安全。<br>
  * 对日记通过LitePal进行CRUD操作<br>
* 2.密码方面:<br>
  * 通过SharedPreferences进行密码的存储，以及读取。<br>
  * 万一忘了密码设置了一个找回密码的方式，由于没有通过网络来实现找回密码的方式，所以还是有可能把问题也忘了，这样密码无法找回就很尴尬了。<br>
* 3.轮播图方面:<br>
  * ViewPager中每个是一个ImageView，这个比较简单和上面的ViewPager中每个存放一个Fragment差不多。<br>
  * 在drawable中通xml文件来定义了两个小圆点，一个是被选中的点，一个是普通情况下。<br>
  * 通过ScheduleExecutorService类来实现定时功能，要让其在前台不断更新就将其在onStart的方法中，但在这里面只是进行该显示的位置的处理。<br>
  UI的更新同过Handler来进行更新。
  * 对ViewPager的每个图片设置了长按点击事件来进行图片的切换，切换后同LitePal来进行图片的存储。只不过最开始傻乎乎的把Bitmap对象拿进去存了。<br>
  就这样问题又来了，“我的天，饶了我吧，为什么又闪了”。网上查了一下才知道，哦，原来LitePal不支持图片的存储。那不能图片不能存呀，我要持久化呀。<br>
  后来又查了下才发现，LitePal支持把图片转化为byte数组来进行存储。
  * 从相册中选去图片进行处理。这个后面在头像选择中一起说。
