package ar.com.velz.springbootbackendapirest.models.services;

import ar.com.velz.springbootbackendapirest.models.dao.IClienteDao;
import ar.com.velz.springbootbackendapirest.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;


    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(long id){
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(long id) {
        clienteDao.deleteById(id);
    }

}
