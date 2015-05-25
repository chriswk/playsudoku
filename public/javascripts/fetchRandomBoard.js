function fetchRandomBoard(url) {
    $.get(url, function (data) {
        var numbers = data.board.split("");
        $.each(numbers, function (idx, val) {
            var id = idx+1
            if (val !== "_") {
                $("#input" + id).prop("value", val).val(val).prop("disabled", "disabled");
            }
        });
        var solution = data.solution.split("");
        $.each(solution, function (idx, val) {
            var id = idx+1;
            $("#solve" +id).val(val).prop("disabled", "disabled");
        });
    });
}