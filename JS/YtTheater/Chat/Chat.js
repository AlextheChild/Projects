

class Chat {

  constructor() {
    this.observingMessages = false;
    this.initialized = false;

    this.messageContainerObserver = new MutationObserver((mutations, observer) => {
      mutations.filter((mutation) => mutation.addedNodes.length > 0).forEach((mutation) => {
        for(let i=0; i<mutation.addedNodes.length; i++) {
          if(mutation.addedNodes[i].tagName === 'YT-LIVE-CHAT-ITEM-LIST-RENDERER') {
            this.disconnectMessageObserver();
            this.tickerObserver.disconnect();
            this.messageContainer = document.querySelector('#items.yt-live-chat-item-list-renderer');
            this.initMessageContainer();
          }
        }
      });
    });
    this.messageObserver = new MutationObserver((mutations, observer) => {
      mutations.filter((mutation) => mutation.addedNodes.length > 0).forEach((mutation) => {
        for(let i=0; i<mutation.addedNodes.length; i++) {
          if(mutation.addedNodes[i].tagName === 'YT-LIVE-CHAT-TEXT-MESSAGE-RENDERER') {
            if (chatSettings.coloredUsernames) this.applyUsernameColor(mutation.addedNodes[i]);
          }
          else if(chatSettings.superchatCurrencyConverter && mutation.addedNodes[i].tagName === 'YT-LIVE-CHAT-PAID-MESSAGE-RENDERER') {
            superChatCurrencyConverter.addConvertedCurrencyToMessage(mutation.addedNodes[i]);
          }
        }
      });
    });
    this.pinnedMessageObserver = new MutationObserver((mutations, observer) => {
      mutations.filter((mutation) => mutation.addedNodes.length > 0).forEach((mutation) => {
        for(let i=0; i<mutation.addedNodes.length; i++) {
          if(mutation.addedNodes[i].tagName === 'YT-LIVE-CHAT-PAID-MESSAGE-RENDERER') {
            superChatCurrencyConverter.addConvertedCurrencyToMessage(mutation.addedNodes[i]);
          }
        }
      });
    });
    this.tickerObserver = new MutationObserver((mutations, observer) => {
      mutations.filter((mutation) => mutation.addedNodes.length > 0).forEach((mutation) => {
        for(let i=0; i<mutation.addedNodes.length; i++) {
          if(mutation.addedNodes[i].tagName === 'YT-LIVE-CHAT-TICKER-PAID-MESSAGE-ITEM-RENDERER') {
            superChatCurrencyConverter.addConvertedCurrency(mutation.addedNodes[i]);
          }
        }
      });
    });

  }

  init() {
    if(document) {
      this.messageContainer = document.querySelector('#items.yt-live-chat-item-list-renderer');
      this.messageContainerObserver.observe(document.getElementById('item-list'), { childList: true });
      if(chatSettings.defaultChatLive) {
        this.switchToLiveChat();
      }
      else {
        this.initMessageContainer();
      }
    }
    else {
      setTimeout(this.init.bind(this), 100);
    }
  }

  initMessageContainer() {
    this.initialized = true;
    this.authorChip = document.querySelector('yt-live-chat-message-input-renderer yt-live-chat-author-chip');
    if(!this.observingMessages && (chatSettings.coloredUsernames || chatSettings.superchatCurrencyConverter)) this.connectMessageObserver();
    if(chatSettings.coloredUsernames) this.applyUsernameColors();
    if(chatSettings.hidePfp) this.applyHidePfp();
    if(chatSettings.hideLiveReactions) this.applyHideLiveReactions();
    if(chatSettings.superchatCurrencyConverter) superChatCurrencyConverter.activate();
  }

  switchToLiveChat() {
    let buttons = document.querySelectorAll('tp-yt-paper-listbox > a');
    if(buttons.length >= 1) buttons[1].click();
  }

  messageObserverSettingsChanged() {
    if(this.observingMessages && !chatSettings.coloredUsernames && !chatSettings.superchatCurrencyConverter) {
      this.disconnectMessageObserver();
    }
    else if(!this.observingMessages && (chatSettings.coloredUsernames || chatSettings.superchatCurrencyConverter)) {
      this.connectMessageObserver();
    }
  }

  connectMessageObserver() {
    this.messageObserver.observe(this.messageContainer, { childList: true });
    this.observingMessages = true;
  }

  disconnectMessageObserver() {
    this.messageObserver.disconnect();
    this.observingMessages = false;
  }

  applyUsernameColors() {
    if(chatSettings.coloredUsernames) {
      this.messageContainer.querySelectorAll('yt-live-chat-text-message-renderer').forEach((element) => {
        this.applyUsernameColor(element);
      });
      if(this.authorChip) this.applyUsernameColor(this.authorChip);
    }
    else {
      this.messageContainer.querySelectorAll('[data-ytlstm-username-color]').forEach((element) => {
        element.removeAttribute('data-ytlstm-username-color');
      });
      if(this.authorChip) this.authorChip.querySelector('#author-name').removeAttribute('data-ytlstm-username-color');
    }
  }

  applyUsernameColor(element) {
    var username = element.querySelector('#author-name');
    if(username) username.setAttribute('data-ytlstm-username-color', chatSettings.getUsernameColor(username.textContent));
  }

  applyHidePfp() {
    if(chatSettings.hidePfp) {
      document.body.setAttribute('data-ytlstm-hide-pfp', '');
    }
    else {
      document.body.removeAttribute('data-ytlstm-hide-pfp');
    }
  }

  applyHideLiveReactions() {
    if(chatSettings.hideLiveReactions) {
      document.body.setAttribute('data-ytlstm-hide-live-reactions', '');
    }
    else {
      document.body.removeAttribute('data-ytlstm-hide-live-reactions');
    }
  }

}
