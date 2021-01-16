#### 类加载器的层级结构
自定义类加载器 --> APPClassLoader --> ExtClassLoader --> BootstrapLoader
#### 代码分析
根据双亲委托机制，loader1不能加载，因为当前项目中有com.Cat，所以AppClassLoader已经加载好了；
loader2指定了双亲加载器为null，即BootstrapClassLoader，而其又不能加载com.Cat，所以MyClassLoader得以加载。
