package com.jorshbg.practiceapispring.util;

import java.lang.reflect.Field;
import java.util.*;

public class UpdatesTrackerUtil<T> {

    public Map<String, Map<String, String>> getUpdates(T base, T updated) throws IllegalAccessException {
        Map<String, Map<String, String>> updates = new HashMap<>();
        return this.trackUpdates(base, updated, updates);
    }

    private Map<String, Map<String, String>> trackUpdates(
            Object base, Object updated, Map<String, Map<String, String>> updates
    ) throws IllegalAccessException {

        Class<?> clazz = base.getClass();

        if(this.checkInValidObjects(clazz)){
            return updates;
        }

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object previous = field.get(base);
            Object current = field.get(updated);


            if (previous != null && current != null && !previous.equals(current)) {
                if (this.isPrimitive(previous.getClass()) || previous.getClass().isArray()) {
                    if (!updates.containsKey(base.getClass().getName())) {
                        updates.put(base.getClass().getName(), new HashMap<>());
                    }
                    if (previous.getClass().isArray() && !Arrays.deepEquals((Object[]) previous, (Object[]) current)) {
                        updates.get(base.getClass().getName())
                                .put(field.getName(), String.format("Previous: %s, Current: %s", this.convertArrayToString((Object[]) previous), this.convertArrayToString((Object[]) current)));
                    } else if(!previous.getClass().isArray()) {
                        updates.get(base.getClass().getName()).put(
                                field.getName(),
                                String.format("Previous: %s, Current: %s", previous, current)
                        );
                    }
                } else {
                    this.trackUpdates(previous, current, updates);
                }
            }
        }
        return updates;
    }

    private boolean isPrimitive(Class<?> clazz) {
        return Integer.class.equals(clazz) || Long.class.equals(clazz)
                || Double.class.equals(clazz) || Float.class.equals(clazz)
                || String.class.equals(clazz) || Boolean.class.equals(clazz)
                || Byte.class.equals(clazz) || Character.class.equals(clazz)
                || Short.class.equals(clazz);
    }

    private String convertArrayToString(Object[] array) {
        StringBuilder sb = new StringBuilder();
        if (array != null) {
            for(Object value: array){
                sb.append(value);
            }
            return sb.toString();
        }
        return null;
    }
    private boolean checkInValidObjects(Class<?> clazz){
        return Map.class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz) || Iterable.class.isAssignableFrom(clazz);
    }

}
