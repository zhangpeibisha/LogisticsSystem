package org.nix.service;

import org.nix.entity.City;
import org.nix.entity.SysOrder;
import org.nix.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 11723
 */
@Service
public interface CityService extends BaseService<City,Integer> {
    enum manageStatus{
        create,
        update,
        delete
    }
    /**
     * 管理city-city间的距离
     * @param srcCityId
     * @param dstCityId
     * @param distance
     * @param status 管理状态
     * @return
     * */
    Integer manageNeighborCity(Integer srcCityId,Integer dstCityId,Double distance,String status);

    /**
     * 删除城市信息
     * @param ids
     * */
    void deleteCities(Integer[] ids);

    /**
     * 模糊搜索城市
     * */
    List<City> search(String name);
    /**
     * 分页查找订单列表（模糊查找）
     * @param page
     * @param size
     * @param order
     * @param sort
     * @param field
     * @param content
     * @param fullMatch
     * @return
     * */
    List<City> list(Integer page, Integer size, String order, String sort, String field, String content, Boolean fullMatch);

    int count();
}
