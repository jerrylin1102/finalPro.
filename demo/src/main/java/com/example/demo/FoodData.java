package com.example.demo;

public class FoodData {
   String date,food;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public FoodData(String date, String food) {
        date = date;
        this.food = food;
    }

    public FoodData() {
    }
    @Override
    public String toString() {
        return "FoodData{" +
                "date='" + date + '\'' +
                ", food='" + food + '\'' +
                '}';
    }

}
