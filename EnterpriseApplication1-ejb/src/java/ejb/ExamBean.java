package ejb;

import java.util.Collections;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;

@Stateless
public class ExamBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void createExam(Exam exam) {
        entityManager.persist(exam);
    }

    public List<Exam> getAllExams() {
        // код для получения всех экзаменов из базы данных
        return entityManager.createQuery("SELECT e FROM Exam e", Exam.class).getResultList();
    }

    public void setExamDate(Long disciplineId, String examDate) {
        // код для установки даты экзамена для определенной дисциплины
        Discipline discipline = entityManager.find(Discipline.class, disciplineId);
        Exam exam = new Exam();
        exam.setDiscipline(discipline);
        exam.setExamDate(examDate);
        entityManager.persist(exam);
    }

    public List<Discipline> getAllDisciplines() {
        try {
            if (entityManager != null) {
                Query query = entityManager.createQuery("SELECT d FROM Discipline d", Discipline.class);
                return query.getResultList();
            } else {
                System.err.println("EntityManager is null");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Exam getExamById(long examId) {
        // Используйте метод find класса EntityManager для получения экзамена по его идентификатору
        return entityManager.find(Exam.class, examId);

    }

    public void updateExam(Exam exam) {
        Exam mergedExam = entityManager.merge(exam);
        if (mergedExam == null) {
            System.out.println("Merge failed: Exam not found");
        }
    }

    public Discipline getDisciplineById(long disciplineId) {
        return entityManager.find(Discipline.class, disciplineId);
    }

    public void deleteExam(Exam exam) {
        entityManager.remove(entityManager.merge(exam));
    }

  
    public void createGrade(Grade grade) {
        entityManager.persist(grade);
    }

//     public List<Exam> getExamsByGroupId(Long groupId) {
//        try {
//            Query query = entityManager.createNativeQuery("SELECT e FROM Exam e WHERE e.disciplineID IN (SELECT agd.DISCIPLINE_ID FROM ACADEMIC_GROUP_DISCIPLINE agd WHERE agd.GROUP_ID = :groupId)", Exam.class);
//            query.setParameter("groupId", groupId);
//            return query.getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public List<Exam> getExamsByGroupId(Long groupId) {
    Query query = entityManager.createNativeQuery(
        "SELECT e.* FROM EXAM e " +
        "JOIN DISCIPLINE d ON e.DISCIPLINE_ID = d.DISCIPLINEID " +
        "JOIN ACADEMIC_GROUP_DISCIPLINE agd ON d.DISCIPLINEID = agd.DISCIPLINE_ID " +
        "WHERE agd.GROUP_ID = ?",
        Exam.class
    );
    query.setParameter(1, groupId);
    return query.getResultList();
}

}
