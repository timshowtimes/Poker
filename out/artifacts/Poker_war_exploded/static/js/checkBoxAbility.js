const checkboxes = document.querySelectorAll("input[type='checkbox']");
checkboxes.forEach(checkbox => {
    checkbox.addEventListener("click", updateSubmitButton);
});

function updateSubmitButton() {
    const submitButton = document.getElementById("checkBoxSubmitButton");
    const checkedCount = document.querySelectorAll("input[type='checkbox']:checked").length;
    submitButton.disabled = checkedCount < 3 || checkedCount > 10;
    if (!submitButton.disabled)
        submitButton.style.borderColor = "green";
    else
        submitButton.style.borderColor = "#363636";
}

updateSubmitButton();