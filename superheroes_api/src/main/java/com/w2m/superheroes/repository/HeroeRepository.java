package com.w2m.superheroes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.w2m.superheroes.entity.Heroe;

@Transactional
@Repository
public interface HeroeRepository extends JpaRepository<Heroe, Long>{

	Optional<Heroe> findByNombre(String nombre);
	
	@Query (value = "select * from HEROE_TABLE where LOWER(NOMBRE) like CONCAT('%', ?1, '%')",
			nativeQuery = true)
	List<Heroe> findByParametro(String nombre);

}
