import ejb.Exam;
import ejb.ExamBean;
import ejb.Discipline;

import javax.ejb.EJB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateExamServlet")
public class UpdateExamServlet extends HttpServlet {

    @EJB
    private ExamBean examBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры из запроса
        long examId = Long.parseLong(request.getParameter("examId"));
        long disciplineId = Long.parseLong(request.getParameter("disciplineId"));
        String newExamDate = request.getParameter("examDate");

        // Получаем существующий экзамен по его идентификатору
        Exam existingExam = examBean.getExamById(examId);

        if (existingExam != null) {
            // Обновляем дату экзамена и выбранную дисциплину
            existingExam.setExamDate(newExamDate);
            
            Discipline discipline = examBean.getDisciplineById(disciplineId);
            existingExam.setDiscipline(discipline);

            // Сохраняем обновленный экзамен в базе данных
            examBean.updateExam(existingExam);

            // Перенаправляем пользователя на страницу существующих экзаменов
            response.sendRedirect("ExamManagementForm.jsp");
        } else {
            // Если экзамен не найден, можно вывести сообщение об ошибке
            response.getWriter().println("Exam not found");
        }
    }
}
