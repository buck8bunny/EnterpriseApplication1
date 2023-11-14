package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.TypedQuery;

@Stateless
public class DisciplineBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void createDiscipline(Discipline discipline) {
        entityManager.persist(discipline);
    }

    public Discipline getDisciplineById(Long disciplineID) {
        return entityManager.find(Discipline.class, disciplineID);
    }

    public List<Discipline> getAllDisciplines() {
        return entityManager.createQuery("SELECT d FROM Discipline d", Discipline.class).getResultList();
    }

    public void updateDiscipline(Discipline discipline) {
        entityManager.merge(discipline);
    }

    public void deleteDiscipline(Long disciplineID) {
        Discipline discipline = getDisciplineById(disciplineID);
        if (discipline != null) {
            entityManager.remove(discipline);
        }
    }
    public List<Discipline> getDisciplinesByIds(List<String> disciplineIds) {
        TypedQuery<Discipline> query = entityManager.createQuery("SELECT d FROM Discipline d WHERE d.disciplineID IN :disciplineIds", Discipline.class);
        query.setParameter("disciplineIds", disciplineIds);
        return query.getResultList();
    }

}
