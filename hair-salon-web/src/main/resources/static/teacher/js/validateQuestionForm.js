function validFunction() {
    let select = document.getElementById('select_answer_type');
    var points = document.getElementById('points').value;
    if (points.trimStart() === '') {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Please, input completion points'
        })
        return false;
    }
    switch (select.selectedIndex) {
        case 0:
            let singleInputText = document.getElementsByClassName("single_answer_text");
            for (let i = 0; i < singleInputText.length; i++) {
                var content = singleInputText[i].value;
                if (content == "") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Please, input all answers'
                    })
                    return false;
                }
                if (content.includes('&nbsp;')) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Please, don`t type spaces at start and end of text'
                    })
                    return false;
                }
            }
            let radioCheckedList = document.querySelectorAll('input[type=radio]:checked');
            if (radioCheckedList.length <= 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Mark one right answer at least'
                })
                return false;
            }
            break;


        case 1:
            let multiInputText = document.getElementsByClassName("multi_answer_text");
            for (let i = 0; i < multiInputText.length; i++) {
                var content = multiInputText[i].value;
                if (content == "") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Please, input all answers'
                    })
                    return false;
                }
                if (content.includes('&nbsp;')) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Please, don`t type spaces at start and end of text'
                    })
                    return false;

                }
                let CheckboxCheckedList = document.querySelectorAll('input[type=checkbox]:checked');
                if (CheckboxCheckedList.length <= 1) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Multi Choice answer will have minimum 2 right answers'
                    })
                    return false;
                }
            }
            break;
        case 4:
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Select Question Type'
            })
            return false;
            break;

    }
}