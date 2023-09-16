const submit = document.getElementById('submitAbility');
let error = false;

function checkSum() {
    const inputs = document.querySelectorAll("input[type='number']#bet");
    const cardCount = parseInt(document.getElementById('cardCount').value, 10);
    const errorMessage = document.getElementById('errorMessage');
    const errorMessage2 = document.getElementById('errorMessage2');

    let sum = 0;
    for (let i = 0; i < inputs.length; i++) {
        sum += parseInt(inputs[i].value);
    }
    if (sum === cardCount) {
        errorMessage.style.display = 'block';
        submit.disabled = true;
        error = true;
        errorMessage2.style.display = 'none';
    } else {
        errorMessage.style.display = 'none';
        error = false;
        funcSubmitAbility();
    }

}