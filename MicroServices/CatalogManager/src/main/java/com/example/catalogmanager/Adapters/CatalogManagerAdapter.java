
package com.example.catalogmanager.Adapters;


import com.example.catalogmanager.ModelData.Catalog;
import com.example.catalogmanager.Controllers.CatalogManagerService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequestMapping(path="/catalog")
public class CatalogManagerAdapter {

    Counter requestCounter;

    public CatalogManagerAdapter(MeterRegistry registry) {
        requestCounter = Counter.builder("catalog_counter")
                .description("Number of post requests to the catalog")
                .register(registry);
    }

    @Autowired
    CatalogManagerService CMS;

    // Add CD
    @PostMapping(path="/addCD")
    public @ResponseBody
    String addCD(@RequestBody Catalog cd)
    {
        try {
            requestCounter.increment();
            return CMS.addCD(cd);

        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }

    //Delete CD
    @DeleteMapping(path = "/deleteCD/{name}")
    public  @ResponseBody
    String deleteCD(@PathVariable String name){
        try {
            return CMS.DeleteCD(name);
        }catch(ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }

    }

    // Get CD
    @PostMapping(path="/getCD/{name}")
    public @ResponseBody
    String getCDbyName(@PathVariable String name) {
        try{
            requestCounter.increment();
            return CMS.getCD(name);
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }

    @GetMapping(path="getAllCD")
    public @ResponseBody Iterable<Catalog> getAllCD() { return CMS.getAllCD(); }



    // Get price
    @PostMapping(path="/getPrice/{idCD}")
    public @ResponseBody
    String getPrice(@PathVariable Integer idCD ){
        try{
            requestCounter.increment();
            return CMS.getPrice(idCD);
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }

    }

    // Update nb available CD
    @PutMapping(path="/updateNBCD/{idCD}/{nbCD}")
    public @ResponseBody
    String UpdateNBavailableCD(@PathVariable Integer idCD, @PathVariable Integer nbCD){
        try{
            return CMS.UpdateNBavailableCD(idCD,nbCD);
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }

    }


    @PostMapping(path="/getNBCD/{idCD}")
    public @ResponseBody String getNBCD(@PathVariable Integer idCD) {

        try {
            requestCounter.increment();
            return CMS.getNBavailableCD(idCD);
        }catch(ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }


    }





}

