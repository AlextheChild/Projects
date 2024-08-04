class ChatIframe {

  isReady() {
    return (this.element && this.document && this.html && this.document.body && this.document.body.childElementCount > 0 && this.element.contentDocument && this.element.contentDocument.readyState === 'complete' && this.element.contentWindow.location.pathname != 'blank');
  }

  get exists() {
    return (this.element && this.document && this.html);
  }

  constructor() {
  }

  init() {
    this.putInTheaterMode = false;
    this.isInTheaterMode = false;
    this.container = document.getElementById('chat');
    this.element = document.getElementById('chatframe');
    if (this.element) {
      this.element.addEventListener('load', () => {
        this.initDocument();
      });
      if (this.element.contentDocument.readyState === 'complete') this.initDocument();
    }
  }

  initDocument() {
    this.document = this.element.contentDocument;
    this.html = this.document.querySelector('html');
    if (this.isReady()) {
      this.initContent();
    }
    else {
      setTimeout(this.initDocument.bind(this), 100);
    }
  }

  initContent() {
    this.setCssVariables();
    this.activatePopoutChatObserver();
    if (!theaterMode.chatOnRight) this.putOnLeft();
    if (theaterMode.chatOverVideo) this.putOverVideo();
    theaterMode.autoEnterTheaterMode();
    if (!this.isInTheaterMode && this.putInTheaterMode) {
      this.enterTheaterMode();
      video.resize();
    }
  }

  activatePopoutChatObserver() {
    let ninjaMessageRenderer = this.document.querySelector('yt-live-chat-ninja-message-renderer');
    if (ninjaMessageRenderer) {
      youtube.popoutChatObserver.observe(ninjaMessageRenderer, { attributeFilter: ["class"] });
    }
    else {
      setTimeout(this.activatePopoutChatObserver.bind(this), 100);
    }
  }

  place() {
    if (theaterMode.active && theaterMode.chatOverVideo) {
      if (video.controls.clientHeight == 0) {
        setTimeout(this.place.bind(this), 100);
      }
      else {
        let chatContainerHeight = youtube.playerTheaterContainer.clientHeight - video.controls.clientHeight - 10;
        let chatHeight = Math.max(350, chatContainerHeight - settings.chatOverVideoMarginTop - settings.chatOverVideoMarginBottom);
        let maxMargin = chatContainerHeight - 350;
        let marginTop = Math.min(maxMargin, settings.chatOverVideoMarginTop);
        if (settings.displayYoutubeHeader && !document.body.hasAttribute('data-ytlstm-new-layout')) {
          this.container.style.setProperty('margin-top', 'calc(' + marginTop + 'px + var(--ytd-toolbar-height))', 'important');
        }
        else {
          this.container.style.setProperty('margin-top', marginTop + 'px', 'important');
        }
        this.container.style.setProperty('height', chatHeight + 'px', 'important');
        this.container.style.setProperty('min-height', '350px', 'important');
      }
    }
    else {
      this.container.style.height = '';
      this.container.style.minHeight = '';
      this.container.style.marginTop = '';
    }
  }

  setWidth() {
    let width = theaterMode.activeChatWidth;
    document.body.style.setProperty('--ytlstm-chat-width', width + 'px');
    if (this.exists) {
      this.html.style.setProperty('--ytlstm-max-chat-over-video-item-width', (width - 20) + 'px');
    }
    if (theaterMode.active) {
      if (!theaterMode.chatOverVideo) {
        video.resize();
      }
      else {
        overlay.place();
      }
    }
  }

  enterTheaterMode() {
    if (!this.isInTheaterMode && this.isReady()) {
      this.isInTheaterMode = true;
      this.setWidth();
      this.applyTheaterModeStyle();
      if (!theaterMode.chatOnRight) this.putOnLeft();
      if (theaterMode.chatOverVideo) this.putOverVideo();
      if (settings.forceDarkTheme) this.applyDarkTheme();
      this.putInTheaterMode = false;
    }
    else {
      this.putInTheaterMode = true;
    }
  }

  leaveTheaterMode() {
    if (this.isInTheaterMode && this.isReady()) {
      this.isInTheaterMode = false;
      this.removeTheaterModeStyle();
      if (!theaterMode.chatOnRight) this.putOnRight();
      if (theaterMode.chatOverVideo) this.putNextToVideo();
      if (settings.forceDarkTheme) this.removeDarkTheme();
    }
    this.putInTheaterMode = false;
  }

  applyDarkTheme() {
    if (this.exists && !this.html.hasAttribute('dark')) {
      this.html.setAttribute('dark', 'ytlstm');
    }
  }

  removeDarkTheme() {
    if (this.exists && this.html.getAttribute('dark') == 'ytlstm') {
      this.html.removeAttribute('dark');
    }
  }

  applyTheaterModeStyle() {
    if (this.exists) {
      this.html.setAttribute('data-ytlstm-theater-mode', '');
    }
  }

  removeTheaterModeStyle() {
    if (this.exists) {
      this.html.removeAttribute('data-ytlstm-theater-mode');
    }
  }

  show() {
    if (this.exists) {
      let button = this.document.querySelector('yt-live-chat-ninja-message-renderer.iron-selected tp-yt-paper-button#button');
      if (button) button.click();
    }
    document.body.removeAttribute('data-ytlstm-chat-hidden');
  }

  hide() {
    document.body.setAttribute('data-ytlstm-chat-hidden', '');
  }

  putOnLeft() {
    youtube.app.setAttribute('data-ytlstm-chat-on-left', '');
    if (this.exists) {
      this.html.setAttribute('data-ytlstm-chat-on-left', '');
      this.html.style.removeProperty('--ytlstm-text-direction');
    }
  }

  putOnRight() {
    youtube.app.removeAttribute('data-ytlstm-chat-on-left');
    if (this.exists) {
      this.html.removeAttribute('data-ytlstm-chat-on-left');
      this.html.style.setProperty('--ytlstm-text-direction', this.document.body.getAttribute('dir'));
    }
  }

  putOverVideo() {
    youtube.app.setAttribute('data-ytlstm-chat-over-video', '');
    this.setWidth();
    this.place();
    if (this.exists) {
      this.html.setAttribute('data-ytlstm-chat-over-video', '');
      if (theaterMode.chatOnRight) this.html.style.setProperty('--ytlstm-text-direction', this.document.body.getAttribute('dir'));
    }
  }

  putNextToVideo() {
    youtube.app.removeAttribute('data-ytlstm-chat-over-video');
    this.setWidth();
    this.place();
    if (this.exists) {
      this.html.removeAttribute('data-ytlstm-chat-over-video');
      this.html.style.removeProperty('--ytlstm-text-direction');
    }
  }

  setCssVariables() {
    if (this.exists) {
      this.html.style.setProperty('--ytlstm-overlay-background-opacity', settings.overlayBackgroundOpacity);
    }
  }

}