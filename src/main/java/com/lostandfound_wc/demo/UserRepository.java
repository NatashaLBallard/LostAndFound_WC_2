package com.lostandfound_wc.demo;


import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    Long countByEmail(String email);
    Long countByUsername(String username);
    User findUserByUsername(String username);

    //Iterable <Item> findAllByUsername(String username);
}
