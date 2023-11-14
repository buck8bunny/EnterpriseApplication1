package ejb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Grade {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public AcademicGroup getAcademicGroup() {
        return academicGroup;
    }

    public void setAcademicGroup(AcademicGroup academicGroup) {
        this.academicGroup = academicGroup;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Column(name = "GRADE")
    private String grade;
    
    @ManyToOne
    @JoinColumn(name = "STUDENT_STUDENTID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "ACADEMICGROUP_GROUPID")
    private AcademicGroup academicGroup;

    @ManyToOne
    @JoinColumn(name = "DISCIPLINE_DISCIPLINEID")
    private Discipline discipline;

    @ManyToOne
    @JoinColumn(name = "EXAM_EXAM_ID")
    private Exam exam;
    

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Exam getExam() {
        return exam;
    }
    
   
}
