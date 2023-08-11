function checkBet(input) {
    const inputValue = parseInt(input.value);
    const cardCount = parseInt(document.getElementById('cardCount').value, 10);
    const errorMessage = document.getElementById('errorMessage2');

    if (inputValue > cardCount) {
        errorMessage.style.display = 'block';
    } else {
        errorMessage.style.display = 'none';
    }
}