package ru.praktikum.models.createOrderResponse;

import com.google.gson.annotations.SerializedName;

public class IngredientsItem{
	private int carbohydrates;
	private String image;
	private int proteins;
	private int price;

	@SerializedName("__v")
	private int v;
	private String name;
	private int fat;

	@SerializedName("_id")
	private String id;
	private int calories;
	private String type;

	@SerializedName("image_mobile")
	private String imageMobile;

	@SerializedName("image_large")
	private String imageLarge;

	public void setCarbohydrates(int carbohydrates){
		this.carbohydrates = carbohydrates;
	}

	public int getCarbohydrates(){
		return carbohydrates;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setProteins(int proteins){
		this.proteins = proteins;
	}

	public int getProteins(){
		return proteins;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setV(int v){
		this.v = v;
	}

	public int getV(){
		return v;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFat(int fat){
		this.fat = fat;
	}

	public int getFat(){
		return fat;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCalories(int calories){
		this.calories = calories;
	}

	public int getCalories(){
		return calories;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setImageMobile(String imageMobile){
		this.imageMobile = imageMobile;
	}

	public String getImageMobile(){
		return imageMobile;
	}

	public void setImageLarge(String imageLarge){
		this.imageLarge = imageLarge;
	}

	public String getImageLarge(){
		return imageLarge;
	}
}