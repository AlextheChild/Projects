

class Video {

  constructor() {
    this.toggleLivestreamsTheaterMode = VideoControlsButton.get('livestreamsTheaterMode', 'Toggle Livestreams Theater Mode', "<svg viewBox='0 0 200 200'><rect x='20' y='50' width='100' height='100'/><rect x='140' y='50' width='40' height='100'/></svg>");
    this.toggleChatSide = VideoControlsButton.get('toggleChatSide', 'Toggle Chat Side', "<svg viewBox='0 0 200 200'><rect x='20' y='50' width='100' height='100'/><rect x='140' y='50' width='40' height='100'/><line x1='70' x2='160' y1='100' y2='100'/><polygon points='80,80 80,120 60,100'/><polygon points='150,80 150,120 170,100'/></svg>");
    this.toggleChatOverVideo = VideoControlsButton.get('toggleChatOverVideo', 'Toggle Chat Over Video', "<svg viewBox='0 0 200 200'><rect x='20' y='50' width='160' height='100'/><line x1='125' x2='165' y1='70' y2='70'/><line x1='125' x2='155' y1='85' y2='85'/><line x1='125' x2='170' y1='100' y2='100'/><line x1='125' x2='140' y1='115' y2='115'/></svg>");
    this.toggleChat = VideoControlsButton.get('toggleChat', 'Toggle Chat', "<svg viewBox='0 0 200 200'><rect x='60' y='60' width='100' height='75' rx='15' ry='15'/><polygon points='46,150 60,125 70,135'/></svg>");
    this.toggleLivestreamsTheaterMode.onclick = () => { theaterMode.toggle(); };
    this.toggleChatSide.onclick = () => { theaterMode.toggleChatSide(); };
    this.toggleChatOverVideo.onclick = () => { theaterMode.toggleChatOverVideo(); };
    this.toggleChat.onclick = () => { theaterMode.toggleChat(); };
    this.setControls();
    this.infoIcon = new InfoIcon();
    this.notLiveHint = null;
    this.notLiveHintClosed = false;
    this.liveObserver = new MutationObserver((mutations, observer) => {
      if(settings.notLiveHint) {
        let videoPlayerContainer = document.querySelector('ytd-player .html5-video-player');
        if(videoPlayerContainer && !videoPlayerContainer.classList.contains('unstarted-mode') && !videoPlayerContainer.classList.contains('ended-mode') && !videoPlayerContainer.classList.contains('ad-interrupting')) {
          mutations.forEach((mutation) => {
            if(mutation.target.disabled || !mutation.target.parentElement.classList.contains('ytp-live')) {
              this.removeNotLiveHint();
            }
            else {
              this.addNotLiveHint();
            }
          });
        }
      }
      else {
        this.removeNotLiveHint();
      }
    });
    this.liveObserver2 = new MutationObserver((mutations, observer) => {
      if(settings.notLiveHint && !this.notLiveHintClosed) {
        let videoPlayerContainer = document.querySelector('ytd-player .html5-video-player');
        if(videoPlayerContainer && !videoPlayerContainer.classList.contains('unstarted-mode') && !videoPlayerContainer.classList.contains('ended-mode') && !videoPlayerContainer.classList.contains('ad-interrupting')) {
          mutations.forEach((mutation) => {
            let liveButton = mutation.target.querySelector('.ytp-live-badge');
            if((liveButton && liveButton.disabled) || !mutation.target.classList.contains('ytp-live')) {
              this.removeNotLiveHint();
            }
            else {
              this.addNotLiveHint();
            }
          });
        }
        else {
          this.removeNotLiveHint();
        }
      }
    });
    setTimeout(this.connectLiveObserver.bind(this), 500);
  }

  addTheaterModeOptions() {
    this.rightControls.appendChild(this.toggleLivestreamsTheaterMode);
    this.rightControls.insertBefore(this.toggleChatSide, this.rightControls.firstChild);
    this.rightControls.insertBefore(this.toggleChatOverVideo, this.rightControls.firstChild);
    this.rightControls.insertBefore(this.toggleChat, this.rightControls.firstChild);
  }

  removeTheaterModeOptions() {
    if(this.toggleLivestreamsTheaterMode.parentNode) this.toggleLivestreamsTheaterMode.parentNode.removeChild(this.toggleLivestreamsTheaterMode);
    if(this.toggleChatSide.parentNode) this.toggleChatSide.parentNode.removeChild(this.toggleChatSide);
    if(this.toggleChatOverVideo.parentNode) this.toggleChatOverVideo.parentNode.removeChild(this.toggleChatOverVideo);
    if(this.toggleChat.parentNode) this.toggleChat.parentNode.removeChild(this.toggleChat);
  }

  enterTheaterMode(leaveTheaterModeFunction) {
    if(theaterMode.active) {
      if(this.miniplayerButton && this.sizeButton && this.fullscreenButton) {
        this.miniplayerButton.addEventListener('click', leaveTheaterModeFunction);
        this.sizeButton.addEventListener('click', leaveTheaterModeFunction);
        if(document.querySelector('.html5-video-player.ytp-fullscreen')) this.fullscreenButton.click();
        this.fullscreenButton.addEventListener('click', leaveTheaterModeFunction);
      }
      else {
        setTimeout(() => {
          this.enterTheaterMode(leaveTheaterModeFunction);
        }, 300);
      }
    }
  }

  leaveTheaterMode(leaveTheaterModeFunction) {
    if(!theaterMode.active) {
      if(this.miniplayerButton && this.sizeButton && this.fullscreenButton) {
      this.miniplayerButton.removeEventListener('click', leaveTheaterModeFunction);
      this.sizeButton.removeEventListener('click', leaveTheaterModeFunction);
      this.fullscreenButton.removeEventListener('click', leaveTheaterModeFunction);
      }
    }
  }

  resize() {
    window.dispatchEvent(new Event('resize'));
  }

  setControls() {
    this.controls = document.querySelector('#movie_player .ytp-chrome-bottom');
    if(this.controls) {
      this.rightControls = this.controls.querySelector('.ytp-right-controls');
      this.miniplayerButton = this.controls.querySelector('.ytp-miniplayer-button');
      this.sizeButton = this.controls.querySelector('.ytp-size-button');
      this.fullscreenButton = this.controls.querySelector('.ytp-fullscreen-button');
      if(this.rightControls && this.miniplayerButton && this.sizeButton && this.fullscreenButton) {
        this.addTheaterModeOptions();
      }
      else {
        setTimeout(() => {
          this.setControls();
        }, 300);
      }
    }
    else {
      setTimeout(() => {
        this.setControls();
      }, 300);
    }
  }

  onMouseEnterInfo() {
    youtube.app.setAttribute('data-ytlstm-show-info', '');
  }

  onMouseLeaveInfo() {
    youtube.app.removeAttribute('data-ytlstm-show-info');
  }

  applyInfoHoverMode() {
    if(theaterMode.active) {
      switch(settings.showInfoOnVideoHover) {
        case 'video':
          document.body.removeAttribute('data-ytlstm-info-hover-mode');
          this.addInfoEventListeners();
          this.infoIcon.remove();
          break;
        case 'icon':
          document.body.setAttribute('data-ytlstm-info-hover-mode', 'icon');
          this.removeInfoEventListeners();
          this.infoIcon.add();
          break;
        case 'disabled':
          document.body.setAttribute('data-ytlstm-info-hover-mode', 'disabled');
          this.removeInfoEventListeners();
          this.infoIcon.add();
          break;
      }
    }
  }

  clear() {
    this.removeInfoEventListeners();
    this.infoIcon.remove();
  }


  addInfoEventListeners() {
    youtube.playerTheaterContainer.addEventListener('mouseover', this.onMouseEnterInfo);
    youtube.playerTheaterContainer.addEventListener('mouseout', this.onMouseLeaveInfo);
  }

  removeInfoEventListeners() {
    youtube.playerTheaterContainer.removeEventListener('mouseover', this.onMouseEnterInfo);
    youtube.playerTheaterContainer.removeEventListener('mouseout', this.onMouseLeaveInfo);
  }

  connectLiveObserver() {
    this.liveObserver.observe(document.querySelector('#ytd-player .ytp-left-controls .ytp-live-badge'), { attributeFilter: [ 'disabled'] });
    this.liveObserver2.observe(document.querySelector('#ytd-player .ytp-left-controls .ytp-time-display'), { attributeFilter: [ 'class' ] });
  }

  disconnectLiveObserver() {
    this.liveObserver.disconnect();
    this.liveObserver2.disconnect();
  }

  updateNotLiveHint() {
    if(!document.querySelector('#ytd-player .ytp-left-controls .ytp-live-badge').disabled) {
      if(settings.notLiveHint) {
        let videoPlayerContainer = document.querySelector('ytd-player .html5-video-player');
        if(videoPlayerContainer && !videoPlayerContainer.classList.contains('unstarted-mode') && !videoPlayerContainer.classList.contains('ended-mode') && !videoPlayerContainer.classList.contains('ad-interrupting')) {
          this.addNotLiveHint();
        }
      }
      else {
        this.removeNotLiveHint();
      }
    }
  }

  addNotLiveHint() {
    let button = document.querySelector('#ytd-player .ytp-left-controls .ytp-live-badge');
    if(!this.notLiveHint && button && theaterMode.available && window.getComputedStyle(button).display != 'none') {
      this.notLiveHintClosed = false;
      this.notLiveHint = document.createElement('div');
      this.notLiveHint.id = 'ytlstm-notLiveHint';
      this.notLiveHint.textContent = 'video is not live';
      this.notLiveHint.title = 'close';
      this.notLiveHint.onclick = () => {
        this.removeNotLiveHint();
        this.notLiveHintClosed = true;
      }
      document.getElementById('ytd-player').appendChild(this.notLiveHint);
    }
  }

  removeNotLiveHint() {
    if(this.notLiveHint) {
      if(this.notLiveHint.parentNode) this.notLiveHint.parentNode.removeChild(this.notLiveHint);
      this.notLiveHint = null;
    }
    let w = document.getElementById('ytlstm-notLiveHint');
    if(w) w.parentNode.removeChild(w);
  }

}

class VideoControlsButton {

  static get(id, title, svg) {
    let button = document.createElement('button');
    button.id = id;
    button.title = title;
    button.innerHTML = svg;
    button.classList.add('ytp-button');
    return button;
  }

}
