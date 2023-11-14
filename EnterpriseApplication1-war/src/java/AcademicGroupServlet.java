import ejb.AcademicGroup;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.ejb.EJB;
import ejb.AcademicGroupBean;

@WebServlet("/AcademicGroupServlet")
public class AcademicGroupServlet extends HttpServlet {
    @EJB
    private AcademicGroupBean academicGroupBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String groupName = request.getParameter("groupName");

        // Create a new AcademicGroup
        AcademicGroup academicGroup = new AcademicGroup(groupName);

        // Call EJB method to create an academic group
        academicGroupBean.createAcademicGroup(academicGroup);

        // Redirect to a success page or display a message
        response.sendRedirect("academicGroups.jsp");
    }
}
