package com.fullcycle.admim.catalogo.domain;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID>{

    protected AggregateRoot(final ID id) {
        super(id);
    }

}
