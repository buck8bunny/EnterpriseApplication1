
import ejb.Student;
import ejb.StudentBean;
import ejb.AcademicGroup;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AddStudentServlet")
public class AddStudentServlet extends HttpServlet {

    @EJB
    private StudentBean studentBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем данные из формы
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Long academicGroupID = Long.parseLong(request.getParameter("academicGroup"));
        
        // Проверяем и обрабатываем данные
        try {
            // Создаем нового студента
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);

            // Устанавливаем академическую группу
            AcademicGroup academicGroup = new AcademicGroup();
            academicGroup.setGroupID(academicGroupID);
            student.setAcademicGroup(academicGroup);

            // Добавляем студента с использованием EJB
            studentBean.createStudent(student);

            // Перенаправляем на страницу просмотра
            response.sendRedirect("StudentManagement.jsp");
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace(); 
            // Обрабатываем неверный ввод
            response.sendRedirect("error.jsp");
        }
    }
}
