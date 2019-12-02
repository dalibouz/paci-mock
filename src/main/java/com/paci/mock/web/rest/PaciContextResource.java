package com.paci.mock.web.rest;

import com.paci.mock.domain.PaciContext;
import com.paci.mock.repository.PaciContextRepository;
import com.paci.mock.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.paci.mock.domain.PaciContext}.
 */
@RestController
@RequestMapping("/api")
public class PaciContextResource {

    private final Logger log = LoggerFactory.getLogger(PaciContextResource.class);

    private static final String ENTITY_NAME = "paciContext";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaciContextRepository paciContextRepository;

    public PaciContextResource(PaciContextRepository paciContextRepository) {
        this.paciContextRepository = paciContextRepository;
    }

    /**
     * {@code POST  /paci-contexts} : Create a new paciContext.
     *
     * @param paciContext the paciContext to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paciContext, or with status {@code 400 (Bad Request)} if the paciContext has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paci-contexts")
    public ResponseEntity<PaciContext> createPaciContext(@RequestBody PaciContext paciContext) throws URISyntaxException {
        log.debug("REST request to save PaciContext : {}", paciContext);
        if (paciContext.getId() != null) {
            throw new BadRequestAlertException("A new paciContext cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaciContext result = paciContextRepository.save(paciContext);
        return ResponseEntity.created(new URI("/api/paci-contexts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paci-contexts} : Updates an existing paciContext.
     *
     * @param paciContext the paciContext to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paciContext,
     * or with status {@code 400 (Bad Request)} if the paciContext is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paciContext couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paci-contexts")
    public ResponseEntity<PaciContext> updatePaciContext(@RequestBody PaciContext paciContext) throws URISyntaxException {
        log.debug("REST request to update PaciContext : {}", paciContext);
        if (paciContext.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaciContext result = paciContextRepository.save(paciContext);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paciContext.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paci-contexts} : get all the paciContexts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paciContexts in body.
     */
    @GetMapping("/paci-contexts")
    public List<PaciContext> getAllPaciContexts() {
        log.debug("REST request to get all PaciContexts");
        return paciContextRepository.findAll();
    }

    /**
     * {@code GET  /paci-contexts/:id} : get the "id" paciContext.
     *
     * @param id the id of the paciContext to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paciContext, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paci-contexts/{id}")
    public ResponseEntity<PaciContext> getPaciContext(@PathVariable String id) {
        log.debug("REST request to get PaciContext : {}", id);
        Optional<PaciContext> paciContext = paciContextRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paciContext);
    }

    /**
     * {@code DELETE  /paci-contexts/:id} : delete the "id" paciContext.
     *
     * @param id the id of the paciContext to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paci-contexts/{id}")
    public ResponseEntity<Void> deletePaciContext(@PathVariable String id) {
        log.debug("REST request to delete PaciContext : {}", id);
        paciContextRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
