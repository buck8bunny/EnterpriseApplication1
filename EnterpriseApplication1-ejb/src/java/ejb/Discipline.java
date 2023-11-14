package ejb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disciplineID;
    
    private String disciplineName;

    // Constructors, getters, and setters

    public Discipline() {
        // Default constructor
    }

    public Discipline(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public Long getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Long disciplineID) {
        this.disciplineID = disciplineID;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

}

