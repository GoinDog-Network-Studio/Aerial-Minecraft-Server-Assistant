var indexOf = window.location.search.indexOf("verify_failed");
if (indexOf !== -1) {
    var text_areas = document.getElementsByTagName("md-outlined-text-field");
    console.log(text_areas);
    for (let i = 0; i < text_areas.length; i++) {
        var element = text_areas[i];
        element.setAttribute("error", true);
        element.setAttribute("error-text", "输入信息有误")
    }
}