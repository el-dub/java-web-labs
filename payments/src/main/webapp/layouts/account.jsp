<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Payments application</title>
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

<div class="row">
    <div class="container">
        <h3 class="text-center">List of accounts</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add new account</a>
        </div>
        <br>
        <div class="container d-flex flex-column justify-content-center mt-50 mb-50">
            <form action="${pageContext.request.contextPath}/profile" method="get" class="mb-0">
                <div class="form-row">
                    <div class="col-sm-3 mb-3">
                        <label for="sortBy">Sort By:</label>
                        <select class="form-control" id="sortBy" name="sortBy">
                            <option value="ID" selected>Id</option>
                            <option value="NAME">Name</option>
                            <option value="MONEY_AMOUNT">Money amount</option>
                        </select>
                    </div>
                    <div class="col-sm-1 mb-3">
                        <label for="direction">Direction: </label>
                        <select class="form-control" id="direction" name="direction">
                            <option value="ASC" selected>ASC</option>
                            <option value="DESC">DESC</option>
                        </select>
                    </div>
                    <div class="col-sm-1 mb-3 align-text-bottom">
                        <label for="btnFilterSubmit">Apply Filter</label>
                        <input type="submit" id="btnFilterSubmit" class="btn btn-outline-dark">
                    </div>
                </div>
            </form>
        </div>
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
                        <c:out value="${account.id}"/>
                    </td>
                    <td>
                        <c:out value="${account.name}"/>
                    </td>
                    <td>
                        <c:out value="${account.moneyAmount}"/>
                    </td>
                    <td>
                        <c:out value="${account.locked}"/>
                    </td>
                    <td><a href="payment/create?senderId=<c:out value='${account.id}' />">Create payment</a> &nbsp;&nbsp;&nbsp;&nbsp;
                        <a
                                href="account/replenish?accountId=<c:out value='${account.id}' />">Replenish</a> &nbsp;&nbsp;&nbsp;&nbsp;
                        <a
                                href="account/lock?accountId=<c:out value='${account.id}' />">Lock</a>
                        <a
                                href="account/lock?accountId=<c:out value='${account.id}' />">Unlock</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>

</html>
