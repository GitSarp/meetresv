package com.meeting.meetresv.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectUtil {


    private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);
//    private static final Logger logger = Logger.getLogger(ReflectUtil.class);

    public static Object invokeSetMethod(Class<?> claszz, Object o, String name, Class<?>[] argTypes, Object[] args) {
        Object ret = null;
        try {
            // 非 常量 进行反射
            if (!checkModifiers(claszz, name)) {
                Method method = claszz.getMethod("set" + StringUtil.firstCharUpperCase(name), argTypes);
                ret = method.invoke(o, args);
            }
        } catch (Exception e) {
            //logger.error(e.toString(), e);
            logger.error("claszz:" + claszz + ",name:" + name + ",argType:"
                    + argTypes + ",args:" + args);
        }
        return ret;
    }

    /**
     * 调用set方法
     * @param target
     * @param methodName
     * @param paramType
     * @param val
     * @return
     */
    public static Object invokeSetM(Object target,String methodName,Class<?> paramType,Object val) {
        Object ret= null;
        try {
            Method method=target.getClass().getMethod(methodName,paramType);
            ret = method.invoke(target,val);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 校验参数类型
     * 目前只校验是否为 常量
     * @param claszz
     * @param name
     * @return 常量返回true，非常量返回false
     */
    private static boolean checkModifiers(Class<?> claszz, String name) {
        try {
            Field field = claszz.getField(name);
            if (isConstant(field.getModifiers())) {
                return true;
            }
        } catch (Exception e) {
        //} catch (NoSuchFieldException | SecurityException e) {
            return false;
        }
        return false;
    }

    /**
     * 是否为常量
     * @param modifiers
     * @return 常量返回true，非常量返回false
     */
    public static boolean isConstant(int modifiers) {
        // static 和 final修饰
        if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
            return true;
        }
        return false;
    }
}
