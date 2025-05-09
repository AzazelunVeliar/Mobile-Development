Отчёт по практической работе №4.


Модуль app. 

В данном модуле было реализовано главное меню аудио плеера с использованием «binding». Была сделана версия для вертикальной ориентации экрана и горизонтальной.
![appHor](https://github.com/user-attachments/assets/768849ee-15fa-4a6a-86ab-19b3f509e24d)
![appVert](https://github.com/user-attachments/assets/187d9293-b76a-4042-912a-0a347aa58990)
При нажатии на кнопки воспроизведения и паузы появляются соответствующие toast уведомления. 

```
binding.buttonPlay.setOnClickListener(v ->
                Toast.makeText(this, "Воспроизведение", Toast.LENGTH_SHORT).show());
```

Модуль thread.

В данном модуле было необходимо реализовать рассчёт среднего колличества пар в день. При этом главный поток, изначально main, должен быть переименован, а при нажатии на кнопку рассчёта должен запускаться новый поток.
![threadStart](https://github.com/user-attachments/assets/a6808b14-5ae7-4837-919a-3979668d07da)
Если наспамить кнопку рассчёта, то в логах отобразиться следующее:
![threadLog](https://github.com/user-attachments/assets/a9712a2c-5d5d-4e10-ae7b-3d4879784667)
По завершении выполнения рассчётов в логах выведется:
![threadLogEnd](https://github.com/user-attachments/assets/db094561-ae1e-4e17-b3ad-05da273084b4)


Модуль data_thread.


В данном модуле выводится в порядке выполенния runn1, runn2, runn3.
Такой порядок вывода обусловлен использованием разных методов: runOnUiThread - выполняет задачу в главном UI потоке немедленно, post - ставит задачу в очередь в UI поток, а postDelayed выполняет задачу с заданной задержкой. 
Логика вывода прописана в следующем коде:

```
TimeUnit.SECONDS.sleep(2);
runOnUiThread(runn1);
TimeUnit.SECONDS.sleep(1);
binding.tvInfo.post(runn2);
binding.tvInfo.postDelayed(runn3, 2000);
```
![datathread](https://github.com/user-attachments/assets/398a0191-a385-4589-b069-7de778c1eb80)


Модуль looper.


В данном модуле вывод текста откладывается на время равное введённому возрасту.
![looperStart](https://github.com/user-attachments/assets/27283a89-d63c-48f8-82a8-42fd1f981894)
![looperStartLog](https://github.com/user-attachments/assets/fa4cca32-00fb-48b0-b2da-bd5a45d224d4)
В данном случае через 21 секунду был получен результат.
![looperEnd](https://github.com/user-attachments/assets/fd16ef1e-f1cb-4c62-a695-fdc9057b0e63)
![looperEndLog](https://github.com/user-attachments/assets/68912af7-9ee1-4755-bd84-02afc66bbc85)


Модуль CryptoLoader.

В данном модуле введённое пользовтелем сообщение шифруется, передаётся в другой поток, дешифруется, возращается и выводится в toast уведомлении.
Передача из Main в loader осуществляется следующим способом:
```
LoaderManager.getInstance(MainActivity.this)
    .restartLoader(LOADER_ID, bundle, MainActivity.this);
```
А возвращается в main дешифрованное сообщение через метод onLoadFinished:
```
    public void onLoadFinished(Loader<String> loader, String result) {
        Toast.makeText(this, "Дешифровка: " + result, Toast.LENGTH_LONG).show();
    }
```
![cryptLoadStart](https://github.com/user-attachments/assets/19e6bd52-38c1-4c9f-be19-ccab8fce6371)
![cryptLoadEnd](https://github.com/user-attachments/assets/4270ad71-c939-4ae9-b3d5-320c1fa588ed)


Модуль ServiceApp.


В данном модуле был создан сервис PlayerService. Далее в этом же модуле был реализован аудио плеер. Был добавлен аудиофайл в формате mp3.

![Снимок экрана 2025-05-08 155238](https://github.com/user-attachments/assets/2a229d03-ab17-4308-bdf2-8780e9f52935)

Далее был создан интерфейс приложения.
![serviceAppStart](https://github.com/user-attachments/assets/c81e6849-cc32-4710-a49f-d99acabebe04)
При нажатии на кнопку воспроизведения включается песня, а так же появляется уведомление в котором написано, какой трек проигрывается.
![serviceappNot](https://github.com/user-attachments/assets/823cdf71-08f1-46e3-bef4-b3bf68945332)
Для правильной работы приложения, помимо написания логики, были добавлены все необходимые разрешения в файле Android Manifest, а так же определён тип сервиса.


Модуль Work Manager.


В данном модуле необходимо было создать WM и задать ему условия срабатывания. Были выбраны следующие: 
1. устройство находится на зарядке;
2. устройство имеет доступ к WIFI.
Данные условия были прописаны в MainActivity.java
```
 Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .build();
```
![workmanagerStart](https://github.com/user-attachments/assets/1690562b-e65f-4605-8561-4a9dd2d38160)
![workmanagerEnd](https://github.com/user-attachments/assets/8a0c43fa-41df-4c64-8ee9-6f8836a24969)

MireaProject.


Было решено добавить Проигрыватель. 
![mireaProj](https://github.com/user-attachments/assets/4af62acb-1aca-432a-b832-9033483cc03d)
Были загружены несколько треков. Когда трек заканчивается, то запускается следующий, если это был последний трек в спиcке, то запускается первый:
```
mediaPlayer.setOnCompletionListener(mp -> {
    currentTrackIndex = (currentTrackIndex + 1) % trackList.length;
    playCurrentTrack();
});
```
Данный метод вызывается каждый раз, когда трек заканчивается.
