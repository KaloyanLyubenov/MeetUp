const toggleLink = document.querySelector('#toggle-link');
const myForm = document.querySelector('#hidden-form');
const btnDiv = document.querySelector('#main-button')

toggleLink.addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the default link behavior
    if (myForm.classList.contains('hidden')) {
        myForm.classList.remove('hidden');
        btnDiv.classList.add('hidden');
    } else {
        myForm.classList.add('hidden');
    }
});