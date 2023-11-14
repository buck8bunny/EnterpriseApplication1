package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.TypedQuery;

@Stateless
public class GradeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Grade> getGradesByStudentId(Long studentId) {
        try {
            TypedQuery<Grade> query = entityManager.createQuery(
                "SELECT g FROM Grade g WHERE g.student.studentID = :studentId", 
                Grade.class
            );
            query.setParameter("studentId", studentId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public void saveGrade(Student student, Exam exam, String gradeValue) {
    // Пытаемся найти существующую оценку для студента и экзамена
    Grade existingGrade = entityManager.createQuery(
            "SELECT g FROM Grade g WHERE g.student = :student AND g.exam = :exam", Grade.class)
            .setParameter("student", student)
            .setParameter("exam", exam)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);

    if (existingGrade != null) {
        // Если оценка уже существует, обновляем ее значение
        existingGrade.setGrade(gradeValue);
        entityManager.merge(existingGrade);
    } else {
        // Если оценки не существует, создаем новую
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setExam(exam);
        grade.setGrade(gradeValue);
        entityManager.persist(grade);
    }
}

    private String getGradeForExam(List<Grade> grades, Exam exam) {
        for (Grade grade : grades) {
            if (grade.getExam().getExamId().equals(exam.getExamId())) {
                return grade.getGrade();
            }
        }
        return ""; // Если оценка не найдена, возвращаем пустую строку
    }



}

