package Student;
import Subject.Subject;
import java.util.ArrayList;

public final class Student implements Comparable<Student>{
    String studentID, firstName, lastName, gender, DOB, email, phoneNumber;
    ArrayList<Subject> studentSubjectList = new ArrayList<>();
    
    public Student(String studentID, String[] info){
        this.studentID = studentID;
        this.setNewInfo(info);
    }

    public String getStudentID() {
        return studentID;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDOB() {
        return DOB;
    }

    public ArrayList<Subject> getStudentSubjectList() {
        return studentSubjectList;
    }

    public int getNumberSubject() {
        return studentSubjectList.size();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void addSubject(Subject subject){
        if(!studentSubjectList.contains(subject))
            studentSubjectList.add(subject);
    }
    
    public String [] getAllCurrentInfo(){
        String[] curInfo = new String[6];
        curInfo[0] = firstName;
        curInfo[1] = lastName;
        curInfo[2] = gender;
        curInfo[3] = DOB;
        curInfo[4] = email;
        curInfo[5] = phoneNumber;
        return curInfo;
    }
    
    public void setNewInfo(String[] newInfo){
        firstName = newInfo[0];
        lastName = newInfo[1];
        gender = newInfo[2];
        DOB = newInfo[3];
        email = newInfo[4];
        phoneNumber = newInfo[5];
    }
    
    public void updateStudent(String[] newInfo){
        String[] info = getAllCurrentInfo();
        for(int i = 0; i < info.length; i++){
            if(!newInfo[i].isEmpty())
                info[i] = newInfo[i];
        }
        setNewInfo(info);
    }
    
    public void printStudentInfo(){
        System.out.println("Student Name: " + getName());
        System.out.println("Student Gender: " + gender);
        System.out.println("Student Date of Birth: " + DOB);
        System.out.println("Student Email: " + email);
        System.out.println("Student Phone Number: " + phoneNumber);       
    }
    
    public String getName(){
        return firstName + " " + lastName;
    }
    
    @Override
    public int compareTo(Student student){
        return this.getName().compareTo(student.getName());
    }
    
    @Override
    public String toString(){
        return studentID;
    }
}
