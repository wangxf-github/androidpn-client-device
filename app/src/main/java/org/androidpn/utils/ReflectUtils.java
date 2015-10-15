package org.androidpn.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Saber on 2015/10/15.
 */
public class ReflectUtils {

    public void getClass(String className){
        Class<?> c= null;
        try {
            c = Class.forName(className);
            Method[] md=c.getMethods();//获取类中所以方法，包括继承的。
            //   Method[] md=c.getDeclaredMethods();//获取自定义的方法
            for(Method m:md){
                String mid= Modifier.toString(m.getModifiers());//获取修饰符
                Class<?>[] cl=m.getParameterTypes();//获取参数类型
                System.out.print(mid+"\t"+m.getReturnType().getName()+"\t"+m.getName()+"(");
                for(Class cs:cl){
                    System.out.print(cs.getName()+",");
                }
                System.out.println("){}");


                Object obj=c.newInstance();
                Method method=c.getMethod(md[1].getName(), new Class[]{Class.forName("java.lang.String")});
                method.invoke(obj, new Object[]{"jxy"});//调用类JavaBean中的setName(String name)方法

                Method mh=c.getMethod(md[0].getName());
                System.out.println("\n调用JavaBean中的getName()方法---------->"+mh.invoke(obj));//调用类JavaBean中的getName()方法

                Method mo=c.getMethod(md[8].getName(), new Class[]{Class.forName("java.lang.String"),String.class,Class.forName("java.lang.String"),Integer.TYPE,long.class,Float.TYPE,double.class});
                mo.invoke(obj, new Object[]{"jxy","wk","fd",23,34L,45F,56D});//调用类JavaBean中的zidingyi(String str1,String str2,String str22,int i3,long i4,float i5,double i6)方法
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
