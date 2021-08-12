package com.w2m.superheroes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.w2m.superheroes.entity.Heroe;
import com.w2m.superheroes.repository.HeroeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HeroesRepositoryIntegrationTest {
	
	  @Autowired
	    private TestEntityManager entityManager;
	  
	    @Autowired
	    private HeroeRepository heroeRepository;
	    
	    @Test
	    public void should_find_all_heroes() {
	    	
	    	Heroe heroe1 = new Heroe();
	    	Heroe heroe2 = new Heroe();
	    	Heroe heroe3 = new Heroe();
	    	heroe1.setNombre("Flash Gordon");
	    	heroe1.setNombre("Robin");
	    	heroe1.setNombre("Batman");
	        entityManager.persist(heroe1);
	        entityManager.persist(heroe2);
	        entityManager.persist(heroe3);
	        
	        Iterable<Heroe> heroes = heroeRepository.findAll();
	        
	        assertThat(heroes).hasSize(3).contains(heroe1, heroe2, heroe3);
	    }

		@Test
	    public void whenFindById_thenReturnHeroe() {
			
			Long id = new Long (1);
			Optional<Heroe> heroe = heroeRepository.findById(id);
			assertThat(heroe).isPresent();
	    }

}
