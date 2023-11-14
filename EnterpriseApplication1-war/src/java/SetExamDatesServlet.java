
import ejb.DisciplineBean;
import ejb.ExamBean;
import ejb.Exam;
import ejb.Discipline;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SetExamDatesServlet")
public class SetExamDatesServlet extends HttpServlet {

    @EJB
    private DisciplineBean disciplineBean;

    @EJB
    private ExamBean examBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем данные из формы
        String disciplineIdString = request.getParameter("disciplineId");
        String examDate = request.getParameter("examDate");

        try {
            // Преобразуем ID дисциплины в Long
            Long disciplineId = Long.parseLong(disciplineIdString);

            // Получаем дисциплину по ID
            Discipline discipline = disciplineBean.getDisciplineById(disciplineId);

            if (discipline != null) {
                // Создаем экзамен
                Exam exam = new Exam(discipline, examDate);

                // Сохраняем экзамен в базе данных
                examBean.createExam(exam);

                // Перенаправляем на страницу успешного выполнения
                response.sendRedirect("ExamManagementForm.jsp");
            } else {
                // В случае, если дисциплина не найдена
                response.sendRedirect("error.jsp");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Обработка ошибки преобразования ID
            response.sendRedirect("error.jsp");
        }
    }
}
