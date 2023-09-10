package ru.praktikum.models.createOrderResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Order{
	private Owner owner;
	private String createdAt;
	private int number;
	private int price;
	private String name;
	private List<IngredientsItem> ingredients;

	@SerializedName("_id")
	private String id;
	private String status;
	private String updatedAt;

	public void setOwner(Owner owner){
		this.owner = owner;
	}

	public Owner getOwner(){
		return owner;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIngredients(List<IngredientsItem> ingredients){
		this.ingredients = ingredients;
	}

	public List<IngredientsItem> getIngredients(){
		return ingredients;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}