package util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/19 下午10:48
 */
public class ClassUtil {

    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    //get classloader

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    //load class ===  get Class<> 对象
    public static Class<?> loadClass(String classname, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(classname, isInitialized, getClassLoader());
        } catch (Exception e) {
            //load class failure
            logger.error("load class failure");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return cls;
    }

    //get the class of all class
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {

            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    logger.info(url.toString());
                    String protocol = url.getProtocol();
                    //file  文件
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath().replace("%20", "");
                        addClass(classSet, packagePath, packageName);
                    }
                    // jar 文件的处理
                    else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> entries = jarFile.entries();
                                while (entries.hasMoreElements()) {
                                    JarEntry jarEntry = entries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }

                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            logger.info("classloader getResource failure", e);
        }
        return classSet;
    }


    public static void addClass(Set<Class<?>> set, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {

                return file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
            }
        });
//        logger.info(Arrays.toString(files) + " ========");
        for (File file : files) {

            String fileName = file.getName();
            logger.info(fileName);
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (!className.endsWith("$1")) {
                    logger.info(className);
                    if (StringUtils.isNotEmpty(packageName)) {
                        className = packageName + "." + className;
                    }
                    doAddClass(set, className);
                }
            } else {
                String subPackageName = packageName;
                if (StringUtils.isNotEmpty(subPackageName)) {
                    subPackageName = packageName + "." + fileName;
                }
                String subPackagePath = packagePath;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + fileName;
                }
                addClass(set, subPackagePath, subPackageName);
            }
        }
    }


    public static void doAddClass(Set<Class<?>> set, String classname) {
        Class<?> cls = loadClass(classname, false);
        set.add(cls);
    }


    public static void main(String[] args) {

        logger.info(Arrays.toString(getClassSet("util").toArray()));
//        Set<Class<?>> classSet = new HashSet<Class<?>>();
//        addClass(classSet,"/Users/zhouxi/Documents/IDEA/Web/simpleApp/smart-framework/src/main/java/configuration","" );
    }


}
