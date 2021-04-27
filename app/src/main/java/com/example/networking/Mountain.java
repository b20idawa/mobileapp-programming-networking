package com.example.networking;

/*
        "ID": "mobilprog_k2",
        "name": "K2",
        "type": "brom",
        "company": "",
        "location": "The Karakoram range",
        "category": "",
        "size": 8611,
        "cost": 28251,
        "auxdata": {
        "wiki": "https://en.wikipedia.org/wiki/K2",
        "img": "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/K2_2006b.jpg/640px-K2_2006b.jpg"
*/

public class Mountain {
    private String ID;
    private String name;
    private String type;
    private String company;
    private String location;
    private String category;
    private int size;
    private int cost;
    private Auxdata auxdata;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return name;

    }
}
