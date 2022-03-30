package com.example.catalogmanager.KafkaListener;

import com.example.catalogmanager.ModelData.Catalog;
import com.example.catalogmanager.ModelData.CatalogCheck;
import com.example.catalogmanager.ModelData.CatalogRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentListener {
    @Autowired
    CatalogRepository CR;

    @Value(value = "${KAFKA_TOPIC_4}")
    private String responseCatalogTopic;

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendMessage(String msg) {
        template.send(responseCatalogTopic,msg);
    }

    // settiamo ora la logica che avviene ogni volta che viene aggiunto un messaggio nel topic
    @KafkaListener(topics = "${KAFKA_TOPIC_3}")
    public void listenCatalogTopic(String message) {
        if (message != null) {
            CatalogCheck catalogCheck = new Gson().fromJson(message, CatalogCheck.class);
            //System.out.println(catalogCheck.toString());
            Optional<Catalog> catalog = CR.findById(catalogCheck.getIdCD());
            if (catalog.isPresent()) {
                Catalog cat = catalog.get();
                if (cat.getnbavailableCD()<catalogCheck.getNbCD()){
                    CatalogCheck responseCatalog= new CatalogCheck()
                            .setIdCD(catalogCheck.getIdCD())
                            .setIdPayment(catalogCheck.getIdPayment());
                    sendMessage(new Gson().toJson(responseCatalog));
                } else {
                    cat.setnbavailableCD(cat.getnbavailableCD()- catalogCheck.getNbCD());
                    CR.save(cat);
                    CatalogCheck responseCatalog= new CatalogCheck()
                            .setIdCD(catalogCheck.getIdCD())
                            .setIdPayment(catalogCheck.getIdPayment())
                            .setNbCD(cat.getpriceCD());  // Utilizzo questa variabile, che in response non servirebbe, per farmi ritornare il prezzo
                    sendMessage(new Gson().toJson(responseCatalog));
                }
            } else {
                CatalogCheck responseCatalog= new CatalogCheck()
                        .setIdPayment(catalogCheck.getIdPayment());
                sendMessage(new Gson().toJson(responseCatalog));
            }
        }
    }

}
