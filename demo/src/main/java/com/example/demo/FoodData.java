package com.example.demo;

public class FoodData {
   String Date,food;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public FoodData(String date, String food) {
        Date = date;
        this.food = food;
    }

    public FoodData() {
    }
}
