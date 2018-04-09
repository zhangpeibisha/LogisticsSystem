package org.nix.service;

import org.nix.entity.City;
import org.nix.service.base.BaseService;
import org.springframework.stereotype.Service;

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
     * @param stats 管理状态
     * @return
     * */
    Integer manageNeighborCity(Integer srcCityId,Integer dstCityId,Double distance,String stats);

    /**
     * 删除城市信息
     * @param ids
     * */
    void deleteCities(Integer[] ids);
}
