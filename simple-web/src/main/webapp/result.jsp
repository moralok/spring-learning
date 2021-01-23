<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: moralok
  Date: 2021/1/23
  Time: 6:43 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Beer Recommendations JSP</title>
</head>
<body>
<h1>Beer Recommendations JSP</h1>
<br/>
<%
    List<String> styles = (List<String>) request.getAttribute("styles");
    for (String style : styles) {
        out.print("<br>try:  " + style);
    }
%>
</body>
</html>
