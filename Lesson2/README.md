Отчёт по практической работе №2

Модуль ActivityLifecycle

Перегружены основные методы жизненного цикла активности: onCreate, onStart, onResume, onPause, onStop, onDestroy.
В каждый из методов добавлен вывод логов через Log.i(...).
На экран добавлен EditText, который сохарняет текст присворачивании приложения, но не сохраняет его при завершении работы приложения. При этом onCreate вызывается только при запуске прииложения, то есть не вызывается повторно, когда приложение выходит из фонового режима работы. 
![ActivityLifeCyclePause](https://github.com/user-attachments/assets/dbd7e48b-ee4d-4fef-88dc-89f2da0003a1)

![ActLifeCycleRestart](https://github.com/user-attachments/assets/ffe4e1e2-b4dd-4908-851d-f70c073817e4)
Модуль MultiActivity

Создано двухэкранное приложение с явным вызовом активности.
Добавлено поле EditText и кнопка для запуска SecondActivity, куда передается введённый текст через Intent.
![MultyActMain](https://github.com/user-attachments/assets/1af7c20d-d47b-4d8f-9582-bc1fc72e2ea4)
![MainMulti](https://github.com/user-attachments/assets/daf573f0-74de-4d86-b3b9-9a8c2e8bdba7)
На втором экране TextView отображает полученные данные. Чтобы приложение знало осуществовании second activity был отредактирован AndroidManifest.xml

            <activity
            android:name=".SecondActivity"
            android:exported="false"
            android:label="SecondActivity"
            android:theme="@style/Theme.Lesson2"/>

![MultiSecondAct](https://github.com/user-attachments/assets/c70d301b-8fd5-4433-bc9b-a5d3190031a7)
![SecondMulti](https://github.com/user-attachments/assets/12fa4cb3-f750-4f87-89c9-9fdcf5416940)

Модуль IntentFilter

Добавлены две кнопки:
![IntentText](https://github.com/user-attachments/assets/316ea102-f253-474c-bd1d-5d54757b3b51)
Первая открывает браузер по адресу https://www.mirea.ru с помощью Intent.ACTION_VIEW.
![IntentBrowser](https://github.com/user-attachments/assets/2b983ddd-956e-4375-bba5-a9e7b024ad59)
Вторая передаёт ФИО и университет через Intent.ACTION_SEND во внешние приложения.
![IntentSharingText](https://github.com/user-attachments/assets/2c311b1d-f20e-473d-999f-2dab537d70cf)

Модуль ToastApp

Создано поле ввода и кнопка.
При нажатии отображается Toast с сообщением:
СТУДЕНТ №26 ГРУППА БСБО-09-22 Количество символов - X
![Toats](https://github.com/user-attachments/assets/60f1932a-7bbd-4581-b055-46707ee52f31)
![ToatsMainAct](https://github.com/user-attachments/assets/9319238f-ae98-4a8c-84d9-80614428c512)

Модуль NotificationApp

Реализовано уведомление с заголовком, текстом и расширенным сообщением.

Для Android 13+ запрашивается разрешение на уведомления (POST_NOTIFICATIONS).
Используется NotificationCompat, создается канал уведомлений.
Иконка и текст задаются программно.
Изменения в AndroidManifest.xml:
Перед тегом application добавлено: 

    <uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
![NitificationMainAct](https://github.com/user-attachments/assets/3a7431b4-c189-4f48-9c95-d6d5d6c69062)
![Notification](https://github.com/user-attachments/assets/f328ebbf-d8f1-43ac-b807-f06f185d6969)

Модуль Dialog

Создано 4 диалоговых окна:

AlertDialogFragment — окно с тремя кнопками ("Иду дальше", "На паузе", "Нет"), вызывается из MainActivity.
![ShowDialog](https://github.com/user-attachments/assets/ec3ec2cf-0936-4922-812a-15db1471adda)
MyTimeDialogFragment — выбор времени.
![TimePicker](https://github.com/user-attachments/assets/3398d581-c519-400d-bacd-7e320259c585)
MyDateDialogFragment — выбор даты.
![DataPicker](https://github.com/user-attachments/assets/7274913c-9c2d-48fc-9c41-30dee8cd5901)
MyProgressDialogFragment — отображение загрузки.
![ProgressDialog](https://github.com/user-attachments/assets/c76a9c35-2a31-4aaa-b1a2-702487299ce2)

Все диалоги подключены к кнопкам в activity_main.xml.
![DialogMainAct](https://github.com/user-attachments/assets/f6e0ac1c-1c91-4ad4-8808-488ffc61bb02)

