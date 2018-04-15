package org.nix.controller;
import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.entity.City;
import org.nix.entity.SysUser;
import org.nix.service.CityService;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    @LoginRequired
    @PostMapping("/create")
    public ReturnObject createCity(@RequestParam("name") String cityName) {
        City city = new City(cityName);
        city = cityService.save(city);
        if (city == null) {
            return ReturnUtil.fail("新增失败");
        }
        return ReturnUtil.success(city);
    }

    /**
     * 给一个city添加相邻的city，或者修改距离
     * */
    @LoginRequired
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
     * 删除一个城市信息
     * */
    @RequestMapping("/delete")
    public ReturnObject delete(@RequestParam("ids")Integer[] ids) {
        cityService.deleteCities(ids);
        return ReturnUtil.success(null);
    }

    /**
     * 根据输入城市名部分查询数据库已经存在的城市
     * */
    @RequestMapping("/search")
    public ReturnObject searchCity(@RequestParam("name") String name) {
        return null;
    }

    /**
     * 获取某个城市的详情
     * */
    @GetMapping("/details/{id}")
    public ReturnObject getCity(@PathVariable("id") Integer id) {
        return ReturnUtil.success(cityService.cityDetails(id));
    }

    @PostMapping("/list")
    public ReturnObject orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
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
