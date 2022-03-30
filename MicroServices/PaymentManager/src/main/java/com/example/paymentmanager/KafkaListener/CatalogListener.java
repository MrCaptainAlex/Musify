package com.example.paymentmanager.KafkaListener;

import com.example.paymentmanager.ModelData.CatalogCheck;
import com.example.paymentmanager.ModelData.Payment;
import com.example.paymentmanager.ModelData.PaymentRepository;
import com.example.paymentmanager.ModelData.UserCheck;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CatalogListener {
    @Autowired
    PaymentRepository PR;

    @Value(value = "${KAFKA_TOPIC_1}")
    private String userTopic;


    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendUserMessage(String msg) {
        template.send(userTopic,msg);
    }


    // settiamo ora la logica che avviene ogni volta che viene aggiunto un messaggio nel topic
    @KafkaListener(topics = "${KAFKA_TOPIC_4}")
    public void listenResponseCatalogTopic(String message) {
        if (message != null) {
            CatalogCheck catalogCheck = new Gson().fromJson(message, CatalogCheck.class);
            if (catalogCheck.getIdCD() == null) {
                Payment paymentToUpdate = PR.getPaymentByIdPayment(catalogCheck.getIdPayment());
                paymentToUpdate.setState("Abort");
                paymentToUpdate.setReason("CD does not exist");
                PR.save(paymentToUpdate);
            } else if (catalogCheck.getNbCD() == null) {
                Payment paymentToUpdate = PR.getPaymentByIdPayment(catalogCheck.getIdPayment());
                paymentToUpdate.setState("Abort");
                paymentToUpdate.setReason("Available CD less than the requested");
                PR.save(paymentToUpdate);
            } else {
                Payment paymentToUpdate = PR.getPaymentByIdPayment(catalogCheck.getIdPayment());
                Integer totalPrice = catalogCheck.getNbCD() * paymentToUpdate.getNbpurchasedCD();
                paymentToUpdate.setTotalPrice(totalPrice);
                paymentToUpdate.setState("Completed");
                PR.save(paymentToUpdate);
                addPurchaseToUser(paymentToUpdate.getIdUser());
            }
        }
    }





    public void addPurchaseToUser (Integer idUser){
        UserCheck userPurchase = new UserCheck()
                .setIdUser(idUser);
        sendUserMessage(new Gson().toJson(userPurchase));
    }
}
