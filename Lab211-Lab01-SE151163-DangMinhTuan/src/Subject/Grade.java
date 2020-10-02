package Subject;

public final class Grade {
    double labGrade, progressGrade, examGrade, averageGrade;
    String status;
    public Grade(double[] grade){
        setGrade(grade);        
    }
    
    private String update(){
        averageGrade = (labGrade + progressGrade + examGrade)/3;
        return ((averageGrade >= 5) ? "Passed" : "Failed");
    }

    public double getLabGrade() {
        return labGrade;
    }

    public double getProgressGrade() {
        return progressGrade;
    }

    public double getExamGrade() {
        return examGrade;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getStatus() {
        return status;
    }
    
    public void setGrade(double[] grade){
        labGrade = grade[0];
        progressGrade = grade[1];
        examGrade = grade[2];
        status = update();
    }
}
