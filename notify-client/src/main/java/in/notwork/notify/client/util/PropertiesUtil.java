package in.notwork.notify.client.util;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * @@author rishabh <br/>
 * http://howtodoinjava.com/2012/10/19/auto-reload-configuration-when-
 * any-change-happen-part-2 <br>
 * http://stackoverflow.com/questions/11747453/loading-updated-
 * properties-automatically-from-multiple-properties-files
 */
public final class PropertiesUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtil.class);
    private static CompositeConfiguration compositeConfig = null;

    /**
     * The Constant NOTIFY_PROPERTIES.
     */
    private static final String NOTIFY_PROPERTIES = "notify.properties";

    /**
     * The Constant files.
     */
    private static final String[] files = {
            NOTIFY_PROPERTIES
    };

    static {

        try {
            compositeConfig = new CompositeConfiguration();
            PropertiesConfiguration configuration = null;

            for (String file : files) {
                LOG.debug("Reading properties file ...{0}", file);
                configuration = new PropertiesConfiguration();
                configuration.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(file));
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
                compositeConfig.addConfiguration(configuration);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("The following properties have been loaded...");
                Iterator<String> keys = compositeConfig.getKeys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    LOG.debug("    " + key + " = " + getPropList(key));
                }
            }
        } catch (ConfigurationException ce) {
            LOG.error("Error reading properties.", ce);
        }
    }

    /**
     * Instantiates a new properties helper.
     */
    private PropertiesUtil() {

    }

    /**
     * Get property for the given key.
     *
     * @param key The property key
     * @return The property value
     */
    public static synchronized String getProperty(final String key) {
        return (String) compositeConfig.getProperty(key);
    }

    /**
     * Gets the int property.
     *
     * @param key the key
     * @return the int property
     */
    public static synchronized int getIntProperty(final String key) {
        return compositeConfig.getInt(key);
    }

    /**
     * Gets the long property.
     *
     * @param key the key
     * @return the long property
     */
    public static synchronized long getLongProperty(final String key) {
        return compositeConfig.getLong(key);
    }

    /**
     * Gets the string array.
     *
     * @param key the key
     * @return the string array
     */
    public static synchronized String[] getStringArray(final String key) {
        return compositeConfig.getStringArray(key);
    }

    /**
     * Gets the prop list.
     *
     * @param key the key
     * @return the prop list
     */
    public static synchronized List getPropList(final String key) {
        return compositeConfig.getList(key);
    }

    /**
     * Gets the boolean property.
     *
     * @param key the key
     * @return the boolean property
     */
    public static synchronized Boolean getBooleanProperty(final String key) {
        return compositeConfig.getBoolean(key);
    }
}
