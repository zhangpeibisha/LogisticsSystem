package org.nix.dto.base;

/**
 * Create by zhangpe0312@qq.com on 2018/4/12.
 *
 * todo: 统一返回dto接口
 */
public interface BaseResultDto {
    /**
     * todo: 用来反馈整合的信息
     * @return
     */
    BaseResultDto result();

    /**
     * todo: 处理信息，使得符合前端请求需要
     */
    void handleInfo();
}
