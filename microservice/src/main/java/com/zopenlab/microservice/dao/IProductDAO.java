package com.zopenlab.microservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zopenlab.microservice.model.Product;

@Repository
public interface IProductDAO extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.nom like :kw")
	List<Product> getByKeywordName(@Param("kw")String nom);
	List<Product> findByPrixGreaterThan(float prixlimite);
}
