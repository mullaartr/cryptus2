package com.example.cryptus.dao;

import com.example.cryptus.dto.UserDTO;
import com.example.cryptus.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public UserDTO toDto (User user){
        String name = user.getFirstName() +user.getPreposition() +user.getLastName();
        return new UserDTO(name);
    }
}
