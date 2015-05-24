function fetchRandomBoard(url) {
    $.get(url, function (data) {
        var numbers = data.split("");
        $.each(numbers, function (idx, val) {
            if (val !== "_") {
                $("#input" + idx).val(val).trigger("keyup");
            }
        });
    });
}