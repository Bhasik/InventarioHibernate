package Hibernate;

// Generated 02-feb-2015 19:57:53 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Subtipo generated by hbm2java
 */
public class Subtipo implements java.io.Serializable {

	private Integer idSubtipo;
	private Tipo tipo;
	private String nombre;
	private Set<Componente> componentes = new HashSet<Componente>(0);

	public Subtipo() {
	}

	public Subtipo(Tipo tipo, String nombre, Set<Componente> componentes) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.componentes = componentes;
	}

	public Integer getIdSubtipo() {
		return this.idSubtipo;
	}

	public void setIdSubtipo(Integer idSubtipo) {
		this.idSubtipo = idSubtipo;
	}

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Componente> getComponentes() {
		return this.componentes;
	}

	public void setComponentes(Set<Componente> componentes) {
		this.componentes = componentes;
	}

}
