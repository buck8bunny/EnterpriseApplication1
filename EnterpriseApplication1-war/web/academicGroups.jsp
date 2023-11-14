<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="ejb.AcademicGroup" %>
<%@ page import="ejb.AcademicGroupBean" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%
    // Получаем ссылку на EJB-бин AcademicGroupBean
    AcademicGroupBean academicGroupBean = null;
    try {
        InitialContext context = new InitialContext();
        academicGroupBean = (AcademicGroupBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/AcademicGroupBean!ejb.AcademicGroupBean");
    } catch (NamingException e) {
        e.printStackTrace();
    }

    // Получаем список академических групп
    List<AcademicGroup> academicGroups = academicGroupBean.getAllAcademicGroups();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Academic Group Management</title>
</head>
<body>
    <h1>Academic Group Management</h1>

    <!-- View and Edit Academic Groups -->
    <h2>View and Edit Academic Groups</h2>
    <table border="1">
        <tr>
            <th>Group Name</th>
            <th>Actions</th>
        </tr>
        <%
            if (academicGroups != null) {
                for (AcademicGroup academicGroup : academicGroups) {
        %>
        <tr>
            <td><%= academicGroup.getGroupName() %></td>
            <td>
                <a href="ViewGroupServlet?id=<%= academicGroup.getGroupID() %>">View</a>
                <a href="DeleteGroupServlet?id=<%= academicGroup.getGroupID() %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <!-- Add Academic Group -->
    <h2>Add Academic Group</h2>
    <form action="AcademicGroupServlet" method="post">
        <label for="groupName">Group Name:</label>
        <input type="text" id="groupName" name="groupName" required>
        <input type="submit" value="Add Academic Group">
    </form>

    <br>

    <a href="index.html">Back to Home</a>
</body>
</html>
