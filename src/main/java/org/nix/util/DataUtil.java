package org.nix.util;

/**
 * @author Kiss
 * @date 2018/04/12 17:54
 */
public final class DataUtil {
    public static Integer offset(Integer page,Integer size) {
        if (page == null || size == null) {
            return null;
        }
        return (page - 1) * size;
    }
    public static String getConditions(String field, String content,Boolean fullMatch) {
        if (field == null || field.isEmpty()) {
            return null;
        }
        String conditions;
        if (fullMatch) {
            conditions = field + " = '" + content + "'";
        } else {
            conditions = field + " like '%" + content + "%'";
        }
        return conditions;
    }
}
