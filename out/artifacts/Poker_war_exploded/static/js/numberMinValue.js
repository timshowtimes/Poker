window.onload = function () {
    const numberInputs = document.querySelectorAll('input[type="number"]');

    function numberMinValue() {
        numberInputs.forEach(input => {
            input.min = 0;
        });
    }

    numberMinValue();
}