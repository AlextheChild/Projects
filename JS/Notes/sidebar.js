// !put main content into a div
var mainContent = document.createElement("div");
Array.from(document.querySelector("body").getElementsByTagName("*")).forEach(
    (element) => mainContent.appendChild(element)
);

// create sidebar
var sideBar = document.createElement("div");
sideBar.style.height = (window.innerHeight) + "px";
sideBar.style.width = "300px";
sideBar.style.position = "fixed";
sideBar.style.top = "0";
sideBar.style.right = "0";
sideBar.style.backgroundColor = "#DDDDDD";

// create textArea
var textArea = document.createElement("textArea");
textArea.style.height = window.innerHeight - 10 + "px";
textArea.style.width = "290px";
textArea.style.margin = "5px"
textArea.style.font = "14px Monospace"
function input() {
    var url = new URL(window.location.href);
    var domain = url.hostname;

    // get current stored
    var currentUrls = [];
    var currentTexts = [];
    chrome.storage.local.get(
        ["urls", "texts"],
        function (data) {
            currentUrls = data.urls;
            currentTexts = data.texts;

            // update stored
            currentTexts[currentUrls.indexOf(domain)] = textArea.value;
            chrome.storage.local.set(
                { texts: currentTexts }
            );
        }
    );

    // !chrome.storage.local.get(
    // !    ["texts"],
    // !    function (data) {
    // !        console.log(data.texts);
    // !    }
    // !);
}
textArea.oninput = function () { input(); };

// update textArea with stored values
import { rgbToHex } from "import.js";
chrome.storage.local.get(
    ["color", "urls", "texts",],
    function (data) {
        // update color
        var rgb = data.color.split(", ");
        var rgbString = rgbToHex(parseInt(rgb[0]), parseInt(rgb[1]), parseInt(rgb[2]));
        textArea.style.backgroundColor = rgbString;

        // update texts and urls
        var url = new URL(window.location.href);
        var domain = url.hostname;
        var currentUrls = data.urls;
        var currentTexts = data.texts;

        if (!currentUrls.includes(domain)) {
            currentUrls.push(domain);
            currentTexts.push("");

            chrome.storage.local.set(
                {
                    urls: currentUrls,
                    texts: currentTexts
                }
            )
        }

        // update textArea
        textArea.value = data.texts[data.urls.indexOf(domain)];
    }
);
sideBar.appendChild(textArea);

// create table
var table = document.createElement("table");
var row = table.insertRow();
row.insertCell().appendChild(mainContent);
row.insertCell().appendChild(sideBar);
document.body.appendChild(table);