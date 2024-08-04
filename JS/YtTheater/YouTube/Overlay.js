
class Overlay {

  place() {
    document.body.style.setProperty('--ytlstm-overlay-width', (window.innerWidth - chatIframe.container.clientWidth) + 'px');
    document.body.style.setProperty('--ytlstm-overlay-max-height', (youtube.playerTheaterContainer.clientHeight - video.controls.clientHeight - 10) + 'px');
  }

  applyTextShadow() {
    if(theaterMode.active && settings.overlayTextShadow) {
      this.setAttribute('data-ytlstm-overlay-text-shadow', '');
    }
    else {
      this.removeAttribute('data-ytlstm-overlay-text-shadow');
    }
  }

  setAttribute(name, value) {
    document.body.setAttribute(name, value);
    if(chatIframe && chatIframe.document) chatIframe.document.querySelector('html').setAttribute(name, value);
  }

  removeAttribute(name) {
    document.body.removeAttribute(name);
    if(chatIframe && chatIframe.document) chatIframe.document.querySelector('html').removeAttribute(name);
  }

}
