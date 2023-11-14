import ejb.AcademicGroup;
import ejb.AcademicGroupBean;
import ejb.Discipline;
import ejb.DisciplineBean;
import ejb.Student;

import javax.ejb.EJB;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewGroupServlet")
public class ViewGroupServlet extends HttpServlet {

    @EJB
    private AcademicGroupBean academicGroupBean;
    @EJB
    private DisciplineBean disciplineBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметр из запроса (id группы для просмотра)
        String groupIdString = request.getParameter("id");
        if (groupIdString != null && !groupIdString.isEmpty()) {
            try {
                // Преобразуем id в Long
                Long groupId = Long.parseLong(groupIdString);

                // Получаем информацию об академической группе
                AcademicGroup academicGroup = academicGroupBean.getAcademicGroupById(groupId);

                if (academicGroup != null) {
                    // Передаем информацию о группе в атрибуты запроса
                    request.setAttribute("academicGroup", academicGroup);
                    request.setAttribute("allDisciplines", disciplineBean.getAllDisciplines());

                    // Получаем список студентов для выбранной группы
                    List<Student> students = academicGroupBean.getStudentsByGroupId(groupId);

                    // Передаем список студентов в атрибуты запроса
                    request.setAttribute("students", students);
                    // Получаем список дисциплин, связанных с текущей группой

                    List<Discipline> groupDisciplines = academicGroupBean.getDisciplinesByGroupId(groupId);

                    // Помещаем список дисциплин в атрибуты запроса
                    request.setAttribute("groupDisciplines", groupDisciplines);

                    // Перенаправляем на страницу просмотра группы
                    // Forward to the viewGroup.jsp
                    request.getRequestDispatcher("viewGroup.jsp").forward(request, response);
                } else {
                    // Обработка ситуации, когда группа не найдена
                    response.sendRedirect("academicGroups.jsp");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Обработка ошибки преобразования id
                response.sendRedirect("academicGroups.jsp");
            }
        } else {
            // Обработка ошибки отсутствия id в запросе
            response.sendRedirect("academicGroups.jsp");
        }
    }
}
