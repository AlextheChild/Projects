class YouTube {

  constructor() {

    this.html = document.querySelector('html');
    this.app = document.querySelector('ytd-app');
    this.chatIsAvailable = false;

    this.theaterObserver = new MutationObserver((mutations, observer) => {
      mutations.filter((mutation) => mutation.addedNodes.length > 0).forEach((mutation) => {
        for (let i = 0; i < mutation.addedNodes.length; i++) {
          if (mutation.addedNodes[i].id === 'player-container') {
            this.activePlayer = this.playerTheaterContainer;
          }
        }
      });
      mutations.filter((mutation) => mutation.removedNodes.length > 0).forEach((mutation) => {
        for (let i = 0; i < mutation.removedNodes.length; i++) {
          if (mutation.removedNodes[i].id === 'player-container') {
            this.activePlayer = this.playerContainer;
          }
        }
      });
    });

    this.chatObserver = new MutationObserver((mutations, observer) => {
      mutations.filter((mutation) => mutation.addedNodes.length > 0).forEach((mutation) => {
        for (let i = 0; i < mutation.addedNodes.length; i++) {
          if (mutation.addedNodes[i].id === 'chat') {
            observer.disconnect();
            this.chatIsAvailable = true;
            if (!this.activePlayer) {
              this.init();
            }
            else {
              theaterMode.makeAvailable();
              this.activateChatRemovalObserver();
            }
          }
        }
      });
    });

    this.chatRemovalObserver = new MutationObserver((mutations, observer) => {
      mutations.forEach((mutation) => {
        for (let i = 0; i < mutation.removedNodes.length; i++) {
          if (mutation.removedNodes[i].id === 'chat') {
            this.chatIsAvailable = false;
            theaterMode.makeUnavailable();
            observer.disconnect();
            this.popoutChatObserver.disconnect();
            this.activateChatObserver();
          }
        }
      });
    });

    this.popoutChatObserver = new MutationObserver((mutations, observer) => {
      if (mutations[0].target.classList.contains('iron-selected') == theaterMode.showChat) {
        theaterMode.toggleChat();
      }
    });

    this.overlayAutoHideObserver = new MutationObserver((mutations, observer) => {
      mutations.forEach((mutation) => {
        if (mutation.type == 'attributes' && mutation.attributeName == 'class') {
          if (document.querySelector('.html5-video-player').classList.contains('ytp-autohide')) {
            youtube.app.setAttribute('data-ytlstm-autohide', '');
            video.infoIcon.icon.setAttribute('data-ytlstm-autohide', '');
          }
          else {
            youtube.app.removeAttribute('data-ytlstm-autohide');
            video.infoIcon.icon.removeAttribute('data-ytlstm-autohide');
          }
        }
      });
    });

    this.clipObserver = new MutationObserver((mutations, observer) => {
      if (mutations[0].target.getAttribute('visibility') === 'ENGAGEMENT_PANEL_VISIBILITY_EXPANDED' && theaterMode.active) theaterMode.deactivate();
    });

    this.resizeObserver = new ResizeObserver((entries) => overlay.place());

  }

  activateChatObserver() {
    this.chatObserver.observe(this.secondaryInner, { childList: true, subtree: true });
  }

  activateChatRemovalObserver() {
    this.chatRemovalObserver.observe(this.secondaryInner, { childList: true, subtree: true });
  }

  init() {
    if (this.initTimeout) clearTimeout(this.initTimeout);
    this.primaryInner = document.getElementById('primary-inner');
    this.secondaryInner = document.getElementById('secondary-inner');
    this.playerContainer = document.getElementById('player');
    this.playerTheaterContainer = document.getElementById('player-full-bleed-container') || document.getElementById('player-wide-container') || document.getElementById('player-theater-container');
    if (this.primaryInner && this.secondaryInner && this.playerContainer && this.playerTheaterContainer) {
      this.theaterObserver.observe(this.playerTheaterContainer, { childList: true });
      this.activePlayer = (this.playerTheaterContainer.querySelector('#player-container') != null) ? this.playerTheaterContainer : this.playerContainer;
      this.clipContainer = document.querySelector('ytd-engagement-panel-section-list-renderer[target-id=engagement-panel-clip-create]');
      if (document.getElementById('chat')) {
        this.chatIsAvailable = true;
        theaterMode.makeAvailable();
        this.activateChatRemovalObserver();
      }
      else {
        this.activateChatObserver();
      }
      let commentsButton = document.querySelector('ytd-comments-entry-point-header-renderer');
      if (commentsButton) commentsButton.addEventListener('click', () => {
        theaterMode.deactivate();
      });
    }
    else {
      this.initTimeout = setTimeout(this.init.bind(this), 300);
    }
  }

  removeDarkTheme() {
    if (this.html.getAttribute('dark') == 'ytlstm') {
      this.html.removeAttribute('dark');
      if (this.activePlayer === this.playerContainer) {
        let masthead = document.getElementById('masthead');
        if (masthead) masthead.removeAttribute('dark');
      }
    }
  }

  applyDarkTheme() {
    if (!this.html.hasAttribute('dark')) {
      this.html.setAttribute('dark', 'ytlstm');
    }
  }

}
