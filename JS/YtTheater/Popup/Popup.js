window.onload = () => {
  document.getElementById('options').onclick = () => chrome.runtime.openOptionsPage();
}
