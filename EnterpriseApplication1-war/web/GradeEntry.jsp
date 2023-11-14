<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.List"%>
<%@page import="ejb.ExamBean"%>
<%@page import="ejb.Exam"%>
<%@page import="ejb.Student"%>
<%@page import="ejb.AcademicGroup"%>
<%@page import="ejb.AcademicGroupBean"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Grade Entry</title>
</head>
<body>

<%
    try {
        // Получаем ссылку на EJB-бин AcademicGroupBean
        AcademicGroupBean academicGroupBean = null;
        try {
            InitialContext context = new InitialContext();
            academicGroupBean = (AcademicGroupBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/AcademicGroupBean!ejb.AcademicGroupBean");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        // Получаем список академических групп
        List<AcademicGroup> allGroups = academicGroupBean.getAllAcademicGroups();

        // Для каждой группы выводим список студентов и форму для ввода оценок
        for (AcademicGroup group : allGroups) {
%>

    <h2>Students in Group <%= group.getGroupName()%></h2>
    <table border="1">
        <tr>
            <th>Student ID</th>
            <th>Student Name</th>
            <th>Actions</th>
        </tr>
        <%
            // Получаем список студентов через AcademicGroupBean
            List<Student> studentsInGroup = academicGroupBean.getStudentsByGroupId(group.getGroupID());
            for (Student student : studentsInGroup) {
        %>
        <tr>
            <td><%= student.getStudentID()%></td>
            <td><%= student.getStudentName()%></td>
            <td><a href="ViewGrades.jsp?studentId=<%= student.getStudentID() %>">View Grades</a></td>
        </tr>
        <%
            }
        %>
    </table>

    <%
        // Получаем ссылку на EJB-бин, который работает с экзаменами
        ExamBean examBean = null;
        try {
            InitialContext context = new InitialContext();
            examBean = (ExamBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/ExamBean!ejb.ExamBean");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        // Получаем список экзаменов для текущей группы
        List<Exam> examsForGroup = examBean.getExamsByGroupId(group.getGroupID());

        if (!examsForGroup.isEmpty()) {
    %>
        <h3>Exams for Group <%= group.getGroupName()%></h3>
        <table border="1">
            <tr>
                <th>Exam ID</th>
                <th>Discipline</th>
                <th>Exam Date</th>
            </tr>
            <%
                for (Exam exam : examsForGroup) {
            %>
            <tr>
                <td><%= exam.getExamId()%></td>
                <td><%= exam.getDiscipline().getDisciplineName()%></td>
                <td><%= exam.getExamDate()%></td>
            </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p>No exams available for this group.</p>
    <%
        }
    }
    } catch (Exception e) {
        // Обработка ошибок (вывод на экран или логирование)
        e.printStackTrace();
    }
    %>

    <br>
    <a href="index.html">Back to Home</a>

</body>
</html>
