package com.talent.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class SimpleMapper {

    /**
     * Converte um objeto de origem para um objeto de destino
     * @param source Objeto de origem (VO ou DTO)
     * @param targetClass Classe do objeto de destino (DTO ou VO)
     * @return Objeto convertido
     * @throws Exception Se ocorrer erro durante a conversão
     */
    public static <T> T convert(Object source, Class<T> targetClass) throws Exception {
        if (source == null) {
            return null;
        }

        T target = targetClass.getDeclaredConstructor().newInstance();

        List<Field> sourceFields = getAllFields(source.getClass());
        List<Field> targetFields = getAllFields(targetClass);

        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);

            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName())) {
                    targetField.setAccessible(true);

                    if (isCompatibleType(sourceField.getType(), targetField.getType())) {
                        targetField.set(target, sourceField.get(source));
                    }
                    break;
                }
            }
        }

        return target;
    }

    /**
     * Obtém todos os campos de uma classe, incluindo os herdados
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());

        // Se houver superclasse, adiciona seus campos também
        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass()));
        }

        return fields;
    }

    /**
     * Verifica se os tipos são compatíveis para conversão
     */
    private static boolean isCompatibleType(Class<?> sourceType, Class<?> targetType) {
        if (sourceType.equals(targetType)) {
            return true;
        }

        if ((sourceType.isPrimitive() || targetType.isPrimitive()) &&
                getWrapperClass(sourceType).equals(getWrapperClass(targetType))) {
            return true;
        }

        return false;
    }

    /**
     * Retorna a classe wrapper para tipos primitivos
     */
    private static Class<?> getWrapperClass(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return clazz;
        }

        if (clazz == int.class) return Integer.class;
        if (clazz == long.class) return Long.class;
        if (clazz == double.class) return Double.class;
        if (clazz == float.class) return Float.class;
        if (clazz == boolean.class) return Boolean.class;
        if (clazz == byte.class) return Byte.class;
        if (clazz == char.class) return Character.class;
        if (clazz == short.class) return Short.class;

        return clazz;
    }
}