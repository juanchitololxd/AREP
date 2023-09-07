package eci.arep;

import eci.arep.anotaciones.Componente;
import eci.arep.anotaciones.GetMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ComponentLoader {
    static Map<String, Method> services = new HashMap<>();

    public static void main(String[] args) throws Exception {
        loadComponents(new String[]{"eci.arep.HelloServices"});
        System.out.format("resultado: %s", ejecutar("/hola", "a"));
    }

    public static byte[] ejecutar(String arg, String param) throws Exception {
        return (byte[]) services.get(arg).invoke(null,param);
    }

    public static void loadComponents(String[] args) throws ClassNotFoundException {
        for (String arg : args) {
            Class<?> c = Class.forName(arg);
            
            if (!c.isAnnotationPresent(Componente.class)) continue;
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(GetMapping.class)) continue;
                String ruta = method.getAnnotation(GetMapping.class).value();
                System.out.format("Metodo %s tiene getMapping\n", method.getName());
                services.put(ruta, method);
                
            }
        }
    }
}
