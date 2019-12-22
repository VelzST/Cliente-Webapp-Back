package ar.com.velz.springbootbackendapirest.controllers;

import ar.com.velz.springbootbackendapirest.models.entity.Cliente;
import ar.com.velz.springbootbackendapirest.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    // ResponseEntity es un tipo de dato de spring para devolver en este caso o un cliente o algun mensaje de error
    public ResponseEntity<?> showClient(@PathVariable Long id){
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente = clienteService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cliente==null){
            response.put("mensaje", "No se encontro un cliente con ese ID");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();
        try {
            clienteNew = clienteService.save(cliente);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente se creo correctamente");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){

        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdate = null;
        Map<String, Object> response = new HashMap<>();

        if(clienteActual==null){
            response.put("mensaje", "No se encontro un cliente con ese ID, no puede editarse");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteUpdate = clienteService.save(clienteActual);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente se actualizo correctamente");
        response.put("cliente", clienteUpdate);
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente se elimino correctamente");
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
