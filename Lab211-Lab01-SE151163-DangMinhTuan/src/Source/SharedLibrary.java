package Source;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SharedLibrary {
    static HashMap<String, String> regexList;
    
    public static void initializeRegex(){
        regexList = new HashMap<>();
        regexList.put("ID", "^[A-Za-z0-9]+$");
        regexList.put("Student Name", "^[A-Za-z ]+$");
        regexList.put("Subject Name", "^[A-Za-z0-9+#. ]+$");
        regexList.put("Gender", "^[MmFfOo]+$");
        regexList.put("Email", "^\\w+[A-Za-z0-9._%+-]?+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        regexList.put("Phone Number", "^\\d{10,12}$");  
        regexList.put("Credit", "^(?:[0-9]|[12][0-9]|30)$");
    }
    
    public String getRegex(String key){
        return regexList.get(key);
    }
    
    public int findID(String ID, ArrayList<?> sourceList){
        for(int i = 0; i < sourceList.size(); i++){
            if(ID.equals(sourceList.get(i).toString()))
                return i;
        }
        return -1;
    }
    
    public String input(String message, boolean allowEmpty){
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
    
    public String capitalizeString(String str){
        if(str.isEmpty())
            return str;
        str = Arrays.stream(str.toLowerCase().trim().split("\\s+"))
                .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
                .collect(Collectors.joining(" "));        
        return str;
    }
    
    public boolean findPart(String str, String regex){
        Pattern stringFormat = Pattern.compile(regex);
        Matcher stringMatcher = stringFormat.matcher(str);
        return stringMatcher.find();
    }
    
    public String inputCheck(String name, String formatRegex, boolean expectEmpty){
        String str;
        while(true){
            str = capitalizeString(input("Input " + name + ": ", expectEmpty));
            if(findPart(str, formatRegex)){
                return str;
            }
            else if(expectEmpty && str.isEmpty()){
                return str;
            }
            else
                System.out.println("Invalid Format");
        }
    }
    
    public String genderConvert(String gender){
        switch(gender.toUpperCase()){
            case "M": return "Male";
            case "F": return "Female";
            case "O": return "Other";
        }
        throw new IllegalArgumentException("Input Must Be (M|F|O)");
    }
    
    public String inputDate(boolean expectEmpty){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        String date = inputCheck("Date of Birth (DD/MM/YYYY)", "[0-9]", expectEmpty);
        
        if(expectEmpty && date.isEmpty())
            return date;
        try{
            formatter.parse(date);
            return date;
        }catch(ParseException e){
            System.out.println("Invalid Date");
        }              
        return inputDate(expectEmpty);
    }    
    
    public int inputInteger(String message){
        while(true){
            try{
                int num = Integer.parseInt(input("Input " + message + " : ", false).trim());
                return num;
            }catch(NumberFormatException e){
                System.out.println(message + " must be an integer.");
            }
        }        
    }
    
    public boolean continueSwitch(String message){
        while(true){
           switch(input("\n" + message + " (Y/N): ", false).toUpperCase().trim()){
               case "Y" : return true;
               case "N" : return false;
               default: System.out.println("Invalid Input"); break;
           }        
        }
    }
}
