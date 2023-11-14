<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="ejb.Student" %>
<%@ page import="ejb.AcademicGroup" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Student</title>
</head>
<body>
    <h1>Edit Student</h1>

    <%-- Получаем студента и списки академических групп --%>
    <% Student student = (Student) request.getAttribute("student"); %>
    <% List<AcademicGroup> academicGroups = (List<AcademicGroup>) request.getAttribute("academicGroups"); %>

    <%-- Проверяем, что студент и списки не равны null --%>
    <% if (student != null && academicGroups != null ) { %>
        <form action="UpdateStudentServlet" method="post">
            <input type="hidden" name="studentId" value="<%= student.getStudentID() %>">
            
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" value="<%= student.getFirstName() %>" required><br>
            
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" value="<%= student.getLastName() %>" required><br>
            
            <label for="academicGroup">Academic Group:</label>
            <select id="academicGroup" name="academicGroup" required>
                <%-- Выводим список академических групп --%>
                <% for (AcademicGroup academicGroup : academicGroups) { %>
                    <option value="<%= academicGroup.getGroupID() %>" <%= (academicGroup.getGroupID().equals(student.getAcademicGroup().getGroupID())) ? "selected" : "" %>><%= academicGroup.getGroupName() %></option>
                <% } %>
            </select><br>
            <input type="submit" value="Update Student">
        </form>
    <% } else { %>
        <p>Error: Unable to retrieve student or lists from request attributes.</p>
    <% } %>

    <br>

    <a href="StudentManagement.jsp">Back to Student Management</a>
</body>
</html>
