jQuery(document).ready(function ($) {
    var mainHeader = $(".auto-hidden-header");

    //set scrolling variables
    var scrolling = false;
    var previousTop = 0;
    var scrollDelta = 10;
    var scrollOffset = 100;

    $(window).on('scroll', function () {
        if (!scrolling) {
            scrolling = true;
            (!window.requestAnimationFrame) ? setTimeout(autoHideHeader, 250) : requestAnimationFrame(autoHideHeader);
        }
    });

    function autoHideHeader() {
        var currentTop = $(window).scrollTop();
        if (previousTop - currentTop > scrollDelta) {
            //if scrolling up...
            mainHeader.removeClass('is-hidden');
        } else if (currentTop - previousTop > scrollDelta && currentTop > scrollOffset) {
            //if scrolling down...
            mainHeader.addClass('is-hidden');
        }
        previousTop = currentTop;
        scrolling = false;
    }
});