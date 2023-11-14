import ejb.AcademicGroupBean;
import javax.ejb.EJB;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteGroupServlet")
public class DeleteGroupServlet extends HttpServlet {
    @EJB
    private AcademicGroupBean academicGroupBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметр из запроса (id группы для удаления)
        String groupIdString = request.getParameter("id");

        if (groupIdString != null && !groupIdString.isEmpty()) {
            try {
                // Преобразуем id в Long
                Long groupId = Long.parseLong(groupIdString);

                // Удаляем академическую группу
                academicGroupBean.deleteAcademicGroup(groupId);

                // Перенаправляем на страницу управления группами
                response.sendRedirect("academicGroups.jsp");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Обработка ошибки преобразования id
            }
        } else {
            // Обработка ошибки отсутствия id в запросе
            response.sendRedirect("academicGroups.jsp");
        }
    }
}
