<%@page import="ejb.Grade"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="ejb.StudentBean"%>
<%@page import="ejb.GradeBean"%>
<%@page import="ejb.ExamBean"%>
<%@page import="ejb.Student"%>
<%@page import="ejb.Exam"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Grades</title>
</head>
<body>

<%
    try {
        // Получаем ссылку на EJB-бин ExamBean
        ExamBean examBean = null;
        try {
            InitialContext context = new InitialContext();
            examBean = (ExamBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/ExamBean!ejb.ExamBean");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        StudentBean studentBean = null;
        try {
            InitialContext context = new InitialContext();
            studentBean = (StudentBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/StudentBean!ejb.StudentBean");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        GradeBean gradeBean = null;
        try {
            InitialContext context = new InitialContext();
            gradeBean = (GradeBean) context.lookup("java:global/EnterpriseApplication1/EnterpriseApplication1-ejb/GradeBean!ejb.GradeBean");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        // Получаем параметры из запроса
        Long studentId = Long.parseLong(request.getParameter("studentId"));

        // Получаем студента по его идентификатору
        Student student = studentBean.getStudentById(studentId);

        if (student == null) {
            // Обработка ситуации, если студент не найден
            throw new Exception("Student not found for ID: " + studentId);
        }

        // Получаем список экзаменов для группы студента
        List<Exam> examsForGroup = examBean.getExamsByGroupId(student.getAcademicGroup().getGroupID());

        // Получаем список оценок для студента и каждого экзамена
        List<Grade> gradesForStudent = gradeBean.getGradesByStudentId(student.getStudentID());

        // Отображаем таблицу с доступными экзаменами и форму для ввода оценок
%>
    <h2>View Grades for <%= student.getStudentName() %></h2>
    <form action="SaveGradesServlet" method="post">
        <input type="hidden" name="studentId" value="<%= student.getStudentID() %>">
        <table border="1">
            <tr>
                <th>Exam ID</th>
                <th>Discipline</th>
                <th>Exam Date</th>
                <th>Grade</th>
            </tr>
            <%
                for (Exam exam : examsForGroup) {
            %>
            <tr>
                <td><%= exam.getExamId() %></td>
                <td><%= exam.getDiscipline().getDisciplineName() %></td>
                <td><%= exam.getExamDate() %></td>
                <td>
                    <input type="text" name="grades[<%= exam.getExamId() %>]" value="<%= getGradeForExam(gradesForStudent, exam) %>">
                </td>
            </tr>
            <%
                }
            %>
        </table>

        <br>
        <input type="submit" value="Save Grades">
    </form>
<%
    } catch (Exception e) {
        // Обработка ошибок (вывод на экран или логирование)
        e.printStackTrace();
    }
%>

<br>
<a href="GradeEntry.jsp">Back to Grades Managment</a>

</body>
</html>

<%! // Объявляем метод getGradeForExam
    private String getGradeForExam(List<Grade> grades, Exam exam) {
        for (Grade grade : grades) {
            if (grade.getExam().getExamId().equals(exam.getExamId())) {
                return grade.getGrade();
            }
        }
        return ""; // Если оценка не найдена, возвращаем пустую строку
    }
%>
