package br.com.builders.api.entity.jpa;

import javax.persistence.EntityManager;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaContext {

	static JpaContext jpaContext;

	public static JpaContext getInstance(){
		if(jpaContext == null){
			jpaContext = new JpaContext (null);
		}
		return jpaContext;
	}

	@Autowired
	private final EntityManager em;

	private ClienteEntity parentEntity;

	public JpaContext(EntityManager em) {
		this.em = em;
	}

	@BeforeMapping
	public void setEntity(@MappingTarget ClienteEntity parentEntity) {
		this.parentEntity = parentEntity;
		// you could do stuff with the EntityManager here
	}


}
