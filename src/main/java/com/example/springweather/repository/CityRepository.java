package com.example.springweather.repository;

import com.example.springweather.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    public City findByName(String name);
}
