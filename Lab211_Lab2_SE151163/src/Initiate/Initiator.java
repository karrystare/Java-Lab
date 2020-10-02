package Initiate;

import Common.InputLibrary;
import User.UserList;

public class Initiator {
    public static void main(String[] args){
        StringBuilder menu = new StringBuilder();
        menu.append("\n").append("------Main Menu------");
        menu.append("\n").append("1. Add User");
        menu.append("\n").append("2. Check Username Status");
        menu.append("\n").append("3. Print All User(s) With Name (Part)");
        menu.append("\n").append("4. Update User");
        menu.append("\n").append("5. Save User List To Txt File");
        menu.append("\n").append("6. Print All Saved User");
        menu.append("\n").append("Else. Exit");        
        
        UserList init = new UserList();
        int choice;
        do{
            System.out.println(menu);
            choice = InputLibrary.inputInteger("Choice: ");
            switch(choice){
                case 1 -> init.addUser();
                case 2 -> init.findUser();
                case 3 -> init.searchUserByName();
                case 4 -> init.updateMenu();
                case 5 -> init.saveTxt();
                case 6 -> init.printTxt();
                default -> choice = 7;             }
        }while(choice != 7);  
        System.out.println("Program Terminated");
    }
}
