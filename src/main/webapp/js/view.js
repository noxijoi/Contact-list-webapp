function render(name, data){
    var elementTo = document.getElementById(name)
    var templateElement = document.getElementById(name + "Template");
    var templateSource = templateElement.innerHTML;
    var html = Mustache.to_html(templateSource, data);
    elementTo.innerHTML = html;
}