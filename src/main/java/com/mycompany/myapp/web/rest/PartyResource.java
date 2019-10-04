package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Party;
import com.mycompany.myapp.repository.PartyRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Party}.
 */
@RestController
@RequestMapping("/api")
public class PartyResource {

    private final Logger log = LoggerFactory.getLogger(PartyResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationParty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRepository partyRepository;

    public PartyResource(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    /**
     * {@code POST  /parties} : Create a new party.
     *
     * @param party the party to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new party, or with status {@code 400 (Bad Request)} if the party has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parties")
    public ResponseEntity<Party> createParty(@RequestBody Party party) throws URISyntaxException {
        log.debug("REST request to save Party : {}", party);
        if (party.getId() != null) {
            throw new BadRequestAlertException("A new party cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Party result = partyRepository.save(party);
        return ResponseEntity.created(new URI("/api/parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parties} : Updates an existing party.
     *
     * @param party the party to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated party,
     * or with status {@code 400 (Bad Request)} if the party is not valid,
     * or with status {@code 500 (Internal Server Error)} if the party couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parties")
    public ResponseEntity<Party> updateParty(@RequestBody Party party) throws URISyntaxException {
        log.debug("REST request to update Party : {}", party);
        if (party.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Party result = partyRepository.save(party);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, party.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parties} : get all the parties.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parties in body.
     */
    @GetMapping("/parties")
    public List<Party> getAllParties() {
        log.debug("REST request to get all Parties");
        return partyRepository.findAll();
    }

    /**
     * {@code GET  /parties/:id} : get the "id" party.
     *
     * @param id the id of the party to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the party, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parties/{id}")
    public ResponseEntity<Party> getParty(@PathVariable Long id) {
        log.debug("REST request to get Party : {}", id);
        Optional<Party> party = partyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(party);
    }

    /**
     * {@code DELETE  /parties/:id} : delete the "id" party.
     *
     * @param id the id of the party to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parties/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        log.debug("REST request to delete Party : {}", id);
        partyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
