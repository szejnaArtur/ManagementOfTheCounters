let state = false;
let password = document.getElementById('password');
let passwordStrength = document.getElementById('password-strenght');
let lowerUpperCase = document.querySelector('.low-upper-case i');
let number = document.querySelector('one-number i');
let specialChar = document.querySelector('.one-special-char i');
let eightChar = document.querySelector('.eight-character i');

function myFunction(show) {
    show.classList.toggle('fa-eye-slash');
}

function toggle() {
    if (state) {
        password.setAttribute("type", "password");
        state = false;
    } else {
        password.setAttribute("type", "text");
        state = true;
    }
}

password.addEventListener('keyup', function () {
    let pass = password.value;
    checkStrenght(pass);
});

function checkStrenght(password) {
    let strenght = 0;

    if (password.match(/([a-z].*[A-Z])|([A-Z.*[a-z])/)) {
        strenght += 1;
        lowerUpperCase.classList.remove('fa-circle');
        lowerUpperCase.classList.add('fa-check');
    } else {
        lowerUpperCase.classList.remove('fa-check');
        lowerUpperCase.classList.add('fa-circle');
    }

    if(password.match(/([0-9])/)){
        strenght += 1;
        number.classList.remove('fa-circle');
        number.classList.add('fa-check');
    } else {
        number.classList.remove('fa-check');
        number.classList.add('fa-circle');
    }

    if(password.match(/([!@#$%^&*?_~])/)){
        strenght += 1;
        specialChar.classList.remove('fa-circle');
        specialChar.classList.add('fa-check');
    } else {
        specialChar.classList.remove('fa-check');
        specialChar.classList.add('fa-circle');
    }

    if(password.length > 7){
        strenght += 1;
        eightChar.classList.remove('fa-circle');
        eightChar.classList.add('fa-check');
    } else {
        eightChar.classList.remove('fa-check');
        eightChar.classList.add('fa-circle');
    }

    if(strenght === 0){
        passwordStrength.style = 'width: 0%';
    } else if(strenght === 2){
        passwordStrength.classList.remove('progress-bar-warning');
        passwordStrength.classList.remove('progress-bar-success');
        passwordStrength.classList.add('progress-bar-danger');
        passwordStrength.style = 'width: 10%';
    } else if(strenght === 3){
        passwordStrength.classList.remove('progress-bar-success');
        passwordStrength.classList.remove('progress-bar-danger');
        passwordStrength.classList.add('progress-bar-warning');
        passwordStrength.style = 'width: 60%';
    } else if(strenght === 4){
        passwordStrength.classList.remove('progress-bar-danger');
        passwordStrength.classList.remove('progress-bar-warning');
        passwordStrength.classList.add('progress-bar-success');
        passwordStrength.style = 'width: 100%';
    }
}
