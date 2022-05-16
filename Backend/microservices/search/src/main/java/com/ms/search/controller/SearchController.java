package com.ms.search.controller;

import com.ms.search.connectInterface.ConnectorConnect;
import com.ms.search.connectInterface.DataConnect;
import com.ms.search.model.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;

@RestController
public class SearchController {

    private final DataConnect dataConnect;
    private final ConnectorConnect connectorConnect;

    public SearchController(DataConnect dataConnect, ConnectorConnect connectorConnect) {
        this.dataConnect = dataConnect;
        this.connectorConnect = connectorConnect;
    }

    @GetMapping
    @RequestMapping("/schema")
    public ResponseEntity<?> getSchema(@RequestHeader(value = "Authorization") String authorizationHeader) throws JAXBException {
        return new ResponseEntity<> (dataConnect.getCurrentSchema(authorizationHeader), HttpStatus.OK);
    }

    @PostMapping
    @RequestMapping("/search")
    public ResponseEntity<?> search(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody SearchQuery searchQuery) {
        try {
            return new ResponseEntity<>(connectorConnect.searcher(authorizationHeader, searchQuery), HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
