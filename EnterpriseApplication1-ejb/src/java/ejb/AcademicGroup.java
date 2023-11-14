package ejb;

import javax.persistence.*;
import java.util.List;

@Entity
public class AcademicGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupID;

    private String groupName;

    @OneToMany(mappedBy = "academicGroup")
    private List<Student> students;


    public AcademicGroup() {
        // Default constructor
    }

    public AcademicGroup(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupID() {
        return groupID;
    }

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    @ManyToMany
    @JoinTable(
        name = "academic_group_discipline",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines;


    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
    public List<Discipline> getSelectedDisciplines() {
        return disciplines;
    }
}

