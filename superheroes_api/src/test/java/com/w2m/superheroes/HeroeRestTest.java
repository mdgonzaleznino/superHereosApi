package com.w2m.superheroes;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.w2m.superheroes.controller.HeroeController;
import com.w2m.superheroes.dto.HeroeDTO;
import com.w2m.superheroes.entity.Heroe;
import com.w2m.superheroes.repository.HeroeRepository;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "spring.cloud.config.enabled:false" })
@AutoConfigureMockMvc
//@WebMvcTest(HeroeController.class)
class HeroeRestTest {

	/** El mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private HeroeController heroeController;

	/** La constante APPLICATION_JSON_UTF8. */
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	HeroeRepository heroeRepositoryMock = Mockito.mock(HeroeRepository.class);

	ResponseEntity<HeroeDTO> response;

	ResponseEntity<List<HeroeDTO>> responseLista;

	Heroe heroe;

//Prueba positiva
	/**
	 * Test Búsqueda de Héroe por ID existente
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void getHeroeById() throws Exception {
		Long id = new Long(3);

		response = heroeController.getHeroeById(id);
		this.mockMvc.perform(get("/api/heroes/" + id)).andDo(print()).andExpect(status().isOk());
		assertEquals(id, response.getBody().getId());
	}

	/**
	 * Test Búsqueda de Todos los Héroes
	 * 
	 * @throws Exception
	 */
	@Test
	public void getHeroes() throws Exception {
		responseLista = heroeController.obtenerHeroes();
		this.mockMvc.perform(get("/api/heroes/")).andDo(print()).andExpect(status().isOk());
		assertFalse(responseLista.getBody().isEmpty());
	}

	/**
	 * Test Consultar todos los súper héroes por parámetro.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getHeroesByParam() throws Exception {
		String nombre = "Man";
		responseLista = heroeController.buscarHeroesPorParametro(nombre);
		this.mockMvc.perform(get("/api/heroes/contiene/" + nombre)).andDo(print()).andExpect(status().isOk());
		assertTrue(responseLista.getBody().size() > 0);
	}

	/**
	 * Test Consultar crear un súper héroe.
	 * 
	 * @throws Exception
	 */
	@Test
	public void crearHeroe() throws Exception {
		String nombre = "Vegeta";
		Heroe heroe = new Heroe();
		heroe.setNombre(nombre);
		String jsonInputString = "{\"id\": \"null\", \"nombre\": \"SuperLopez\"}";

		response = heroeController.crearHeroe(heroe);
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/heroes/")
			      .content(jsonInputString)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		// this.mockMvc.perform(post("/api/heroes/").contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk());
		assertTrue(response.getBody().getId() > 0);
	}

	/**
	 * Test Modificar Héroe
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Test
	//@Sql(scripts = "data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void modificarHeroe() throws Exception {
		Heroe heroe = new Heroe();
		Long id = new Long(3);
		String nombre = "Logan";
		// heroe.setId(id);
		heroe.setNombre(nombre);
		response = heroeController.modificarHeroe(id, heroe);
		heroe.setNombre(nombre);
		String jsonInputString = "{\"id\": \"null\", \"nombre\": \"SuperLopez\"}";

		response = heroeController.modificarHeroe(id, heroe);

		mockMvc.perform(MockMvcRequestBuilders
			      .put("/api/heroes/3")
			      .content(jsonInputString)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				  .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").exists())
			      ;
		assertFalse(response.getBody() == null);
	}

	/**
	 * Test Eliminar Héroe
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void eliminarHeroe() throws Exception {
		Long id = new Long(2);
		heroeController.deleteHeroeById(id);
		this.mockMvc.perform(delete("/api/heroes/" + id)).andDo(print()).andExpect(status().isOk());
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
		response = heroeController.getHeroeById(id);
		this.mockMvc.perform(get("/api/heroes/" + id)).andDo(print()).andExpect(status().isNotFound());// is4xxClientError());
		assertNotEquals(id, null);
	}

	/**
	 * Test Búsqueda de Todos los Héroes
	 * 
	 * @throws Exception
	 */
	@Test
	@Sql(statements = "TRUNCATE TABLE HEROE_TABLE", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getHeroesBad() throws Exception {
		responseLista = heroeController.obtenerHeroes();
		this.mockMvc.perform(get("/api/heroes/")).andDo(print()).andExpect(status().isNotFound());
		assertTrue(responseLista.getBody() == null);
	}

	/**
	 * Test Consultar todos los súper héroes por parámetro no existente.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getHeroesByParamBad() throws Exception {
		String nombre = "Mansssss";
		responseLista = heroeController.buscarHeroesPorNombre(nombre);
		this.mockMvc.perform(get("/api/heroes/contiene/" + nombre)).andDo(print()).andExpect(status().isNoContent());
		assertTrue(responseLista.getBody() == null);
	}

	/**
	 * Test Consultar crear un súper héroe.
	 * 
	 * @throws Exception
	 */
//	@Test
//	public void crearHeroeBad() throws Exception {
//		response = heroeController.crearHeroe(heroe);
//		String jsonInputString = "{\"id\": \"null\", \"nombre\": \"null\"}";
//		 response = heroeController.crearHeroe(heroe);
//		 
//			mockMvc.perform( MockMvcRequestBuilders
//				      .post("/api/heroes/")
//				      .content(jsonInputString)
//				      .contentType(MediaType.APPLICATION_JSON)
//				      .accept(MediaType.APPLICATION_JSON))
//				      .andExpect(status().isBadRequest())
//				      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
//		 assertFalse(response.getBody() == null);
//	}

}
