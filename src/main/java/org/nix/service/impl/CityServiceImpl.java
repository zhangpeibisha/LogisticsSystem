package org.nix.service.impl;

import org.nix.Exception.SelectObjectException;
import org.nix.dao.impl.CityDaoImpl;
import org.nix.dao.repositories.CityDisJpa;
import org.nix.entity.City;
import org.nix.dao.repositories.CityJpa;
import org.nix.entity.CityDis;
import org.nix.service.CityService;
import org.nix.service.base.BaseServiceImpl;
import org.nix.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.thymeleaf.util.DateUtils;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CityServiceImpl extends BaseServiceImpl<City,Integer> implements CityService {
    @Autowired
    private CityJpa cityJpa;
    @Autowired
    private CityDisJpa cityDisJpa;
    @Autowired
    private CityDaoImpl cityDao;
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
     * @param status     管理状态
     * @return
     */
    @Override
    @Transactional
    public Integer manageNeighborCity(Integer srcCityId, Integer dstCityId, Double distance, String status) {
        CityDis cityDis = new CityDis(cityJpa.findOne(srcCityId), cityJpa.findOne(dstCityId), distance);
        manageStatus manageStatus = CityService.manageStatus.valueOf(status);
        switch (manageStatus) {
            case create:
                cityDisJpa.saveAndFlush(cityDis);
                break;
            case update:
                cityDisJpa.saveAndFlush(cityDis);
                break;
            case delete:
                cityDisJpa.delete(cityDis);
                break;
            default:
                break;
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

    /**
     * 分页查找订单列表（模糊查找）
     *
     * @param page
     * @param size
     * @param order
     * @param sort
     * @param field
     * @param content
     * @param fullMatch
     * @return
     */
    @Override
    public List<City> list(Integer page, Integer size, String order, String sort, String field, String content, Boolean fullMatch) {
        return cityDao.list(DataUtil.offset(page,size),size,order,sort,DataUtil.getConditions(field,content,fullMatch));
    }

    /**
     * 模糊搜索城市
     *
     * @param name
     */
    @Override
    public List<City> search(String name) {
        return cityJpa.search("%" + name.replaceAll(" ","%") + "%");
    }

    @Override
    public int count() {
        return (int) cityJpa.count();
    }
}
