package com.w2m.superheroes;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.w2m.superheroes.dto.HeroeDTO;
import com.w2m.superheroes.entity.Heroe;
import com.w2m.superheroes.repository.HeroeRepository;
import com.w2m.superheroes.service.HeroeService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "spring.cloud.config.enabled:false" })
@AutoConfigureMockMvc
class HeroeServiceTest {

	/** El mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private HeroeService heroeService;

	HeroeRepository heroeRepositoryMock = Mockito.mock(HeroeRepository.class);

	ResponseEntity<HeroeDTO> response;

	List<HeroeDTO> responseLista;

//Prueba positiva
	/**
	 * Test Búsqueda de Héroe por ID existente
	 * 
	 * @throws Exception
	 */
	@Test
	public void getHeroeById() throws Exception {
		Long id = new Long(3);
		heroeService.getHeroeById(id);
	}

	/**
	 * Test Consultar todos los súper héroes por parámetro.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getHeroesByParam() throws Exception {
		String nombre = "Man";
		responseLista = heroeService.findHeroesContainsName(nombre);
		assertTrue(responseLista.size() > 0);
	}

	/**
	 * Test Búsqueda de Todos los Héroes
	 * 
	 * @throws Exception
	 */
	@Test
//	@Sql("truncate.sql")
//	@Sql("data.sql")
	public void getHeroes() throws Exception {
		responseLista = heroeService.buscarTodos();
		assertTrue(responseLista.size() > 0);
	}

	/**
	 * Test Crear Héroe
	 * 
	 * @throws Exception
	 */
	@Test
	public void createHeroe() throws Exception {
		Heroe heroe = new Heroe();
		heroe.setNombre("Vegeta");
		heroeService.crearHeroe(heroe);
		assertTrue(heroeService.findHeroesByName("Vegeta").size() > 0);
	}

	/**
	 * Test Modificar Héroe
	 * 
	 * @throws Exception
	 */
	@Test
	public void modificarHeroe() throws Exception {
		Heroe heroe = new Heroe();
		Long id = new Long(3);
		String nombre = "Logan";
		heroe.setId(id);
		heroe.setNombre(nombre);
		heroeService.modificarHeroe(heroe);
		String expected = heroeService.getHeroeById(id).getNombre();
		//assertTrue(expected == nombre);
	}

	/**
	 * Test Eliminar Héroe
	 * 
	 * @throws Exception
	 */
	@Test
	public void eliminarHeroe() throws Exception {

		heroeService.deleteHeroeById(new Long(2));
	}

	// Prueba negativa

	/**
	 * Test Búsqueda de Héroe por ID no existente.
	 *
	 * @throws Exception la exception
	 */

	@SuppressWarnings("deprecation")
	@Test
	public void findByBadId() throws Exception {
		Long id = new Long(9);
		HeroeDTO heroe = heroeService.getHeroeById(id);
		assertTrue(heroe.getId() == null);
//		Exception exception = assertThrows(
//				Exception.class, 
//	            () -> heroeService.getHeroeById(id));
		// assertTrue(exception.getMessage().contains("not found"));

	}

	/**
	 * Test Búsqueda de Todos los Héroes vacio
	 * 
	 * @throws Exception
	 */
	@Test
	@Sql("truncate.sql")
	public void getHeroesBad() throws Exception {
		responseLista = heroeService.buscarTodos();
		assertTrue(responseLista.isEmpty());
	}

	/**
	 * Test Consultar todos los súper héroes por parámetro no existente
	 * 
	 * @throws Exception
	 */
	@Test
	public void getHeroesByParamBad() throws Exception {
		String nombre = "wwwwwww";
		responseLista = heroeService.findHeroesContainsName(nombre);
		assertTrue(responseLista.isEmpty());
	}

}
