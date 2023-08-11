window.onload = function () {
    const listSize = document.getElementById("playersCount");
    let id = 'deleteButton';
    const deleteButtons = document.querySelectorAll('input[type="submit"]#deleteButton');

    function deleteStatement() {
        const size = parseInt(listSize.value);
        console.log("Size is: " + size);
        console.log("Count of buttons: " + deleteButtons.length);
        if (size > 3) {
            for (let i = 0; i < deleteButtons.length; i++) {
                console.log("Прошелся по каждой кнопке");
                deleteButtons[i].removeAttribute('disabled');
            }

        } else {
            for (let i = 0; i < deleteButtons.length; i++) {
                deleteButtons[i].setAttribute('disabled', 'true');
            }
        }
    }

    deleteStatement();
};