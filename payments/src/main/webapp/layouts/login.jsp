<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login form</title>
</head>
<body>
<div class="login-form">
    <form action="<c:url value="/login"/>" method="post">
        <div class="form-group">
            <label>
                <input type="text" class="form-control" name="email" placeholder="Email" required="required">
            </label>
        </div>
        <div class="form-group">
            <label>
                <input type="password" class="form-control" name="password" placeholder="Password" required="required">
            </label>
        </div>
        <input type="submit" class="btn btn-primary btn-block btn-lg" value="Login">
        <c:if test="${requestScope.errMsg != null}">
            <p class="mb-2 text-danger text-center font-italic">${requestScope.errMsg}</p>
        </c:if>
    </form>
</div>
</body>
</html>
