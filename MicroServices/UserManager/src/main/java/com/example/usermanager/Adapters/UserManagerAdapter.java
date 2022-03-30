package com.example.usermanager.Adapters;


import com.example.usermanager.ModelData.User;
import com.example.usermanager.Controllers.UserManagerService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;


@Controller
@RequestMapping(path="/user")
public class UserManagerAdapter {

    Counter requestCounter;

    public UserManagerAdapter(MeterRegistry registry) {
        requestCounter = Counter.builder("user_counter")
                .description("Number of post requests to the user")
                .register(registry);
    }

    @Autowired
    UserManagerService UMS;

    // Add user
    @PostMapping(path="/addUser")
    public @ResponseBody
    String insertUser(@RequestBody User u)
    {
        try {
            requestCounter.increment();
            return UMS.addUser(u);

        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }

    // Update user
    @PutMapping(path="/updateUser/{username}/{psw}")
    public @ResponseBody
    String updateUser(@PathVariable String username, @PathVariable String psw, @RequestBody User user ){
        try{
            requestCounter.increment();
            return UMS.UpdateUser(username,psw,user);
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }

    }

    //Delete user
    @DeleteMapping(path = "/deleteUser/{username}/{psw}")
    public  @ResponseBody
    String deleteUser(@PathVariable String username, @PathVariable String psw){

        try {
            return UMS.DeleteUser(username,psw);
        }catch(ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }

    }

    // Get user
    @PostMapping(path="/getIdUser")
    public @ResponseBody
    String getUserId(@RequestBody Map<String,String> user) {
        try{
            requestCounter.increment();
            return UMS.getIdUser(user.get("username"), user.get("psw"));
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }

    // Add purchase
    @PostMapping(path="/addPurchase")
    public @ResponseBody
    String addPurchase (@RequestBody Map<String,String> user){
        try {
            requestCounter.increment();
            return UMS.addPurchase(user.get("username"), user.get("psw"), user.get("nb_acquisti") );
        }catch (ResponseStatusException e){
            return e.getStatus() + " " + e.getReason();
        }
    }

    @GetMapping(path="getAllUsers")
    public @ResponseBody Iterable<User> getAllUsers() { return UMS.getAllUsers(); }
}
