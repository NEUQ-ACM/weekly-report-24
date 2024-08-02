const videoData = [
  { id: 1, title: '谁不爱周杰伦', description: '可爱女人mv', imagesrc: 'images/image1.jpg', src: 'videos/video1.mp4' },
  { id: 2, title: '周杰伦i love you', description: '周杰伦音乐mv', imagesrc: 'images/image2.jpg', src: 'videos/video1.mp4' },
  { id: 3, title: '可爱女人', description: '这是一个mv', imagesrc: 'images/image3.jpg', src: 'videos/video1.mp4' }
];

// 显示视频缩略图
function displayVideos() {
  const videoContainer = document.getElementById('videos');
  // 遍历视频数据，为每个视频创建缩略图
  videoData.forEach(video => {
    const videoElement = document.createElement('div');
    videoElement.classList.add('video-thumbnail');
    videoElement.innerHTML = `
            <img src="${video.imagesrc}" alt="${video.title}">
            <div class="video-info">
                <h3 class="video-title">${video.title}</h3>
                <p>${video.description}</p>
            </div>
        `;
    // 添加点击事件，打开对应视频的模态框
    videoElement.addEventListener('click', () => openModal(video));
    videoContainer.appendChild(videoElement);// 将视频缩略图添加到容器中
  });
}
// 函数用于打开视频模态框
function openModal(video) {
  const modal = document.getElementById('modal');// 获取模态框元素
  const videoPlayer = document.getElementById('videoPlayer');// 获取视频播放器元素
  const videoSource = document.getElementById('videoSource');// 获取视频源元素
  const closeModal = document.getElementsByClassName('close')[0];// 获取关闭按钮元素

  videoSource.src = video.src;// 设置视频源
  videoPlayer.load();// 加载视频
  modal.style.display = 'block';// 显示模态框

  // 点击关闭按钮时关闭模态框并暂停视频播放
  closeModal.onclick = function () {
    modal.style.display = 'none';
    videoPlayer.pause();
  };

  // 点击模态框外部时关闭模态框并暂停视频播放
  window.onclick = function (event) {
    if (event.target === modal) {
      modal.style.display = 'none';
      videoPlayer.pause();
    }
  };
}


// 页面加载完成后显示视频缩略图
window.onload = function () {
  displayVideos();
};
