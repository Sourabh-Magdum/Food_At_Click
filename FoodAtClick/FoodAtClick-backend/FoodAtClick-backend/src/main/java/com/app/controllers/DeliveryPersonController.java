package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.Credentials;
import com.app.dtos.CustomerDto;
import com.app.dtos.DeliveryPersonDto;
import com.app.dtos.DeliveryPersonHomePageDto;
import com.app.dtos.DeliveryPersonSignUpDto;
import com.app.dtos.FoodAtClickResponse;
import com.app.dtos.OrdersDto;
import com.app.entities.DeliveryPerson;
import com.app.services.DeliveryPersonService;
import com.app.services.OrdersService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/")
public class DeliveryPersonController {
	
	@Autowired
	private DeliveryPersonService deliveryPersonService;
	
	@Autowired
	private OrdersService ordersService;
	
	@PostMapping("/deliveryperson/signin")
	public ResponseEntity<FoodAtClickResponse> signIn(@RequestBody Credentials cred) {
		DeliveryPersonDto deliveryPersonDto = deliveryPersonService.findDeliveryPersonByEmailAndPassword(cred);
		if(deliveryPersonDto == null)
			return FoodAtClickResponse.error("Couldn't find Delivery Person with that credentials");
		return FoodAtClickResponse.success(deliveryPersonDto);
	}
	
	@GetMapping("/deliverypersonhomepage/{id}")
	public ResponseEntity<FoodAtClickResponse> findDeliveryPersonHomePageDetails(@PathVariable("id") int id){
		DeliveryPersonHomePageDto deliveryPersonDto = ordersService.getdeliveryPersonHomePageDtoById(id);
		if(deliveryPersonDto == null)
			return FoodAtClickResponse.error("Couldn't find Delivery Person Details with that id");
		return FoodAtClickResponse.success(deliveryPersonDto);
	}
	
	@PostMapping("/deliveryperson/{orderId}/{status}")
	public ResponseEntity<FoodAtClickResponse> setStatusByOrder(@PathVariable("orderId") int orderId, @PathVariable("status") String status) {
		boolean updateStatus = ordersService.setStatusForOrder(orderId, status);
		if(!updateStatus)
			return FoodAtClickResponse.error("Couldn't update status for order");
		return FoodAtClickResponse.success("Order status updated");
	}
	
	@GetMapping("/orders/deliveryperson/{id}")  // getAllOrdersbyCustomer
	public ResponseEntity<FoodAtClickResponse> getAllOrdersbydeliveryPersonId(@PathVariable("id") int deliveryPersonId) {
		List<DeliveryPersonHomePageDto> dphpDtoList = ordersService.findAllOrdersByDeliveryPerson(deliveryPersonId);
		if(dphpDtoList == null || dphpDtoList.isEmpty())
			return FoodAtClickResponse.error("List empty!");
		return FoodAtClickResponse.success(dphpDtoList);
	}
	
	@PostMapping("/deliveryperson/arrivedorders/{deliverypersonId}")
	public ResponseEntity<FoodAtClickResponse> getArrivedOrders(@PathVariable("deliverypersonId") int deliverypersonId){
		String status = "arrived";
		List<DeliveryPersonHomePageDto> orders = ordersService.findArrivedordersByDeliverypersonIdAndStatus(deliverypersonId,status);
		if(orders == null || orders.isEmpty())
			return FoodAtClickResponse.error("No orders assigned");
		
		//List<OrdersDto>ordersDtoList = DaoToEntityConverter.ordersToOrdersDto(orders);
		return FoodAtClickResponse.success(orders);
		
	}
	
	@GetMapping("/deliveryperson/{deliverypersonId}/status/{status}")
	public ResponseEntity<FoodAtClickResponse> getOrders(@PathVariable("deliverypersonId") int deliverypersonId, @PathVariable("status") String status){
		
		List<DeliveryPersonHomePageDto> orders = ordersService.findArrivedordersByDeliverypersonIdAndStatus(deliverypersonId,status);
		if(orders == null || orders.isEmpty())
			return FoodAtClickResponse.error("No orders assigned");
		
		return FoodAtClickResponse.success(orders);
	}
	
	@PostMapping("/deliveryperson/signup")
	public ResponseEntity<FoodAtClickResponse> deliveryPersonSignUp(@RequestBody DeliveryPersonSignUpDto deliveryPersonSignUpDto) {
		boolean status = deliveryPersonService.addDeliveryPerson(deliveryPersonSignUpDto);
		if(status)
			return FoodAtClickResponse.success("Delivery Person Added");
		return FoodAtClickResponse.error("Delivery person could not be added");
	}
	
}
