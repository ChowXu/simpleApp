package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/17 下午5:02
 * load config.properties
 * return a property
 */
public class PropUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropUtil.class);

    public static Properties loadProps(String filename) {
        Properties props = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (is == null) {
                throw new FileNotFoundException(filename + "not found");
            }
            props = new Properties();
            props.load(is);

        } catch (IOException e) {
            logger.error("load property is error" + e);
        } finally {

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.info("close inputStream failure" + e);
                }
            }

        }
        return props;

    }


}
