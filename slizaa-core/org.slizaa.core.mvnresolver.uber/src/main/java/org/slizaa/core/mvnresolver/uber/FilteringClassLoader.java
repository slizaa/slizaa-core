package org.slizaa.core.mvnresolver.uber;

/**
 *
 */
public class FilteringClassLoader extends ClassLoader {

    //
    private static final ClassLoader EXTENSION_CLASS_LOADER;

    //
    private String[] _regexps;

    static {
        EXTENSION_CLASS_LOADER = ClassLoader.getSystemClassLoader().getParent();

        try {
            ClassLoader.registerAsParallelCapable();
        } catch (NoSuchMethodError ignore) {
            // Not supported on Java 6
        }
    }

    /**
     *
     */
    private static class RetrieveSystemPackagesClassLoader extends ClassLoader {

        RetrieveSystemPackagesClassLoader(ClassLoader parent) {
            super(parent);
        }

        protected Package[] getPackages() {
            return super.getPackages();
        }
    }

    public FilteringClassLoader(ClassLoader parent, String... regexps) {
        super(parent);

        _regexps = regexps;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        //
        try {
            return EXTENSION_CLASS_LOADER.loadClass(name);
        } catch (ClassNotFoundException ignore) {
            // ignore
        }

        //
        if (!isClassAllowed(name)) {
            throw new ClassNotFoundException(name + " not found.");
        }

        //
        Class<?> cl = super.loadClass(name, false);
        if (resolve) {
            resolveClass(cl);
        }

        //
        return cl;
    }

    @Override
    public String toString() {
        return FilteringClassLoader.class.getSimpleName() + "(" + getParent() + ")";
    }

    /**
     * @param className
     * @return
     */
    private boolean isClassAllowed(String className) {

        //
        for (String regexp : _regexps) {
            if (className.matches(regexp)) {
                return true;
            }
        }

        //
        return false;
    }
}
