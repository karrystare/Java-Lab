package Student;
import Source.SharedLibrary;
import Subject.Grade;
import Subject.Subject;
import java.util.ArrayList;
import java.util.Collections;

public class StudentList extends ArrayList<Student>{
    SharedLibrary tools = new SharedLibrary();
    
    public void add(){
        System.out.println("\nAdding New Student");
        do{
            System.out.println("");
            String studentID = inputID(); //Input and check ID availability
            String[] info = getInfo(false); //Input the rest and decline empty strings
            
            System.out.println("\nInformation Received");
            printCurrentInfo(studentID, info); //Print all info to confirm before adding
            
            if(tools.continueSwitch("Add Student?")) //Generate confirmation
                this.add(new Student(studentID, info)); //Create and Add a new Student into the ArrayList
            else
                System.out.println("Cancelled");       
        }while(tools.continueSwitch("Do you want to add another student?")); //Generate loop confirmation
    }
    
    public void updateMenu(){
        if(this.isEmpty()){
            System.out.println("There is no student to update");
            return;
        }
        
        int choice;
        do{
            System.out.println("\n---Student Information Updating Menu---"); //Update Menu
            System.out.println("1. Update a student");
            System.out.println("2. Remove a student");
            System.out.println("Others. Exit");  
            choice = tools.inputInteger("Choice"); //Call method to only input and return an int
                        
            switch (choice) {
                case 1:
                    System.out.println("Updated Student " + (update(getIndex()) ? "Successful" : "Failure")); //Call update method, return boolean
                    break;
                case 2:
                    System.out.println("Removed Student " + (removeStudent(getIndex()) ? "Successful" : "Failure")); //Call remove method, return boolean
                    break;
                default:
                    System.out.println("Returning to main menu");
                    break;
            }
        }while(choice == 1 || choice == 2); //Return to main menu if encountered any other number
    }
    
    private boolean update(int updateIndex){
        System.out.println("Updating Information of student");
        System.out.println("Current Information");
        this.get(updateIndex).printStudentInfo(); //Print current info
        
        while(true){
            if(tools.continueSwitch("Do you want to update this student's information?")){ //Generate update confirmation
                System.out.println("");
                String[] newInfo = getInfo(true); //Get new info   
                
                System.out.println("\nInformation Entered");
                printCurrentInfo(this.get(updateIndex).getStudentID(), newInfo); //Print new info for confirmation
                
                if(tools.continueSwitch("Update Student?")){ //Generate confirmation
                    this.get(updateIndex).updateStudent(newInfo); // Update new info
                    return true; //Return true if successful
                }
            }
            return false; //Return false if declined
        }
    }    
    
    private boolean removeStudent(int removeIndex){
        if(!this.get(removeIndex).getStudentSubjectList().isEmpty()){
            System.out.println("Unable to remove a student that has already enrolled in a subject");
            return false;
        }
        
        System.out.println("\nDetails of student");
        this.get(removeIndex).printStudentInfo(); //Print student info
        
        while(true){
            if(tools.continueSwitch("Remove Student?")){ //Generate confirmation
                this.remove(removeIndex); //Remove student
                return true; //Return true if successful
            }
            return false; //Return false if declined
        }
    }
    
    public void printStudentGradeReport(){
        if(this.isEmpty()){
            System.out.println("There is no student to print");
            return;
        }
        
        do{
            Student student = this.get(getIndex());            
            
            StringBuilder gradeList = new StringBuilder();
            gradeList.append("\n").append("Student ID: ").append(student.getStudentID()).append("\n");
            gradeList.append("Student Name: ").append(student.getName()).append("\n");
            gradeList.append("This Student Has Enrolled in ").append(student.getNumberSubject()).append(" subject(s)").append("\n");
            gradeList.append("---------------------------Sorted Grade Report---------------------------").append("\n");
            gradeList.append(String.format("|%-5s|%-40s|%-13s|%-6s|", "No.", "Subject Name", "Average Grade", "Status")).append("\n");
            
            int subjectCount = 1;
            Collections.sort(student.getStudentSubjectList());
            for(Subject subject : student.getStudentSubjectList()){
                Grade grade = subject.getGrade(student);
                gradeList.append(String.format("|%-5s|%-40s|%-13.2f|%-6s|", subjectCount++
                                            , subject.getName(), grade.getAverageGrade()
                                            , grade.getStatus())).append("\n");
            }         
            System.out.print(gradeList);   
        }while(tools.continueSwitch("Do you want to print another student's grade report?"));    
    }
    
    private void printCurrentInfo(String studentID, String[] info){
        System.out.println("Student ID: " + studentID);                     //ID
        System.out.println("Student Name: " + info[0] + " " + info[1]);     //Full Name
        System.out.println("Student Gender: " + info[2]);                   //Gender
        System.out.println("Student Date of Birth: " + info[3]);            //DOB
        System.out.println("Student Email: " + info[4]);                    //Email        
        System.out.println("Student Phone Number: " + info[5]);             //Phone Number
    }
    
    private String[] getInfo(boolean allowEmpty){
        String[] info = new String[6];
        info[0] = tools.inputCheck("First Name", tools.getRegex("Student Name"), allowEmpty);                   //First Name
        info[1] = tools.inputCheck("Last Name", tools.getRegex("Student Name"), allowEmpty);                    //Last Name
        info[2] = tools.genderConvert(tools.inputCheck("Gender (M|F|O)", tools.getRegex("Gender"), allowEmpty));//Gender
        info[3] = tools.inputDate(allowEmpty);                                                                  //DOB
        info[4] = tools.inputCheck("Email (example@gmail.com)", tools.getRegex("Email"), allowEmpty);           //Email
        info[5] = tools.inputCheck("Phone Number (10-12 Digits)", tools.getRegex("Phone Number"), allowEmpty);  //Phone Number
        return info;
    }
    
    public int getIndex(){
        while(true){
            String id = tools.inputCheck("Student ID (Must be Unique)", tools.getRegex("ID"), false).toUpperCase();
            int index = tools.findID(id, this);
            if(index != -1)
                return index;
            else
                System.out.println("ID Does Not Exist");
        }     
    }
    
    public String inputID(){
        while(true){
            String studentID = tools.inputCheck("Student ID (Must be Unique)", tools.getRegex("ID"), false).toUpperCase();
            if(tools.findID(studentID, this) == -1)
                return studentID;
            else      
                System.out.println("The ID (" + studentID + ") is Unavailable");                      
        }
    }
}