Отчёт по практической работе №7.


Модуль TimeService


В данном модуле использовалось прямое сокет‐соединение с публичными Daytime‐серверами (порт 13) и AsyncTask для фонового запроса.
Приложение перебирает зеркала NIST (time.nist.gov, time‐a.nist.gov и пр.), читает вторую строку ответа, разбивает её через split("\\s+") и берёт parts[1] как дату, parts[2] как время. Результат выводится в два TextView через ViewBinding.
При ошибках выводит «Дата: ошибка» и «Время: --».
![timeservice](https://github.com/user-attachments/assets/0a34727b-d9f6-4c95-ae16-6105307b005e)


Модуль httpurlconnection


Этот модуль выводит на экран следующие поля: город, регион, страну, широту/долготу, температуру и скорость ветра. 
При нажатии на кнопку «Получить IP и погоду» выполняется один AsyncTask, внутри которого:
```
GET https://ipinfo.io/json через HttpURLConnection.
```
Читается весь JSON-ответ, из него извлекаются поля "city", "region", "country" и строка "loc" (формат "55.7522,37.6156"). Эту информацию сразу же записывают в пять TextView:
```
binding.tvCity.setText("Город: " + city);  
binding.tvRegion.setText("Регион: " + region);  
binding.tvCountry.setText("Страна: " + country);  
binding.tvLatitude.setText("Широта: " + latitude);  
binding.tvLongitude.setText("Долгота: " + longitude);
```
Собирается URL вида

```
https://api.open-meteo.com/v1/forecast?latitude=<lat>&longitude=<lon>&current_weather=true
```
и снова отправляется HttpURLConnection GET.

Из JSON-ответа Open-Meteo достаётся объект current_weather с полями temperature и windspeed. Эти два значения выводятся в оставшиеся TextView:

```
binding.tvTemperature.setText("Темп.: " + temp + "°C");  
binding.tvWindspeed.setText("Ветер: " + wind + " м/с");
```
При любом сбое (отсутствие сети, неверный JSON или код ответа ≠ 200) соответствующие поля остаются пустыми или помещаются в состояние «ошибка».

![urlconn](https://github.com/user-attachments/assets/9a6f0a50-fbb4-49b8-b37b-d19715f25363)


Модуль FirebaseAuth


В данном модуле была реализована аутентификация пользователя через Email/Password.
В едином экранe (activity_main.xml) реализована форма регистрации/входа через Firebase Auth:
два EditText для email и пароля, кнопки «Регистрация» и «Войти», а также скрытый TextView для ошибок.
После успешного входа или регистрации (методы createUserWithEmailAndPassword и signInWithEmailAndPassword) переключается UI в «профильный» режим: отображаются tvEmailInfo (Email + статус верификации) и tvUidInfo (UID), кнопка «Verify Email» для отправки письма с подтверждением (sendEmailVerification()) и «Sign Out» (auth.signOut()).
Логика обмена между формой и профилем выполняется в onStart() и методах showLoginScreen()/showProfile().
![Firebase](https://github.com/user-attachments/assets/793c234b-fa1a-44e5-8b8e-38310f516b3b)
![FirebaseReg](https://github.com/user-attachments/assets/4840d480-c029-41a4-bd88-ab6c0e036383)
![FirebaseLgin](https://github.com/user-attachments/assets/c01f1ad2-be09-42e9-8cda-9816640e252d)


MireaProject


В LoginActivity реализована регистрация и вход через Firebase Auth. При старте в onCreate() вызывается FirebaseApp.initializeApp(this) и инициализируется FirebaseAuth auth = FirebaseAuth.getInstance(). Интерфейс через ViewBinding (ActivityLoginBinding) содержит два поля (etEmail, etPassword), две кнопки (btnRegister, btnLogin) и скрытый TextView tvMessage для ошибок.

Регистрация (btnRegister):

```
auth.createUserWithEmailAndPassword(email, pass)
    .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            updateUI(auth.getCurrentUser());
        } else {
            binding.tvMessage.setText("Ошибка регистрации");
            binding.tvMessage.setVisibility(View.VISIBLE);
        }
    });
```
При успешной регистрации сразу вызывается updateUI(user), запускающий MainActivity.

Вход (btnLogin):

```
auth.signInWithEmailAndPassword(email, pass)
    .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            updateUI(auth.getCurrentUser());
        } else {
            binding.tvMessage.setText("Ошибка входа");
            binding.tvMessage.setVisibility(View.VISIBLE);
        }
    });
```

В методе updateUI(FirebaseUser user) проверяется ненулевой user и выполняется:
```
Intent intent = new Intent(this, MainActivity.class);
startActivity(intent);
finish();
```
— после чего экран входа закрывается, и пользователь попадает на главный экран.
![mireaProjFirebase](https://github.com/user-attachments/assets/911c9dcf-ac90-4d0c-a05c-20aea3d4a753)
![mireaProjLogin](https://github.com/user-attachments/assets/74443691-8435-4f94-891a-d656cc3dfb1c)

WeatherFragment получает текущую погоду по координатам 55.7522, 37.6156 через Retrofit. В onViewCreated() создаётся Retrofit с базой https://api.open-meteo.com/ и интерфейсом:

```
@GET("v1/forecast?current_weather=true")
Call<WeatherResponse> getCurrentWeather(
    @Query("latitude") double latitude,
    @Query("longitude") double longitude,
    @Query("timezone") String timezone
);
```
Модель WeatherResponse содержит вложенный класс CurrentWeather { double temperature; double windspeed; }. Фрагмент сразу ставит tvLocation в "Локация: 55.7522, 37.6156", затем вызывает api.getCurrentWeather(...). 
В onResponse() при удаче заполняются tvTemperature и tvWindSpeed; при ошибке выводятся «Температура: ошибка» и «Ветер: ошибка».

![mireaProjWeather](https://github.com/user-attachments/assets/10dc4a46-540c-42de-a38c-9b2e58c959a5)
