<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create an account</title>
    <link href="${contextPath}/resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <a onclick="document.forms['logoutForm'].submit()" style="position: absolute; top: 5px; right: 100px;">Logout</a>

        <h2>Welcome ${pageContext.request.userPrincipal.name}</h2>
    </c:if>
</div>
<script src="${contextPath}/resources/js/jquery.1.11.2.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>
</body>
</html>