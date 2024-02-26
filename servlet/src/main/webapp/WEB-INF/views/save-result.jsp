<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
<%--    <li>id = <%=((Member)request.getAttribute("member")).getId()%></li>--%>
    <li>id= ${member.id}</li>
<%--    <li>username = <%=((Member)request.getAttribute("member")).getUsername()%></li>--%>
    <li>username = ${member.username}</li>
<%--    <li>age = <%=((Member)request.getAttribute("member")).getAge()%></li>--%>
    <li>age = ${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
