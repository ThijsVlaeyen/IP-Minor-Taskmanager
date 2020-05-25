$(document).ready(function() {
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        console.log(selectedOption);
        if (selectedOption != ''){
            window.location.replace(window.location.pathname+'?lang=' + selectedOption);
            console.log(selectedOption);
        }
    });
});