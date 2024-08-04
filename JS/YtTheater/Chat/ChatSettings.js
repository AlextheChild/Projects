
class ChatSettings {

  constructor() {
    this.usernameColors = ['blue', 'green', 'yellow', 'red', 'turquoise', 'orange', 'pink', 'violet', 'lime', 'skyblue'];
    this.colorRandomNumber = Math.floor(Math.random() * this.usernameColors.length);
  }

  init() {
    chrome.storage.onChanged.addListener(this.storageChange.bind(this));
    chrome.storage.local.get(['defaultChatLive', 'coloredUsernames', 'hidePfp', 'hideLiveReactions', 'superchatCurrencyConverter', 'convertToCurrency', 'currencyData'], (data) => {
      this.defaultChatLive = (data.defaultChatLive === undefined) ? false : data.defaultChatLive;
      this.coloredUsernames = (data.coloredUsernames === undefined) ? false : data.coloredUsernames;
      this.hidePfp = (data.hidePfp === undefined) ? false : data.hidePfp;
      this.hideLiveReactions = (data.hideLiveReactions === undefined) ? false : data.hideLiveReactions;
      this.superchatCurrencyConverter = (data.superchatCurrencyConverter === undefined) ? false : data.superchatCurrencyConverter;
      this.currencyData = (data.currencyData === undefined) ? null : data.currencyData;
      this.convertToCurrencyCode = (data.convertToCurrency === undefined) ? 'USD' : data.convertToCurrency;
      if(this.superchatCurrencyConverter) {
        if(!this.currencyData || this.currencyData.synchronized < (Date.now() - (24 * 60 * 60 * 1000))) {
          this.updateCurrencies();
        }
        else {
          this.convertToCurrency = this.currencyData.currencies.find(currency => currency.code == this.convertToCurrencyCode);
        }
      }
      chat.init();
    });
  }

  updateCurrencies() {
    this.currenciesLoading = true;
    chrome.runtime.sendMessage('CurrencyUpdate', () => {
      this.currenciesLoading = false;
    });
  }

  storageChange(changes, area) {
    if(area == 'local') {
      let changedItems = Object.keys(changes);
      for (let item of changedItems) {
        switch(item) {
          case 'defaultChatLive':
            this.defaultChatLive = changes[item].newValue;
            break;
          case 'coloredUsernames':
            this.coloredUsernames = changes[item].newValue;
            chat.messageObserverSettingsChanged();
            chat.applyUsernameColors();
            break;
          case 'hidePfp':
            this.hidePfp = changes[item].newValue;
            chat.applyHidePfp();
            break;
          case 'hideLiveReactions':
            this.hideLiveReactions = changes[item].newValue;
            chat.applyHideLiveReactions();
            break;
          case 'superchatCurrencyConverter':
            this.superchatCurrencyConverter = changes[item].newValue;
            if(!this.superchatCurrencyConverter) {
              superChatCurrencyConverter.deactivate();
            }
            break;
          case 'convertToCurrency':
            this.convertToCurrencyCode = changes[item].newValue;
            this.convertToCurrency = this.currencyData.currencies.find(currency => currency.code == this.convertToCurrencyCode);
            superChatCurrencyConverter.deactivate();
            superChatCurrencyConverter.activate();
            break;
          case 'currencyData':
            this.currencyData = changes[item].newValue;
            if(this.currencyData) {
              this.convertToCurrency = this.currencyData.currencies.find(currency => currency.code == this.convertToCurrencyCode);
              superChatCurrencyConverter.deactivate();
              superChatCurrencyConverter.activate();
            }
            break;
        }
      }
    }
  }

  getUsernameColor(username) {
    return this.usernameColors[(username.charCodeAt(0) + username.length + this.colorRandomNumber) % this.usernameColors.length];
  }

}
