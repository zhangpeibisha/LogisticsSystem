package org.nix.dto.resources;

import org.nix.dto.base.BaseResultDto;

import java.util.Set;

/**
 * Create by zhangpe0312@qq.com on 2018/4/13.
 *
 * todo: 给与前端URI信息
 */
public class ResultURIlistDto implements BaseResultDto {

    private Set<String> uris;

    public ResultURIlistDto(Set<String> uris) {
        this.uris = uris;
    }

    @Override
    public BaseResultDto result() {
        return this;
    }

    @Override
    public void handleInfo() {

    }

    public Set<String> getUris() {
        return uris;
    }

    public void setUris(Set<String> uris) {
        this.uris = uris;
    }
}
