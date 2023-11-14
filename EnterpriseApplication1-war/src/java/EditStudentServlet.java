
import ejb.Student;
import ejb.StudentBean;
import ejb.AcademicGroup;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
import javax.servlet.ServletException;

@WebServlet("/EditStudentServlet")
public class EditStudentServlet extends HttpServlet {

    @EJB
    private StudentBean studentBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Получаем ID студента из запроса
        String studentIdString = request.getParameter("id");

        // Проверяем, что ID предоставлен и не является пустым
        if (studentIdString != null && !studentIdString.isEmpty()) {
            try {
                // Преобразуем ID в Long

                Long studentId = Long.parseLong(studentIdString);

                // Получаем студента по ID с использованием EJB
                Student student = studentBean.getStudentById(studentId);

                // Получаем список всех академических групп и дисциплин для формы редактирования
                List<AcademicGroup> academicGroups = studentBean.getAllAcademicGroups();

                // Устанавливаем атрибуты в запрос для передачи данных в JSP
                request.setAttribute("student", student);
                request.setAttribute("academicGroups", academicGroups);

                // Перенаправляем на страницу редактирования
                request.getRequestDispatcher("editStudent.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                // Обрабатываем ошибку преобразования ID
                e.printStackTrace(); // Логгирование или другая обработка ошибки
                response.sendRedirect("error.jsp");
            }
        } else {
            // Обрабатываем ситуацию, когда ID не предоставлен
            response.sendRedirect("error.jsp");
        }
    }
}
