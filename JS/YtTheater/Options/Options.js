
var forceDarkTheme, displayYoutubeHeader, overlayTextShadow, showInfoOnVideoHover, showChatOnRight, showChatOverVideo, autoEnterTheaterMode, autoEnterTheaterModeReplay,
    chatWidth, chatOverVideoWidth, chatOverVideoMarginTop, chatOverVideoMarginBottom, overlayBackgroundOpacity, preventPausingOnClick,
    defaultChatLive, coloredUsernames, hidePfp, hideLiveReactions, superchatCurrencyConverter, convertToCurrency, currencyData, notLiveHint;

class ToggleButton {

  constructor(id, on, off, value) {
    this.element = document.getElementById(id);
    this.on = on;
    this.off = off;
    this.setValue(value);
    this.element.onclick = (event) => {
      this.onclick(event);
    };
  }

  onclick(event) {
    this.setValue(!this.value);
    chrome.storage.local.set({[this.element.id]: this.value}, () => {});
    event.target.blur();
  }

  setValue(value) {
    this.value = value;
    this.element.textContent = (value) ? this.on : this.off;
  }

}

class Dropdown {

  constructor(id, value) {
    this.element = document.getElementById(id);
    this.element.value = value;
    this.element.addEventListener('change', (event) => {
      chrome.storage.local.set({[this.element.id]: this.element.value}, () => {});
    });
  }

}

class NumberInput {

  constructor(id, min, max, value) {
    this.element = document.getElementById(id);
    this.error = document.getElementById(id + 'Error')
    this.min = min;
    this.max = max;
    this.element.value = value;
    this.element.oninput = () => {
      if(this.element.value >= min && this.element.value <= max) {
        chrome.storage.local.set({[this.element.id]: this.element.value}, () => {});
        this.error.style.display = 'none';
      }
      else {
        this.error.style.display = 'initial';
      }
    }
    this.element.onkeydown = (event) => blurOnEnter(event);
  }

}

class SuperchatButton extends ToggleButton {

  constructor(value) {
    super('superchatCurrencyConverter', 'On', 'Off', value);
  }

  onclick(event) {
    if(this.value) {
      chrome.permissions.remove({ origins: ['https://*.napalighost.com/*'] }, (removed) => {});
      super.onclick(event);
      hideConvertToCurrency();
    }
    else {
      chrome.permissions.request({ origins: ['https://*.napalighost.com/*'] }, (granted) => {
        if(granted) super.onclick(event);
        chrome.runtime.sendMessage('CurrencyUpdate', () => {
          showConvertToCurrency();
        });
      });
    }
  }

}



window.onload = () => {
  chrome.storage.onChanged.addListener(this.storageChange.bind(this));
  chrome.storage.local.get(['forceDarkTheme', 'displayYoutubeHeader', 'chatWidth', 'chatOverVideoWidth', 'chatOverVideoMarginTop', 'chatOverVideoMarginBottom', 'showInfoOnVideoHover', 'preventPausingOnClick', 'autoEnterTheaterMode', 'autoEnterTheaterModeReplay', 'toggleShortcut', 'overlayBackgroundOpacity', 'overlayTextShadow', 'showChatOnRight', 'showChatOverVideo',
                            'defaultChatLive', 'coloredUsernames', 'hidePfp', 'hideLiveReactions', 'superchatCurrencyConverter', 'convertToCurrency', 'currencyData', 'notLiveHint'], (data) => {
    forceDarkTheme = new ToggleButton('forceDarkTheme', 'On', 'Off', (data.forceDarkTheme === undefined) ? true : data.forceDarkTheme);
    displayYoutubeHeader = new ToggleButton('displayYoutubeHeader', 'On', 'Off', (data.displayYoutubeHeader === undefined) ? false : data.displayYoutubeHeader);
    overlayTextShadow = new ToggleButton('overlayTextShadow', 'On', 'Off', (data.overlayTextShadow === undefined) ? true : data.overlayTextShadow);
    chatWidth = new NumberInput('chatWidth', 300, 800, data.chatWidth || 400);
    chatOverVideoWidth = new NumberInput('chatOverVideoWidth', 250, 800, data.chatOverVideoWidth || 300);
    chatOverVideoMarginTop = new NumberInput('chatOverVideoMarginTop', 0, 1000, data.chatOverVideoMarginTop || 0);
    chatOverVideoMarginBottom = new NumberInput('chatOverVideoMarginBottom', 0, 1000, data.chatOverVideoMarginBottom || 0);
    let showInfoValue;
    switch(data.showInfoOnVideoHover) {
      case undefined:
        showInfoValue = 'video';
        break;
      case true:
        showInfoValue = 'video';
        break;
      case false:
        showInfoValue = 'icon';
        break;
      default:
        showInfoValue = data.showInfoOnVideoHover;
        break;
    }
    showInfoOnVideoHover = new Dropdown('showInfoOnVideoHover', showInfoValue);
    preventPausingOnClick = new ToggleButton('preventPausingOnClick', 'On', 'Off', (data.preventPausingOnClick === undefined) ? false : data.preventPausingOnClick);
    autoEnterTheaterMode = new ToggleButton('autoEnterTheaterMode', 'On', 'Off', (data.autoEnterTheaterMode === undefined) ? false : data.autoEnterTheaterMode);
    autoEnterTheaterModeReplay = new ToggleButton('autoEnterTheaterModeReplay', 'On', 'Off', (data.autoEnterTheaterModeReplay === undefined) ? false : data.autoEnterTheaterModeReplay);
    document.getElementById('toggleShortcut').value = (data.toggleShortcut === undefined) ? "t" : data.toggleShortcut;
    overlayBackgroundOpacity = new NumberInput('overlayBackgroundOpacity', 0, 1, data.overlayBackgroundOpacity || 0.8);
    showChatOnRight = new ToggleButton('showChatOnRight', 'Right', 'Left', (data.showChatOnRight === undefined) ? true : data.showChatOnRight);
    showChatOverVideo = new ToggleButton('showChatOverVideo', 'Over video', 'Next to video', (data.showChatOverVideo === undefined) ? false : data.showChatOverVideo);
    defaultChatLive = new ToggleButton('defaultChatLive', 'Live chat', 'Top chat', (data.defaultChatLive === undefined) ? false : data.defaultChatLive);
    coloredUsernames = new ToggleButton('coloredUsernames', 'On', 'Off', (data.coloredUsernames === undefined) ? false : data.coloredUsernames);
    hidePfp = new ToggleButton('hidePfp', 'On', 'Off', (data.hidePfp === undefined) ? false : data.hidePfp);
    hideLiveReactions = new ToggleButton('hideLiveReactions', 'On', 'Off', (data.hideLiveReactions === undefined) ? false : data.hideLiveReactions);
    superchatCurrencyConverter = new SuperchatButton((data.superchatCurrencyConverter === undefined) ? false : data.superchatCurrencyConverter);
    convertToCurrency = new Dropdown('convertToCurrency', null);
    convertToCurrencyCode = data.convertToCurrency || 'USD';
    currencyData = data.currencyData;
    if(superchatCurrencyConverter.value) {
      populateConvertToCurrency();
    }
    else {
      hideConvertToCurrency();
    }
    notLiveHint = new ToggleButton('notLiveHint', 'On', 'Off', (data.notLiveHint === undefined) ? false : data.notLiveHint);
  });
  document.getElementById('toggleShortcut').addEventListener('keydown', blurOnEnter);
  document.getElementById('toggleShortcut').oninput = (event) => {
    chrome.storage.local.set({toggleShortcut: event.target.value || null}, () => {});
  };
};

function storageChange(changes, area) {
  if(area == 'local') {
    let changedItems = Object.keys(changes);
    for (let item of changedItems) {
      switch(item) {
        case 'convertToCurrency':
          convertToCurrencyCode = changes[item].newValue;
          break;
        case 'currencyData':
          currencyData = changes[item].newValue;
          clearConvertToCurrency();
          populateConvertToCurrency();
          break;
      }
    }
  }
}

function clearConvertToCurrency() {
  while(convertToCurrency.element.firstChild) {
    convertToCurrency.element.removeChild(convertToCurrency.element.firstChild);
  }
}

function populateConvertToCurrency() {
  if(currencyData) {
    currencyData.currencies.forEach((currency) => {
      let option = document.createElement('option');
      option.value = currency.code;
      option.textContent = currency.code + ' - ' + currency.name;
      convertToCurrency.element.appendChild(option);
    });
  }
  convertToCurrency.element.value = convertToCurrencyCode;
}

function hideConvertToCurrency() {
  document.getElementById('convertToCurrencyContainer').setAttribute('data-hidden', '');
}

function showConvertToCurrency() {
  document.getElementById('convertToCurrencyContainer').removeAttribute('data-hidden');
}

function blurOnEnter(event) {
  if(event.keyCode === 13) event.target.blur();
}
