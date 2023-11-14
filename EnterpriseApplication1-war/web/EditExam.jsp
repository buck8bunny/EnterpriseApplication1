<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page import="java.util.List" %>
<%@ page import="ejb.Discipline" %>
<%@ page import="ejb.ExamBean" %>
<%@ page import="ejb.Exam" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Exam</title>
</head>
<body>

<h1>Edit Exam</h1>

<%
    // Получаем информацию о дисциплинах через EJB
    ExamBean examBean = null;
    try {
        InitialContext context = new InitialContext();
        examBean = (ExamBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/ExamBean!ejb.ExamBean");
    } catch (NamingException e) {
        e.printStackTrace();
    }

    // Получаем идентификатор экзамена из параметра запроса
    long examId = Long.parseLong(request.getParameter("examId"));

    // Получаем экзамен по его идентификатору
    Exam exam = examBean.getExamById(examId);

    // Получаем список дисциплин для отображения в выпадающем списке
    List<Discipline> allDisciplines = examBean.getAllDisciplines();
%>

<form action="UpdateExamServlet" method="post">
    <input type="hidden" name="examId" value="<%= exam.getExamId() %>">
    
    <label>Select Discipline:</label><br>
    <select name="disciplineId">
        <% 
            for (Discipline discipline : allDisciplines) { 
                // Выбираем текущую дисциплину
                boolean isSelected = (discipline.getDisciplineID() == exam.getDiscipline().getDisciplineID());
        %>
            <option value="<%= discipline.getDisciplineID() %>" <%= isSelected ? "selected" : "" %>><%= discipline.getDisciplineName() %></option>
        <% } %>
    </select>
    
    <br>
    <label>Edit Exam Date:</label><br>
    <input type="date" name="examDate" value="<%= exam.getExamDate() %>" required>
    
    <br>
    <input type="submit" value="Save Changes">
</form>

<br>
<a href="ExamManagementForm.jsp">Back to Exam List</a>

</body>
</html>
