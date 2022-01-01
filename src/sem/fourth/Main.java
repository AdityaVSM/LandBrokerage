package sem.fourth;
import sem.fourth.Models.Land;
import sem.fourth.Models.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<User> all_users;
    private static ArrayList<Land> available_lands;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        all_users = new ArrayList<>();
        available_lands = new ArrayList<>();

        System.out.println("*************************** LAND BROKERAGE ***************************");
        System.out.print("\nEnter name : ");
        User current_user = new User(sc.nextLine());
        all_users.add(current_user);

        System.out.println("\nSelect operation (-99) to exit **");
        System.out.println("** 1 View available lands **");
        System.out.println("** 2 Add a land **");
        System.out.println("** 3 Buy a land **");
        System.out.println("** 4 View Lands bought buy you **");
        System.out.println("** 5 View Lands you added **");
        System.out.println("** 6 Login as different user **");
        int choice=0;

        while (choice!=-99){
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:{
                    print_all_land_details();
                    break;
                }
                case 2:{
                    add_land(current_user);
                    break;
                }
                case 3:{
                    buy_land(current_user);
                    break;
                }
                case 4:{
                    specific_user_bought_land_details(current_user);
                    break;
                }
                case 5:{
                    print_user_specific_uploaded_land_details(current_user);
                    break;
                }
                case 6:{
                    System.out.println("Enter user name");
                    current_user = get_or_create_user(sc.nextLine());
                    break;
                }
                default:{
                    System.out.println("Enter correct option\n\n");
                    break;
                }
            }
        }
    }

    private static void print_all_land_details(){
        if(available_lands.size()==0)
            System.out.println("No lands available\n\n");
        else{
            System.out.println("Available lands:");
            for(Land land : available_lands){
                print_individual_land_details(land.getLand_id());
            }
        }
    }

    public static void print_individual_land_details(int id){
        Land land = findLand(id);
        System.out.println("ID = "+id);
        System.out.println("\tOwner name = "+land.getOwner_name());
        System.out.println("\tPrice = "+land.getPrice());
        System.out.println("\tTotal spread area = "+land.getTotal_area());
        System.out.println("\tTotal number of times leased = "+land.getNumber_of_times_leased());
        System.out.println("\tLocation : "+land.getLocation());
        if (land.isFree())
            System.out.println("\tSOLD = "+"NO\n\n");
        else
            System.out.println("\tSOLD = "+"YES\n\n");
    }


    private static void add_land(User current_user){
        int land_id;
        String owner_name = current_user.getName();
        long total_area;
        String location;
        long price;


        System.out.print("\nEnter land id : ");
        land_id = sc.nextInt();

        // TODO : check if land with same id already exists and reply accordingly

        System.out.print("Enter total area : ");
        total_area = sc.nextLong();
        System.out.print("Enter location :");
        location = sc.next();
        System.out.print("Enter price : ");
        price = sc.nextLong();

        Land land = new Land(land_id,total_area,owner_name,location,price);
        available_lands.add(land);
        current_user.setSoldLands(land);
        System.out.println("Land with id "+land_id+" successfully added\n\n");
    }

    private static void buy_land(User current_user){
        /*FIXME
        *  User should not be able to buy his own land
        *  If a land is already bought we should not be able to buy same land*/

        print_all_land_details();
        System.out.print("Enter land id which you want to buy : ");
        int id = sc.nextInt();
        Land buying_land = findLand(id);
        if(buying_land!=null){
            current_user.setBoughtLands(buying_land);
            buying_land.setFree(false);
            buying_land.increment_number_of_times_leased();
            System.out.println("Land with id "+id+" bought successfully\n\n");
        }else if(!buying_land.isFree()){
            System.out.println("Land already bought by others\n\n");
        }else
            System.out.println("Land with entered id do not exist\n\n");
    }

    private static Land findLand(int requested_id){
        for(Land land: available_lands){
            if(land.getLand_id()==requested_id){
                return land;
            }
        }
        return null;
    }

    private static void specific_user_bought_land_details(User current_user){
        if(current_user.getBoughtLands().size()==0){
            System.out.println("You have not bought any lands\n\n");
        }else
            for (int i : current_user.getBoughtLands())
                print_individual_land_details(i);
    }

    private  static void print_user_specific_uploaded_land_details(User current_user){
        if(current_user.getSoldLands().size()==0)
            System.out.println("You have not added any lands\n\n");
        else
            for (int i : current_user.getSoldLands())
                print_individual_land_details(i);
    }

    private static User get_or_create_user(String name){
        for(User user : all_users)
            if(user.getName().equals(name)) {
                System.out.println("User already exists, Logged in as same user");
                return user;
            }
        System.out.println("New user created");
        return new User(name);
    }


}
