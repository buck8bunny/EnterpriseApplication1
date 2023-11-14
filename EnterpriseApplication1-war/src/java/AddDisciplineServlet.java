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

@WebServlet("/AddDisciplineServlet")
public class AddDisciplineServlet extends HttpServlet {
    @EJB
    private DisciplineBean disciplineBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String disciplineName = request.getParameter("disciplineName");

        // Create a new Discipline
        Discipline discipline = new Discipline(disciplineName);

        try {
            // Call EJB method to create a discipline
            disciplineBean.createDiscipline(discipline);

            // Обновляем список дисциплин в запросе
            List<Discipline> disciplines = disciplineBean.getAllDisciplines();

            // Добавляем список дисциплин в атрибут запроса
            request.setAttribute("disciplines", disciplines);

            
            request.getRequestDispatcher("DisciplineForm.jsp").forward(request, response);
            // Redirect to a success page
            response.sendRedirect("success.jsp");
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace(); // Зарегистрируйте ошибку
            // Redirect to an error page or display an error message to the user
            response.sendRedirect("error.jsp");
        }
    }
}
