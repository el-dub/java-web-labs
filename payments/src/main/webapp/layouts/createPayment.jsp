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
            <form action="/payment/create" method="post">
                <caption>
                    <h2>
                        Create new payment
                    </h2>
                </caption>

                <input type="hidden" name="senderId" value="<c:out value='${requestScope.senderId}' />"/>
                <fieldset class="form-group">
                    <label>Recipient id</label> <input type="text" class="form-control" name="recipientId" required="required">
                </fieldset>

                <fieldset class="form-group">
                    <label>Amount</label> <input type="number" step="0.01" class="form-control" name="amount">
                </fieldset>

                <fieldset class="form-group">
                    <label>Operation</label>
                    <select class="form-control" id="operation" name="operation">
                        <option value="PREPARED" selected>PREPARE</option>
                        <option value="SENT">SEND</option>
                    </select>
                </fieldset>

                <button type="submit" class="btn btn-success">Save</button>
            </form>
        </div>
    </div>
</div>
</body>

</html>
