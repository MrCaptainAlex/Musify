
package com.example.catalogmanager.Controllers;


import com.example.catalogmanager.ModelData.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
@Component
public class CatalogManagerService {
    @Autowired
    CatalogRepository CR;


    // AddCD
    public String addCD(Catalog catalog)
    {
        if (CR.getCatalogBynameCD(catalog.getnameCD()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CD already exist.");
        }else {
            CR.save(catalog);
            return "CD created successfully!";
        }
    }


    // Delete CD
    public String DeleteCD(String name){
        Catalog CDToBeDeleted = CR.getCatalogBynameCD(name);
        if (CDToBeDeleted != null){
            CR.delete(CDToBeDeleted);

            return "CD deleted successfully!";
        }  else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CD not found. Please check if data are correct.");
        }
    }

    // Get CD
    public String getCD (String name){
        Catalog CDSearched = CR.getCatalogBynameCD(name);
        if (CDSearched != null) {
            return CDSearched.toString();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CD not found. Please check if data are correct.");

        }
    }
    // Get all CD
    public Iterable<Catalog> getAllCD() { return CR.findAll(); }


    // Get Price
    public String getPrice(Integer idCD) {
        Catalog CDSearched = CR.getCatalogByIdCD(idCD);
        if (CDSearched != null) {
            return CDSearched.getpriceCD().toString();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CD not found");
        }
    }


    // Update nbavailableCD
    public String UpdateNBavailableCD(Integer idCD,Integer nbCD){
        Catalog CDToBeUpdated = CR.getCatalogByIdCD(idCD);
        if (CDToBeUpdated !=null) {
            CDToBeUpdated.setnbavailableCD(CDToBeUpdated.getnbavailableCD()+nbCD);
            CR.save(CDToBeUpdated);
            return "Nb available CD updated successfully!";
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CD not found");
        }
    }

    public String getNBavailableCD (Integer idCD){
        Catalog CDSearched = CR.getCatalogByIdCD(idCD);
        if (CDSearched !=null) {
            return CDSearched.getnbavailableCD().toString();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CD not found");
        }

    }

}


