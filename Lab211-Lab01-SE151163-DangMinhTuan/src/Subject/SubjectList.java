package Subject;
import java.util.ArrayList;
import Source.SharedLibrary;
import Student.Student;
import Student.StudentList;
import java.util.Map;

public class SubjectList extends ArrayList<Subject>{
    SharedLibrary tools = new SharedLibrary();
    
    public void add(){
        System.out.println("\nAdding New Subject");
        do{
            System.out.println("");
            String subjectID = inputID(); //Input and check ID availability
            String name = tools.inputCheck("Subject Name", tools.getRegex("Subject Name"), false); //Input and check name format
            int credit = Integer.parseInt(tools.inputCheck("Credit (0-30)", tools.getRegex("Credit"), false)); //Input and check number format
            
            System.out.println("\nInformation Received");
            printCurrentInfo(subjectID, name, String.valueOf(credit)); //Print all info to confirm before adding
            
            if(tools.continueSwitch("Add Subject?")) //Generate confirmation
                this.add(new Subject(subjectID, name, credit)); //Create and Add a new Subject into the ArrayList
            else
                System.out.println("Cancelled");       
        }while(tools.continueSwitch("Do you want to add another subject?")); //Generate loop confirmation
    }
    
    public void updateMenu(){
        if(this.isEmpty()){
            System.out.println("There is no subject to update");
            return;
        }
        
        int choice;
        do{
            System.out.println("\n---Subject Information Updating Menu---"); //Update Menu
            System.out.println("1. Update a subject");
            System.out.println("2. Remove a subject");
            System.out.println("Others. Exit");  
            choice = tools.inputInteger("Choice"); //Call method to only input and return an int
                        
            switch (choice) {
                case 1:
                    System.out.println("Updated Subject " + (update(getIndex()) ? "Successful" : "Failure")); //Call update method, return boolean
                    break;
                case 2:
                    System.out.println("Removed Subject " + (removeSubject(getIndex()) ? "Successful" : "Failure")); //Call remove method, return boolean
                    break;
                default:
                    System.out.println("Returning to main menu");
                    break;
            }
        }while(choice == 1 || choice == 2); //Return to main menu if encountered any other number
    }
    
    private boolean update(int updateIndex){
        System.out.println("\nUpdating Information of subject");
        System.out.println("\nCurrent Information");
        this.get(updateIndex).printSubjectInfo(); //Print current info
        
        while(true){
            if(tools.continueSwitch("Do you want to update this subject's information?")){ //Generate update confirmation
                String[] newInfo = new String[2];
                newInfo[0] = tools.inputCheck("Subject Name", tools.getRegex("Subject Name"), true); //Input and check name format
                newInfo[1] = tools.inputCheck("Credit (0-30)", tools.getRegex("Credit"), true); //Input and check number format
            
                System.out.println("\nInformation Entered");
                printCurrentInfo(this.get(updateIndex).getSubjectID(), newInfo[0], newInfo[1]); //Print new info for confirmation
                
                if(tools.continueSwitch("Update Subject?")){ //Generate confirmation
                    this.get(updateIndex).updateSubject(newInfo); // Update new info
                    return true; //Return true if successful
                }
            }
            return false; //Return false if declined
        }
    }    
    
    private boolean removeSubject(int removeIndex){
        if(!this.get(removeIndex).getGradeMap().isEmpty()){
            System.out.println("Unable to remove a subject that has gotten a student enrolled");
            return false;
        }        
        
        System.out.println("\nDetails of subject");
        this.get(removeIndex).printSubjectInfo(); //Print subject info
        
        while(true){
            if(tools.continueSwitch("Remove Subject?")){ //Generate confirmation
                this.remove(removeIndex); //Remove subject
                return true; //Return true if successful
            }
            return false; //Return false if declined
        }
    }
    
    public void updateGrade(StudentList source){       
        if(this.isEmpty()){
            System.out.println("There is no subject to update");
            return;
        }
        else if(source.isEmpty()){
            System.out.println("There is no student to update");
            return;
        }
        
        System.out.println("\nUpdating Grades");
        do{
            System.out.println("");
            double[] grade = new double[3];
            int studentIndex = source.getIndex();
            int subjectIndex = this.getIndex();
            if(this.get(subjectIndex).isContain(source.get(studentIndex))){
                System.out.println("The subject " + this.get(subjectIndex).getName()
                                                  + " has already gotten grade for student "
                                                  + source.get(studentIndex).getName());
                if(!tools.continueSwitch("Overwrite Grade?")){
                    System.out.println("Cancelled");
                    continue;
                }
            }
            
            grade[0] = inputGrade("Labs Grade");
            grade[1] = inputGrade("Progress Tests Grade");
            grade[2] = inputGrade("Final Exam Grade"); 
            
            System.out.println("\nGrades Entered");
            System.out.println("Labs Grade: " + String.valueOf(grade[0]));
            System.out.println("Progress Tests Grade: " + String.valueOf(grade[1]));
            System.out.println("Final Exam Grade: " + String.valueOf(grade[2]));
            
            if(tools.continueSwitch("Update Grade?")){
                this.get(subjectIndex).addGrade(source.get(studentIndex), grade);
                source.get(studentIndex).addSubject(this.get(subjectIndex));
            }               
        }while(tools.continueSwitch("Do you want to update another grade?"));
    }
    
    public void printSubjectReport(){ 
        if(this.isEmpty()){
            System.out.println("There is no subject to print");
            return;
        }
        
        do{
            Subject subject = this.get(getIndex());

            StringBuilder gradeList = new StringBuilder();
            gradeList.append("\n").append("Subject ID: ").append(subject.getSubjectID()).append("\n");
            gradeList.append("Subject Name: ").append(subject.getName()).append("\n");
            gradeList.append("This Subject Has ").append(subject.numberStudent()).append(" student(s)").append("\n");
            gradeList.append("---------------------------Sorted Grade Report---------------------------").append("\n");
            gradeList.append(String.format("|%-5s|%-40s|%-13s|%-6s|", "No.", "Student Name", "Average Grade", "Status")).append("\n");

            int studentCount = 1;
            for(Map.Entry<Student, Grade> entry : subject.getGradeMap().entrySet())    
                gradeList.append(String.format("|%-5s|%-40s|%-13.2f|%-6s|", studentCount++
                        , entry.getKey().getName() , entry.getValue().getAverageGrade()
                        , entry.getValue().getStatus())) .append("\n");
            System.out.print(gradeList);   
        }while(tools.continueSwitch("Do you want to print another subject's report?"));
    }
    
        private void printCurrentInfo(String subjectID, String name, String credit){
            System.out.println("Subject ID: " + subjectID);
            System.out.println("Subject Name: " + name);
            System.out.println("Subject Credit: " + credit);       
        }  
    
        public int getIndex(){
            while(true){
                String id = tools.inputCheck("Subject ID (Must be Unique)", tools.getRegex("ID"), false).toUpperCase();
                int index = tools.findID(id, this);
                if(index != -1)
                    return index;
                else
                    System.out.println("ID Does Not Exist");
            }     
        }
    
        public String inputID(){
            while(true){
                String id = tools.inputCheck("Subject ID (Must be Unique)", tools.getRegex("ID"), false).toUpperCase();
                if(tools.findID(id, this) == -1)
                    return id;
                else      
                    System.out.println("The ID (" + id + ") is Unavailable");                      
            }
        }
        
        private double inputGrade(String message){
            while(true){
                try{
                    double grade = Double.parseDouble((tools.input("Input " + message + " : ", false)));
                    if(grade >= 0 && grade <= 10)
                        return grade;
                    else 
                        System.out.println("Must be from 0 to 10");
                }catch(NumberFormatException e){
                    System.out.println("Must be a number");
                }           
            }
        }
}    