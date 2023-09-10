package ru.praktikum.models.createOrderResponse;

public class CreateOrderResponse{
	private boolean success;
	private String name;
	private Order order;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOrder(Order order){
		this.order = order;
	}

	public Order getOrder(){
		return order;
	}
}