package com.paci.mock.web.rest;

import com.paci.mock.domain.Paci;
import com.paci.mock.repository.PaciRepository;
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
 * REST controller for managing {@link com.paci.mock.domain.Paci}.
 */
@RestController
@RequestMapping("/api")
public class PaciResource {

    private final Logger log = LoggerFactory.getLogger(PaciResource.class);

    private static final String ENTITY_NAME = "paci";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaciRepository paciRepository;

    public PaciResource(PaciRepository paciRepository) {
        this.paciRepository = paciRepository;
    }

    /**
     * {@code POST  /pacis} : Create a new paci.
     *
     * @param paci the paci to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paci, or with status {@code 400 (Bad Request)} if the paci has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pacis")
    public ResponseEntity<Paci> createPaci(@RequestBody Paci paci) throws URISyntaxException {
        log.debug("REST request to save Paci : {}", paci);
        if (paci.getId() != null) {
            throw new BadRequestAlertException("A new paci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paci result = paciRepository.save(paci);
        return ResponseEntity.created(new URI("/api/pacis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pacis} : Updates an existing paci.
     *
     * @param paci the paci to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paci,
     * or with status {@code 400 (Bad Request)} if the paci is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paci couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pacis")
    public ResponseEntity<Paci> updatePaci(@RequestBody Paci paci) throws URISyntaxException {
        log.debug("REST request to update Paci : {}", paci);
        if (paci.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Paci result = paciRepository.save(paci);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paci.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pacis} : get all the pacis.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pacis in body.
     */
    @GetMapping("/pacis")
    public List<Paci> getAllPacis() {
        log.debug("REST request to get all Pacis");
        return paciRepository.findAll();
    }

    /**
     * {@code GET  /pacis/:id} : get the "id" paci.
     *
     * @param id the id of the paci to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paci, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pacis/{id}")
    public ResponseEntity<Paci> getPaci(@PathVariable String id) {
        log.debug("REST request to get Paci : {}", id);
        Optional<Paci> paci = paciRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paci);
    }

    /**
     * {@code DELETE  /pacis/:id} : delete the "id" paci.
     *
     * @param id the id of the paci to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pacis/{id}")
    public ResponseEntity<Void> deletePaci(@PathVariable String id) {
        log.debug("REST request to delete Paci : {}", id);
        paciRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
