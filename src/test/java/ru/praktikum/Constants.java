package ru.praktikum;

public class Constants {
    //URL адреса
    public final static String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    public final static String CREATE_USER_URL = "api/auth/register";
    public final static String DELETE_USER_URL = "api/auth/user";
    public final static String UPDATE_USER_URL = "api/auth/user";
    public final static String AUTHORIZATION_USER_URL = "api/auth/login";
    public final static String CREATE_ORDER_URL = "api/orders";
    public final static String GET_USER_ORDER_LIST_URL = "api/orders";

    //Хеши ингредиентов
    public final static String INGREDIENT_HASH_1 = "61c0c5a71d1f82001bdaaa71";
    public final static String INGREDIENT_HASH_2 = "61c0c5a71d1f82001bdaaa74";
    public final static String INGREDIENT_HASH_3 = "61c0c5a71d1f82001bdaaa6d";
    public final static String INVALID_INGREDIENT_HASH = "71c2c6a81d2f81005bdaaa86";
}
