var textArea = document.getElementById('textArea');

function toHexidecimal(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}

function rgbaToHex(r, g, b) {
    return "#" + toHexidecimal(r) + toHexidecimal(g) + toHexidecimal(b);
}

// get color from storage and displays
chrome.storage.local.get(
    ["color"],
    function (data) {
        textArea.value = data.color;
    }
)

// updates storage and also textArea bg color
textArea.oninput = function () {
    var rgb = textArea.value.split(", ");
    var rgbString = rgbaToHex(parseInt(rgb[0]), parseInt(rgb[1]), parseInt(rgb[2]));
    textArea.style.backgroundColor = rgbString;

    chrome.storage.local.set(
        { color: textArea.value }
    )
}
