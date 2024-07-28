const btn = document.querySelector('.btn')
const input = document.querySelector('.input')
const search = document.querySelector('.search')


btn.addEventListener('click', () => {
  search.classList.toggle('active')
  input.focus()
}) //when the button is clicked, the value of the input field is assigned to the value of the search field
