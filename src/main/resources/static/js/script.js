console.log("script loaded...");

function changeTheme() {

    // changeContent();
    
    // add new theme
    document.querySelector("html").classList.add(currentTheme);
    const changeThemeBtn = document.querySelector("#theme_change_btn");

    changeThemeBtn.addEventListener("click", (event) => {
        const oldTheme = currentTheme;
        console.log('old theme : ', oldTheme);


        if (currentTheme === "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";
        }

        // set theme to storage
        setTheme(currentTheme);

        //remove old theme
        document.querySelector("html").classList.remove(oldTheme);

        // add new theme
        document.querySelector("html").classList.add(currentTheme);
        console.log('new theme : ', currentTheme);

    })
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";


}

function changeContent() {
    const changeThemeBtn = document.querySelector("#theme_change_btn");
    //  change content of button
    changeThemeBtn.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";
}

let currentTheme = getTheme();
changeTheme();
