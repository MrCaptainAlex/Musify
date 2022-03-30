package com.example.paymentmanager.Adapters;

import com.example.paymentmanager.Controllers.*;
import com.example.paymentmanager.ModelData.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(path="/payment")
public class PaymentManagerAdapter {

        Counter requestCounter;

        public PaymentManagerAdapter(MeterRegistry registry) {
            requestCounter = Counter.builder("pay_counter")
                    .description("Number of post requests to the payment")
                    .register(registry);
        }

    @Autowired
    PaymentManagerService PMS;

    // Add Payment
    @PostMapping(path="/addPayment/{idUser}/{nbCD}/{idCD}")
    public @ResponseBody
    String addPayment(@PathVariable Integer idUser, @PathVariable Integer nbCD, @PathVariable Integer idCD)
    {
        try {
            requestCounter.increment();
            String Response = PMS.addPayment(idUser,nbCD,idCD);
            return Response;
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }

    // Get all payments
    @GetMapping(path="/getPayment")
    public @ResponseBody  Iterable <Payment> getPayment(){   return PMS.getPayments();}


    // delete by idPayment
    @DeleteMapping(path="/deletePaymentbyidPayment/{idPayment}")
    public @ResponseBody
    String deletePaymentbyidPayment( @PathVariable Integer idPayment)
    {
        try {
            return PMS.deletePaymentbyidPayment(idPayment);
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }

    // delete by idUser
    @DeleteMapping(path="/deletePaymentsByIdUser/{idUser}")
    public @ResponseBody
    String deletePaymentsByIdUser( @PathVariable Integer idUser)
    {
        try {
            return PMS.deletePaymentsByIdUser(idUser);
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }


}


/*
    // delete by idCD
    @DeleteMapping(path="/deletePaymentbyCD/{idCD}")
    public @ResponseBody
    String deletePaymentbyCD( @PathVariable Integer idCD)
    {
        try {
            return PMS.deletePayment(idCD);
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }
*/
