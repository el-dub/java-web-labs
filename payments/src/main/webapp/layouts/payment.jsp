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
        <h3 class="text-center">List of payments</h3>
        <hr>
        <br>
        <div class="container d-flex flex-column justify-content-center mt-50 mb-50">
            <form action="${pageContext.request.contextPath}/payment" method="get" class="mb-0">
                <div class="form-row">
                    <div class="col-sm-3 mb-3">
                        <label for="sortBy">Sort By:</label>
                        <select class="form-control" id="sortBy" name="sortBy">
                            <option value="ID" selected>Id</option>
                            <option value="DATE">Time</option>
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
                <th>Recipient ID</th>
                <th>Time</th>
                <th>Amount</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="payment" items="${requestScope.payments}">
                <tr>
                    <td>
                        <c:out value="${payment.id}"/>
                    </td>
                    <td>
                        <c:out value="${payment.recipient.id}"/>
                    </td>
                    <td>
                        <c:out value="${payment.time}"/>
                    </td>
                    <td>
                        <c:out value="${payment.amount}"/>
                    </td>
                    <td>
                        <c:out value="${payment.status}"/>
                    </td>
                    <td><a href="payment/send?paymentId=<c:out value='${payment.id}' />">Send prepared</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>

</html>
