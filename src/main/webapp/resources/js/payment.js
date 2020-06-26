$(() => {
    $("#allowedTypeCurrencyEntityId").on("change", function () {
        switchType($(this));
    }).trigger("change");

    $(".cancel-payment").on("click", function () {
        $.ajax("/rest-api/cancel/payment", {
            method: "PUT",
            headers: {
                Authorization: "Basic " + btoa("RESTapi:api_pass")
            },
            XMLHttpRequest: {
                withCredentials: false
            },
            contentType: "application/json",
            data: JSON.stringify({UUID: $(this).data("cancel-id")}),
            done: function (data) {
                if (data.status == 'success') {
                    alert(data.UUID + "payment cancel success fee : " . data.fee)
                } else if (typeof data.errorMessage != "undefined") {
                    alert(data.errorMessage);
                } else {
                    alert("something gone wrong");
                }
            }
        })
    })
});

function switchType($item) {
    $(".optionalField").hide().find("input").attr("disabled", "disabled").attr("required", "required");
    let selectedElement = getSelectedOption($item);
    $("." + selectedElement).show().find("input").removeAttr("disabled");
    $(".optional" + selectedElement).removeAttr("required");
}

function getSelectedOption($item) {
    return $item.find("option:selected").data("type");
}
