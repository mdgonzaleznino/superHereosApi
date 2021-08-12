package com.w2m.superheroes.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.w2m.superheroes.dto.HeroeDTO;
import com.w2m.superheroes.entity.Heroe;
import com.w2m.superheroes.exceptions.ResourceNotFoundException;
import com.w2m.superheroes.repository.HeroeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CacheConfig(cacheNames = "heroes")
@Service
@Transactional
public class HeroeService {

	@Autowired
	private HeroeRepository heroeRepository;

	public HeroeDTO getHeroeById(Long id) {
		Optional<Heroe> heroeRequest = heroeRepository.findById(id);
		HeroeDTO response = new HeroeDTO();
		if (heroeRequest.isPresent()) {
			response.setId(heroeRequest.get().getId());
			response.setNombre(heroeRequest.get().getNombre());
			// response = modelMapper.map(heroeRequest, HeroeDTO.class);
			return response;
		} else {
			new ResourceNotFoundException("No existe un heroe con id = " + id);
		}
		return response;
	}

	public HeroeDTO crearHeroe(Heroe heroe) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(heroeRepository.save(heroe), HeroeDTO.class);

	}

	//@Cacheable(value = "allheroescache")
	public List<HeroeDTO> buscarTodos() {
		ModelMapper modelMapper = new ModelMapper();
		return heroeRepository.findAll().stream().map(heroe -> modelMapper.map(heroe, HeroeDTO.class))
				.collect(Collectors.toList());
	}

	public List<HeroeDTO> findHeroesByName(String name) {
		ModelMapper modelMapper = new ModelMapper();
		return heroeRepository.findByNombre(name).stream().map(heroe -> modelMapper.map(heroe, HeroeDTO.class))
				.collect(Collectors.toList());
	}

	public List<HeroeDTO> findHeroesContainsName(String nombre) {
		ModelMapper modelMapper = new ModelMapper();
		return heroeRepository.findByParametro(nombre.toLowerCase()).stream()
				.map(heroe -> modelMapper.map(heroe, HeroeDTO.class)).collect(Collectors.toList());
	}

	public void deleteHeroeById(Long id) {
		Optional<Heroe> heroe = heroeRepository.findById(id);
		if (heroe.isPresent()) {
			heroeRepository.deleteById(id);
		} else {
			new ResourceNotFoundException("No existe un heroe con id = " + id);
		}
	}

	public HeroeDTO modificarHeroe(Heroe heroe) {
		Optional<Heroe> heroeRequest = heroeRepository.findById(heroe.getId());
		HeroeDTO response = new HeroeDTO();
		if (heroeRequest.isPresent()) {
			ModelMapper modelMapper = new ModelMapper();
			response = modelMapper.map(heroeRepository.save(heroe), HeroeDTO.class);
		}else {
			new ResourceNotFoundException("No existe un heroe con id = " + heroe.getId());
		} 
		return response;
	}

//	public HeroeDTO modificarHeroe(Heroe heroe) {
//
//		HeroeDTO response = new HeroeDTO();
//
//		heroeRepository.findById(heroe.getId())
//				.orElseThrow(() -> new ResourceNotFoundException("No existe un heroe con id = " + heroe.getId()));
//		ModelMapper modelMapper = new ModelMapper();
//		response = modelMapper.map(heroeRepository.save(heroe), HeroeDTO.class);
//		return response;
//	}

}
