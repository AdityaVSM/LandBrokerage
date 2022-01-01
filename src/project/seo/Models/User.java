package project.seo.Models;

import java.util.ArrayList;

public class User {

    String name;
    ArrayList<Integer> soldLands;
    ArrayList<Integer> boughtLands;

    public User(String name) {
        this.name = name;
        this.soldLands = new ArrayList<>();
        this.boughtLands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getSoldLands() {
        return soldLands;
    }

    public void setSoldLands(Land land) {
        this.soldLands.add(land.getLand_id());
    }

    public ArrayList<Integer> getBoughtLands() {
        return boughtLands;
    }

    public void setBoughtLands(Land land) {
        this.boughtLands.add(land.getLand_id());
    }
}
