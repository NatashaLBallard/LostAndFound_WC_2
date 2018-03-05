package com.lostandfound_wc.demo;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Long> {
    Iterable <Item> findAllByItemCategoryContainingIgnoreCase(String itemCategory);
    Iterable <Item> findAllByFoundContainingIgnoreCase(String found);
    Iterable <Item> findAllByAliasContainingIgnoreCase(String alias);
   // Iterable <Item> findAllByUserAndId(Long id);
}
