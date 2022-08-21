let count = 0;
let lastFileIndex = 0;
let lastHomework = 0;
let foundButton;
let fileName;

var datalistInput = document.getElementById('quizName');
if (datalistInput === null) {
    datalistInput = document.getElementById('dbQuizName');
}
datalistInput.addEventListener('change', function () {
    var datalist = document.getElementById('quizzes');
    var id = datalist.options.namedItem(datalistInput.value).id;
    document.getElementById('quizIdForEditQuiz').value = id;
    document.getElementById('quizEditForm').submit();

});

let selectForQuestion = document.getElementById('select_answer_type');
let blocked = document.querySelectorAll('.blocked');
let lastQuestionIndex = 0;
selectForQuestion.addEventListener('change', function () {
    blocked[lastQuestionIndex].style.display = "none";
    let index = selectForQuestion.selectedIndex;
    blocked[index].style.display = "block";
    lastQuestionIndex = index;
    switch (index) {
        case 0:
            document.getElementsByClassName('newAnswerButton')[0].id = 'create_new_single_answer';
            document.getElementsByClassName('newAnswerButton')[0].setAttribute('onclick', 'createNewAnswers("radio", "single_choice_block");');
            break;
        case 1:
            document.getElementsByClassName('newAnswerButton')[0].id = 'create_new_multi_answer';
            document.getElementsByClassName('newAnswerButton')[0].setAttribute('onclick', 'createNewAnswers("checkbox", "multi_choice_block");')
            break;
    }
});


function PreviewImage() {
    var image = document.getElementById("selected_image");
    var forImage = document.getElementById("forImage");
    var oFReader = new FileReader();
    oFReader.readAsDataURL(document.getElementById("imageInput").files[0]);
    if (document.getElementById("imageInput").files[0].size >= 1048576) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Maximum size of image can be 1mb!'
        })
        document.getElementById("imageInput").value = null;
        return;
    }
    oFReader.onload = function (oFREvent) {
        image.src = oFREvent.target.result;
    };
    if (document.getElementById("imageIcon") !== undefined) {
        document.getElementById("imageIcon").style.display = "none";
        document.getElementById("inputForImage").className = "col-sm-9";
    }
    forImage.style.display = "block"
};

function getImage() {
    var imageBlock = document.getElementById("image_answer_block");
    var answerImage = document.createElement("div");
    var imageDiv = document.createElement("div");
    var zoomDiv = document.createElement("div");
    var span = document.createElement("span");
    var input = document.createElement("input");
    var rightAnswer = document.createElement("input");
    rightAnswer.type = "radio";
    rightAnswer.name = "imageRightAnswer"
    input.name = "images[]"
    input.type = "text"
    imageDiv.style.width = "250px"
    imageDiv.style.height = "200px"
    answerImage.style.width = "250px"
    answerImage.style.height = "200px"
    answerImage.style.display = "inline-block"
    answerImage.style.marginLeft = "10px"
    span.style.marginLeft = "97%"
    span.textContent = "×";
    span.style.cursor = "pointer";
    var oFReader = new FileReader();
    var file = document.getElementById("file").files[0];
    oFReader.readAsDataURL(file);
    if (file.size >= 1048576) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Maximum size of image can be 1mb!'
        })
        document.getElementById("imageInput").value = null;
        return;
    }
    oFReader.onload = function (oFREvent) {
        var img = oFREvent.target.result;
        rightAnswer.value = count.toString();
        input.value = "--" + file.name + "--" + img;
        input.style.display = "none";
        imageDiv.style.backgroundImage = "url('" + img + "')";
        imageDiv.style.backgroundSize = "250px";
        imageDiv.hover = "250px";
        zoomDiv.setAttribute("onclick", 'style = "transform:scale(3);"')
        zoomDiv.setAttribute("onmouseout", 'style = "transform:scale(1);"')
        imageDiv.style.backgroundRepeat = "no-repeat";
        imageDiv.style.display = "inline-block";
        span.id = count.toString();
        answerImage.appendChild(span)
        imageDiv.appendChild(rightAnswer)
        zoomDiv.appendChild(imageDiv);
        answerImage.appendChild(zoomDiv);
        answerImage.appendChild(input);
        imageBlock.appendChild(answerImage);
        count++
        span.addEventListener("click", function () {
            countForDelete = 0;
            var span = document.getElementById(countForDelete.toString());
            var parentElement = span.parentElement;
            parentElement.remove();
            count--;
        })
    };
}

let dropArea = document.querySelector(".drag-area"),
    dragText = dropArea.querySelector("header"),
    button = dropArea.querySelector("button"),
    input = dropArea.querySelector("input");
var fileInputElement = document.getElementById('file');
let file; //this is a global variable and we'll use it inside multiple functions

button.onclick = () => {
    input.click(); //if user click on the button then the input also clicked
}

//If user Drag File Over DropArea
dropArea.addEventListener("dragover", (event) => {
    event.preventDefault(); //preventing from default behaviour
    dropArea.classList.add("active");
});

dropArea.addEventListener("drop", (event) => {
    event.preventDefault();
    if (event.dataTransfer.files.length > 50) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Maximum Files Count for upload is 50'
        })
        return;
    }
    for (let i = 0; i < event.dataTransfer.files.length; i++) {
        file = event.dataTransfer.files[i];
        validateFiles();
    }
});

fileInputElement.addEventListener("change", (event) => {
    for (let i = 0; i < fileInputElement.files.length; i++) {
        file = fileInputElement.files[i];
        validateFiles();
    }
});

function showMultipartFiles(htmlElement, divId, collapseId, inputName, forDelete) {
    let fileReader = new FileReader();
    let createdTag = document.createElement(htmlElement);
    let fileURL;
    var input = document.createElement('input');
    if (htmlElement === "img") {
        fileReader.onload = (oFREvent) => {
            fileURL = fileReader.result;
            input.value = "--" + file.name + "--" + oFREvent.target.result;
            createdTag.src = fileURL;
            createdTag.className = "col-sm-12"
        }
    } else {
        fileReader.onload = (oFREvent) => {
            fileURL = fileReader.result;
            input.value = "--" + file.name + "--" + oFREvent.target.result;
            createdTag.src = fileURL;
            createdTag.id = 'videojs-playbackrate-adjuster-player_html5_api';
            createdTag.className = "col-sm-12 vjs-tech";
            createdTag.controls = true;
            createdTag.tabIndex = "-1";
            createdTag.setAttribute('controlsList', 'nodownload');
        }
    }
    let divForClone = document.getElementById(divId);
    $(divForClone).clone().appendTo('#forClone')
    var clonedDiv = document.getElementById('forClone').firstElementChild;
    var deleteButton;
    for (const child of clonedDiv.children) {
        deleteButton = findChildren(child, forDelete);
    }
    clonedDiv.style.display = 'inline-block';
    lastFileIndex++;
    deleteButton.style.cursor = "pointer";
    deleteButton.setAttribute('onclick', 'deleteFile(this);');
    deleteButton.id = "delete" + lastFileIndex;
    clonedDiv.id = ('fileDiv' + lastFileIndex)
    clonedDiv.appendChild(createdTag);
    fileReader.readAsDataURL(file);
    document.getElementById(collapseId).appendChild(clonedDiv);
    input.id = 'input' + lastFileIndex;
    input.name = inputName;
    input.type = 'text';
    document.getElementById('forInputs').appendChild(input)
}

function showFiles(fileForReading) {
    let fileReader = new FileReader();
    let fileNameTag = document.createElement('p');
    let fileURL;
    var inputForFiles = document.createElement('input');
    fileReader.onload = (oFREvent) => {
        fileURL = fileReader.result;
        inputForFiles.value = "--" + fileForReading.name + "--" + oFREvent.target.result;
        fileNameTag.className = "col-sm-12";
        let fileName = '';
        var fileSplittedParts = fileForReading.name.split('.');
        for (let i = 0; i < fileSplittedParts.length - 1; i++) {
            fileName += fileSplittedParts[i]
        }
        fileNameTag.textContent = fileName;
    }
    let divForClone = document.getElementById('innerDivForFiles');
    $(divForClone).clone().appendTo('#forClone')
    fileReader.readAsDataURL(fileForReading);
    var clonedDiv = document.getElementById('forClone').firstElementChild;
    var deleteButton;
    for (const child of clonedDiv.children) {
        deleteButton = findChildren(child, 'forOtherDelete');
    }
    lastFileIndex++;
    deleteButton.style.cursor = "pointer";
    deleteButton.setAttribute('onclick', 'deleteFile(this);');
    deleteButton.id = "delete" + lastFileIndex;
    clonedDiv.style.display = 'inline-block';
    clonedDiv.id = ('fileDiv' + lastFileIndex);
    clonedDiv.appendChild(fileNameTag);
    document.getElementById('collapse4').appendChild(clonedDiv);
    inputForFiles.name = 'files[]';
    inputForFiles.type = 'text';
    inputForFiles.id = 'input' + lastFileIndex;
    document.getElementById('forInputs').appendChild(inputForFiles)
}

function validateFiles() {
    let fileType = file.type;
    var uploadStatusTr = document.getElementById('showUploadStatus');
    var td = document.createElement('td');
    var mainDiv = document.createElement('div');
    var innerDivForText = document.createElement('div');
    var innerDivForStatus = document.createElement('div');
    var uploadStatus = document.createElement('img');
    var uploadStatusDiv = document.createElement('div');
    var br = document.createElement('br');
    innerDivForText.className = 'col-sm-9 mx-2';
    if (file.name.length > 40) {
        innerDivForText.textContent = file.name.substr(0, 35) + '...';
        innerDivForText.title = file.name;
    } else {
        innerDivForText.textContent = file.name;
    }
    mainDiv.style.display = 'flex';
    innerDivForText.style.textAlign = 'left';
    innerDivForStatus.className = 'col-sm-2 mx-2';
    innerDivForStatus.style.textAlign = 'right';
    td.className = 'align-middle text-nowrap col-sm-1'
    document.getElementById("uploadProcess").style.display = 'block';
    uploadStatusDiv.appendChild(uploadStatus);
    innerDivForStatus.appendChild(uploadStatusDiv);
    mainDiv.appendChild(innerDivForText);
    mainDiv.appendChild(innerDivForStatus);
    td.appendChild(mainDiv);
    uploadStatusTr.appendChild(td);
    uploadStatusTr.appendChild(br);
    if (file.size >= 1048576) {
        uploadStatus.src = '/teacher/img/icons/exclamation-lg.svg';
        innerDivForText.style.color = "red";
        uploadStatus.title = "file is too large, maximum size for file is 1mb (1048576 bytes)";
    } else {
        uploadStatus.src = '/teacher/img/icons/check-circle.svg';
        innerDivForText.style.color = "green";
        if (fileType.includes('image')) {
            showMultipartFiles("img", "innerDivForImages", "collapse2", "images[]", 'forImageDelete');
        } else if (fileType.includes('video')) {
            showMultipartFiles("video", "innerDivForVideos", "collapse0", "videos[]", 'forVideoDelete');
        } else {
            showFiles(file);
        }
    }
}

function saveTextFile() {
    isInputModalSubmited = true;
    var p = document.createElement('p');
    var name = document.getElementById('textFileName').value.trimStart();
    var text = document.getElementById('textAreaForTextFile').value.trimStart();
    if (name === '' || text === '') {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'One of places is empty!'
        })
        return;
    }
    p.textContent = name;
    p.title = text;
    let divForClone = document.getElementById('innerDivForText');
    $(divForClone).clone().appendTo('#forClone')
    var deleteButton;
    var editButton;
    var divForText = document.getElementById('forClone').firstElementChild;
    for (const child of divForText.children) {
        deleteButton = findChildren(child, 'forTextDelete');
        editButton = findChildren(child, 'forTextEdit');
    }
    lastFileIndex++;
    const deleteDiv = deleteButton.parentElement;
    deleteDiv.parentElement.style.textAlign = 'right'
    deleteButton.style.cursor = "pointer";
    editButton.parentElement.style.display = "inline-block";
    editButton.style.cursor = "pointer";
    deleteDiv.style.display = "inline-block";
    deleteButton.setAttribute('onclick', 'deleteFile(this);');
    editButton.setAttribute('onclick', 'editTextFile(this,"text");');
    deleteButton.id = "delete" + lastFileIndex;
    editButton.id = "editText" + lastFileIndex;
    divForText.style.display = 'inline-block';
    divForText.id = ('fileDiv' + lastFileIndex);
    divForText.appendChild(p);
    document.getElementById('collapse1').appendChild(divForText);
    hideTextInput();
    var inputForTextName = document.createElement('input');
    var inputForText = document.createElement('input');
    inputForTextName.type = 'text';
    inputForTextName.name = 'textNames[]';
    inputForTextName.id = 'input' + lastFileIndex;
    inputForTextName.value = name;
    inputForText.type = 'text';
    inputForText.id = 'inputText' + lastFileIndex;
    inputForText.name = 'text[]';
    inputForText.value = text;
    Swal.fire({
        icon: 'success',
        title: 'Done',
        text: 'File' + " ' " + name + " ' " + 'was created successfully!'
    })
    document.getElementById('forInputs').appendChild(inputForTextName);
    document.getElementById('forInputs').appendChild(inputForText);
}

function saveLinkFile() {
    var p = document.createElement('p');
    var name = document.getElementById('linkFileName').value.trimStart();
    var link = document.getElementById('textAreaForLinkFile').value.trimStart();
    var linkType = document.getElementById('linkType').value;
    if (name === '' || link === '') {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'One of places is empty!'
        })
        return;
    }
    if (linkType === 'unselected') {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Select link type!'
        })
        return;
    }
    p.textContent = name;
    p.title = link;
    let divForClone = document.getElementById('innerDivForLink');
    $(divForClone).clone().appendTo('#forClone')
    var deleteButton;
    var editButton;
    var divForLink = document.getElementById('forClone').firstElementChild;
    for (const child of divForLink.children) {
        deleteButton = findChildren(child, 'forLinkDelete');
        editButton = findChildren(child, 'forLinkEdit');
    }
    lastFileIndex++;
    const deleteDiv = deleteButton.parentElement;
    deleteDiv.parentElement.style.textAlign = 'right'
    deleteButton.style.cursor = "pointer";
    editButton.parentElement.style.display = "inline-block";
    editButton.style.cursor = "pointer";
    deleteDiv.style.display = "inline-block";
    deleteButton.setAttribute('onclick', 'deleteFile(this);');
    editButton.setAttribute('onclick', 'editTextFile(this,"link");');
    deleteButton.id = "delete" + lastFileIndex;
    editButton.id = "editLink" + lastFileIndex;
    divForLink.style.display = 'inline-block';
    divForLink.id = ('fileDiv' + lastFileIndex);
    divForLink.appendChild(p);
    document.getElementById('collapse3').appendChild(divForLink);
    var inputForLinkName = document.createElement('input');
    var inputForLink = document.createElement('input');
    inputForLinkName.type = 'text';
    inputForLinkName.id = 'input' + lastFileIndex;
    inputForLink.id = 'inputText' + lastFileIndex;
    inputForLink.type = 'text';
    inputForLink.value = link;
    inputForLinkName.value = name;
    if (linkType === 'video') {
        inputForLinkName.name = 'videoLinkNames[]';
        inputForLink.name = 'videoLink[]';
    } else if (linkType === 'content') {
        inputForLinkName.name = 'linkNames[]';
        inputForLink.name = 'links[]';
    }
    Swal.fire({
        icon: 'success',
        title: 'Done',
        text: 'File' + " ' " + name + " ' " + 'was created successfully!'
    })
    document.getElementById('forInputs').appendChild(inputForLinkName);
    document.getElementById('forInputs').appendChild(inputForLink);
    hideLinkInput();
}

function deleteFile(element, index) {
    var elementIndex;
    if (index != null) {
        elementIndex = index
    } else {
        elementIndex = element.id.substr(6);
        Swal.fire({
            icon: 'success',
            title: 'Done',
            text: 'File is deleted successfully'
        })
    }
    var inputById = document.getElementById('input' + elementIndex);
    var inputForText = document.getElementById('inputText' + elementIndex);
    if (inputById !== null) {
        inputById.remove();
        inputForText.remove();
    }
    document.getElementById('fileDiv' + elementIndex).remove();

}

function findChildren(child, forDelete) {
    if (child.hasChildNodes()) {
        for (const childElement of child.children) {
            console.log(childElement.id)
            if (childElement.id === forDelete) {
                return childElement;
            }
            foundButton = findChildren(childElement, forDelete);
        }
    }
    return foundButton
}

function addToLesson(element) {
    var id = element.id;
    var input = document.createElement('input');
    var inputForDelete = document.getElementById('deletedResource' + element.id);
    if (inputForDelete != null) {
        inputForDelete.remove();
    }
    input.type = 'text';
    input.name = 'resourcesId[]';
    input.value = id;
    input.id = 'input' + id;
    input.style.display = 'none';
    element.src = '/teacher/img/icons/trash.svg';
    element.style.cursor = 'pointer';
    element.setAttribute('onclick', 'deleteResourceIdInput(this)');
    document.getElementById('forInputs').appendChild(input);
    Swal.fire({
        icon: 'success',
        title: 'Done',
        text: 'File was added successfully!'
    })
}

function deleteResourceIdInput(elementFromDb, isEdit) {
    let index = elementFromDb.id;
    let element = elementFromDb;
    if (index === undefined) {
        index = elementFromDb;
        element = document.getElementById(index);
    }
    var elementForDelete = document.getElementById('input' + index);
    if (elementForDelete === null) {
        var input = document.createElement('input');
        input.type = 'text'
        input.value = index
        input.id = 'deletedResource' + index
        document.getElementById('forInputs').appendChild(input)
    } else {
        elementForDelete.remove();
    }
    if (element !== null) {
        element.src = "/teacher/img/icons/cloud-plus-fill.svg";
        element.style.cursor = 'pointer';
        element.setAttribute('onclick', 'addToLesson(this)');
    }
    if (!isEdit) {
        input.name = 'removedResources[]'
        Swal.fire({
            icon: 'success',
            title: 'Done',
            text: 'File is deleted successfully'
        })
    } else {
        input.name = 'editedResources[]'
    }
}

function editTextFile(element, type) {

    var index = element.id.substr(8);
    var pTag = document.getElementById('fileDiv' + index).lastElementChild;
    var name = pTag.textContent;
    var text = pTag.title;
    if (type === 'text') {
        document.getElementById('textAreaForTextFile').value = text;
        document.getElementById('textFileName').value = name;
        showTextInput();
        document.getElementById('buttonForTextSave').addEventListener("click", function deleteText() {
            deleteFile(null, index);
            if (index.length > 10) {
                deleteResourceIdInput(index, true)
            }
        }, {once: true})
    } else if (type === 'link') {
        let linkType = document.getElementById('input' + index).name;
        document.getElementById('textAreaForLinkFile').value = text;
        document.getElementById('linkFileName').value = name;
        if (linkType === 'videoLinkNames[]') {
            document.getElementById('linkType').value = 'video'
        } else {
            document.getElementById('linkType').value = 'content'
        }
        showLinkInput();
        document.getElementById('buttonForLinkSave').addEventListener("click", function () {
            deleteFile(null, index);
            if (index.length > 10) {
                deleteResourceIdInput(index, true)
            }
        }, {once: true})
    }
}

function validateLessonRequest() {
    var name = document.getElementById('exampleFormControlInput1').value;
    var description = document.getElementById('exampleFormControlTextarea1').value;
    if (name.trimStart() === '' || description.trimStart() === '') {
        Swal.fire({
            icon: 'error',
            title: 'Oops..',
            text: 'Please input all places'
        })
        return false;
    } else if (document.getElementById("lesson-video").files.length <= 0 && document.getElementById('videoLink').value.trimStart() === "") {
        Swal.fire({
            icon: 'error',
            title: 'Oops..',
            text: 'Please upload video or input actually video link'
        })
        return false;
    }
    return true;
}

function showHomework() {
    var oFReader = new FileReader();
    var image;
    var divForClone = document.getElementById('forHomeworkClone');
    var name = document.getElementById('homeworkName').value;
    var text = document.getElementById('homeworkText').value;
    if (name.trimStart() === '' || text.trimStart() === '') {
        if (text.trimStart() === '' && image === undefined) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'One of places is empty!'
            })
        }
        return;
    }
    let homework = document.getElementById('homework');
    $(homework).clone().appendTo(divForClone)
    var homeworkTitle = document.getElementById('homeworkTitleText');
    var homeworkText = document.getElementById('homeworkTask');
    var deleteButton = document.getElementById('delete-homework');
    var editButton = document.getElementById('homeworkEditButton');
    var forImage = document.getElementById('showHomeworkImage');
    var homeworkImageInput = document.getElementById("forHomeworkImage");
    if (homeworkImageInput.files.length > 0) {
        image = document.getElementById("forHomeworkImage").files[0]
        oFReader.readAsDataURL(image);
    }
    lastHomework++;
    deleteButton.id = 'delete-homework' + lastHomework
    editButton.id = 'homeworkEditButton' + lastHomework
    deleteButton.setAttribute('onclick', 'deleteHomework(this);')
    editButton.setAttribute('onclick', 'editHomework(this);')
    homework.id = 'homework' + lastHomework;
    homework.className = 'col-sm-12 p-2 homework';
    homeworkTitle.textContent = name;
    homeworkTitle.id = 'homeworkTitleText' + lastHomework;
    homeworkText.textContent = text;
    homeworkText.id = 'homeworkTask' + lastHomework;
    deleteButton.id = 'delete-homework' + lastHomework;
    editButton.id = 'homeworkEditButton' + lastHomework;
    var inputForTitle = document.createElement('input');
    var inputForText = document.createElement('input');
    inputForTitle.name = 'titles[]'
    inputForTitle.type = 'text'
    inputForTitle.id = 'title' + lastHomework
    inputForTitle.value = name;
    inputForText.name = 'texts[]'
    inputForText.type = 'text'
    inputForText.id = 'text' + lastHomework
    inputForText.value = text;
    var homeworkInputsDiv = document.getElementById('forHomeworkInputs');
    homeworkInputsDiv.appendChild(inputForText);
    homeworkInputsDiv.appendChild(inputForTitle);
    var blobImage = document.getElementById('forEditImage').value;
    if (image !== undefined || blobImage !== undefined && blobImage !== 'http://localhost:8081/teacher/lessons/resources/add' && blobImage !== "") {
        forImage.id = 'showHomeworkImage' + lastHomework;
        var inputForImage = document.createElement('input');
        inputForImage.name = 'images[]'
        inputForImage.id = 'image' + lastHomework;
        inputForImage.type = 'text';
        var fileNamePlace = document.createElement('p');
        fileNamePlace.id = 'fileName' + lastHomework;
        homework.className = 'col-sm-12 p-2 homework img'
        if (image !== undefined) {
            if (image.size >= 1048576) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Maximum size of image can be 1mb!'
                })
                document.getElementById("forHomeworkInputs").value = null;
                return;
            }
            fileNamePlace.id = 'fileName' + lastHomework;
            fileNamePlace.value = image.name
            document.getElementById('forFileNames').appendChild(fileNamePlace);
            oFReader.onload = function (oFREvent) {
                inputForImage.value = "--" + image.name + "--" + oFREvent.target.result;
                forImage.src = oFREvent.target.result
                forImage.style.display = 'block'
            };

        } else if (image === undefined && blobImage !== 'http://localhost:8081/teacher/lessons/resources/add' && blobImage !== undefined) {
            inputForImage.value = "--" + fileName + "--" + blobImage;
            forImage.src = blobImage;
            fileNamePlace.value = fileName;
            forImage.style.display = 'block';
        }
        document.getElementById('forEditImage').value = 'http://localhost:8081/teacher/lessons/resources/add';
        document.getElementById('forFileNames').appendChild(fileNamePlace);
        var inputForImageIndex = document.createElement('input');
        inputForImageIndex.type = 'number'
        inputForImageIndex.name = 'imageHomeworkIndex[]'
        inputForImageIndex.value = lastHomework;
        homeworkInputsDiv.appendChild(inputForImage);
        homeworkInputsDiv.appendChild(inputForImageIndex);
    } else {
        forImage.id = 'noImage' + lastHomework;
    }
    document.getElementById('homeworkName').value = '';
    document.getElementById('homeworkText').value = '';
    document.getElementById('homeworks').appendChild(homework);
    Swal.fire({
        icon: 'success',
        title: 'Done',
        text: 'Homework' + " ' " + name + " ' " + 'was created successfully!'
    })
    homeworkImageInput.value = null;
}

function deleteHomework(element) {
    var index = element.id.substr(15);
    document.getElementById('homework' + index).remove()
    document.getElementById('text' + index).remove()
    document.getElementById('title' + index).remove()
    var image = document.getElementById('imageHomeworkIndex' + index);
    if (image !== null) {
        image.remove()
        document.getElementById('image' + index).remove();
        document.getElementById('fileName' + index).remove();
    }

}

function editHomework(element) {
    var imageRemove = document.getElementById('removeImage');
    var index = element.id.substr(18);
    imageRemove.className = 'remove' + index;
    var submitButton = document.getElementById('submitHomework');
    var editButton = document.createElement('img');
    editButton.src = "/teacher/img/icons/send.png";
    editButton.style.display = 'inline-block';
    editButton.style.cursor = 'pointer';
    editButton.id = 'editHomework' + index;
    editButton.setAttribute('onclick', 'updateHomework(this)');
    submitButton.replaceWith(editButton);
    var name = document.getElementById('homeworkTitleText' + index).textContent;
    var text = document.getElementById('homeworkTask' + index).textContent;
    document.getElementById('homeworkName').value = name;
    document.getElementById('homeworkText').value = text;
    var image = document.getElementById('showHomeworkImage' + index);
    if (image !== null) {
        document.getElementById('forEditImage').value = image.src;
        document.getElementById('removeImage').style.display = 'inline-block';
        fileName = document.getElementById('fileName' + index).value;
    }
    $('html,body').animate({
            scrollTop: $("#newHomework").offset().top
        },
        'fast');
}

function updateHomework(element) {
    var index = element.id.substr(12);
    var editButton = document.getElementById('editHomework' + index);
    var submitButton = document.createElement('img');
    document.getElementById('removeImage').style.display = 'none';
    var name = document.getElementById('homeworkName').value;
    var text = document.getElementById('homeworkText').value;
    submitButton.src = "/teacher/img/icons/send.png";
    submitButton.style.display = 'inline-block';
    submitButton.style.cursor = 'pointer';
    submitButton.id = 'submitHomework';
    submitButton.setAttribute('onclick', 'showHomework(this)');
    editButton.replaceWith(submitButton);
    var name = document.getElementById('homeworkName').value
    document.getElementById('homeworkTitleText' + index).textContent = name
    document.getElementById('homeworkTask' + index).textContent = text
    var homework = document.getElementById('homework' + index);
    $('html,body').animate({
            scrollTop: $(homework).offset().top
        },
        'fast');
    Swal.fire({
        icon: 'success',
        title: 'Done',
        text: 'Homework' + " ' " + name + " ' " + 'was saved successfully!'
    })
    document.getElementById('homeworkName').value = "";
    document.getElementById('homeworkText').value = "";
}

function removeImageFromHomework(element) {
    var index = element.className.substr(6);
    document.getElementById('forEditImage').value = 'http://localhost:8081/teacher/lessons/resources/add';
    var image = document.getElementById('imageHomeworkIndex' + (index + 1));
    document.getElementById('showHomeworkImage' + index).style.display = 'none';
    if (image !== null) {
        image.remove()
        document.getElementById('image' + index).remove();
        document.getElementById('fileName' + index).remove();
    }
    document.getElementById('removeImage').style.display = 'none';
    Swal.fire({
        icon: 'success',
        title: 'Done',
        text: 'Image was removed successfully!'
    })
}

var homeworkImage = document.getElementById('forHomeworkImage');
homeworkImage.addEventListener('change', function () {
    if (homeworkImage.files.length > 0) {
        Swal.fire({
            icon: 'success',
            title: 'Done',
            text: 'Image' + " ' " + homeworkImage.files[0].name + " ' " + 'was added to homework!'
        })
    }
})

function removeFromHomework(element) {
    var index = element.id.substr(6);
    var input = document.createElement('input');
    input.id = 'removed' + index;
    input.type = 'text';
    input.value = index
    input.name = 'removedHomeworks[]'
    document.getElementById('forHomeworkInputs').appendChild(input);
    element.textContent = "Add"
    element.style.color = 'green'
    element.style.border = '1px solid green'
    element.setAttribute('onclick', 'addToHomework(this);')
}

function addToHomework(element) {
    var index = element.id.substr(6);
    document.getElementById('removed' + index).remove();
    element.textContent = 'Remove';
    element.style.color = 'red';
    element.style.border = '1px solid red';
    element.setAttribute('onclick', 'removeFromHomework(this)');
}

function editQuestion(element) {
    let index = element.id.substr(12);
    var question = document.getElementById('question' + index);
    var questionOptions = document.getElementsByClassName('questionOption' + index);
    var singleRightAnswer = document.getElementsByClassName('questionRadio' + index);
    var multiRightAnswers = document.getElementsByClassName('questionCheckbox' + index);
    document.getElementsByClassName('questionImage').value = null;
    document.getElementById('question').value = question.textContent;
    var editedQuestion = document.getElementById('editedQuestion');
    editedQuestion.name = 'removedQuestionId[]';
    editedQuestion.value = index;
    if (singleRightAnswer.length > 0) {
        document.getElementById('select_answer_type').value = 'SINGLE_CHOICE';
        blocked[1].style.display = "none";
        blocked[0].style.display = "block";
        document.getElementsByClassName('newAnswerButton')[0].id = 'create_new_single_answer';
        document.getElementsByClassName('newAnswerButton')[0].setAttribute('onclick', 'createNewAnswers("radio", "single_choice_block");');
        lastQuestionIndex = 0;
        var singleAnswerTexts = document.getElementsByClassName('single_answer_row');
        if (singleAnswerTexts.length < singleRightAnswer.length) {
            let createAnswersCount = singleRightAnswer.length - singleAnswerTexts.length;
            for (let i = 0; i < createAnswersCount; i++) {
                createNewAnswers('radio', 'single_choice_block');
            }
            singleAnswerTexts = document.getElementsByClassName('single_answer_row');
        } else if (singleAnswerTexts.length > singleRightAnswer.length) {
            let deleteInputsCount = singleAnswerTexts.length - singleRightAnswer.length;
            for (let i = 0; i < deleteInputsCount; i++) {
                deleteAnswer(singleAnswerTexts[i].firstElementChild.firstElementChild);
            }
        }
        let rightAnswers = document.getElementsByClassName('check_input');
        for (let i = 0; i < singleAnswerTexts.length; i++) {
            singleAnswerTexts[i].firstElementChild.lastElementChild.firstElementChild.value = questionOptions[i].value;
            if (singleRightAnswer[i].checked) {
                rightAnswers[i].checked = true;
            }
        }
    } else if (multiRightAnswers.length > 0) {
        document.getElementById('select_answer_type').value = 'MULTI_CHOICE';
        blocked[1].style.display = "block";
        blocked[0].style.display = "none";
        document.getElementsByClassName('newAnswerButton')[0].id = 'create_new_multi_answer';
        document.getElementsByClassName('newAnswerButton')[0].setAttribute('onclick', 'createNewAnswers("checkbox", "multi_choice_block");')
        lastQuestionIndex = 0;
        var multiAnswerTexts = document.getElementsByClassName('multi_answer_row');
        if (multiAnswerTexts.length < multiRightAnswers.length) {
            let createAnswersCount = multiRightAnswers.length - multiAnswerTexts.length;
            for (let i = 0; i < createAnswersCount; i++) {
                createNewAnswers('checkbox', 'multi_choice_block');
            }
            multiAnswerTexts = document.getElementsByClassName('multi_answer_row');
        } else if (multiAnswerTexts.length > multiRightAnswers.length) {
            let deleteInputsCount = multiAnswerTexts.length - singleRightAnswer.length;
            for (let i = 0; i < deleteInputsCount; i++) {
                deleteAnswer(multiAnswerTexts[i].firstElementChild.firstElementChild);
            }
        }
        let rightAnswers = document.getElementsByClassName('checkbox_input');
        for (let i = 0; i < multiAnswerTexts.length; i++) {
            multiAnswerTexts[i].firstElementChild.lastElementChild.firstElementChild.value = questionOptions[i].value;
            if (multiRightAnswers[i].checked) {
                rightAnswers[i].checked = true;
            }
        }
    }

}

function deleteQuestion(element) {
    let questionId = element.id.substr(14);
    $.ajax({
        method: "GET",
        url: "/teacher/lessons/question/delete/" + questionId,
        success: function () {
            location.reload();
        }
    })
}

function deleteQuizQuestion(element) {
    let questionId = element.id.substr(14);
    $.ajax({
        method: "GET",
        url: "/teacher/quiz/questions/remove/" + questionId,
        success: function () {
            location.reload();
        }
    })
}