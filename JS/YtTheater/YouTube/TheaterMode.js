class TheaterMode {

  constructor() {
    this.available = false;
    this.active = false;
    this.chatOnRight = true;
    this.chatOverVideo = false;
    this.showChat = true;
    this.boundDeactivate = this.deactivate.bind(this);
    this.boundShortcutEventListener = this.shortcutEventListener.bind(this);
  }

  makeAvailable() {
    console.log('make available');
    if (!this.available && youtube.chatIsAvailable) {
      this.available = true;
      chatIframe = new ChatIframe();
      chatIframe.init();
      video = new Video();
      //video.addTheaterModeOptions();
      document.addEventListener('keydown', this.boundShortcutEventListener);
    }
  }

  autoEnterTheaterMode() {
    if (this.available && !this.active && !document.body.hasAttribute('data-ytlstm-new-layout')) {
      switch (chatIframe.element.contentWindow.location.pathname) {
        case '/live_chat':
          if (settings.autoEnterTheaterMode) this.activate();
          break;
        case '/live_chat_replay':
          if (settings.autoEnterTheaterModeReplay) this.activate();
          break;
      }
    }
  }

  makeUnavailable() {
    console.log('make UNavailable');
    if (this.available) {
      this.available = false;
      document.removeEventListener('keydown', this.boundShortcutEventListener);
      if (this.active) this.deactivate();
      video.removeTheaterModeOptions();
      video = null;
      chatIframe = null;
    }
  }

  toggle() {
    if (!this.active) {
      this.activate();
    }
    else {
      this.deactivate();
    }
  }

  activate() {
    if (!this.active) {
      this.active = true;
      this.disableEFYT();
      overlay.applyTextShadow();
      if (youtube.activePlayer == youtube.playerContainer) video.sizeButton.click();
      setTimeout(() => {
        if (!document.querySelector('#secondary #chat-container')) {
          document.body.setAttribute('data-ytlstm-new-layout', '');
          video.resize();
          //chatIframe = new ChatIframe();
          //chatIframe.init();
        }
      }, 500);
      /*video.miniplayerButton.addEventListener('click', this.boundDeactivate);
      video.sizeButton.addEventListener('click', this.boundDeactivate);
      if(document.querySelector('.html5-video-player.ytp-fullscreen')) video.fullscreenButton.click();
      video.fullscreenButton.addEventListener('click', this.boundDeactivate);*/
      video.enterTheaterMode(this.boundDeactivate);
      if (youtube.clipContainer) youtube.clipObserver.observe(youtube.clipContainer, { attributeFilter: ["visibility"] });
      document.body.setAttribute('data-ytlstm-theater-mode', '');
      if (!this.showChat) chatIframe.hide();
      this.applyDisplayYouTubeHeader();
      this.applyPreventPausingOnClick();
      video.applyInfoHoverMode();
      let secondaryInfoRenderer = document.querySelector('ytd-expander.ytd-video-secondary-info-renderer');
      if (secondaryInfoRenderer && !secondaryInfoRenderer.hasAttribute('collapsed')) {
        let lessButton = document.querySelector('[slot="less-button"][role="button"].ytd-video-secondary-info-renderer');
        if (lessButton) lessButton.click();
      }
      youtube.resizeObserver.observe(youtube.playerTheaterContainer);
      youtube.resizeObserver.observe(video.controls);
      if (chatIframe.container.hasAttribute('collapsed')) {
        let button = document.querySelector('div#show-hide-button button');
        if (button) button.click();
      }
      chatIframe.enterTheaterMode();
      if (settings.forceDarkTheme) youtube.applyDarkTheme();
      youtube.overlayAutoHideObserver.observe(document.querySelector('.html5-video-player'), { attributes: true });
      video.resize();
    }
  }

  disableEFYT() {
    this.disabledEFYT = document.body.classList.contains('efyt-wide-player');
    if (this.disabledEFYT) document.body.classList.remove('efyt-wide-player');
  }

  reenableEFYT() {
    if (this.disabledEFYT) document.body.classList.add('efyt-wide-player');
  }

  applyDisplayYouTubeHeader() {
    if (this.active && settings.displayYoutubeHeader) {
      document.body.setAttribute('data-ytlstm-display-youtube-header', '');
    }
    else {
      document.body.removeAttribute('data-ytlstm-display-youtube-header');
    }
  }

  applyPreventPausingOnClick() {
    if (this.active && settings.preventPausingOnClick) {
      if (!this.preventPausingOnClickElement) {
        this.preventPausingOnClickElement = document.createElement('div');
        this.preventPausingOnClickElement.id = 'ytlstm-preventPausingOnClickElement';
        this.preventPausingOnClickElement.onclick = (event) => {
          event.stopPropagation();
        };
      }
      let parentNode = document.querySelector('.html5-video-player');
      if (parentNode) parentNode.appendChild(this.preventPausingOnClickElement);
    }
    else {
      if (this.preventPausingOnClickElement && this.preventPausingOnClickElement.parentNode) this.preventPausingOnClickElement.parentNode.removeChild(this.preventPausingOnClickElement);
    }
  }

  setCssVariables() {
    document.body.style.setProperty('--ytlstm-overlay-background-opacity', settings.overlayBackgroundOpacity);
  }

  deactivate() {
    if (this.active) {
      this.active = false;
      document.body.removeAttribute('data-ytlstm-theater-mode');
      if (!this.showChat) chatIframe.show();
      youtube.primaryInner.style.width = '';
      youtube.app.removeAttribute('data-ytlstm-showInfo');
      if (document.head.querySelector('#yltm-overlayMetaStyle')) document.head.removeChild(document.head.querySelector('#yltm-overlayMetaStyle'));
      /*video.miniplayerButton.removeEventListener('click', this.boundDeactivate);
      video.sizeButton.removeEventListener('click', this.boundDeactivate);
      video.fullscreenButton.removeEventListener('click', this.boundDeactivate);*/
      video.leaveTheaterMode(this.boundDeactivate);
      youtube.clipObserver.disconnect();
      youtube.resizeObserver.disconnect();
      video.clear();
      if (settings.forceDarkTheme) youtube.removeDarkTheme();
      chatIframe.leaveTheaterMode();
      overlay.applyTextShadow();
      this.applyDisplayYouTubeHeader();
      this.applyPreventPausingOnClick();
      youtube.overlayAutoHideObserver.disconnect();
      this.reenableEFYT();
      video.resize();
    }
  }

  toggleChatSide() {
    this.chatOnRight = !this.chatOnRight;
    if (this.chatOnRight) {
      chatIframe.putOnRight();
    }
    else {
      chatIframe.putOnLeft();
    }
  }

  toggleChatOverVideo() {
    this.chatOverVideo = !this.chatOverVideo;
    if (this.chatOverVideo) {
      chatIframe.putOverVideo();
    }
    else {
      chatIframe.putNextToVideo();
    }
    overlay.applyTextShadow();
    video.resize();
  }

  get activeChatWidth() {
    return (this.chatOverVideo) ? settings.chatOverVideoWidth : settings.chatWidth;
  }

  toggleChat() {
    this.showChat = !this.showChat;
    if (this.showChat) {
      chatIframe.show();
    }
    else {
      chatIframe.hide();
    }
    video.resize();
    overlay.place();
  }

  shortcutEventListener(event) {
    if (!event.ctrlKey && !event.metaKey && settings.toggleShortcut && event.key.toUpperCase() === settings.toggleShortcut.toUpperCase() &&
      (document.activeElement == null || (document.activeElement.tagName !== 'TEXTAREA' && document.activeElement.tagName !== 'INPUT' && document.activeElement.getAttribute("contenteditable") !== "true")) &&
      (document.querySelector('ytd-engagement-panel-section-list-renderer') == null || document.querySelector('ytd-engagement-panel-section-list-renderer').getAttribute('visibility') !== 'ENGAGEMENT_PANEL_VISIBILITY_EXPANDED')) {
      theaterMode.toggle();
      event.preventDefault();
      event.stopPropagation();
    }
    else if (this.active && (event.keyCode === 27 /*ESC*/ || (!event.ctrlKey && !event.metaKey && event.keyCode === 84 /*T*/) || (!event.ctrlKey && !event.metaKey && event.keyCode === 70 /*F*/))) {
      theaterMode.deactivate();
    }
  }

}
