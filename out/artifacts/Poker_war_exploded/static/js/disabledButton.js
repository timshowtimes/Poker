window.onload = function () {
    const unFinishedGames = document.getElementById('unFinishedGamesId');
    const submitButton = document.getElementById('submitButton');
    function submitStatement() {
        let has = unFinishedGames.value === "true";

        console.log(has);

        if (has) {
            console.log("ENTER TO TRUE");
            submitButton.removeAttribute('disabled');
        } else {
            submitButton.setAttribute('disabled', 'true');
        }
    }
    submitStatement();
};

