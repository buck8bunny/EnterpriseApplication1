
import ejb.Student;
import ejb.StudentBean;
import ejb.AcademicGroup;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateStudentServlet")
public class UpdateStudentServlet extends HttpServlet {

    @EJB
    private StudentBean studentBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get data from the form
        String studentIdString = request.getParameter("studentId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String academicGroupIDstring = request.getParameter("academicGroup");

        // Validate and process the data
        
            try {
                
                Long studentID = Long.parseLong(studentIdString);
                Long academicGroupID = Long.parseLong(academicGroupIDstring);
                // Retrieve the existing student
                Student student = studentBean.getStudentById(studentID);

                if (student != null) {
                    // Update student details
                    student.setFirstName(firstName);
                    student.setLastName(lastName);

                    // Set academic group
                    AcademicGroup academicGroup = new AcademicGroup();
                    academicGroup.setGroupID(academicGroupID);
                    student.setAcademicGroup(academicGroup);

                    // Update the student using EJB
                    studentBean.updateStudent(student);

                    // Redirect to the view page
                    response.sendRedirect("StudentManagement.jsp");
                }
            } catch (NumberFormatException e) {
                // Обрабатываем ошибку преобразования ID
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } 
    }

