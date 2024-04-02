package br.com.will.GerenciaUsers.Dto;

import br.com.will.GerenciaUsers.model.Role;

public record UsuarioDTO(
        String login,
        String senha,
        Role role
) {
}
