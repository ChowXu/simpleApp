package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/20 下午9:21
 * 依赖注入注解
 */


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

}
