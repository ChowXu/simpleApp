<%--
  Created by IntelliJ IDEA.
  User: zhouxi
  Date: 17/1/17
  Time: 下午4:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="base" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Customer</title>
</head>
<body>
<h1>customer managing</h1>
<table>
    <tr>
        <th>name</th>
        <th>contact</th>
        <th>telephone</th>
        <th>email</th>
        <th>opt</th>

    </tr>
    <c:forEach var="customer" items="${customers}">
        <tr>
            <td>${customer.name}</td>
            <td>${customer.contact}</td>
            <td>${customer.telephone}</td>
            <td>${customer.email}</td>
            <td>
                <a href="${base}/customer_edit.jsp?id = ${customer.id}">edit</a>
                <a href="${base}/customer_delete.jsp?id = ${customer.id}">edit</a>
            </td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
