package com.w2m.superheroes.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
//@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "HEROE_TABLE")//, uniqueConstraints = {@UniqueConstraint(name = "NOMBRE", columnNames = {"NOMBRE"})})

@ApiModel("Model Superheroe")
public class Heroe implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @Column(name = "ID_HEROE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Identificador único de Heroe", required = true)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HEROE_SEQ")
    //@SequenceGenerator(name = "HEROE_SEQ", sequenceName = "HEROE_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "NOMBRE", length = 30, nullable = false)
	@NotNull
	@ApiModelProperty(value = "Nombre del Superheroe", required = true)
    private String nombre;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    

}
