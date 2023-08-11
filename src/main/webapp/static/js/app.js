function checkSum() {
    const inputs = document.querySelectorAll("input[type='number']");
    const cardCount = parseInt(document.getElementById('cardCount').value, 10);
    let sum = 0;
    for (let i = 0; i < inputs.length; i++) {
        sum += parseInt(inputs[i].value);
    }
    const errorMessage = document.getElementById('errorMessage');

    if (sum === cardCount) {
        errorMessage.style.display = 'block';
    } else {
        errorMessage.style.display = 'none';
    }
}