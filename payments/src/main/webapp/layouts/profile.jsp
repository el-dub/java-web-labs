<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Payments application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
        <div>
            <a href="https://www.javaguides.net" class="navbar-brand"> Payments Application </a>
        </div>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/profile" class="nav-link">Accounts</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">
    <div class="container">
        <h3 class="text-center">List of accounts</h3>
        <hr>
        <div class="container text-left">

            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add new account</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Money amount</th>
                <th>Is locked</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="account" items="${requestScope.accounts}">

                <tr>
                    <td>
                        <c:out value="${account.id}" />
                    </td>
                    <td>
                        <c:out value="${account.name}" />
                    </td>
                    <td>
                        <c:out value="${account.moneyAmount}" />
                    </td>
                    <td>
                        <c:out value="${account.locked}" />
                    </td>
                    <td><a href="replenish?id=<c:out value='${account.id}' />">Replenish</a> &nbsp;&nbsp;&nbsp;&nbsp; <a href="lock?id=<c:out value='${account.id}' />">Lock</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>

</html>
