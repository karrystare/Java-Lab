package Common;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommonLibrary { 
    
    public static boolean switchYN(String message){
        while(true){
           switch(InputLibrary.input(message, false).toUpperCase().trim()){
               case "Y" -> {
                   return true;
                }
               case "N" -> {        
                   return false;
                }
               default -> System.out.println("Invalid Input");            }        
        }
    }
    
    public static boolean findPart(String str, String regex){
        Pattern stringFormat = Pattern.compile(regex);
        Matcher stringMatcher = stringFormat.matcher(str);
        return stringMatcher.find();
    }   
    
    static public String capitalizeString(String str){
        if(str.isEmpty())
            return str;
        str = Arrays.stream(str.toLowerCase().trim().split("\\s+"))
                .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
                .collect(Collectors.joining(" "));        
        return str;
    }     
    
    static public String[] update(String[] currentInfo, String[] newInfo){
        for(int i = 0; i < currentInfo.length; i++)
            if(!newInfo[i].isEmpty())
                currentInfo[i] = newInfo[i];
        return currentInfo;
    }
    
    private static byte[] getByte(String password){
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedPassword = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
            return encodedPassword;
        } catch (NoSuchAlgorithmException ex) {
            System.out.print("");
        }	
        return null;
    }
    
    static public String hashPassword(String password){
        StringBuffer hexString = new StringBuffer();
        byte[] hash = getByte(password);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();    
    }
    
    static public boolean writeTxt(String filePath, String infoOut){
        Path path = Paths.get(filePath);
        try {
            Files.writeString(path, infoOut, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException ex) {
            System.out.println("File Is Inaccessible");
        } 
        return false;
    }
    
    static public ArrayList<String> readTxt(String filePath){
        ArrayList<String> userInfoList = new ArrayList<>();
        Path path = Paths.get(filePath);
        try{
            String info = Files.readString(path);
            if(findPart(info, "\n"))
                userInfoList.addAll(Arrays.asList(info.split("\n")));
            return userInfoList;
        }catch(IOException e){
            System.out.println("File Inaccessible or There was nothing to read");
            return null;
        }
    }
}
