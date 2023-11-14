import ejb.Exam;
import ejb.ExamBean;

import javax.ejb.EJB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteExamServlet")
public class DeleteExamServlet extends HttpServlet {

    @EJB
    private ExamBean examBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметр из запроса
        long examId = Long.parseLong(request.getParameter("examId"));

        // Получаем экзамен по его идентификатору
        Exam examToDelete = examBean.getExamById(examId);

        if (examToDelete != null) {
            // Удаляем экзамен из базы данных
            examBean.deleteExam(examToDelete);

            // Перенаправляем пользователя на страницу существующих экзаменов
            response.sendRedirect("ExamManagementForm.jsp");
        } else {
            // Если экзамен не найден, можно вывести сообщение об ошибке
            response.getWriter().println("Exam not found");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметр из запроса
        long examId = Long.parseLong(request.getParameter("examId"));

        // Получаем экзамен по его идентификатору
        Exam examToDelete = examBean.getExamById(examId);

        if (examToDelete != null) {
            // Удаляем экзамен из базы данных
            examBean.deleteExam(examToDelete);

            // Перенаправляем пользователя на страницу существующих экзаменов
            response.sendRedirect("ExamManagementForm.jsp");
        } else {
            // Если экзамен не найден, можно вывести сообщение об ошибке
            response.getWriter().println("Exam not found");
        }
    }
}
