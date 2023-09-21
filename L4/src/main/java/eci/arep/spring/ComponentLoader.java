package eci.arep.spring;

import eci.arep.spring.anotaciones.Componente;
import eci.arep.spring.anotaciones.GetMapping;
import eci.arep.spring.anotaciones.RequestParam;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ComponentLoader {
    Map<String, Method> services = new HashMap<>();

    public String getContentType(String endpoint) {
        System.out.println(endpoint);
        return services.get(endpoint).getAnnotation(GetMapping.class).contentType();
    }

    public byte[] ejecutar(String endpoint, Map<String, String> queryParams) throws Exception {
        Method method =services.get(endpoint);
        Object[] params = getParams(method, queryParams);
        if (params.length == 0) params = null;
        return (byte[]) method.invoke(null, params);
    }


    /**
     * Recorre los parametros del metodo, y si tiene una anotación, la asigna en el arreglo
     */
    private static Object[] getParams(Method method, Map<String, String> params){
        Object[] rta = new Object[method.getParameters().length];
        for (int i = 0; i < method.getParameters().length; i++) {
            if (method.getParameters()[i].isAnnotationPresent(RequestParam.class)){
                System.out.println(method.getParameters()[i].getName());
                rta[i] = params.get(method.getParameters()[i].getName());
            }
        }
        return rta;
    }

    public void load(String className) throws ClassNotFoundException {
        Class<?> c = Class.forName(className);

        if (c.isAnnotationPresent(Componente.class)) {
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(GetMapping.class)) continue;
                String ruta;
                ruta = method.getAnnotation(GetMapping.class).value();
                services.put(ruta, method);
            }
        }
    }
}
