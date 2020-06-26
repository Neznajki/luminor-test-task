<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div>

    <table border="1px">
        <thead>
            <td width="40px">id</td>
            <td width="40px">UUID</td>
            <td>type</td>
            <td>currency</td>
            <td>amount</td>
            <td>date</td>

            <td>debtor iban</td>
            <td>creditor iban</td>
            <td>details</td>
            <td>creditor bank bic code</td>

            <td>canceled ??</td>
            <td>cancel !!</td>
        </thead>

    <c:forEach items="${existingPayments}" var="payment">
        <%--@elvariable id="payment" type="com.luminor.task.payment.db.entity.ExistingPaymentEntity"--%>
        <%--@elvariable id="cancelFeeService" type="com.luminor.task.payment.payment.CancelFeeServiceImpl"--%>
            <tr>
                <td>${payment.id}</td>
                <td>${payment.uniqueIdByUniqueId.hashValue}</td>
                <td>${payment.paymentTypeByPaymentTypeId}</td>
                <td>${payment.currencyByCurrencyId}</td>
                <td>${payment.paymentAmount}</td>
                <td>${payment.created}</td>

                <td>${payment.existingPaymentDataById.debtorIban}</td>
                <td>${payment.existingPaymentDataById.creditorIban}</td>
                <td
                        <c:if test="${payment.paymentTypeByPaymentTypeId.typeName=='TYPE3'}">style="background-color: red"</c:if>
                        <c:if test="${payment.paymentTypeByPaymentTypeId.typeName=='TYPE2'}">style="background-color: yellow"</c:if>
                >${payment.existingPaymentDataById.details}</td>
                <td <c:if test="${payment.paymentTypeByPaymentTypeId.typeName!='TYPE3'}">style="background-color: red"</c:if>> ${payment.existingPaymentDataById.creditorBankBicCode}</td>
                <td><c:choose><c:when test="${payment.canceledPaymentEntity==null}">no</c:when><c:when test="${payment.canceledPaymentEntity!=null}">yes</c:when></c:choose></td>
                <td> <c:if test="${cancelFeeService.canCancelPayment(payment)}">
                    <form:form action="/cancel/payment" method="POST" class="form-signin">
                        <input name="paymentId" value="${payment.uniqueIdByUniqueId.hashValue}" style="display: none;"/>
                        <button type="submit" data-cancel-id="${payment.uniqueIdByUniqueId.hashValue}">cancel payment minimal costs(~${cancelFeeService.getCalculatedFee(payment)})</button>
                    </form:form>
                </c:if></td>
            </tr>
    </c:forEach>
    </table>

    <%--@elvariable id="paymentFormData" type="com.luminor.task.payment.payment.PaymentFormData"--%>
    <form:form action="/create/payment" method="POST" modelAttribute="paymentFormData" class="form-signin">
        <span>${errorMessage}</span>
        <div class="form-group"><label for="allowedTypeCurrencyEntityId">Type </label><form:select path="allowedTypeCurrencyEntityId">
            <%--@elvariable id="option" type="com.luminor.task.payment.db.entity.AllowedTypeCurrencyEntity"--%>
            <c:forEach items="${allowedPaymentOptions}" var="option">
                <form:option value="${option.id}" label="${option}" data-type="${option.paymentTypeByTypeId.typeName}"></form:option>
            </c:forEach>
        </form:select>
        </div>
        <div class="form-group"><label for="debtorIban">debtor iban</label><form:input path="debtorIban" required="required" pattern="[a-zA-Z\s]{4,}"></form:input></div>
        <div class="form-group"><label for="creditorIban">creditor iban</label><form:input path="creditorIban" required="required" pattern="[a-zA-Z\s]{4,}"></form:input></div>
        <div class="form-group optionalField TYPE1 TYPE2"><label for="details">details</label><form:input path="details" required="required" pattern="[a-zA-Z\s]{4,}" class="optionalTYPE2"></form:input></div>
        <div class="form-group optionalField TYPE3"><label for="creditorBankBicCode">creditor bank bic code</label><form:input path="creditorBankBicCode" required="required" pattern="[a-zA-Z\s]{4,}"></form:input></div>
        <div class="form-group"><label for="amount">amount</label><form:input path="amount" type="number" placeholder="10.45" step="0.01" required="required" min="2"></form:input></div>
        <button id="submitPayment" type="submit" class="btn btn-success">add payment</button>
    </form:form>
    <script src="${contextPath}/resources/js/payment.js"></script>

</div>