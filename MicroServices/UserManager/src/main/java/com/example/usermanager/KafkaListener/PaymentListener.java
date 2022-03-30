package com.example.usermanager.KafkaListener;

import com.example.usermanager.ModelData.User;
import com.example.usermanager.ModelData.UserCheck;
import com.example.usermanager.ModelData.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentListener {
    @Autowired
    UserRepository UR;

    @Value(value = "${KAFKA_TOPIC_2}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendMessage(String msg) {
        template.send(topic,msg);
    }


    // settiamo ora la logica che avviene ogni volta che viene aggiunto un messaggio nel topic
    @KafkaListener(topics = "${KAFKA_TOPIC_1}")
    public String listenUserTopic(String message) {
        if (message != null) {
            //System.out.println(message);
            UserCheck userCheck = new Gson().fromJson(message, UserCheck.class);
            User user = UR.getUserByIdUser(userCheck.getIdUser());
            //System.out.println(user);
            if (user != null) {
                if(userCheck.getIdPayment() != null) {

                    UserCheck responseUser = new UserCheck()
                            .setIdUser(user.getIdUser())
                            .setIdPayment(userCheck.getIdPayment());
                    //System.out.println(responseUser);
                    sendMessage(new Gson().toJson(responseUser));
                }
                else {
                    user.setNb_acquisti((user.getNb_acquisti()+1));
                    UR.save(user);
                }

            }
            else {
                UserCheck responseUser= new UserCheck()
                        .setIdPayment(userCheck.getIdPayment());
                sendMessage(new Gson().toJson(responseUser));
                return "User not found";
            }
        }
        return "User checked";
    }


    public User getUserByIdUser (Integer idUser){
        User UserSearched = UR.getUserByIdUser(idUser);
        if (UserSearched != null) {
            return UserSearched;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please check if data are correct.");
        }
    }
}
