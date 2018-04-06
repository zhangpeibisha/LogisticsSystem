package org.nix.service.impl;

import org.nix.dao.repositories.CityDisJpa;
import org.nix.entity.City;
import org.nix.dao.repositories.CityJpa;
import org.nix.entity.CityDis;
import org.nix.service.CityService;
import org.nix.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
