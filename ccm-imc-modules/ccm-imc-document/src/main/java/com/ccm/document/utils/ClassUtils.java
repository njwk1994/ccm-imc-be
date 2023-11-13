
package com.ccm.document.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

/**
 * class operation utils.
 *
 * @author kekai.huang
 */
@Slf4j
@SuppressWarnings("all")
public final class ClassUtils {

    public static void loadJar(String jarPath) {
        File jarFile = new File(jarPath);
        // 从URLClassLoader类中获取类所在文件夹的方法，jar也可以认为是一个文件夹
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e) {
            log.info("load class error ", e);
        }
        //获取方法的访问权限以便写回
        boolean accessible = method.isAccessible();
        try {
            method.setAccessible(true);
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
            URL url = jarFile.toURI().toURL();
            method.invoke(classLoader, url);
        } catch (Exception e) {
            log.info("load class error ", e);
        } finally {
            method.setAccessible(accessible);
        }
    }

    /**
     * Finds and returns class by className.
     *
     * @param className String value for className.
     * @return class Instances of the class represent classes and interfaces.
     */
    public static Class findClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            throw new RuntimeException("this class name not found", e);
        }
    }

    /**
     * Determines if the class or interface represented by this object is either the same as, or is a superclass or
     * superinterface of, the class or interface represented by the specified parameter.
     *
     * @param clazz Instances of the class represent classes and interfaces.
     * @param cls   Instances of the class represent classes and interfaces.
     * @return the value indicating whether objects of the type can be assigned to objects of this class.
     */
    public static boolean isAssignableFrom(Class clazz, Class cls) {
        Objects.requireNonNull(cls, "cls");
        return clazz.isAssignableFrom(cls);
    }
    
    public static <T> Class<T> resolveGenericType(Class<?> declaredClass) {
        return (Class<T>) ResolvableType.forClass(declaredClass).getSuperType().resolveGeneric(0);
    }
    
    public static <T> Class<T> resolveGenericTypeByInterface(Class<?> declaredClass) {
        return (Class<T>) ResolvableType.forClass(declaredClass).getInterfaces()[0].resolveGeneric(0);
    }
    
    public static String getName(Object obj) {
        Objects.requireNonNull(obj, "obj");
        return obj.getClass().getName();
    }
    
    public static String getCanonicalName(Object obj) {
        Objects.requireNonNull(obj, "obj");
        return obj.getClass().getCanonicalName();
    }
    
    public static String getSimplaName(Object obj) {
        Objects.requireNonNull(obj, "obj");
        return obj.getClass().getSimpleName();
    }
    
    public static String getName(Class cls) {
        Objects.requireNonNull(cls, "cls");
        return cls.getName();
    }
    
    public static String getCanonicalName(Class cls) {
        Objects.requireNonNull(cls, "cls");
        return cls.getCanonicalName();
    }
    
    public static String getSimplaName(Class cls) {
        Objects.requireNonNull(cls, "cls");
        return cls.getSimpleName();
    }
    
}