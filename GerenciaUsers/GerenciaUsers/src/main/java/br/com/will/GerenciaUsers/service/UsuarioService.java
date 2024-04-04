package br.com.will.GerenciaUsers.service;

import br.com.will.GerenciaUsers.Dto.TokenDto;
import br.com.will.GerenciaUsers.Dto.UsuarioDTO;
import br.com.will.GerenciaUsers.Dto.UsuarioLoginDto;
import br.com.will.GerenciaUsers.Dto.UsuarioRedefinirDto;
import br.com.will.GerenciaUsers.model.Usuario;
import br.com.will.GerenciaUsers.model.mail.EmailDetails;
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
    @Autowired
    private EmailService emailService;

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
            var hashSenha = criarHashSenhas(usuarioDTO.senha());
            Usuario usuario = new Usuario(usuarioDTO.login(), hashSenha);
            repository.save(usuario);
            return ResponseEntity.ok().body("Usuario criado");
        }
    }

    public ResponseEntity esqueci(UsuarioRedefinirDto usuarioRedefinirDto) {
        if(this.repository.findByLogin(usuarioRedefinirDto.login()) == null){
            return ResponseEntity.badRequest().build();
        }
        var usuario = repository.findByLogin(usuarioRedefinirDto.login());
        var token = jwtService.gerarToken((Usuario) usuario);

        EmailDetails email = criarEmail(usuarioRedefinirDto.login(), token);
        var sendEmail = emailService.simpleMail(email);
        return ResponseEntity.ok(sendEmail);
    }
    public ResponseEntity redefinir(UsuarioDTO usuarioDTO) {
        if(this.repository.findByLogin(usuarioDTO.login()) == null){
            return ResponseEntity.badRequest().build();
        }
        Usuario usuario = repository.buscarPorLogin(usuarioDTO.login());
        usuario.redefinirSenha(criarHashSenhas(usuarioDTO.senha()));
        return ResponseEntity.ok().body(usuario);
    }

    private EmailDetails criarEmail(String usuario, String token) {
        EmailDetails email = new EmailDetails();
        email.setRecipient(usuario);
        email.setSubject("Redefinição de email do usuario : " + usuario);
        email.setMsgBody(String.format("""
                Para redefinir clique aqui!
                'http://127.0.0.1:5500/view/Redefinir.html?token=%s'"
                """,token));
        return email;
    }

    private String criarHashSenhas(String senha){
       return new BCryptPasswordEncoder().encode(senha);
    }
}
