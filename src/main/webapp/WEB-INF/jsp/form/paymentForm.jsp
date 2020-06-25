<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div>
    <form:form method="POST" modelAttribute="paymentFormData" class="form-signin">
        <form:select path="allowedTypeCurrencyEntity">
            <c:forEach items="${allowedPaymentOptions}" var="option">
                <form:option value="${option.id}" label="${option}"></form:option>
            </c:forEach>
        </form:select>
        <form:input path="amount"></form:input>
        <button id="submitPayment" type="button" class="btn btn-success">add payment</button>
    </form:form>
    <script src="${contextPath}/resources/js/payment.js"></script>

</div>