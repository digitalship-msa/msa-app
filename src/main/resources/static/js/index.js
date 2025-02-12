const text = document.querySelector('#text');
const btn = document.querySelector('#submit-btn');
const result = document.querySelector('#result');

const URL = "/text";
const METHOD = "POST"

let prevTimeoutId = "";

btn.addEventListener('click', async () => {
    console.log(text.value);

    const data = {
        data: text.value
    }
    const res = await fetch(URL, {
        method: METHOD,
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    });

    if (res.status / 100 !== 2) {
        throw new Error(`status code is ${res.status}`);
    }
    const json = await res.json();
    result.textContent = "OK";

    if (prevTimeoutId) clearTimeout(prevTimeoutId);

    prevTimeoutId = setTimeout(() => {
        result.textContent = "";
    }, 1000);

});