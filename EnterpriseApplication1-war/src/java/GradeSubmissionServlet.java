import ejb.ExamBean;
import ejb.StudentBean;
import ejb.Grade;
import ejb.Student;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/GradeSubmissionServlet")
public class GradeSubmissionServlet extends HttpServlet {

    @EJB
    private ExamBean examBean;

    @EJB
    private StudentBean studentBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры из запроса
        long studentId = Long.parseLong(request.getParameter("studentId"));

        // Получаем текущего студента
        Student student = studentBean.getStudentById(studentId);

        // Получаем оценки из параметров запроса
        Map<String, String[]> gradeParams = request.getParameterMap();

        for (String examIdParam : gradeParams.keySet()) {
            long examId = Long.parseLong(examIdParam);
            String gradeValue = gradeParams.get(examIdParam)[0];

            // Создаем экземпляр Grade и сохраняем в базу данных
            Grade grade = new Grade();
            grade.setStudent(student);
            grade.setExam(examBean.getExamById(examId));
            grade.setGrade(gradeValue);

            examBean.createGrade(grade);
        }

        // Перенаправляем пользователя на страницу существующих экзаменов
        response.sendRedirect("ExamResultForm.jsp");
    }
}
