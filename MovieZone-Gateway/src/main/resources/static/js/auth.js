function sendRequest(url, options = {}) {
    const token = localStorage.getItem('jwt_token');
    if (token) {
        if (!options.headers) {
            options.headers = {};
        }
        options.headers['Authorization'] = `${token}`;
    }
    return fetch(url, options);
}

document.querySelectorAll('a').forEach(link => {
    link.addEventListener('click', (event) => {
        event.preventDefault();
        const targetHref = link.getAttribute('href')
        if (targetHref === 'reg' || targetHref === 'login' || targetHref === 'home') {
            sendRequest(targetHref)
                .then(response => response.text())
                .then(html => {
                    document.open();
                    document.write(html);
                    document.close();
                })
                .catch(() => {
                    alert('Ошибка загрузки страницы.');
                });
            return;
        }
        const token = localStorage.getItem('jwt_token');
        if (!token) {
            alert('Вы не авторизованы!');
            sendRequest('login')
                .then(response => response.text())
                .then(html => {
                    document.open();
                    document.write(html);
                    document.close();
                })
                .catch(() => {
                    alert('Ошибка загрузки страницы.');
                });
            return;
        }
        else {
            const payload = JSON.parse(atob(token.split('.')[1])); // Расшифровка токена
                const expTime = payload.exp * 1000;
                const currentTime = Date.now();
                if (currentTime > expTime) {
                    alert('Авторизуйтесь повторно, срок действия авторизации истек.');
                    localStorage.removeItem('jwt_token');
                    sendRequest('login')
                        .then(response => response.text())
                        .then(html => {
                            document.open();
                            document.write(html);
                            document.close();
                        })
                        .catch(() => {
                            alert('Ошибка загрузки страницы.');
                        });
                    return;
                }
                sendRequest(targetHref)
                .then(response => response.text())
                .then(html => {
                    document.open();
                    document.write(html);
                    document.close();
                })
                .catch(() => {
                    alert('Доступ запрещён');
                });
        }
    });
});