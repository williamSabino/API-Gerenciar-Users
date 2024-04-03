package br.com.will.GerenciaUsers.controller;

import br.com.will.GerenciaUsers.Dto.UsuarioDTO;
import br.com.will.GerenciaUsers.Dto.UsuarioLoginDto;
import br.com.will.GerenciaUsers.Dto.UsuarioRedefinirDto;
import br.com.will.GerenciaUsers.service.AuthService;
import br.com.will.GerenciaUsers.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/esqueci")
    public ResponseEntity esqueci(@RequestBody UsuarioRedefinirDto usuarioRedefinirDto){
        return service.esqueci(usuarioRedefinirDto);
    }

    @PutMapping("/redefinir")
    @Transactional
    public ResponseEntity redefinir(@RequestBody UsuarioDTO usuarioDTO){
        return service.redefinir(usuarioDTO);
    }
}
