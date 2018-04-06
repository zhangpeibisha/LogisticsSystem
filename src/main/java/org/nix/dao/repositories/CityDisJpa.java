package org.nix.dao.repositories;

import org.nix.entity.City;
import org.nix.entity.CityDis;
import org.nix.entity.CityDisPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CityDisJpa extends JpaRepository<CityDis,CityDisPrimaryKey> {
//    /**
//     * 给city新增加一个邻居city
//     * @param srcCityId
//     * @param dstCityId
//     * @param distance
//     * @return
//     * */
//    @Query(value = "insert into city_dis values (?1,?2,?3)",nativeQuery = true)
//    @Modifying
//    @Transactional(rollbackFor = Exception.class)
//    Integer addNeighbor(@Param("srcCityId")Integer srcCityId, @Param("dstCityId")Integer dstCityId, @Param("distance")Double distance);
//    /**
//     * 修改city-city间的距离
//     * @param srcCityId
//     * @param dstCityId
//     * @param distance
//     * @return
//     * */
//    @Query(value = "update CityDis as cd set cd.distance = :distance where cd.primaryKey.srcCityId = :srcCityId and cd.primaryKey.dstCityId = :dstCityId")
//    @Modifying
//    @Transactional(rollbackFor = Exception.class)
//    Integer updateNeighbor(@Param("srcCityId")Integer srcCityId,@Param("dstCityId")Integer dstCityId,@Param("distance")Double distance);
//
//    /**
//     * 删除city-city间的距离
//     * @param srcCityId
//     * @param dstCityId
//     * @return
//     * */
//    @Query(value = "delete from city_dis where srcCityId = ?1 and dstCityId = ?2 ",nativeQuery = true)
//    @Modifying
//    @Transactional(rollbackFor = Exception.class)
//    Integer deleteNeighbor(@Param("srcCityId")Integer srcCityId,@Param("dstCityId")Integer dstCityId);
}
