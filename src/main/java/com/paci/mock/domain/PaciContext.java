package com.paci.mock.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * A PaciContext.
 */
@Document(collection = "paci_context")
public class PaciContext implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("PACIDISPATCHER")
    private String paciDispatcher;

    @Field("PACIENGINE")
    private String paciEngine;

    @Field("PACIREDIRECT")
    private String paciRedirect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaciDispatcher() {
        return paciDispatcher;
    }

    public PaciContext paciDispatcher(String paciDispatcher) {
        this.paciDispatcher = paciDispatcher;
        return this;
    }

    public void setPaciDispatcher(String paciDispatcher) {
        this.paciDispatcher = paciDispatcher;
    }

    public String getPaciEngine() {
        return paciEngine;
    }

    public PaciContext paciEngine(String paciEngine) {
        this.paciEngine = paciEngine;
        return this;
    }

    public void setPaciEngine(String paciEngine) {
        this.paciEngine = paciEngine;
    }

    public String getPaciRedirect() {
        return paciRedirect;
    }

    public PaciContext paciRedirect(String paciRedirect) {
        this.paciRedirect = paciRedirect;
        return this;
    }

    public void setPaciRedirect(String paciRedirect) {
        this.paciRedirect = paciRedirect;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaciContext)) {
            return false;
        }
        return id != null && id.equals(((PaciContext) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaciContext{" +
            "id=" + getId() +
            ", paciDispatcher='" + getPaciDispatcher() + "'" +
            ", paciEngine='" + getPaciEngine() + "'" +
            ", paciRedirect='" + getPaciRedirect() + "'" +
            "}";
    }
}
