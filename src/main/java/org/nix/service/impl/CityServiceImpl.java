package org.nix.service.impl;

import org.nix.Exception.SelectObjectException;
import org.nix.dao.repositories.CityDisJpa;
import org.nix.entity.City;
import org.nix.dao.repositories.CityJpa;
import org.nix.entity.CityDis;
import org.nix.service.CityService;
import org.nix.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(rollbackFor = Exception.class)
public class CityServiceImpl extends BaseServiceImpl<City,Integer> implements CityService {
    @Autowired
    private CityJpa cityJpa;
    @Autowired
    private CityDisJpa cityDisJpa;
    /**
     * 初始化
     */
    @Override
    protected void init() {
        jpaRepository = cityJpa;
    }

    /**
     * 管理city-city间的距离
     *
     * @param srcCityId
     * @param dstCityId
     * @param distance
     * @param stats     管理状态
     * @return
     */
    @Override
    public Integer manageNeighborCity(Integer srcCityId, Integer dstCityId, Double distance, String stats) {
        CityDis cityDis = new CityDis(cityJpa.findOne(srcCityId),cityJpa.findOne(dstCityId),distance);
        manageStatus manageStatus = CityService.manageStatus.valueOf(stats);
        switch (manageStatus) {
            case create:cityDisJpa.saveAndFlush(cityDis);break;
            case update:cityDisJpa.saveAndFlush(cityDis);break;
            case delete:cityDisJpa.delete(cityDis);break;
            default:break;
        }
        return 1;
    }

    /**
     * todo: 通过这个城市的名字找到这个城市的实体
     * @see SelectObjectException 如果没有找到这个城市，那么将抛出这个异常
     * @param cityName 城市名字
     * @return 城市实体
     */
    public City findCityByCityName(String cityName){
        City city = cityJpa.findCityByCityName(cityName);
        if (city == null){
            throw new SelectObjectException();
        }
        return city;
    }

    /**
     * 删除城市信息
     *
     * @param ids
     */
    @Override
    public void deleteCities(Integer[] ids) {
        Assert.notNull(ids,"id不能为空");
        for (Integer id:ids) {
            cityJpa.delete(id);
        }
    }
}
