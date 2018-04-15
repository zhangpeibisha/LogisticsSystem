package org.nix.common.sysenum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/15.
 *
 * todo: 用于权限验证
 * value 为角色在数据库中的字段角色名字
 * message 为角色解释
 */
public enum SysRoleEnum {

    ROLE_GENERAL("general","普通用户"),
    ROLE_ADMINISTRATOR("administrator","管理员"),
    ROLE_FINANCE("finance","财务管理员"),
    ROLE_PUBLIC("public","所有角色都可以使用");

    private String value;
    private String message;

    SysRoleEnum(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
