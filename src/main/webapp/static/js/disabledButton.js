window.onload = function () {
    const listSize = document.getElementById('listSize');
    const submitButton = document.getElementById('submitButton');
    function submitStatement() {
        const size = parseInt(listSize.value);
        console.log("Функция вызвана")
        if (size > 0) {
            submitButton.removeAttribute('disabled');
        } else {
            submitButton.setAttribute('disabled', 'true');
        }
    }
    submitStatement();
};

