package com.w2m.superheroes.utils;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.w2m.superheroes.dto.HeroeDTO;

public class Utils {
	
	
	public static <T> List<T> mapperList(List list, Class<T> type) {

		List temp = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		for (Object p : list) {
			temp.add(modelMapper.map(p, type));
		}

		return temp;
	}
	
}
