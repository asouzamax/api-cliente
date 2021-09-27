package br.com.builders.api.cliente.adapter.jpa;

import br.com.builders.api.cliente.entity.jpa.ClienteEntity;
import br.com.builders.api.cliente.entity.jpa.JpaContext;
import br.com.builders.api.cliente.entity.mapper.ClienteRestMapper;
import br.com.builders.domain.entity.Cliente;
import br.com.builders.domain.repository.ClienteRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Repository(value = "clienteCrudRepository")
public class ClienteCrudRepository implements ClienteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Cliente save(Cliente Cliente) {
        return ClienteRestMapper.INSTANCE.ClienteDBtoCliente(em.merge (getT (Cliente)));
    }

    @Override
    @Transactional
    public void delete(Cliente cliente){
        em.remove (getT (cliente));
    }

    @Override
    public boolean ehClienteComNomeJaExistente(String primeiroNome, String ultimoNome){
        try{
            StringBuilder hql = new StringBuilder();
            Map<String, Object> params = new HashMap<String, Object> ();
            hql.append("Select p from ClienteEntity p WHERE p.nome = :nome and ultimoNome = :ultimoNome");

            Query query = em.createQuery(hql.toString());
            query.setParameter("nome", primeiroNome);
            query.setParameter("ultimoNome", ultimoNome);
            return query.getSingleResult () != null ? true : false;
        }
        catch(NoResultException e){
            return false;
        }

    }

    private ClienteEntity getT(Cliente cliente) {
        return ClienteRestMapper.INSTANCE.ClienteToClienteDB (cliente, JpaContext.getInstance ());
    }
}
