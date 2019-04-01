package com.voidstar.base.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 邹强
 * @date 17-5-12
 */
public class BeanMapUtils {

//    public static final Object mapToBean(Class<?> type, Map<String, ? extends Object> map) throws IntrospectionException, IllegalAccessException,  InstantiationException, InvocationTargetException {
//        BeanInfo beanInfo = Introspector.getBeanInfo(type)
//        Object obj = type.newInstance()
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors()
//        for (int i = 0; i< propertyDescriptors.length; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i]
//            String propertyName = descriptor.getName()
//            if (map.containsKey(propertyName)) {
//                Object value = map.get(propertyName)
//                Object[] args = new Object[1]
//                args[0] = value
//                descriptor.getWriteMethod().invoke(obj, args)
//            }
//        }
//        return obj
//    }
//
//    public static Map<String, String> beanToMap(Object obj) {
//
//        if(obj == null){
//            return null
//        }
//        Map<String, Object> map = new HashMap<String, String>()
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass())
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors()
//            for (PropertyDescriptor property : propertyDescriptors) {
//                String key = property.getName()
//
//                // 过滤class属性
//                if (!key.equals("class") && !key.equals("metaClass")) {
//                    // 得到property对应的getter方法
//                    Method getter = property.getReadMethod()
//                    String value = getter.invoke(obj)
//
//                    map.put(key, value)
//                }
//
//            }
//        } catch (Exception e) {
//            System.out.println("Bean To Map Error " + e)
//        }
//
//        return map
//    }
}
