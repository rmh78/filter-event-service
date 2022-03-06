package de.rmh78.filter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

@Entity
@NamedQuery(name = "FilterEvent.maxId", query = "from FilterEvent e where e.id = (select max(id) from FilterEvent)")
public class FilterEvent extends PanacheEntity {
    public String name;
    public String message;

    @CreationTimestamp
    public Date timestamp;
}
