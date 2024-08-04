

class SuperChatCurrencyConverter {

  constructor() {
    this.active = false;
  }

  activate() {
    if(!this.active && chat.initialized && !chatSettings.currenciesLoading) {
      this.active = true;
      let ticker = document.querySelector('#ticker #items');
      if(ticker) chat.tickerObserver.observe(ticker, { childList: true });
      let pinnedMessageContainer = document.querySelector('yt-live-chat-pinned-message-renderer');
      if(pinnedMessageContainer) chat.pinnedMessageObserver.observe(pinnedMessageContainer, { childList: true, subtree: true });
      document.querySelectorAll('#ticker #items yt-live-chat-ticker-paid-message-item-renderer').forEach((element) => {
        this.addConvertedCurrency(element);
      });
      chat.messageContainer.querySelectorAll('yt-live-chat-paid-message-renderer').forEach((element) => {
        this.addConvertedCurrencyToMessage(element);
      });
      chat.messageObserverSettingsChanged();
    }
  }

  deactivate() {
    if(this.active) {
      this.active = false;
      document.querySelectorAll('#ticker #items yt-live-chat-ticker-paid-message-item-renderer.ytlstm-currency-converted').forEach((element) => {
        this.removeConvertedCurrency(element);
      });
      chat.messageContainer.querySelectorAll('yt-live-chat-paid-message-renderer.ytlstm-currency-converted').forEach((element) => {
        this.removeConvertedCurrencyFromMessage(element);
      });
      chat.messageObserverSettingsChanged();
    }
  }

  getConvertedCurrency(currencyToConvert) {
    let value = (Number(currencyToConvert.amount) * (chatSettings.convertToCurrency.value / currencyToConvert.currency.value)).toFixed(2);
    return ((chatSettings.convertToCurrency.prefixes.length > 0) ? chatSettings.convertToCurrency.prefixes[0] : (chatSettings.convertToCurrency.code + ' ')) + value;
  }

  getCurrencyToConvert(text) {
    let index, youTubePrefix = '', amount;
    if(text.charAt(0) >= '0' && text.charAt(index) <= '9') {
      index = text.length - 1;
      while(index >= 0 && !(text.charAt(index) >= '0' && text.charAt(index) <= '9')) {
        youTubePrefix = text.charAt(index) + youTubePrefix;
        index--;
      }
      amount = text.substring(0, index + 1);
    }
    else {
      index = 0;
      while(index < text.length && !(text.charAt(index) >= '0' && text.charAt(index) <= '9')) {
        youTubePrefix += text.charAt(index);
        index++;
      }
      amount = text.substring(index);
    }
    amount = amount.replace(/\s+/g, '').replace(',','.'); /*remove spaces, replace commas with dots.*/
    index = 0;
    while(index < amount.length) {
      if(amount.charAt(index) == '.' && index != amount.length - 3) {
        amount = amount.substring(0, index) + amount.substring(index + 1); /*remove dots that are not for decimals*/
      }
      else {
        index++;
      }
    }
    youTubePrefix = youTubePrefix.replace(/\s+/g, '').replace(/\u00a0/g, ''); /*remove spaces and non breaking spaces*/
    let currency = chatSettings.currencyData.currencies.find(currency => currency.code === youTubePrefix);
    if(!currency) currency = chatSettings.currencyData.currencies.find((currency) => (currency.prefixes.find((prefix) => prefix === youTubePrefix) != null));
    return { currency: currency, amount: amount };
  }

  addConvertedCurrency(element) {
    let currencyContainer = element.querySelector('#text');
    if(currencyContainer) {
      let currencyString = currencyContainer.textContent,
          currencyToConvert = this.getCurrencyToConvert(currencyString);
      if(currencyToConvert.currency && currencyToConvert.currency !== chatSettings.convertToCurrency) {
        element.classList.add('ytlstm-currency-converted');
        let span = document.createElement('span');
        span.classList.add('ytlstm-converted-currency');
        span.textContent = this.getConvertedCurrency(currencyToConvert);
        currencyContainer.appendChild(span);
        element.title = currencyToConvert.amount + ' ' + currencyToConvert.currency.name + ' ≈ ' + span.textContent;
      }
    }
    else {
      setTimeout(() => {
        this.addConvertedCurrency(element);
      }, 100);
    }
  }

  removeConvertedCurrency(element) {
    element.classList.remove('ytlstm-currency-converted');
    element.querySelector('#text').removeChild(element.querySelector('.ytlstm-converted-currency'));
  }

  addConvertedCurrencyToMessage(element) {
    let currencyString = element.querySelector('#purchase-amount').firstElementChild.textContent,
        currencyToConvert = this.getCurrencyToConvert(currencyString);
    if(currencyToConvert.currency && currencyToConvert.currency !== chatSettings.convertToCurrency) {
      element.classList.add('ytlstm-currency-converted');
      let div = document.createElement('div');
      div.classList.add('ytlstm-paid-message-converted-currency');
      div.textContent = this.getConvertedCurrency(currencyToConvert);
      let purchaseAmountColumn = element.querySelector('#purchase-amount-column');
      purchaseAmountColumn.appendChild(div);
      purchaseAmountColumn.title = currencyToConvert.amount + ' ' + currencyToConvert.currency.name + ' ≈ ' + div.textContent;
    }
  }

  removeConvertedCurrencyFromMessage(element) {
    element.classList.remove('ytlstm-currency-converted');
    element.querySelector('#purchase-amount-column').removeChild(element.querySelector('.ytlstm-paid-message-converted-currency'));
  }

}
