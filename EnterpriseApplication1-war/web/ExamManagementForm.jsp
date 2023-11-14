<%@page import="ejb.Exam"%>
<%@ page import="java.util.List" %>
<%@ page import="ejb.Discipline" %>
<%@ page import="ejb.ExamBean" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Set Exam Dates</title>
</head>
<body>


<h2>Existing Exams:</h2>
<%
    // Получаем информацию о дисциплинах через EJB
    ExamBean examBean = null;
    try {
        InitialContext context = new InitialContext();
        examBean = (ExamBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/ExamBean!ejb.ExamBean");
    } catch (NamingException e) {
        e.printStackTrace();
    }

    // Получаем список экзаменов через EJB
    List<Exam> existingExams = examBean.getAllExams();

    // Проверяем, что список экзаменов не пустой
    if (existingExams != null && !existingExams.isEmpty()) {
%>
    <table border="1">
        <tr>
            <th>Discipline Name</th>
            <th>Exam Date</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
<%
        // Отображаем список экзаменов в таблице
        for (Exam exam : existingExams) {
%>
            <tr>
                <td><%= exam.getDiscipline().getDisciplineName() %></td>
                <td><%= exam.getExamDate() %></td>
                <td><a href="EditExam.jsp?examId=<%= exam.getExamId() %>">Edit</a></td>
                <td><a href="DeleteExamServlet?examId=<%= exam.getExamId() %>">Delete</a></td>
            </tr>
<%
        }
%>
    </table>
<%
    } else {
%>
    <p>No exams have been set yet.</p>
<%
    }
%>

<h1>Set Exam Dates</h1>
<form action="SetExamDatesServlet" method="post">
    <label>Select Discipline and Set Exam Date:</label><br>
    <select name="disciplineId">
<%
    try {
//        ExamBean examBean = null;
        try {
            InitialContext context = new InitialContext();
            examBean = (ExamBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/ExamBean!ejb.ExamBean");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        // Проверяем, что список дисциплин не пустой
        List<Discipline> allDisciplines = examBean.getAllDisciplines();
        if (allDisciplines != null && !allDisciplines.isEmpty()) {
            // Отображаем список дисциплин внутри формы
            for (Discipline discipline : allDisciplines) { 
%>
                <option value="<%= discipline.getDisciplineID() %>"><%= discipline.getDisciplineName() %></option>
<%
            }
        }
    } catch (Exception e) {
        // Обработка ошибок (вывод на экран или логирование)
        e.printStackTrace();
    }
%>
    </select>
    <input type="date" name="examDate" required>
    <input type="submit" value="Set Exam Date">
</form>


<br>
<a href="index.html">Back to Home</a>

</body>
</html>
