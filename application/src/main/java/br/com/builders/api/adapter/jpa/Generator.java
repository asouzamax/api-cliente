package br.com.builders.api.adapter.jpa;

import java.util.UUID;

import br.com.builders.domain.entity.IdGenerator;
import org.springframework.stereotype.Component;

@Component(value = "idGenerator")
public class Generator implements IdGenerator {

	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}

}
