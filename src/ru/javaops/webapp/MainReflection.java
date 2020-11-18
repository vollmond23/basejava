package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Resume r = new Resume("NEW RESUME");
        Class<? extends Resume> resumeClass = r.getClass();
        Field field = resumeClass.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println("Field name: " + field.getName());
        System.out.println("Field value: " + field.get(r));
        field.set(r, "new_uuid");
        System.out.println("r.toString: " + r);

        Method method = resumeClass.getDeclaredMethod("toString");
        System.out.print("r.toString via reflection: ");
        method.setAccessible(true);
        System.out.println(method.invoke(r));
    }
}
