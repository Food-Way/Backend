package com.foodway.api.service.estabelecimento;

import com.foodway.api.model.Estabelecimento;
import com.foodway.api.repository.EstabelecimentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EstabelecimentoService {

    private EstabelecimentoRepository estabelecimentoRepository;

    public ResponseEntity getEstabelecimentos() {
        if(estabelecimentoRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(estabelecimentoRepository.findAll());
    }
    public ResponseEntity<Estabelecimento> getEstabelecimentos(UUID paramId) {
        Optional<Estabelecimento> estabelecimento = estabelecimentoRepository.findById(paramId);
        return estabelecimento.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() -> ResponseEntity.status(404).build());
    }

    public ResponseEntity<Estabelecimento> deleteEstabelecimento(UUID id) {
        Optional<Estabelecimento> estabelecimento = estabelecimentoRepository.findById(id);
         if(estabelecimento.isEmpty()){
             return ResponseEntity.status(404).build();
         }
         estabelecimentoRepository.delete(estabelecimento.get());
         return ResponseEntity.status(204).build();
    }

    public ResponseEntity<Estabelecimento> save(Estabelecimento estabelecimento) {
        return ResponseEntity.status(201).body(estabelecimentoRepository.save(estabelecimento));
    }
}
