$(() => {
    $("#submitPayment").on("click", function () {
        var data = {};

        $.each($(this).parent('form').serializeArray(), (_,kv) => {
            data[kv.name] = kv.value;
        });

        if (data.amount < 1) {
            alert("minimum amount is 1");
            return;
        }

        $.ajax("/rest-api/create/payment/" + data.allowedTypeCurrencyEntity + "/" + data.amount, {
            method: "PUT",
            headers: {
                'Authorization':'Basic UkVTVGFwaTphcGlfcGFzcw==',
            },
            contentType: "application/json",
            success: function (response) {

            }
        })
    })
});