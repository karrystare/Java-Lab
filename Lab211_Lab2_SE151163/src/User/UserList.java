package User;

import Common.CommonLibrary;
import Common.InputLibrary;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserList extends ArrayList<User>{
        StringBuilder printMenu;
        
    public UserList(){
        printMenu = new StringBuilder();
        printMenu.append("\n").append("------User List With Similar Name (Part)------")
                .append("\n").reverse();
    }
    
    public void addUser(){        
        System.out.println("\nAdd A New User");
        
        String username = getUsername();
        String password = InputLibrary.inputPassword(true);
        String firstName = InputLibrary.inputName("First", false);
        String lastName = InputLibrary.inputName("Last", false);
        String phone = InputLibrary.inputPhoneNumber(false);
        String email = InputLibrary.inputEmail(false);

        System.out.println("\nInformation Entered");
        printCurrentInfo(username, "", firstName, lastName, phone, email);

        if(CommonLibrary.switchYN("\nAdd User? (Y/N): ")){ 
            this.add(new User(username, password, firstName, lastName, phone, email));
            System.out.println("Added Successful");
        }
        else
            System.out.println("Cancelled"); 
        
        System.out.println("\nReturning to main menu");
    }
    
    public void updateMenu(){
        if(this.isEmpty()){
            System.out.println("There is no user to update");
            return;
        }
                 
        System.out.println("-----User Information Updating Menu-----");
        System.out.println("1. Update User");
        System.out.println("2. Remove User");
        System.out.println("Else. Exit");
        int choice = InputLibrary.inputInteger("Choice: ");
        
        if(choice > 0 && choice < 3){
            User user = getUser(InputLibrary.inputUsername());
            String password = InputLibrary.inputPassword(false);

            if(!login(user, password))
                return;

            switch(choice){
                case 1 -> System.out.println("Updating user " + (updateUser(user) ? "Succeed" : "Failed"));
                case 2 -> System.out.println("Removing user " + (removeUser(user) ? "Succeed" : "Failed"));
            }               
        }    
        System.out.println("\nReturning to main menu");      
    }
    
    private boolean updateUser(User user){
        System.out.println("\nCurrent Information");
        user.printInfo();
        
        if(CommonLibrary.switchYN("Do you want to update this user? (Y/N): ")){
            String[] newInfo = new String[5];
            
            newInfo[0] = InputLibrary.inputPassword(true);
            newInfo[1] = InputLibrary.inputName("First", true);
            newInfo[2] = InputLibrary.inputName("Last", true);
            newInfo[3] = InputLibrary.inputPhoneNumber(true);
            newInfo[4] = InputLibrary.inputEmail(true);
            
            System.out.println("\nEntered Information");
            printCurrentInfo(user.getUsername(), "", newInfo[1]
                            ,newInfo[2], newInfo[3], newInfo[4]);
            
            if(CommonLibrary.switchYN("Update User? (Y/N): ")){
                user.updateInfo(CommonLibrary.update(user.getInfo(), newInfo));
                return true;
            }
        }       
        System.out.println("\nCancelled");
        return false;
    }
    
    private boolean removeUser(User user){
        if(CommonLibrary.switchYN("\nDo you want to remove this user? (Y/N): "))
            return this.remove(user);
        System.out.println("\nCancelled");
        return false;
    }
    
    public void printTxt(){
        ArrayList<String> infoList = CommonLibrary.readTxt("save.txt");
        if(infoList == null)
            return;
        System.out.println("\nSaved User List");
        infoList.forEach((infoString) -> {
            printCurrentInfo(infoString.split(";"));
            System.out.println("------------");
        });
    }
    
    public void findUser(){
        String username = InputLibrary.inputUsername();
        ArrayList<String> infoList = CommonLibrary.readTxt("save.txt");
        
        if(infoList == null){
            return;
        }
        else if(infoList.isEmpty()){
            System.out.println("There is no user saved");
            return;
        }
            
        
        Optional<String> check = infoList.stream()
                .filter(infoString -> infoString.substring(0, infoString.indexOf(";")).equals(username))
                .findFirst();
          
        System.out.println(check.isPresent() ? "User Exist" : "User does not exist");
    }
    
    public void searchUserByName(){
        if(this.isEmpty()){
            System.out.println("There is no user to search");
            return;
        }
        
        String namePart = InputLibrary.input("Enter Name Part: ", false);
        StringBuilder infoList = new StringBuilder();
        
        Pattern stringFormat = Pattern.compile(namePart, Pattern.CASE_INSENSITIVE);
        this.forEach((user) -> {
            Matcher matcher = stringFormat.matcher(user.getName());
            if(matcher.find())
                infoList.append("Username: ").append(user.getName()).append("\n")
                        .append("Name: ").append(user.getName()).append("\n")
                        .append("Phone Number: ").append(user.getPhone()).append("\n")
                        .append("Email: ").append(user.getEmail()).append("\n")
                        .append("----------------").append("\n");
        });
        
        if(infoList.length() == 0){
            System.out.println("No User With Such Name (Part) Found");
            return;
        }
        
        infoList.reverse().append(printMenu).reverse();    
        System.out.println(infoList);
    }
    
    public void saveTxt(){
        StringBuilder infoString = new StringBuilder();       
        this.forEach((user) -> {
            infoString.append(user.toString()).append("\n");
        });
        
        System.out.println("Info List Saving " 
                    + (CommonLibrary.writeTxt("save.txt"
                            , infoString.toString()) ? "Succeed" : "Failed"));
    }
    
    private boolean login(User user, String password){
        if(user == null)
            System.out.println("Username does not exist");
        else{
            if(!user.validatePassword(password))
                System.out.println("Wrong Password");   
            else{
                System.out.println("Login Successful");
                return true;
            }
        }        
        return false;
    }
    
    private void printCurrentInfo(String... info){
        System.out.println("Username: " + info[0]);
        System.out.println("First Name: " + info[2]);
        System.out.println("Last Name: " + info[3]);
        System.out.println("Phone Number: " + info[4]);
        System.out.println("Email: " + info[5]);
    }
    
    private String getUsername(){
        while(true){
            String username = InputLibrary.inputUsername();
            if(getUser(username) == null)
                return username;
            System.out.println("Username Unavailable");                           
        }
    }
    
    private User getUser(String username){        
        int index = this.indexOf(new User(username));
        if(index != -1)
            return this.get(index);
        return null;
    }     
}