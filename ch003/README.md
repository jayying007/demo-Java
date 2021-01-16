#### 什么是反射？
程序运行时，能够动态加载类，获得和执行其所有属性和方法。
#### 如何读取一个类
方法一：
```
Class person = Person.class;
```
方法二：
```
try {
	Class person = Class.forName("com.jane.Person");
} catch (ClassNotFoundException e) {
	e.printStackTrace();
}
```
> 在实际开发中，方法二用得比较多，因为不需要显式获取一个类，可以很方面后期修改，算是一种解耦，保证开闭原则。
> 此外，方法二还能批量读取类。

比如下面这个例子
```xml
<root>
	<className>com.jane.Person</className>
</root>
```
```
String name = Xml.getClassName();
try {
	Class person = Class.forName(name);
} catch (ClassNotFoundException e) {
	e.printStackTrace();
}
```
如果要实例化其他类，改一下xml的配置信息即可
#### 动态生成类
```
Class personClass = Class.forName("Person");
Person person = personClass.newInstance();
```
jdk1.9后淘汰上面这种方法，因为只能调用无参构造器；