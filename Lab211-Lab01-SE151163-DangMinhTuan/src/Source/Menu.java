package Source;
import Student.StudentList;
import Subject.SubjectList;

class Menu {
    static SharedLibrary tools = new SharedLibrary();
    StudentList student;
    SubjectList subject;

    public Menu(){
        student = new StudentList();
        subject = new SubjectList();   
        SharedLibrary.initializeRegex();
    }
    
    public void choice(){
        int choice;
        do{
            System.out.println("\n-----Menu-----");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student Information");
            System.out.println("3. Add Subject");
            System.out.println("4. Update Subject Information");
            System.out.println("5. Update Grade");
            System.out.println("6. Print Student Grade Report");
            System.out.println("7. Print Subject Grade Report");
            System.out.println("8. Exit");
            choice = tools.inputInteger("Choice");

            if(choice < 1 || choice > 7)
                choice = 8;
            else
                routing(choice);            
        }while(choice != 8);
    }
    
    private void routing(int choice){
        switch(choice){
            case 1: student.add(); break;
            case 2: student.updateMenu(); break;
            case 3: subject.add(); break;
            case 4: subject.updateMenu(); break;
            case 5: subject.updateGrade(student); break;
            case 6: student.printStudentGradeReport(); break;
            case 7: subject.printSubjectReport(); break;
        }
    }
}
