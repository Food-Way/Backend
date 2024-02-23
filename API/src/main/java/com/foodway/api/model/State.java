package com.foodway.api.model;

import com.foodway.api.record.RequestState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

@Table(name = "tbState")
@Entity(name = "state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String initials;

    public State() {
    }

    public State(String name, String initials) {
        this.name = name;
        this.initials = initials;
    }

    public State(RequestState state) {
        this.name = state.name();
        this.initials = state.initials();
    }

    public void update(@NotNull Optional<?> optional) {
        RequestState s = (RequestState) optional.get();
        this.setName(s.name());
        this.setInitials(s.initials());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
}
