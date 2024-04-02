package br.com.will.GerenciaUsers.service;

import br.com.will.GerenciaUsers.Dto.TokenDto;
import br.com.will.GerenciaUsers.Dto.UsuarioDTO;
import br.com.will.GerenciaUsers.Dto.UsuarioLoginDto;
import br.com.will.GerenciaUsers.model.Usuario;
import br.com.will.GerenciaUsers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private JwtService jwtService;

    public ResponseEntity login(UsuarioLoginDto usuarioLoginDto) {
        var token = new UsernamePasswordAuthenticationToken(usuarioLoginDto.login(), usuarioLoginDto.senha());
        var autenticacao = manager.authenticate(token);
        var tokenJwt = jwtService.gerarToken((Usuario) autenticacao.getPrincipal());
        return ResponseEntity.ok(new TokenDto(tokenJwt));
    }

    public ResponseEntity registrar(UsuarioDTO usuarioDTO) {
        if(this.repository.findByLogin(usuarioDTO.login()) != null){
            return ResponseEntity.badRequest().build();
        } else {
            var hashSenha = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
            Usuario usuario = new Usuario(usuarioDTO.login(), usuarioDTO.role(), hashSenha);
            repository.save(usuario);
            return ResponseEntity.ok().build();
        }
    }
}
