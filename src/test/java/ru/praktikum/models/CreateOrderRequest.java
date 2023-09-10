package ru.praktikum.models;

import java.util.List;

public class CreateOrderRequest{
	public CreateOrderRequest(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	private List<String> ingredients;

	public void setIngredients(List<String> ingredients){
		this.ingredients = ingredients;
	}

	public List<String> getIngredients(){
		return ingredients;
	}
}