function funcSubmitAbility() {
    const taken = document.querySelectorAll("input[type='number']#taken");
    const cardCount = parseInt(document.getElementById('cardCount').value, 10);
    const bets = document.querySelectorAll("input[type='number']#bet");

    for (let i = 0; i < bets.length; i++) {
        if (bets[i].value > cardCount) {
            betOverflow();
            break;
        }
    }

    let sum = 0;
    for (let i = 0; i < taken.length; i++) {
        let number = parseInt(taken[i].value);
        if (number < 0)
            return;
        sum += number;
    }
    if (!error)
        submit.disabled = sum !== cardCount;
}
