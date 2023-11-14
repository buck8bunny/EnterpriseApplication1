package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;

@Stateless
public class AcademicGroupBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void createAcademicGroup(AcademicGroup academicGroup) {
        entityManager.persist(academicGroup);
    }

    public AcademicGroup getAcademicGroupById(Long academicGroupId) {
        return entityManager.find(AcademicGroup.class, academicGroupId);
    }

    public List<AcademicGroup> getAllAcademicGroups() {
        try {
            return entityManager.createQuery("SELECT ag FROM AcademicGroup ag", AcademicGroup.class).getResultList();
        } catch (Exception e) {
            // Обработка ошибки, например, логирование
            e.printStackTrace();
            return null;
        }
    }

    public void updateAcademicGroup(AcademicGroup academicGroup) {
        entityManager.merge(academicGroup);
    }

    public void deleteAcademicGroup(Long academicGroupId) {
        AcademicGroup academicGroup = getAcademicGroupById(academicGroupId);
        if (academicGroup != null) {
            entityManager.remove(academicGroup);
        }
    }

    public List<Student> getStudentsByGroupId(Long groupId) {
        try {
            Query query = entityManager.createQuery("SELECT s FROM Student s WHERE s.academicGroup.groupID = :groupId", Student.class);
            query.setParameter("groupId", groupId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addAcademicGroup(String groupName, List<Long> disciplineIds) {
        AcademicGroup academicGroup = new AcademicGroup();
        academicGroup.setGroupName(groupName);

        List<Discipline> disciplines = getDisciplinesByIds(disciplineIds);
        academicGroup.setDisciplines(disciplines);

        entityManager.persist(academicGroup);
    }

    public void updateAcademicGroup(Long groupId, String groupName, List<Long> disciplineIds) {
        AcademicGroup academicGroup = getAcademicGroupById(groupId);
        if (academicGroup != null) {
            academicGroup.setGroupName(groupName);

            List<Discipline> disciplines = getDisciplinesByIds(disciplineIds);
            academicGroup.setDisciplines(disciplines);

            entityManager.merge(academicGroup);
        }
    }

    private List<Discipline> getDisciplinesByIds(List<Long> disciplineIds) {
        return entityManager.createQuery("SELECT d FROM Discipline d WHERE d.disciplineID IN :ids", Discipline.class)
                .setParameter("ids", disciplineIds)
                .getResultList();
    }

    public List<Discipline> getDisciplinesByGroupId(Long groupId) {
        Query query = entityManager.createNativeQuery(
                "SELECT d.* FROM DISCIPLINE d "
                + "JOIN ACADEMIC_GROUP_DISCIPLINE agd ON d.DISCIPLINEID = agd.DISCIPLINE_ID "
                + "WHERE agd.GROUP_ID = ?",
                Discipline.class
        );
        query.setParameter(1, groupId);
        return query.getResultList();
    }

}
