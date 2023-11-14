<%@page import="java.util.List"%>
<%@ page import="ejb.Discipline" %>
<%@ page import="ejb.DisciplineBean" %>
<%@ page import="ejb.AcademicGroupBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Academic Group</title>
</head>
<body>

<h1>View Academic Group</h1>
<%
    // Получаем информацию об академической группе из атрибутов запроса
    ejb.AcademicGroup academicGroup = (ejb.AcademicGroup) request.getAttribute("academicGroup");
    List<ejb.Student> students = (List<ejb.Student>) request.getAttribute("students");
   List<ejb.Discipline> groupDisciplines = (List<ejb.Discipline>) request.getAttribute("groupDisciplines");

  List<ejb.Discipline> allDisciplines = (List<ejb.Discipline>) request.getAttribute("allDisciplines");
    if (academicGroup != null && students != null) {
%>
    <script>
    window.onload = function () {
        var groupDisciplines = [<% for (ejb.Discipline discipline : groupDisciplines) { %>'<%= discipline.getDisciplineID() %>', <% } %>];
        var checkboxes = document.getElementsByClassName('disciplineCheckbox');

        for (var i = 0; i < checkboxes.length; i++) {
            if (groupDisciplines.includes(checkboxes[i].value)) {
                checkboxes[i].checked = true;
            }
        }
    };
</script>

    <table border="1">
        <tr>
            <th>Group ID</th>
            <th>Group Name</th>
        </tr>
        <tr>
            <td><%= academicGroup.getGroupID() %></td>
            <td><%= academicGroup.getGroupName() %></td>
        </tr>
    </table>
    
<h2>Disciplines for this Academic Group:</h2>
<% if (groupDisciplines != null && !groupDisciplines.isEmpty()) { %>
    <table border="1">
        <tr>
            <th>Discipline ID</th>
            <th>Discipline Name</th>
        </tr>
        <% for (ejb.Discipline discipline : groupDisciplines) { %>
            <tr>
                <td><%= discipline.getDisciplineID() %></td>
                <td><%= discipline.getDisciplineName() %></td>
            </tr>
        <% } %>
    </table>
<% } else { %>
    <p>No disciplines have been added to this Academic Group.</p>
<% } %>


<!-- Add Academic Group Form -->
<form action="AddDisciplineToGroupServlet" method="post" id="addDisciplineForm">
    <input type="hidden" name="groupId" value="<%= academicGroup.getGroupID() %>">

    <label>Disciplines:</label><br>
    <% for (ejb.Discipline discipline : allDisciplines) { %>
        <input type="checkbox" class="disciplineCheckbox" name="disciplines" value="<%= discipline.getDisciplineID() %>">
        <%= discipline.getDisciplineName() %><br>
    <% } %>
    <input type="submit" value="Add Disciplines to Academic Group">
</form>


    
    <h2>Students in this Academic Group:</h2>
    <table border="1">
        <tr>
            <th>Student ID</th>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
        <% for (ejb.Student student : students) { %>
            <tr>
                <td><%= student.getStudentID() %></td>
                <td><%= student.getFirstName() %></td>
                <td><%= student.getLastName() %></td>
            </tr>
        <% } %>
    </table>

<%
    } else {
%>

    <p>Academic Group not found or no students in this group.</p>

<%
    }
%>

<br>
<a href="academicGroups.jsp">Back to Academic Group Management</a>

</body>
</html>