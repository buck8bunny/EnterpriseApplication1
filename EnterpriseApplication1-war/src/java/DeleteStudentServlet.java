import ejb.StudentBean;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {

    @EJB
    private StudentBean studentBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем ID студента из запроса
        String studentIdString = request.getParameter("id");

        // Проверяем, что ID предоставлен и не является пустым
        if (studentIdString != null && !studentIdString.isEmpty()) {
            try {
                // Преобразуем ID в Long
                Long studentId = Long.parseLong(studentIdString);

                // Удаляем студента по ID с использованием EJB
                studentBean.deleteStudent(studentId);

                // Перенаправляем на страницу успешного удаления (или другую)
                response.sendRedirect("StudentManagement.jsp");
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
