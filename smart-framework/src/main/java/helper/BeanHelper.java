package helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/20 下午11:02
 */
public class BeanHelper {

    /**
     * 定义bean 映射
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    private static final Logger logger = LoggerFactory.getLogger(BeanHelper.class);

    static{
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> cls: beanClassSet){
            Object obj = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls,obj);
        }
    }

    /**
     * 获取 bean 映射
     */

    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }


    /**
     *  获取bean 实例
     */

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            logger.error("getBean failure");
        }
        return (T) getBeanMap().get(cls);
    }








}

