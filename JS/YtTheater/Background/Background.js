
chrome.runtime.onMessage.addListener((message, sender, callback) => {
  switch(message) {
    case 'CurrencyUpdate':
      fetch("https://www.napalighost.com/api.php", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ queries: [ { query: "CurrenciesRead" } ] })
      })
        .then((response) => response.json())
        .then((json) => {
          let currencyData = json.queries[0].data;
          currencyData.synchronized = Date.now();
          chrome.storage.local.set({ currencyData: currencyData }, () => {});
          callback();
        });
      return true;
    break;
  }
});

chrome.runtime.onInstalled.addListener(({reason}) => {
  switch(reason) {
    case 'install':
      chrome.tabs.create({
        url: 'ExtensionInfo/Onboarding.html'
      });
    break;
    case 'update':
      chrome.tabs.create({
        url: 'ExtensionInfo/UpdateNotes.html',
        active: false
      });
    break;
  }
});
