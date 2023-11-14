
import ejb.Discipline;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.ejb.EJB;
import ejb.DisciplineBean;
import java.util.List;

@WebServlet("/DisciplineServlet")
public class DisciplineServlet extends HttpServlet {

    @EJB
    private DisciplineBean disciplineBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("view")) {
                // Retrieve and display a list of disciplines
                List<Discipline> disciplines = disciplineBean.getAllDisciplines();
                request.setAttribute("disciplines", disciplines);
                request.getRequestDispatcher("viewDisciplines.jsp").forward(request, response);
            }
        } else {
            //  other GET requests
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("add")) {
                // Retrieve form data
                String disciplineName = request.getParameter("disciplineName");

                // Create a new Discipline
                Discipline discipline = new Discipline(disciplineName);

                // Call EJB method to create a discipline
                disciplineBean.createDiscipline(discipline);

                // Redirect to a success page or display a message
                response.sendRedirect("success.jsp");
            } else if (action.equals("delete")) {
                // Retrieve the ID of the discipline to delete
                long disciplineId = Long.parseLong(request.getParameter("disciplineId"));

                // Call EJB method to delete the discipline
                disciplineBean.deleteDiscipline(disciplineId);

                // Redirect to a success page or display a message
                response.sendRedirect("success.jsp");
            }
        } else {
            // 
        }
    }
}
