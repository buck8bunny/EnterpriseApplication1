package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class StudentBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void createStudent(Student student) {
        entityManager.persist(student);
    }

    public Student getStudentById(Long studentID) {
        return entityManager.find(Student.class, studentID);
    }

    public List<Student> getAllStudents() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public void updateStudent(Student student) {
        entityManager.merge(student);
    }

    public void deleteStudent(Long studentID) {
        Student student = getStudentById(studentID);
        if (student != null) {
            entityManager.remove(student);
        }
    }

    public List<AcademicGroup> getAllAcademicGroups() {
        try {
            return entityManager.createQuery("SELECT ag FROM AcademicGroup ag", AcademicGroup.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AcademicGroup getAcademicGroupById(Long academicGroupId) {
        try {
            return entityManager.find(AcademicGroup.class, academicGroupId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
   
    

}
