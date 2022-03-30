package com.example.paymentmanager.KafkaListener;

import com.example.paymentmanager.ModelData.CatalogCheck;
import com.example.paymentmanager.ModelData.Payment;
import com.example.paymentmanager.ModelData.PaymentRepository;
import com.example.paymentmanager.ModelData.UserCheck;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserListener {
    @Autowired
    PaymentRepository PR;

    @Value(value = "${KAFKA_TOPIC_3}")
    private String catalogTopic;


    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendCatalogMessage(String msg) {
        template.send(catalogTopic,msg);
    }


    @KafkaListener(topics = "${KAFKA_TOPIC_2}")
    public void listenResponseUserTopic(String message) {
        //System.out.println(message);
        if (message != null) {
            UserCheck userCheck = new Gson().fromJson(message, UserCheck.class);
            if (userCheck.getIdUser() == null) {
                Payment paymentToUpdate = PR.getPaymentByIdPayment(userCheck.getIdPayment());
                paymentToUpdate.setState("Abort");
                paymentToUpdate.setReason("User not found");
                PR.save(paymentToUpdate);
            } else {
                Payment pay = PR.getPaymentByIdPayment(userCheck.getIdPayment());
                checkNbCD(pay.getIdCD(), pay.getIdPayment(),pay.getNbpurchasedCD());
            }
        }
    }


    public void checkNbCD(Integer idCD,Integer idPayment,Integer nbCD){
        CatalogCheck cdCheck = new CatalogCheck()
                .setIdCD(idCD)
                .setIdPayment(idPayment)
                .setNbCD(nbCD);
        sendCatalogMessage(new Gson().toJson(cdCheck));
    }



    public Payment getPaymentByIdPayment (Integer idPayment){
        Payment paymentSearched = PR.getPaymentByIdPayment(idPayment);
        if (paymentSearched != null) {
            return paymentSearched;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found. Please check if data are correct.");

        }
    }
}
