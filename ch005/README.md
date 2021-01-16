### 简介
> 为了方便，这里采用maven辅助编译

一开始只有reader项目，编译后执行命令
```
java -cp ch005/reader/target/classes/ com.App text/csv 1,2,3
```
因为没有相关实现，所以会报错
```
Exception in thread "main" java.lang.Exception: Not supported encoding:text/csv
        at DecoderFactory.getDecoder(DecoderFactory.java:15)
        at App.main(App.java:12)
```
接下来新建csv项目，新建类CSVReader，并实现Decoder接口，然后编译(由于需要Decoder类，所以要导入reader的jar包，即需要包含Decoder类)  
然后在csv的class目录下创建META-INF/services/com.Decoder,内容写入decoder.CSVReader  
下面执行命令
```
java -cp reader/target/classes/:csv/target/classes/ com.App text/csv 1,2,3
```
此时可以看到结果
```
converted result=[1, 2, 3]
```
### 参考资料