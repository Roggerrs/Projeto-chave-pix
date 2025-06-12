package br.com.Roger.Roger.pix.pix;

import org.json.JSONObject;                         // ← import correto
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/pix",
        produces = MediaType.APPLICATION_JSON_VALUE)
public record PixController(PixService pixService) {

    // 1) LISTAR → GET /api/v1/pix/listar
    @GetMapping("/listar")
    public ResponseEntity<JSONObject> listar() {
        JSONObject resultado = pixService.listarChavesPix();
        return ResponseEntity.ok(resultado);
    }

    // 2) CRIAR CHAVE → POST /api/v1/pix/chave
    @PostMapping("/chave")
    public ResponseEntity<JSONObject> criarChave() {
        JSONObject resultado = pixService.criarChavePix();
        return ResponseEntity
                .status(201)
                .body(resultado);
    }

    // 3) DELETAR CHAVE → DELETE /api/v1/pix/chave?chavePix=...
    @DeleteMapping("/chave")
    public ResponseEntity<JSONObject> deletar(@RequestParam String chavePix) {
        JSONObject resultado = pixService.deletarChavePix(chavePix);
        return ResponseEntity.ok(resultado);
    }

    // 4) GERAR QRCODE → POST /api/v1/pix/qrcode
    @PostMapping(value = "/qrcode",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> qrcode(
            @RequestBody PixRequestPayload payload) {
        JSONObject resultado = pixService.criarQrCode(payload);
        return ResponseEntity.ok(resultado);
    }
}