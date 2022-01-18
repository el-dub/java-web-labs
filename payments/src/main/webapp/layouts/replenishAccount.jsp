<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>User Management Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
        <div>
            <a href="<%=request.getContextPath()%>/profile" class="navbar-brand"> Payments Application </a>
        </div>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/profile" class="nav-link">Accounts</a></li>
        </ul>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/payment" class="nav-link">Payments</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <form action="/account/replenish" method="post">
                <caption>
                    <h2>
                        Replenish account
                    </h2>
                </caption>
                <input type="hidden" name="accountId" value="<c:out value='${requestScope.accountId}' />"/>
                <fieldset class="form-group">
                    <label>Amount</label> <input type="number" step="0.01" class="form-control" name="amount">
                </fieldset>
                <button type="submit" class="btn btn-success">Replenish</button>
            </form>
        </div>
    </div>
</div>
</body>

</html>
