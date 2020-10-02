package Common;
import java.util.Scanner;

public class InputLibrary {
    static public String input(String message, boolean allowEmpty){
        Scanner sc = new Scanner(System.in);
        String input;
        while(true){
            System.out.print(message);
            input = sc.nextLine();
            if(allowEmpty || (!input.isEmpty() && !allowEmpty))
                return input;
            System.out.println("Cannot Input Nothing");
        }          
    }
    
    public static int inputInteger(String message){
        while(true){
            try{
                int num = Integer.parseInt(input(message, false).trim());
                return num;
            }catch(NumberFormatException e){
                System.out.println(message + " must be an integer.");
            }
        }        
    } 
    
    public static String inputUsername(){
        while(true){
            String username = input("Enter username (Minimum 5 Characters): ", false);
            String regex = "^(?=.{5,32}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
            if(CommonLibrary.findPart(username, regex))
                return username;
            System.out.println("Invalid Username "
                    + "(Must not contain space and special characters)");
        }
    }
    
    public static String inputPassword(boolean reConfirm){
        while(true){
            String password = input("Enter Password: ", false);
            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";
            String negate ="[\\\\;]";
            
            if(!reConfirm)
                return CommonLibrary.hashPassword(password);  
            
            if(CommonLibrary.findPart(password, regex) && (!CommonLibrary.findPart(password, negate))){                
                String passwordConfirm;
                do{
                    passwordConfirm = input("Confirm Password (Enter 0 to reset): ", false);
                    if(password.equals(passwordConfirm))
                        return CommonLibrary.hashPassword(password);
                    else if(!passwordConfirm.equals("0"))
                        System.out.println("Passwords Mismatched");                    
                }while(!passwordConfirm.equals("0"));
            }
            else{
                System.out.println("\nInvalid Format, A Password Must Have");
                System.out.println("At Least 1 upper case letter");
                System.out.println("At least 1 lower case letter");
                System.out.println("At least 1 number");
                System.out.println("No white space");
                System.out.println("At least 6 characters total\n");
            }     
        }
    }
    
    public static String inputName(String type, boolean allowEmpty){
        while(true){
            String name = input("Enter " + type + " Name: ", allowEmpty);
            String regex = "^[A-Za-z0-9 '.]+$";
            if((allowEmpty && name.isEmpty()) || (!name.isEmpty() && CommonLibrary.findPart(name, regex)))
                return CommonLibrary.capitalizeString(name);
            System.out.println("Invalid Name");
        }           
    }
    
    public static String inputPhoneNumber(boolean allowEmpty){
        while(true){
            String phone = input("Enter Phone Number (10 Digits): ", allowEmpty);
            String regex = "^[0-9]{10}$";
            if(CommonLibrary.findPart(phone, regex) || (allowEmpty && phone.isEmpty()))
                return phone;
            System.out.println("Invalid Phone Number");
        }         
    }
    
    public static String inputEmail(boolean allowEmpty){
        while(true){
            String email = input("Enter Email: ", allowEmpty);
            String regex = "^\\w+[A-Za-z0-9._%+-]?+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            if(CommonLibrary.findPart(email, regex) || (allowEmpty && email.isEmpty()))
                return email;
            System.out.println("Invalid Email");
        }       
    }
}
