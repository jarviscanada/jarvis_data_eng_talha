package ca.jrvs.apps.trading.model.domain;

public interface Entity<ID> {

    ID getId();

    void setID(ID id);
}
