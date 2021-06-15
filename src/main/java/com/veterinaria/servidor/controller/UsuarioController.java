package com.veterinaria.servidor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.servidor.entity.Opcion;
import com.veterinaria.servidor.entity.Usuario;
import com.veterinaria.servidor.service.UsuarioService;
import com.veterinaria.servidor.util.Constantes;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin(origins = {"http://localhost:8090","http://localhost:8091"}, methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	
	@ResponseBody
	@PostMapping(path = "/login")
	public Usuario login(@RequestBody Usuario bean){
		return service.login(bean);
	}
	/*@ResponseBody
	@PostMapping(path = "/iniciarSesion")
	public Usuario login(@RequestBody Usuario bean,  HttpSession session){
		UserDetails objsalida = (UserDetails) session.getAttribute("objUsuario");
		List<Opcion> lstOpciones = service.traerEnlacesDeUsuario(bean.getIdusuario());
		Usuario objUsusarioLogeado = service.buscaUsuarioPorCorreo(objsalida.getUsername());
		
		session.setAttribute("objUsuario", objUsusarioLogeado);
		session.setAttribute("objMenus", lstOpciones);
		return service.login(bean);
	}
	*/
	
	@ResponseBody
	@GetMapping(value = "/traerEnlaces/{id}")
	public List<Opcion> traerEnlaces(@PathVariable("id") int id){
		return service.traerEnlacesDeUsuario(id);
	}
	
	@GetMapping(value="/listaUsuarios")
	@ResponseBody
	public List<Usuario> listaUsuarios(){
		List<Usuario> lista= service.listaUsuario();
		return lista;
	}
	
	@GetMapping(value="/listaPersonalTrabajo")
	@ResponseBody
	public List<Usuario> listaPersonalTrabajo(){
		List<Usuario> lista= service.listaPersonalTrabajo();
		return lista;
	}
	
	@GetMapping(value="/listaUsuarioByRol")
	@ResponseBody
	public List<Usuario> listaUsuarioByRol(int cod){
		List<Usuario> lista= service.listaUsuarioPorRol(cod);
		return lista;
	}

	@GetMapping(value="/buscaUsuarioXID")
	@ResponseBody
	public Optional<Usuario> buscaUsuarioXID(int id){
		Optional<Usuario> usuario= service.buscaUsuarioPorId(id);
		return usuario;
	}
	
	@PostMapping(path="/registrarUsuario")
	public ResponseEntity<?> registraUsuario(@RequestBody Usuario obj) {
		try {
			List<Usuario> verific=service.verificarRegistro(obj);
			if(!verific.isEmpty()) {
				return Constantes
						.mensaje(HttpStatus.BAD_REQUEST, "Error", "Este Usuario ya existe. Revise sus datos por favor");
			}else {
				return ResponseEntity.ok(service.registraUsuario(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constantes
					.mensaje(HttpStatus.BAD_REQUEST, "Error", "Hubo un error al realizar la operacion.");
		}
	}
	
	@PutMapping(path="/actualizaUsuario")
	public  ResponseEntity<?>  actualizaUsuario(@RequestBody Usuario obj) {
		try {
			List<Usuario> verific=service.verificarRegistro(obj);
			if(!verific.isEmpty()) {
				return Constantes
						.mensaje(HttpStatus.BAD_REQUEST, "Error", "Este Usuario ya existe. Revise sus datos por favor");
			}else {
				return ResponseEntity.ok(service.registraUsuario(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constantes
					.mensaje(HttpStatus.BAD_REQUEST, "Error", "Hubo un error al realizar la operacion.");
		}
	}
	
	@DeleteMapping(path="/eliminaUsuario/")
	public void eliminaUsuario(int id) {
		service.eliminaUsuario(id);
	}
	
	
}
