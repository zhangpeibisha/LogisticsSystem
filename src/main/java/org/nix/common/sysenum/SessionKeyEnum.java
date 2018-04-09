package org.nix.common.sysenum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 *
 * todo； 用于统一指定httpSession中的KEY值代表的意思
 */
public enum SessionKeyEnum {

    SESSION_KEY_CURRENT_USER("CurrentUser","当前会话用户对象信息");

    private Object key;
    private Object value;

    SessionKeyEnum(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SessionKeyEnum{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
