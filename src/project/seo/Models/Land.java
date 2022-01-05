package project.seo.Models;

public class Land {

    int land_id;
    String owner_name;
    long total_area;
    String location;
    long price;
    int number_of_times_leased;
    User leased_by_user;
    boolean free;
    boolean approved;

    public Land(int land_id, long total_area,String owner_name, String location, long price) {
        this.land_id = land_id;
        this.total_area = total_area;
        this.owner_name = owner_name;
        this.location = location;
        this.price = price;
        this.number_of_times_leased = 0;
        free = true;

        approved = false;
        leased_by_user = null;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getLand_id() {
        return land_id;
    }

    public void setLand_id(int land_id) {
        this.land_id = land_id;
    }

    public long getTotal_area() {
        return total_area;
    }

    public void setTotal_area(long total_area) {
        this.total_area = total_area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getNumber_of_times_leased() {
        return number_of_times_leased;
    }

    public void increment_number_of_times_leased(){
        number_of_times_leased++;
    }

    public void setNumber_of_times_leased(int number_of_times_leased) {
        this.number_of_times_leased = number_of_times_leased;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public User getLeased_by_user() {
        return leased_by_user;
    }

    public void setLeased_by_user(User leased_by_user) {
        this.leased_by_user = leased_by_user;
    }
}
