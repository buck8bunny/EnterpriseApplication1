<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Enter Test Result</title>
</head>
<body>
    <h1>Enter Test Result</h1>
    <form action="TestResultServlet" method="post">
        <label for="studentId">Student:</label>
        <select id="studentId" name="studentId">
            <option value="1">Student 1</option>
            <option value="2">Student 2</option>
            <!-- Add options for students dynamically or manually -->
        </select>

        <label for="disciplineId">Discipline:</label>
        <select id="disciplineId" name="disciplineId">
            <option value="1">Math</option>
            <option value="2">Science</option>
            <!-- Add options for disciplines dynamically or manually -->
        </select>

        <label for="testDate">Test Date:</label>
        <input type="text" id="testDate" name="testDate" placeholder="yyyy-MM-dd" />

        <label for="testScore">Test Score:</label>
        <input type="text" id="testScore" name="testScore" />

        <input type="submit" value="Enter Test Result" />
    </form>
</body>
</html>
