let paragraph = document.getElementById("trumpSymbol");
let trump = paragraph.textContent;

if (trump === "♥" || trump === "♦") {
    paragraph.style.color = "red";
} else {
    paragraph.style.color = "black";
}