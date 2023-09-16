window.onload = function () {
    const listSize = document.getElementById("playersCount");
    const deleteButtons = document.querySelectorAll('input[type="submit"]#deleteButton');

    function deleteStatement() {
        const size = parseInt(listSize.value);
        if (size > 3) {
            for (let i = 0; i < deleteButtons.length; i++) {
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