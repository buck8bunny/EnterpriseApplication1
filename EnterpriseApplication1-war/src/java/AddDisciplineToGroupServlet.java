
import ejb.AcademicGroup;
import ejb.Discipline;
import ejb.AcademicGroupBean;
import ejb.DisciplineBean;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/AddDisciplineToGroupServlet")
public class AddDisciplineToGroupServlet extends HttpServlet {

    @EJB
    private AcademicGroupBean academicGroupBean;

    @EJB
    private DisciplineBean disciplineBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем данные из формы
        String groupIdString = request.getParameter("groupId");
        String[] disciplineIds = request.getParameterValues("disciplines");

        // Проверяем, что disciplineIds не равен null
        if (disciplineIds != null && disciplineIds.length > 0) {
            try {
                // Преобразуем ID группы в Long
                Long groupId = Long.parseLong(groupIdString);

                // Получаем академическую группу по ID
                AcademicGroup academicGroup = academicGroupBean.getAcademicGroupById(groupId);

                // Получаем список выбранных дисциплин
                List<String> disciplineIdList = Arrays.asList(disciplineIds);

                // Проверяем, что disciplineIdList не равен null и не пуст
                if (disciplineIdList != null && !disciplineIdList.isEmpty()) {
                    // Получаем список дисциплин по их ID
                    List<Discipline> selectedDisciplines = disciplineBean.getDisciplinesByIds(disciplineIdList);

                    // Проверяем, что selectedDisciplines не равен null и не пуст
                    if (selectedDisciplines != null && !selectedDisciplines.isEmpty()) {

                        // Привязываем дисциплины к академической группе
                        academicGroup.setDisciplines(selectedDisciplines);
                        academicGroupBean.updateAcademicGroup(academicGroup);

                        // После обновления академической группы, получаем ее актуальное состояние
                        academicGroup = academicGroupBean.getAcademicGroupById(groupId);

                        // Получаем список дисциплин из академической группы
                        List<Discipline> updatedDisciplines = academicGroup.getDisciplines();

                        // Перенаправляем на страницу просмотра группы
                        response.sendRedirect("ViewGroupServlet?id=" + groupId);
                    } else {
                        // Обработка случая, когда список дисциплин пустой или null
                        response.sendRedirect("someErrorPage4.jsp");
                    }
                } else {
                    // Обработка случая, когда disciplineIdList пустой или null
                    response.sendRedirect("someErrorPage3.jsp");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Обработка ошибки преобразования ID
                response.sendRedirect("academicGroups.jsp");
            }
        } else {
            // Обработка случая, когда disciplineIds равен null
            response.sendRedirect("someErrorPage2.jsp");
        }

    }
}
