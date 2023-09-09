package com.foodway.api.controller;

import com.foodway.api.model.Estabelecimento;
import com.foodway.api.service.estabelecimento.EstabelecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {


    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping
    public ResponseEntity<List<Estabelecimento>> getEstabelecimentos(){
            return estabelecimentoService.getEstabelecimentos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estabelecimento> getEstabelecimento(@PathVariable UUID id){
        return estabelecimentoService.getEstabelecimentos(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estabelecimento> deleteEstabelecimento(@PathVariable UUID id){
        return estabelecimentoService.deleteEstabelecimento(id);
    }

    @PostMapping
    public ResponseEntity<Estabelecimento> postEstabelecimento(@RequestBody Estabelecimento estabelecimento){
        return estabelecimentoService.save(estabelecimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estabelecimento> putEstabelecimento(@PathVariable UUID id, @RequestBody Estabelecimento estabelecimento){
        return estabelecimentoService.putEstabelecimento(id, estabelecimento);
    }

//    @PostMapping("/t")
//    public ResponseEntity<List<Estabelecimento>> postEstabelecimentos(@RequestBody List<Estabelecimento> estabelecimentos){
//        if(estabelecimentos.isEmpty())  return ResponseEntity.status(204).build();
//
//        return ResponseEntity.status(201).body(estabelecimentoRepository.saveAll(estabelecimentos));
//    }

    /*
    {
    "nome": "nome",
    "descricao": "descricao",
    "cep": "cep",
    "number": "number",
    "logo": "logo",
    "rate": "rate",
    "cnpj": "cnpj"
    }
    */


}
