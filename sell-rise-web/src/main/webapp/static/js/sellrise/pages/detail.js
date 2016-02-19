$(function () {
    try {
        console.log("detail.js init");
        loadImageInfos();
        $('#tbody-detail [name="show-img"]').lightBox();
    } catch (err) {
        console.log(err);
    }
});