package org.nix.dao.repositories;

import org.nix.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 11723
 */
public interface CityJpa extends JpaRepository<City,Integer> {


}
