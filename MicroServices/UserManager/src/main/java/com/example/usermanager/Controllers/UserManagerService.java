package com.example.usermanager.Controllers;

import com.example.usermanager.ModelData.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
@Component
public class UserManagerService {
    @Autowired
    UserRepository UR;




    // Add User
    public String addUser(User user)
    {

        if (UR.getUserByUsernameAndEmail(user.getUsername(),user.getEmail()) != null) {
            //Se email o username sono presenti nel database non posso crearlo poichè non rispetto i vincoli dell'entity
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username or email already exist. Are You sure You are not already registered?");
        }else {
            UR.save(user);
            user.setNb_acquisti(0);
            return "User created successfully!";
        }
    }

    // Update User
    public String UpdateUser(String username, String psw,User UserUpdated){
        User UserToBeUpdated = UR.getUserByUsernameAndPsw(username,psw);
        UserToBeUpdated.setEmail(UserUpdated.getEmail());
        UserToBeUpdated.setUsername(UserUpdated.getUsername());
        UserToBeUpdated.setPsw(UserUpdated.getPsw());
        UR.save(UserToBeUpdated);
        return "User updated successfully!";
    }

    public String DeleteUser(String username, String psw){
        User UserToBeDeleted = UR.getUserByUsernameAndPsw(username,psw);
        if (UserToBeDeleted != null){
            UR.delete(UserToBeDeleted);

            return "User deleted successfully!";
        }  else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please check if data are correct.");
        }
    }

    public String getIdUser (String username, String psw){
        User UserSearched = UR.getUserByUsernameAndPsw(username,psw);
        if (UserSearched != null) {
            return UserSearched.getIdUser().toString();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please check if data are correct.");

        }
    }




    public Iterable<User> getAllUsers() { return UR.findAll(); }



    public User getUserByIdUser (Integer idUser){
        User UserSearched = UR.getUserByIdUser(idUser);
        if (UserSearched != null) {
            return UserSearched;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please check if data are correct.");

        }
    }





    public String addPurchase(String username, String psw, String N) {
        User UserSearched = UR.getUserByUsernameAndPsw(username, psw);
        if (UserSearched != null) {
            UserSearched.setNb_acquisti(UserSearched.getNb_acquisti() + Integer.parseInt(N));
            return UserSearched.getIdUser().toString();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found. Please check if data are correct.");

        }
    }
}