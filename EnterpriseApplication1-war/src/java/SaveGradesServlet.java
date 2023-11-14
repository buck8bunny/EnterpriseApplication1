import ejb.GradeBean;
import ejb.ExamBean;
import ejb.Exam;
import ejb.Student;
import ejb.StudentBean;

import java.io.IOException;
import java.util.Map;
import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SaveGradesServlet")
public class SaveGradesServlet extends HttpServlet {

    @EJB
    private GradeBean gradeBean;

    @EJB
    private ExamBean examBean;

    @EJB
    private StudentBean studentBean;
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Получаем параметры из запроса
            Long studentId = Long.parseLong(request.getParameter("studentId"));
            Map<String, String[]> parameterMap = request.getParameterMap();
            // Получаем ссылки на EJB-бины

            // Получаем студента по идентификатору
            Student student = studentBean.getStudentById(studentId);

            if (student == null) {
                throw new Exception("Student not found for ID: " + studentId);
            }

            // Обработка оценок для каждого экзамена
            for (String examId : parameterMap.keySet()) {
                if (examId.startsWith("grades[")) {
                    Long examIdLong = Long.parseLong(examId.substring(7, examId.length() - 1));
                    String gradeValue = parameterMap.get(examId)[0];

                    // Получаем экзамен по его идентификатору
                    Exam exam = examBean.getExamById(examIdLong);

                    if (exam != null) {
                        // Сохраняем оценку
                        gradeBean.saveGrade(student, exam, gradeValue);
                    }
                }
            }
            
            // Перенаправляем пользователя на страницу с результатами
            response.sendRedirect("GradeEntry.jsp");
        } catch (Exception e) {
            // Обработка ошибок (вывод на экран или логирование)
            e.printStackTrace();
            response.sendRedirect("Error.jsp");
        }
    }
}
