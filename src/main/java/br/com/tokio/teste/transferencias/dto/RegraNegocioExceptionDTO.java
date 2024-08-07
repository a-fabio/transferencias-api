package br.com.tokio.teste.transferencias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RegraNegocioExceptionDTO {

    private final String mensagem;
    private final HttpStatus httpStatus;
    private final String dataHora;
}