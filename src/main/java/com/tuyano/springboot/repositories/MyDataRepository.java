package com.tuyano.springboot.repositories;

import com.tuyano.springboot.MyData;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {
	
	
	public Optional<MyData> findById(Long name);
	
	@Query("SELECT d FROM MyData d ORDER BY d.name")
	List<MyData> findAllOrderByName();
	
	@Query("from MyData where age > :min and age < :max")
	public List<MyData> findByAge(@Param("min")int min, @Param("max")int max);
	
	
}
