package com.app.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.FoodItem;
import com.app.entities.Restaurant;

@Repository
public interface FoodItemDao extends JpaRepository<FoodItem, Integer> {

	List<FoodItem> findFoodItemsByRestaurantId(Restaurant restaurantId);
}
