package com.lostandfound_wc.demo;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Long> {
    Iterable <Item> findAllByItemCategoryContainingIgnoreCase(String itemCategory);
    Iterable <Item> findAllByFoundContainingIgnoreCase(String found);
    Iterable <Item> findAllBySavedUsernameContainingIgnoreCase(String alias);

    Iterable <Item> findAllBySavedUsernameIs(String savedUsername);
//    Iterable <Item> findAllBySavedUsernameIs(String savedUsername);
//

    //Iterable <Item> findAllByUsers(String name);
//   User findUserByUsername(String username);
   // User findByUsername(String username);
   // Iterable <Item> findAllByUserAndId(Long id);
}
