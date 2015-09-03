package in.notwork.notify.client.util;

import java.lang.reflect.InvocationTargetException;

/**
 * @author rishabh.
 */
public class Instance {

    /*
     * Code from:
     * http://stackoverflow.com/questions/18251299/creating-java-object-from-class-name-with-constructor-which-contains-parameters/18251429#18251429
     */
    public static <T> T newInstance(final String className, final Object... args)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // Derive the parameter types from the parameters themselves.
        Class[] types = new Class[args.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = args[i].getClass();
        }
        return (T) Class.forName(className).getConstructor(types).newInstance(args);
    }
}
