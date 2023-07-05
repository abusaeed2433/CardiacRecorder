

window.onload = function () {
    var zoomContainer = document.getElementById('zoom-container');
    var zoomImage = document.getElementById('zoom-image');
    var scale = 1;
    var isDragging = false;
    var lastX = 0;
    var lastY = 0;
    var imageOffsetX = 0;
    var imageOffsetY = 0;
    var currentTranslateX = 0;
    var currentTranslateY = 0;

    zoomContainer.addEventListener('wheel', function (event) {
        event.preventDefault();

        var zoomPointX = event.clientX - zoomContainer.offsetLeft;
        var zoomPointY = event.clientY - zoomContainer.offsetTop;

        var zoomIn = event.deltaY < 0;

        if (zoomIn) {
            scale += 0.13;
        } else {
            scale -= 0.13;
        }

        if(scale < 1) scale = 1;

        zoomImage.style.transformOrigin = zoomPointX + 'px ' + zoomPointY + 'px';
        zoomImage.style.transform = 'scale(' + scale + ')';
        zoomImage.style.transition = 'transform 0.2s ease-out';
    });

    zoomContainer.addEventListener('mousedown', function (event) {
        event.preventDefault();
        isDragging = true;
        lastX = event.clientX;
        lastY = event.clientY;
        imageOffsetX = currentTranslateX;
        imageOffsetY = currentTranslateY;
    });

    zoomContainer.addEventListener('mousemove', function (event) {
        if (isDragging) {
            var deltaX = event.clientX - lastX;
            var deltaY = event.clientY - lastY;

            deltaX /= 3;
            deltaY /= 3;

            currentTranslateX = imageOffsetX + deltaX;
            currentTranslateY = imageOffsetY + deltaY;

            zoomImage.style.transform = 'scale(' + scale + ') translate(' + currentTranslateX + 'px, ' + currentTranslateY + 'px)';
        }
    });

    zoomContainer.addEventListener('mouseup', function (event) {
        event.preventDefault();
        isDragging = false;
    });

    zoomContainer.addEventListener('mouseleave', function (event) {
        event.preventDefault();
        isDragging = false;
    });

}
