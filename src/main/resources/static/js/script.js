

function checkAddAndUpdate() {

    var name = document.getElementById('name').value;
    var weight = document.getElementById('weight').value;
    var height = document.getElementById('height').value;
    var hairColor = document.getElementById('hairColor').value;
    var gpa = document.getElementById('gpa').value;
    var age = document.getElementById('age').value;

    var submitButton = document.querySelector('.btn-primary');

    if (name && weight && height && hairColor && gpa && age) {

        submitButton.removeAttribute('disabled');

    } else {

        submitButton.setAttribute('disabled', 'disabled');
    }
}

function checkDeleteAndEdit() {

    var name = document.getElementById('name').value;
    var age = document.getElementById('age').value;

    var submitButton = document.querySelector('.btn-primary');

    if (name && age) {

        submitButton.removeAttribute('disabled');

    } else {

        submitButton.setAttribute('disabled', 'disabled');
    }
}

checkAddAndUpdate();