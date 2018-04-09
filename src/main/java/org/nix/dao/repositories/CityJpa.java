package org.nix.dao.repositories;

import org.nix.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author 11723
 */
public interface CityJpa extends JpaRepository<City,Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM city WHERE city_name = ?1")
    City findCityByCityName(String cityName);

    @Query(value = "select * from city as c where c.city_name like ?1",nativeQuery = true)
    List<City> search(@Param("name")String name);

}
