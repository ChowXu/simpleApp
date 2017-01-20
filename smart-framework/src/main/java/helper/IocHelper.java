package helper;

import annotation.Inject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/20 下午11:56
 */
public class IocHelper {

    static {
        //获得所有的map 映射
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            //遍历所有的映射
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                //遍历Class 的field
                Field[] beanFeilds = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFeilds)) {
                    for (Field field : beanFeilds) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = field.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化 beanField 的值
                                ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
                            }

                        }
                    }
                }

            }

        }
    }
}
