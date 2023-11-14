import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.ejb.EJB;
import ejb.DisciplineBean;

@WebServlet("/DeleteDisciplineServlet")
public class DeleteDisciplineServlet extends HttpServlet {
    @EJB
    private DisciplineBean disciplineBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the discipline's ID from the request
        long disciplineId = Long.parseLong(request.getParameter("id"));

        // Call EJB method to delete the discipline
        disciplineBean.deleteDiscipline(disciplineId);

        // Redirect to a success page or display a message
        response.sendRedirect("DisciplineForm.jsp");
    }
}
