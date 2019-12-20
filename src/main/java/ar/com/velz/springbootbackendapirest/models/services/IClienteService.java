package ar.com.velz.springbootbackendapirest.models.services;

import ar.com.velz.springbootbackendapirest.models.entity.Cliente;

import java.util.List;

public interface IClienteService {
    public List<Cliente> findAll();

    public Cliente save(Cliente cliente);

    public Cliente findById(long id);

    public void delete(long id);
}
