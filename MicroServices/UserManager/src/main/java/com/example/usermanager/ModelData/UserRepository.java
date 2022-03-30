package com.example.usermanager.ModelData;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User getUserByUsernameAndEmail(String Username, String Email);
    public User getUserByUsernameAndPsw(String Username,String psw);
    public User getUserByIdUser(Integer idUser);


}
