<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="ejb.Student" %>
<%@ page import="ejb.StudentBean" %>
<%@ page import="ejb.AcademicGroup" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>

<%
    // Получаем ссылку на EJB-бин StudentBean
    StudentBean studentBean = null;
    try {
        InitialContext context = new InitialContext();
        studentBean = (StudentBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/StudentBean!ejb.StudentBean");
    } catch (NamingException e) {
        e.printStackTrace();
    }

    // Получаем список студентов
    List<Student> students = studentBean.getAllStudents();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Management</title>
</head>
<body>
    <h1>Student Management</h1>

    <!-- View and Edit Students -->
    <h2>View and Edit Students</h2>
    <table border="1">
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Academic Group</th>
            <th>Actions</th>
        </tr>
        <%
            if (students != null) {
                Iterator<Student> iterator = students.iterator();
                while (iterator.hasNext()) {
                    Student student = iterator.next();
        %>
        <tr>
            <td><%= student.getFirstName() %></td>
            <td><%= student.getLastName() %></td>
            <td><%= student.getAcademicGroup().getGroupName() %></td>


            <td>
                <a href="EditStudentServlet?id=<%= student.getStudentID() %>">Edit</a>
                <a href="DeleteStudentServlet?id=<%= student.getStudentID() %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <!-- Add Student -->
    <h2>Add Student</h2>
    <form action="AddStudentServlet" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required><br>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required><br>
        <label for="academicGroup">Academic Group:</label>
        <select id="academicGroup" name="academicGroup" required>
            <%
                List<AcademicGroup> academicGroups = studentBean.getAllAcademicGroups();
                for (AcademicGroup academicGroup : academicGroups) {
            %>
            <option value="<%= academicGroup.getGroupID() %>"><%= academicGroup.getGroupName() %></option>
            <%
                }
            %>
        </select><br>
        <input type="submit" value="Add Student">
    </form>

    <br>

    <a href="index.html">Back to Home</a>
</body>
</html>
