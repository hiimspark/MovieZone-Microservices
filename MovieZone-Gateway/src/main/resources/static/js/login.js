document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();
    let formData = new FormData(document.getElementById("loginForm"));
    const formObject = {};
         formData.forEach((value, key) => {
                formObject[key] = value;
            });
    const jsonBody = JSON.stringify(formObject);
              fetch("/auth/login", {
                      method: "POST",
                      headers: {
                         'Content-Type': 'application/json',
                      },
                      body: jsonBody
              })
              .then(response => response.json())
              .then(data => {
                      if (data.jwt) {
                          const tokenWithBearer = `Bearer ${data.jwt}`;
                          localStorage.setItem('jwt_token', tokenWithBearer);
                          console.log("Токен сохранен в localStorage:", tokenWithBearer);
                          window.location.href = "/";
                      } else {
                          console.error("Токен не получен");
                      }
                  })
                  .catch(error => {
                      console.error('Ошибка при отправке запроса:', error);
                  });
              });