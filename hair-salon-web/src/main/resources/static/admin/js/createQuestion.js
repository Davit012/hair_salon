let singleEndIndex;
let multiEndIndex;
let textAreaId = 4;

function createNewAnswers(type, tagId) {
    let inputForRadio = document.createElement("input");
    let inputForText = document.createElement("input");
    let mainDiv = document.createElement("div");
    let innerDiv = document.createElement("div");
    let inlineDiv = document.createElement("div");
    let divForImg = document.createElement("div");
    let img = document.createElement("img");
    let pTag = document.createElement("p");
    inlineDiv.className = 'col-sm-10 d-inline-block mx-1';
    divForImg.style.display = 'inline'
    img.src = "/teacher/img/icons/trash.svg";
    img.className = "deleteSingleAnswer";
    inputForText.type = 'text';
    innerDiv.className = "check_right_answer";
    pTag.textContent = " Right Answer";
    pTag.style.display = "inline";
    if (type == 'radio') {
        singleEndIndex = document.getElementsByName("singleRightAnswer").length;
        inputForText.className = 'single_answer_text form-control';
        inputForText.name = 'singleAnswers[]';
        inputForText.type = 'text';
        inputForRadio.value = singleEndIndex;
        inputForRadio.name = 'singleRightAnswer';
        mainDiv.className = "col-sm-12 single_answer_row";
        inputForRadio.className = 'check_input';
    } else {
        multiEndIndex = document.getElementsByName("multiRightAnswers[]").length;
        inputForText.className = 'multi_answer_text form-control';
        inputForText.name = 'multiAnswers[]';
        inputForText.type = 'text';
        inputForRadio.value = multiEndIndex;
        inputForRadio.name = 'multiRightAnswers[]';
        mainDiv.className = "col-sm-12 multi_answer_row";
        inputForRadio.className = 'checkbox_input';
    }

    divForImg.appendChild(img)
    divForImg.appendChild(inlineDiv)
    inputForRadio.type = type;
    inputForText.id = 'textarea_' + textAreaId;
    mainDiv.style.border = "none";
    textAreaId++;
    inlineDiv.appendChild(inputForText)
    mainDiv.appendChild(divForImg);
    innerDiv.appendChild(inputForRadio);
    innerDiv.appendChild(pTag);
    mainDiv.appendChild(innerDiv)
    document.getElementById(tagId).appendChild(mainDiv);
    img.addEventListener("click", function () {
        deleteAnswer(img);
        return null;
    });
}
