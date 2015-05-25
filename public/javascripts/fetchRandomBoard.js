function fetchRandomBoard(url) {
    $(".sudoku input").val("").attr("value", "").removeAttr("disabled");

    $.get(url, function (data) {
        var numbers = data.board.split("");
        $.each(numbers, function (idx, val) {
            var id = idx+1
            if (val !== "_") {
                $("#input" + id).attr("value", val).val(val).prop("disabled", "disabled");
            }
        });
    });
}

$("#easyNew").click(function (e) {
    fetchRandomBoard(boardUrl + "/easy");
});
$("#mediumNew").click(function (e) {
    fetchRandomBoard(boardUrl + "/medium");
})
$("#hardNew").click(function (e) {
    fetchRandomBoard(boardUrl + "/hard");
})