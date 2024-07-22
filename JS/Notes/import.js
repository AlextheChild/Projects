function toHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}

export default function rgbToHex(r, g, b) {
    return "#" + toHex(r) + toHex(g) + toHex(b);
};