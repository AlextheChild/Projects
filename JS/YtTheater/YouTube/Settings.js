class Settings {

  constructor() {
    this.colorRandomNumber = Math.floor(Math.random() * 8);
    chrome.storage.onChanged.addListener(this.storageChange.bind(this));
    chrome.storage.local.get(['forceDarkTheme', 'displayYoutubeHeader', 'chatWidth', 'chatOverVideoWidth', 'chatOverVideoMarginTop', 'chatOverVideoMarginBottom', 'showInfoOnVideoHover', 'preventPausingOnClick', 'autoEnterTheaterMode', 'autoEnterTheaterModeReplay', 'toggleShortcut', 'overlayBackgroundOpacity', 'overlayTextShadow', 'showChatOnRight', 'showChatOverVideo', 'notLiveHint'], (data) => {
      this.forceDarkTheme = (data.forceDarkTheme === undefined) ? true : data.forceDarkTheme;
      this.displayYoutubeHeader = (data.displayYoutubeHeader === undefined) ? false : data.displayYoutubeHeader;
      this.overlayTextShadow = (data.overlayTextShadow === undefined) ? true : data.overlayTextShadow;
      this.chatWidth = data.chatWidth || 400;
      this.chatOverVideoWidth = data.chatOverVideoWidth || 300;
      this.chatOverVideoMarginTop = data.chatOverVideoMarginTop || 0;
      this.chatOverVideoMarginBottom = data.chatOverVideoMarginBottom || 0;
      let showInfoValue;
      switch (data.showInfoOnVideoHover) {
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
      this.showInfoOnVideoHover = showInfoValue;
      this.preventPausingOnClick = (data.preventPausingOnClick === undefined) ? false : data.preventPausingOnClick;
      this.autoEnterTheaterMode = (data.autoEnterTheaterMode === undefined) ? false : data.autoEnterTheaterMode;
      this.autoEnterTheaterModeReplay = (data.autoEnterTheaterModeReplay === undefined) ? false : data.autoEnterTheaterModeReplay;
      this.toggleShortcut = (data.toggleShortcut === undefined) ? "t" : data.toggleShortcut;
      this.overlayBackgroundOpacity = data.overlayBackgroundOpacity || 0.8;
      theaterMode.chatOnRight = (data.showChatOnRight === undefined) ? true : data.showChatOnRight;
      theaterMode.chatOverVideo = (data.showChatOverVideo === undefined) ? false : data.showChatOverVideo;
      this.notLiveHint = (data.notLiveHint === undefined) ? false : data.notLiveHint;
      theaterMode.setCssVariables();
    });
  }

  storageChange(changes, area) {
    if (area == 'local') {
      let changedItems = Object.keys(changes);
      for (let item of changedItems) {
        switch (item) {
          case 'forceDarkTheme': this.setForceDarkTheme(changes[item].newValue); break;
          case 'displayYoutubeHeader':
            this.displayYoutubeHeader = changes[item].newValue;
            theaterMode.applyDisplayYouTubeHeader();
            if (theaterMode.active && theaterMode.chatOverVideo) chatIframe.place();
            break;
          case 'overlayTextShadow': this.overlayTextShadow = changes[item].newValue; overlay.applyTextShadow(); break;
          case 'chatWidth': this.chatWidth = changes[item].newValue; chatIframe.setWidth(); break;
          case 'chatOverVideoWidth': this.chatOverVideoWidth = changes[item].newValue; chatIframe.setWidth(); break;
          case 'chatOverVideoMarginTop': this.chatOverVideoMarginTop = changes[item].newValue; chatIframe.place(); break;
          case 'chatOverVideoMarginBottom': this.chatOverVideoMarginBottom = changes[item].newValue; chatIframe.place(); break;
          case 'showInfoOnVideoHover': this.showInfoOnVideoHover = changes[item].newValue; video.applyInfoHoverMode(); break;
          case 'preventPausingOnClick':
            this.preventPausingOnClick = changes[item].newValue;
            theaterMode.applyPreventPausingOnClick();
            break;
          case 'autoEnterTheaterMode': this.autoEnterTheaterMode = changes[item].newValue; break;
          case 'autoEnterTheaterModeReplay': this.autoEnterTheaterModeReplay = changes[item].newValue; break;
          case 'toggleShortcut': this.toggleShortcut = changes[item].newValue; break;
          case 'overlayBackgroundOpacity':
            this.overlayBackgroundOpacity = changes[item].newValue;
            chatIframe.setCssVariables();
            theaterMode.setCssVariables();
            break;
          case 'notLiveHint':
            this.notLiveHint = changes[item].newValue;
            video.updateNotLiveHint();
            break;
        }
      }
    }
  }

  setForceDarkTheme(value) {
    this.forceDarkTheme = value;
    if (theaterMode.active) {
      if (this.forceDarkTheme) {
        youtube.applyDarkTheme();
        chatIframe.applyDarkTheme();
      }
      else {
        youtube.removeDarkTheme();
        chatIframe.removeDarkTheme();
      }
    }
  }

}
