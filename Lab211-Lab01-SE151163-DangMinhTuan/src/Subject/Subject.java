package Subject;
import Student.Student;
import java.util.TreeMap;

public class Subject implements Comparable<Subject>{
    String subjectID, name;
    int credit;
    TreeMap<Student, Grade> studentGrade;
    
    public Subject(String subjectID, String name, int credit){
        this.studentGrade = new TreeMap<>();
        this.name = name;
        this.subjectID = subjectID;
        this.credit = credit;
    }

    public String getSubjectID() {
        return subjectID;
    }
    
    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
    
    public void addGrade(Student student, double[] grade){
        this.studentGrade.put(student, new Grade(grade));
    }

    public TreeMap<Student, Grade> getGradeMap() {
        return studentGrade;
    }
    
    public Grade getGrade(Student student){
        return studentGrade.get(student);
    }
    public boolean isContain (Student student){
        return studentGrade.containsKey(student);
    }
    
    public int numberStudent(){
        return studentGrade.size();
    }
    
    public String [] getAllCurrentInfo(){
        String[] curInfo = new String[2];
        curInfo[0] = name;
        curInfo[1] = String.valueOf(credit);
        return curInfo;
    }
    
    public void setNewInfo(String[] newInfo){
        name = newInfo[0];
        credit = Integer.parseInt(newInfo[1]);
    }
    
    public void updateSubject(String[] newInfo){
        String[] info = getAllCurrentInfo();
        for(int i = 0; i < info.length; i++){
            if(!newInfo[i].isEmpty())
                info[i] = newInfo[i];
        }
        setNewInfo(info);
    }
    
    public void printSubjectInfo(){
        System.out.println("Subject Name: " + name);
        System.out.println("Subject Credit: " + String.valueOf(credit));
    }
    
    @Override
    public int compareTo(Subject subject){
        return this.getName().compareTo(subject.getName());
    }
    
    @Override
    public String toString(){
        return subjectID;
    }
}
