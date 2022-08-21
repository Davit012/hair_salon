function deleteAnswer(element) {
    var parent = element.parentElement.parentElement;
    if (singleEndIndex != undefined && parent.className.includes('single_answer_row')) {
        if (singleEndIndex <= 1) {
            Swal.fire({
                icon: 'error',
                title: 'Oops..',
                text: 'You can not delete this answer field!'
            })
            return;
        } else {
            parent.remove();
        }
        singleEndIndex--;
    } else if (multiEndIndex != undefined && parent.className.includes('multi_answer_row')) {
        if (multiEndIndex <= 1) {
            Swal.fire({
                icon: 'error',
                title: 'Oops..',
                text: 'You can not delete this answer field!'
            })
            return;
        } else {
            parent.remove();
        }
        multiEndIndex--;
    }
}

let buttonsForDeleteSingleAnswer = document.getElementsByClassName("deleteSingleAnswer")
let buttonForDelete;
for (buttonForDelete of buttonsForDeleteSingleAnswer) {
    createEventListenerForSingleChoice(buttonForDelete)
}

function addButton(element) {
    element.addEventListener("click", function () {
        deleteAnswer(element);
        return null;
    });
}

function createEventListenerForSingleChoice(element) {
    element.addEventListener("click", function () {
        deleteAnswer(element);
        return null;
    });
}

function createEventListenerForMultiChoice(element) {
    element.addEventListener("click", function () {
        deleteAnswer(element);
        return null;
    });
}


