package br.com.will.GerenciaUsers.controller;

import br.com.will.GerenciaUsers.Dto.UsuarioDTO;
import br.com.will.GerenciaUsers.Dto.UsuarioLoginDto;
import br.com.will.GerenciaUsers.service.AuthService;
import br.com.will.GerenciaUsers.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UsuarioLoginDto usuarioLoginDto){
        return service.login(usuarioLoginDto);
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody UsuarioDTO usuarioDTO){
        return service.registrar(usuarioDTO);
    }

}
