package ejb;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EXAM")
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAM_ID")
    private Long examId;

    @ManyToOne
    @JoinColumn(name = "DISCIPLINE_ID")
    private Discipline discipline;

    @Column(name = "EXAM_DATE")
    private String examDate;

    // Конструкторы, геттеры и сеттеры

    public Exam() {
        // Конструктор без параметров (может потребоваться для JPA)
    }

    public Exam(Discipline discipline, String examDate) {
        this.discipline = discipline;
        this.examDate = examDate;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }
   
}
