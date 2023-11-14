<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="ejb.Discipline" %>
<%@ page import="ejb.DisciplineBean" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%
    // Получаем ссылку на EJB-бин DisciplineBean
    DisciplineBean disciplineBean = null;
    try {
        InitialContext context = new InitialContext();
        disciplineBean = (DisciplineBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/DisciplineBean!ejb.DisciplineBean");
    } catch (NamingException e) {
        e.printStackTrace();
    }

    // Получаем список дисциплин
    List<Discipline> disciplines = disciplineBean.getAllDisciplines();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Discipline Management</title>
</head>
<body>
    <h1>Discipline Management</h1>

    <!-- View and Edit Disciplines -->
    <h2>View and Edit Disciplines</h2>
    <table border="1">
        <tr>
            <th>Discipline Name</th>
            <th>Actions</th>
        </tr>
        <%
            if (disciplines != null) {
                for (Discipline discipline : disciplines) {
        %>
        <tr>
            <td><%= discipline.getDisciplineName() %></td>
            <td>
                <a href="DeleteDisciplineServlet?id=<%= discipline.getDisciplineID() %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <!-- Add Discipline -->
    <h2>Add Discipline</h2>
    <form action="AddDisciplineServlet" method="post">
        <label for="disciplineName">Discipline Name:</label>
        <input type="text" id="disciplineName" name="disciplineName" required>
        <input type="submit" value="Add Discipline">
    </form>

    <br>

    <a href="index.html">Back to Home</a>
</body>
</html>
