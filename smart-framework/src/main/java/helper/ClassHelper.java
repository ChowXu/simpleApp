package helper;

import annotation.Controller;
import annotation.Service;
import configuration.ConfigHelper;
import util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/20 下午9:38
 * <p>
 * <p>
 * 获取指定包下的类
 * service controller inject
 */
public class ClassHelper {


    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }


    /**
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }


    /**
     * Service 类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> resultSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                resultSet.add(cls);
            }
        }
        return resultSet;
    }

    /**
     * Controller 类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> resultSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                resultSet.add(cls);
            }
        }
        return resultSet;
    }

    /**
     * get all beans(service and controller
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> resultSet = new HashSet<Class<?>>();
        resultSet.addAll(getControllerClassSet());
        resultSet.addAll(getServiceClassSet());
        return resultSet;
    }

}
