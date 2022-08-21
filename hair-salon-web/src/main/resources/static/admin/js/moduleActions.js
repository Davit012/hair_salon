var addModuleButtons = document.getElementsByClassName("addModule");
var inputsList = document.getElementById("inputsList");
var choosenModules = document.getElementById("choosen-modules");
for (const addModuleButton of addModuleButtons) {
    updateListeners(addModuleButton);
}

function deleteModule(element) {
    var id = element.getAttribute("value");
    var name = element.getAttribute("name");
    if (id === null) {
        id = element.value;
        name = element.name;
    }
    document.getElementById('input' + id).remove();
    document.getElementById(id).remove();
    element.remove();
    var td = document.createElement("td");
    var div = document.createElement("div");
    td.id = id;
    td.style = "color:#2C7BE5;cursor: pointer";
    td.className = "align-middle text-nowrap addModule"
    div.className = "ms-2"
    div.textContent = "Add"
    td.name = name
    td.appendChild(div);
    document.getElementById("module" + id).appendChild(td);
    updateListeners(td)
}

function updateListeners(addModuleButton) {
    addModuleButton.addEventListener("click", function () {
        var name = addModuleButton.getAttribute("name");
        if (name === null || name === undefined) {
            name = addModuleButton.name
        }
        var divForDelete = document.createElement('div');
        divForDelete.className = "ms-2";
        divForDelete.style = "color:#ff0000;cursor: pointer";
        divForDelete.textContent = 'x';
        divForDelete.setAttribute("onclick", "deleteModule(this);")
        divForDelete.setAttribute("name", name);
        divForDelete.value = addModuleButton.id;
        divForDelete.name = name;
        addModuleButton.remove()
        var input = document.createElement("input");
        var moduleName = document.createElement("p");
        var moduleRange = document.createElement("p");
        var img = document.createElement("img");
        var mainDiv = document.createElement("div");
        var innerDiv = document.createElement("div");
        mainDiv.id = addModuleButton.id;
        mainDiv.className = "col-sm-12";
        img.src = "/admin/img/icons/lines.png";
        input.type = "text";
        input.name = "modules[]";
        input.value = addModuleButton.id;
        input.id = 'input' + addModuleButton.id;
        input.style.display = "none";
        inputsList.appendChild(input);
        moduleName.textContent = name;
        moduleName.style.display = "inline-block";
        moduleName.style.marginLeft = "1%";
        moduleRange.textContent = "50 min";
        innerDiv.style.textAlign = "right";
        innerDiv.style.display = "inline-block";
        innerDiv.className = "col-sm-10";

        mainDiv.appendChild(img);
        innerDiv.appendChild(moduleRange);
        mainDiv.appendChild(moduleName);
        mainDiv.appendChild(innerDiv);
        inputsList.appendChild(input);
        choosenModules.appendChild(mainDiv);
        document.getElementById("forDelete" + addModuleButton.id).appendChild(divForDelete)
    })
}