package com.w2m.superheroes.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.w2m.superheroes.dto.HeroeDTO;
import com.w2m.superheroes.entity.Heroe;
import com.w2m.superheroes.exceptions.ResourceNotFoundException;
import com.w2m.superheroes.service.HeroeService;
import com.w2m.superheroes.timerLog.LogExecutionTime;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/heroes")
@Api(value = "/api/heroes", description = "REST Heroes")
@Slf4j
//@ContextConfiguration(classes=ConfigBean.class)
public class HeroeController {

	@Autowired
	private HeroeService heroeService;

	@Autowired
	private ModelMapper modelMapper;

	// Consultar todos los súper héroes.
	@GetMapping
	@LogExecutionTime
	@ApiOperation(value = "Encuentra todos los superheroes",
			notes = "Devuelve todos los superhéroes")
//	@ApiParam(
//	            value = "Devuelve todos los superhéroes")//,
//	            //tags = "todos", description = "Devuelve todos los superhéroes" )
	public ResponseEntity<List<HeroeDTO>> obtenerHeroes() {
		List<HeroeDTO> listaHeroeDTO = heroeService.buscarTodos();
		if (!listaHeroeDTO.isEmpty()) {
			return ResponseEntity.ok(listaHeroeDTO);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Consultar un único súper héroe por id
	@GetMapping("/{id}")
	@LogExecutionTime
	@ApiOperation(value = "Consulta un superheroe mediante Id", notes = "Devuelve un superheroe mediante Id")
	public ResponseEntity<HeroeDTO> getHeroeById(@PathVariable(name = "id") Long id) throws Exception {
		HeroeDTO heroeDTO = heroeService.getHeroeById(id);
		if (heroeDTO.getId() != null) {
			return ResponseEntity.ok(heroeDTO);
		} else {
			new ResourceNotFoundException("No existe un heroe con id = " + id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Consultar todos los súper héroes por parámetro.
	@GetMapping("/contiene/{nombre}")
	@ApiOperation(value = "Consulta un superheroe por parámetro", notes = "Devuelve un superheroe mediante parámetro")
	@LogExecutionTime
	public ResponseEntity<List<HeroeDTO>> buscarHeroesPorParametro(@PathVariable(name = "nombre") String nombre) {
		List<HeroeDTO> heroes = heroeService.findHeroesContainsName(nombre);
		if (heroes.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok().body(heroes);
		}
	}

	// Crear heroe
	@PostMapping
	@LogExecutionTime
	@ApiOperation(value = "Crear un superheroe", notes = "Crea un superheroe a partir un nombre y se le asigna un Id. El usuario no debe existir\" ")
	public ResponseEntity<HeroeDTO> crearHeroe(
			@ApiParam(value = "Datos del nuevo heroe", required = true) @RequestBody Heroe heroe) {
		// if (heroe==null){
		heroeService.crearHeroe(heroe);
		return new ResponseEntity<>(heroeService.crearHeroe(heroe), HttpStatus.CREATED);// }
		// else {return ResponseEntity.badRequest().build();}
	}

	// *****Borrar*****
	@GetMapping("/heroe/{nombre}")
	@LogExecutionTime
	@ApiOperation(value = "Buscar un superheroe por nombre")
	public ResponseEntity<List<HeroeDTO>> buscarHeroesPorNombre(@PathVariable(name = "nombre") String nombre) {
		List<HeroeDTO> heroe = heroeService.findHeroesByName(nombre);
		if (heroe.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok().body(heroe);
		}
	}
	// Modificar un súper héroe.
	@PutMapping("/{id}")
	@LogExecutionTime
	@ApiOperation(value = "Modificar un superheroe", notes = "Modifica un superheroe")
	public ResponseEntity<HeroeDTO> modificarHeroe(@PathVariable("id") Long id,
			//@ApiParam(value = "Datos del heroe a modificar", required = true) 
	@RequestBody Heroe heroe)throws Exception {
		heroe.setId(id);
		HeroeDTO hereoDTO = 
				heroeService.modificarHeroe(heroe);
		if (hereoDTO.getId() == null) {
			new ResourceNotFoundException("No existe un heroe con id = " + heroe.getId());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return ResponseEntity.ok(hereoDTO);
		}

	}

	// Eliminar un súper héroe.
	@DeleteMapping("/{id}")
	@LogExecutionTime
	@ApiOperation(value = "Borrar un superheroe", notes = "Borra un superheroe mediante Id")
	public ResponseEntity<?> deleteHeroeById(
			//@ApiParam(value = "ID Heroe a eliminar",
			// allowableValues = "range[1," + Long.MAX_VALUE + "]",
			//required = true) 
	@PathVariable("id") Long id) {
		
		heroeService.deleteHeroeById(id);
		// heroe.orElseThrow(()-> new RuntimeException("No existe heroe con id " + id));
		// ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.OK);
		return ResponseEntity.ok().body("Se ha borrado el heroe con id: " + id);
	}

}
