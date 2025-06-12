package br.com.Roger.Roger.pix.pix;

import java.math.BigDecimal;

public record PixRequestPayload(
        String chave,
        BigDecimal valor,
        String cpf,
        String nome
) {}