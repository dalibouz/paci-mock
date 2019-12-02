package com.paci.mock.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A Paci.
 */
@Document(collection = "paci")
public class Paci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("has_paci")
    private Boolean hasPaci;

    @Field("url")
    private String url;

    @DBRef
    @Field("context")
    private PaciContext context;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isHasPaci() {
        return hasPaci;
    }

    public Paci hasPaci(Boolean hasPaci) {
        this.hasPaci = hasPaci;
        return this;
    }

    public void setHasPaci(Boolean hasPaci) {
        this.hasPaci = hasPaci;
    }

    public String getUrl() {
        return url;
    }

    public Paci url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PaciContext getContext() {
        return context;
    }

    public Paci context(PaciContext paciContext) {
        this.context = paciContext;
        return this;
    }

    public void setContext(PaciContext paciContext) {
        this.context = paciContext;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paci)) {
            return false;
        }
        return id != null && id.equals(((Paci) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Paci{" +
            "id=" + getId() +
            ", hasPaci='" + isHasPaci() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
