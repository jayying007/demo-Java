package factory;

import annotation.Autowire;
import annotation.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * @author: jane
 * @CreateTime: 2020/5/6
 * @Description:
 */
public class Factory {
    public HashMap<String, Object> hashMap = new HashMap<>();
    private final String xmlPath;
    private String packetName;
    public Factory(String xmlPath){
        this.xmlPath = xmlPath;
        initXml();
        initAnnotation();
    }
    private void initXml(){
        try{
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new File(xmlPath));
            //获取包扫描路径
            NodeList nl = doc.getElementsByTagName("packet-scan");
            Node classNode = nl.item(0).getFirstChild();
            packetName = classNode.getNodeValue().trim();
            //创造喵星人~
            NodeList beans = doc.getElementsByTagName("bean");
            for(int i=0;i<beans.getLength();i++){
                Node bean = beans.item(i);
                Element element = (Element)bean;
                //获取bean的id和class
                String key = element.getAttribute("id");
                String className = element.getAttribute("class");
                //
                Class<?> clz = Class.forName(className);
                Object obj = clz.getDeclaredConstructor().newInstance();
                hashMap.put(key, obj);
                //注入bean的属性值
                NodeList nodeList = element.getElementsByTagName("property");
                for(int j=0;j<nodeList.getLength();j++){
                    Element node = (Element)nodeList.item(j);
                    String propertyName = node.getAttribute("name");
                    String propertyValue = node.getFirstChild().getNodeValue();
                    //自动注入变量
                    Field field = obj.getClass().getDeclaredField(propertyName);
                    field.setAccessible(true);//可注入private
                    field.set(obj, propertyValue);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initAnnotation(){
        packetScan("ch008/target/classes/"+packetName.replaceAll("\\.", "/"));
    }
    public Object get(String key){
        return hashMap.get(key);
    }
    //扫描路径下所有类，通过放射获取注解
    public void packetScan(String path){
        File file = new File(path);
        if(file.isFile()){
            try {
                //处理绝对路径，转换为包名，使反射能够获取这个class文件
                String className = produce(path);
                Class<?> clz = Class.forName(className);
                //找到有添加注解的class
                if(clz.isAnnotationPresent(Component.class)){
                    Object obj = clz.getDeclaredConstructor().newInstance();
                    hashMap.put(clz.getSimpleName(), obj);
                    //自动注入变量
                    Field[] fields = obj.getClass().getDeclaredFields();
                    for(Field field : fields){
                        if(field.getDeclaredAnnotation(Autowire.class) != null){
                            field.setAccessible(true);//可注入private
                            field.set(obj, hashMap.get(field.getName()));
                        }
                    }
                }
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else{
            String[] fileNames = file.list();
            assert fileNames != null;
            for(String name: fileNames){
                packetScan(path+"/"+name);
            }
        }
    }
    public String produce(String path){
        String tmp1 = path.replaceAll("/", ".");
        String tmp2 = tmp1.substring(tmp1.lastIndexOf(packetName),tmp1.length()-".class".length());
        return tmp2;
    }
}
