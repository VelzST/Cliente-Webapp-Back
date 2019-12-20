package ar.com.velz.springbootbackendapirest.models.dao;

import ar.com.velz.springbootbackendapirest.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente,Long> {

}
