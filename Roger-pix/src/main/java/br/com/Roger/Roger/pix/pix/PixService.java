// — PixService.java —
package br.com.Roger.Roger.pix.pix;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
public class PixService {

    private final JSONObject config;

    public PixService(PixConfig pixConfig) {
        config = new JSONObject();
        config.put("client_id",     pixConfig.clientId());
        config.put("client_secret", pixConfig.clientSecret());
        config.put("certificate",   pixConfig.certificatePath());
        config.put("sandbox",       pixConfig.sandbox());
        config.put("debug",         pixConfig.debug());
    }

    public JSONObject listarChavesPix() {
        return executar("pixListEvp", Collections.emptyMap(), new JSONObject());
    }

    public JSONObject criarChavePix() {
        return executar("pixCreateEvp", Collections.emptyMap(), new JSONObject());
    }

    public JSONObject deletarChavePix(String chavePix) {
        return executar("pixDeleteEvp",
                Map.of("chave", chavePix),
                new JSONObject());
    }

    public JSONObject criarQrCode(PixRequestPayload payload) {
        JSONObject body = new JSONObject()
                .put("calendario", new JSONObject().put("expiracao", 3600))
                .put("devedor",    new JSONObject()
                        .put("cpf",  payload.cpf())
                        .put("nome", payload.nome()))
                .put("valor",      new JSONObject().put("original", payload.valor()))
                .put("chave",      payload.chave());

        JSONArray infoAdic = new JSONArray()
                .put(new JSONObject().put("nome","Campo 1")
                        .put("valor","Info Adicional 1"))
                .put(new JSONObject().put("nome","Campo 2")
                        .put("valor","Info Adicional 2"));

        body.put("infoAdicionais", infoAdic);

        return executar("pixCreateImmediateCharge",
                Collections.emptyMap(),
                body);
    }

    private JSONObject executar(String operacao,
                                Map<String,String> params,
                                JSONObject body) {
        JSONObject retorno = new JSONObject();
        try {
            EfiPay efi = new EfiPay(config);
            JSONObject resp = efi.call(operacao, params, body);
            log.info("Resposta PIX [{}]: {}", operacao, resp);
            return resp;
        }
        catch (EfiPayException e) {
            log.error("EfiPayException: {}", e.getErrorDescription());
            retorno.put("erro", e.getErrorDescription());
        }
        catch (JSONException ex) {
            log.warn("JSON inválido: {}", ex.getMessage());
            retorno.put("erro", "Payload JSON inválido");
        }
        catch (Exception ex) {
            log.error("Erro genérico: {}", ex.getMessage(), ex);
            retorno.put("erro", "Não foi possível completar a operação");
        }
        return retorno;
    }
}