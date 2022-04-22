package com.example.onlineshopping.repository;


import com.example.onlineshopping.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items,Integer> {

}
