// Валидация формы регистрации
function validateRegistrationForm() {
    const username = document.getElementById('username').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    if (username.length < 3 || username.length > 20) {
        alert('Имя пользователя должно быть от 3 до 20 символов');
        return false;
    }

    if (!email.includes('@') || !email.includes('.')) {
        alert('Введите корректный email');
        return false;
    }

    if (password.length < 6) {
        alert('Пароль должен содержать не менее 6 символов');
        return false;
    }

    return true;
}

// Валидация формы входа
function validateLoginForm() {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    if (username === '') {
        alert('Введите имя пользователя');
        return false;
    }

    if (password === '') {
        alert('Введите пароль');
        return false;
    }

    return true;
}

// Валидация формы добавления контакта
function validateContactForm() {
    const contactName = document.getElementById('contactName').value.trim();
    const phoneNumber = document.getElementById('phoneNumber').value.trim();
    const phoneRegex = /^\+?[0-9\s\-\(\)]{7,15}$/;

    if (contactName.length < 2 || contactName.length > 50) {
        alert('Имя контакта должно быть от 2 до 50 символов');
        return false;
    }

    if (!phoneRegex.test(phoneNumber)) {
        alert('Введите корректный номер телефона');
        return false;
    }

    return true;
}

// Добавляем обработчики форм
document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.querySelector('form[action*="/register"]');
    if (registerForm) {
        registerForm.onsubmit = validateRegistrationForm;
    }

    const loginForm = document.querySelector('form[action*="/login"]');
    if (loginForm) {
        loginForm.onsubmit = validateLoginForm;
    }

    const contactForm = document.querySelector('form[action*="/addEntry"]');
    if (contactForm) {
        contactForm.onsubmit = validateContactForm;
    }
});