const dateControl = document.querySelectorAll('input[type="date"]');
const date = new Date(Date.now());
dateControl.forEach(control => control.value = new Date(Date.now()).toISOString().substring(0, '2021-02-18'.length));