package org.nix.dao.repositories;

import org.nix.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 11723
 */
@Service
public interface CityJpa extends JpaRepository<City,Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM city WHERE city_name = ?1")
    City findCityByCityName(String cityName);

}
