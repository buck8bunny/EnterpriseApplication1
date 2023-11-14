package ejb;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentID;

    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "academicGroupID")
    private AcademicGroup academicGroup;

    @ManyToMany
    @JoinTable(
            name = "student_discipline",
            joinColumns = @JoinColumn(name = "studentID"),
            inverseJoinColumns = @JoinColumn(name = "disciplineID")
    )
    private List<Discipline> disciplines;

    // Constructors, getters, and setters

    public Student() {
        // Default constructor
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName, AcademicGroup academicGroup, List<Discipline> disciplines) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.academicGroup = academicGroup;
        this.disciplines = disciplines;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AcademicGroup getAcademicGroup() {
        return academicGroup;
    }

    public void setAcademicGroup(AcademicGroup academicGroup) {
        this.academicGroup = academicGroup;
    }
    
    public String getStudentName() {
        return firstName + " " + lastName;
    }
 
   }
