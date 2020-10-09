package com.test.Provider;

public class Movie {


    private long id;
    private String name;

    public Movie(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



    public long getId() {
        return id;
    }
}
