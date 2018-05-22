package org.nix.controller;
import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.common.sysenum.SysRoleEnum;
import org.nix.entity.City;
import org.nix.entity.CityDis;
import org.nix.entity.SysUser;
import org.nix.service.CityService;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11723
 */
@RestController
@RequestMapping("/city")
public class CityAndDistancesController {

    @Autowired
    private CityService cityService;
    /**
     * 新增加一个city
     * @param cityName
     * @return
     * */
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    @PostMapping("/create")
    public ReturnObject createCity(@RequestParam("name") String cityName,
                                   @RequestParam(value = "dstCityIds",required = false) Integer[] dstCityIds,
                                   @RequestParam(value = "distances",required = false) Double[] distances) throws Exception {

        for (Double dou : distances) {
            if (dou < 0)
                throw new Exception("两个城市之间的距离小于0");
        }

        City city = new City(cityName);
        city = cityService.save(city);
        if (dstCityIds != null) {
            for (int i = 0;i < dstCityIds.length;i ++) {
                cityService.manageNeighborCity(city.getId(),dstCityIds[i],distances[i],"create");
            }
        }

        if (city == null) {
            return ReturnUtil.fail("新增失败");
        }
        return ReturnUtil.success(city);
    }

    /**
     * 给一个city添加相邻的city，或者修改距离
     * */
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    @PostMapping("/neighbor")
    public ReturnObject addNeighborCity(@RequestParam("srcCityId") Integer srcCityId,
                                        @RequestParam("dstCityId") Integer dstCityId,
                                        @RequestParam(value = "distance",required = false) Double distance,
                                        @RequestParam(value = "status",required = false) String status) {
        Integer line = cityService.manageNeighborCity(srcCityId,dstCityId,distance,status);
        if (line != null && line > 0) {
            return ReturnUtil.success(null);
        }
        return ReturnUtil.fail("null");
    }

    /**
     * 给一个city添加相邻的city，或者修改距离
     * */
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    @PostMapping("/addNeighbor")
    public ReturnObject addNeighbor(@RequestParam("srcCityId") Integer srcCityId,
                                        @RequestParam("dstCityIds") Integer[] dstCityIds,
                                        @RequestParam(value = "distance",required = false) Double[] distance) {
        Integer line = null;
        for (int i = 0;i < dstCityIds.length;i ++) {
            line = cityService.manageNeighborCity(srcCityId,dstCityIds[i],distance[i],CityService.manageStatus.create.name());
        }
        if (line != null && line > 0) {
            return ReturnUtil.success(null);
        }
        return ReturnUtil.fail("null");
    }

    /**
     * 删除一个城市信息
     * */
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    public ReturnObject delete(@RequestParam("ids")Integer[] ids) {
        cityService.deleteCities(ids);
        return ReturnUtil.success(null);
    }

    /**
     * 根据输入城市名部分查询数据库已经存在的城市
     * */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject searchCity(@RequestParam("name") String name) {
        System.out.println(name);
        return ReturnUtil.success(cityService.list(1, 10, null, null, "city_name", name, false));
    }

    /**
     * 获取某个城市的详情
     * */
    @GetMapping("/details/{id}")
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject getCity(@PathVariable("id") Integer id) {
        return ReturnUtil.success(cityService.cityDetails(id));
    }

    @PostMapping("/list")
//    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject cityList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "20") Integer size,
                                  @RequestParam(value = "order", defaultValue = "id") String order,
                                  @RequestParam(value = "sort", defaultValue = "ASC") String sort,
                                  @RequestParam(value = "field", defaultValue = "") String field,
                                  @RequestParam(value = "content", defaultValue = "") String content,
                                  @RequestParam(value = "fullMatch", defaultValue = "true") Boolean fullMatch,
                                  HttpServletRequest request) {
        Map map = new HashMap();
        map.put("total",cityService.count());
        return ReturnUtil.success("",cityService.list(page, size, order, sort, field, content, fullMatch),map);
    }
}
