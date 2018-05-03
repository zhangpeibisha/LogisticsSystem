package org.nix.util;

/**
 * @author Kiss
 * @date 2018/04/12 17:54
 */
public final class DataUtil {
    public static int offset(int page,int size) {
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
