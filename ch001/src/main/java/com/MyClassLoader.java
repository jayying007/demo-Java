package com;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class MyClassLoader extends ClassLoader{
    //
    private final String classpath;

    public MyClassLoader(String classpath){
        this.classpath = classpath;
    }
    public MyClassLoader(ClassLoader loader, String classpath){
        //声明父类加载器，null则为Bootstrap ClassLoader
        super(loader);
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String absolutePath = this.classpath + getPath(name) + ".class";
        byte[] bytes = readFileAsByte(absolutePath);
        return this.defineClass(null, bytes, 0, bytes.length);
    }

    /**
     * 根据包名转换为路径
     * @param packageName 包名
     * @return 路径
     */
    private String getPath(String packageName){
        return packageName.replaceAll("\\.", "/");
    }

    private byte[] readFileAsByte(String name){
        byte[] data = null;
        //try（...）需要实现Closeable接口
        try (
                FileInputStream fis = new FileInputStream(name);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        )
        {
            int tmp;
            while ((tmp = fis.read()) != -1){
                baos.write(tmp);
            }
            data = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
