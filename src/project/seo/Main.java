package project.seo;
import project.seo.Models.Land;
import project.seo.Models.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<User> all_users;
    private static ArrayList<Land> available_lands;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        all_users = new ArrayList<>();
        available_lands = new ArrayList<>();

        System.out.println("\n\n*************************** LAND BROKERAGE ***************************\n");
        System.out.print("\nEnter name(govt for government) : ");
        String name = sc.next();
        if(name.equalsIgnoreCase("govt")){
            govt_authority_case();
        }else{
            general_user_case(name);
        }
    }

    private static void general_user_case(String name){
        User current_user = get_or_create_user(name);
        if(!all_users.contains(current_user))
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

    private static void govt_authority_case(){
        System.out.println("Approve following land details");
        print_all_land_details();
        int select_id_for_approval = 0;
        while (select_id_for_approval!=-100){
            System.out.print("Enter ID of land which you want to approve(-100 to exit): ");
            select_id_for_approval = sc.nextInt();
            sc.nextLine();
            if(select_id_for_approval==-100) {
                System.out.print("Exited successfully\n\n Enter username to login: ");
                general_user_case(sc.nextLine());
            }
            else if(available_lands.contains(findLand(select_id_for_approval))) {
                if(findLand(select_id_for_approval).isApproved())
                    System.out.println("Land is already approved. Choose another id");
                else{
                    findLand(select_id_for_approval).setApproved(true);
                    System.out.println("Land with id " + select_id_for_approval + " approved successfully\n\n");
                }
            }else
                System.out.println("Entered id do not exists in land database");
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
            System.out.println("\tSOLD = "+"NO");
        else
            System.out.println("\tSOLD = "+"YES");
        if(land.isApproved())
            System.out.println("\tApproved by govt authority = "+"YES");
        else
            System.out.println("\tApproved by govt authority = "+"NO");
        if(land.getLeased_by_user()!=null)
            System.out.println("\tleased by = "+land.getLeased_by_user().getName()+"\n\n");
        else
            System.out.println("\tleased by = "+ "No one has leased this land yet\n\n");
    }


    private static void add_land( User current_user){
        int land_id=0;
        String owner_name = current_user.getName();
        long total_area;
        String location;
        long price;

        boolean land_exists = true;

        while(land_exists){
            System.out.print("\nEnter land id : ");
            land_id = sc.nextInt();
            if(available_lands.contains(findLand(land_id))) {
                System.out.println("Entered land id already exists");
            }else
                land_exists = false;
        }

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
        print_all_land_details();
        System.out.print("Enter land id which you want to buy : ");
        int id = sc.nextInt();
        Land buying_land = findLand(id);
        if(buying_land!=null){
            if(buying_land.getOwner_name().equals(current_user.getName())){
                System.out.println("You cannot buy your own land\n\n");
            }else if(!buying_land.isFree())
                System.out.println("Land already bought by others\n\n");
            else if(!buying_land.isApproved())
                System.out.println("Land is not approved by government authority\n\n");
            else {
                current_user.setBoughtLands(buying_land);
                buying_land.setFree(false);
                buying_land.setLeased_by_user(current_user);
                buying_land.increment_number_of_times_leased();
                System.out.println("Land with id " + id + " bought successfully\n\n");
            }
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

    private  static void print_user_specific_uploaded_land_details( User current_user){
        if(current_user.getSoldLands().size()==0)
            System.out.println("You have not added any lands\n\n");
        else
            for (int i : current_user.getSoldLands())
                print_individual_land_details(i);
    }

    private static User get_or_create_user(String name){
        if(name.equalsIgnoreCase("govt")){
            govt_authority_case();
        }else{
            for(User user : all_users)
                if(user.getName().equals(name)) {
                    System.out.println("User already exists, Logged in as same user");
                    return user;
                }
            System.out.println("New user created");
            return new User(name);
        }
        return null;
    }
}
