package eci.arep;

import eci.arep.spring.anotaciones.Componente;
import eci.arep.spring.anotaciones.GetMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ComponentLoader {
    static Map<String, Method> services = new HashMap<>();

    public static byte[] ejecutar(String arg, String param) throws Exception {
        return (byte[]) services.get(arg).invoke(null,param);
    }

    public static void load(String className) throws ClassNotFoundException {
        Class<?> c = Class.forName(className);

        if (c.isAnnotationPresent(Componente.class)) {
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(GetMapping.class)) continue;
                String ruta = method.getAnnotation(GetMapping.class).value();
                services.put(ruta, method);
            }
        }
    }
}
