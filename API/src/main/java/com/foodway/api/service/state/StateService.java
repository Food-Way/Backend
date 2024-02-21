package com.foodway.api.service.state;

import com.foodway.api.handler.exceptions.StateNotFoundException;
import com.foodway.api.model.State;
import com.foodway.api.record.RequestState;
import com.foodway.api.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    @Autowired
    StateRepository stateRepository;

    public ResponseEntity<List<State>> getAll(){
        if(stateRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(stateRepository.findAll());
    }

    public ResponseEntity<State> get(int id){
        State state = stateRepository.findById(id).orElseThrow(() -> new StateNotFoundException("State not found"));
        return ResponseEntity.status(200).body(state);
    }

    public ResponseEntity<State> post(RequestState state) {
        State createdState = new State(state);
        return ResponseEntity.status(201).body(stateRepository.save(createdState));
    }

    public ResponseEntity<State> put(int id, RequestState state) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if(stateOptional.isEmpty()){
            throw new StateNotFoundException("State not found");
        }
        stateOptional.get().update(Optional.ofNullable(state));
        return ResponseEntity.status(200).body(stateRepository.save(stateOptional.get()));
    }

    public ResponseEntity<Void> delete(int id) {
        Optional<State> state = stateRepository.findById(id);
        if(state.isPresent()){
            stateRepository.delete(state.get());
            return ResponseEntity.status(200).build();
        }
        throw new StateNotFoundException("State not found");
    }
}
