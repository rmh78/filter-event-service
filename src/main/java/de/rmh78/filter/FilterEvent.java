package de.rmh78.filter;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

@Entity
@Cacheable
public class FilterEvent extends PanacheEntity {
    public String name;
    public String message;
}
