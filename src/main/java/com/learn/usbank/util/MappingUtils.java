package com.learn.usbank.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static com.learn.usbank.constant.StackConstant.EXCLUDE_FIELD;

@Slf4j
public class MappingUtils {

    /**
     * This method is responsible to update only fields which doesn't match with db.
     * Updates fields of the target object using reflection.
     *
     * @param sourceStock The object containing the updated values
     * @param targetStock The object to be updated
     */
    public static void updateTargetFieldWithSourceField(Object sourceStock, Object targetStock) {
        Field[] sourceFields = sourceStock.getClass().getDeclaredFields();
        for (Field sourceField : sourceFields) {
            if (EXCLUDE_FIELD.contains(sourceField.getName())) {
                continue;
            }
            ReflectionUtils.makeAccessible(sourceField);
            try {
                Object value = ReflectionUtils.getField(sourceField, sourceStock);
                if (value != null) {
                    Field targetField = ReflectionUtils.findField(targetStock.getClass(), sourceField.getName());
                    if (targetField != null) {
                        ReflectionUtils.makeAccessible(targetField);
                        ReflectionUtils.setField(targetField, targetStock, value);
                    }
                }
            } catch (Exception e) {
                log.error("Exception occurred while processing db and stock updated data : {}", e.getMessage());
            }
        }
    }
}