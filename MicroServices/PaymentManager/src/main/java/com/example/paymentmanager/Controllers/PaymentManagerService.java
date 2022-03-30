package com.example.paymentmanager.Controllers;

import com.example.paymentmanager.ModelData.Payment;
import com.example.paymentmanager.ModelData.PaymentRepository;
import com.example.paymentmanager.ModelData.UserCheck;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Component
public class PaymentManagerService {

    // parte di kafka

    @Value(value = "${KAFKA_TOPIC_1}")
    private String userTopic;


    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendUserMessage(String msg) {
        template.send(userTopic,msg);
    }

// parte della gestione dei Payment


    @Autowired
    PaymentRepository PR;

    public Iterable<Payment> getPayments() { return PR.findAll(); }




    public String addPayment( Integer idUser,  Integer nbCD,  Integer idCD) {

        Payment newPayment = new Payment();
        newPayment.setIdUser(idUser);
        newPayment.setIdCD(idCD);
        newPayment.setNbpurchasedCD(nbCD);
        newPayment.setState("Pending");
        PR.save(newPayment);
        checkIdUser(idUser,newPayment.getIdPayment());
        //System.out.println("Ciao");
        return newPayment.toString();
    }



    public void checkIdUser(Integer idUser,Integer idPayment) {
        UserCheck userCheck = new UserCheck()
                .setIdUser(idUser)
                .setIdPayment(idPayment);
        sendUserMessage(new Gson().toJson(userCheck));
    }



    @Transactional
    public String deletePaymentbyidPayment(Integer idPayment) {

        Optional<Payment> SearchedPayment;
        try {
            SearchedPayment = PR.findById(idPayment);
            Integer ReleasedCD = ((SearchedPayment.get().getNbpurchasedCD()));
            PR.deleteById(idPayment);
            return "Purchase cancelled and " + updateAvailableCD(SearchedPayment.get().getIdCD(), ReleasedCD);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase not found");
        }

    }

    public String updateAvailableCD (Integer idCD, Integer nbPurchasedCD) {
        RestTemplate restTemplate = new RestTemplate();
        //String urlupdateAvailableCD = "http://catalogmanager:2222/catalog/updateNBCD/" + idCD + "/" + nbPurchasedCD;
        String urlupdateAvailableCD = "http://catalog-service.default.svc.cluster.local:8080/catalog/updateNBCD/" + idCD + "/" + nbPurchasedCD;
        restTemplate.exchange(urlupdateAvailableCD, HttpMethod.PUT, null, String.class);
        return "available CD updated";
    }

    @Transactional
    public String deletePaymentsByIdUser(Integer IdUser){

        Iterable<Payment>  Payments = PR.getPaymentsByIdUser(IdUser);
        if (Payments == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchases not found");

        } else {
            PR.deleteAll(Payments);
            return "Purchases deleted" ;

        }


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


