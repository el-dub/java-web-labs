<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <c:set var="statusCode" value="${requestScope.statusCode}"/>
    <h3 class="text-center">Oops, smth went wrong... ${statusCode}</h3>
    <h3 class="text-center">
        <c:choose>
            <c:when test="${statusCode == 404}">
                The page you requested was not found.
            </c:when>
            <c:when test="${statusCode >= 400 && statusCode <= 499}">
                There are some problems on your side. Contact us and we will try to help you.
            </c:when>
            <c:when test="${statusCode >= 500 && statusCode <= 599}">
                Sorry, we have some problems with our site. We will establish connection as soon as possible.
            </c:when>
        </c:choose>
    </h3>
</div>
</body>
</html>