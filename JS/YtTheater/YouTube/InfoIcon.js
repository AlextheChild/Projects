class InfoIcon {
  constructor() {
    this.icon = document.createElement('div');
    this.icon.textContent = 'i';
    this.icon.id = 'ytlstm-streamInfoHoverIcon';
  }

  add() {
    youtube.playerTheaterContainer.appendChild(this.icon);
    this.icon.addEventListener('mouseenter', video.onMouseEnterInfo);
    youtube.primaryInner.addEventListener('mouseleave', video.onMouseLeaveInfo);
  }

  remove() {
    if (this.icon.parentNode) this.icon.parentNode.removeChild(this.icon);
    this.icon.removeEventListener('mouseenter', video.onMouseEnterInfo);
    youtube.primaryInner.removeEventListener('mouseleave', video.onMouseLeaveInfo);
  }
}