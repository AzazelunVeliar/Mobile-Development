Отчёт по практической работе №5.

Модуль app.


В данном модуле был выведен список всех доступных датчиков на утройстве.
![app](https://github.com/user-attachments/assets/8418c008-3e50-4eea-a228-74e691640b2b)

Модуль Accelerometer.


В данном модуле был реализован интерфейс для вывода данных с акселерометра. При изменении положения устройства в пространстве зачения динамически меняются.
![accelerometerStart](https://github.com/user-attachments/assets/7e9d7c6c-9c65-4514-b33d-6f4bc8786657)
![accelerometerEnd](https://github.com/user-attachments/assets/9d5b8213-dec3-4014-972f-295a3b1ba2f5)


Модуль Camera. 


В данном модуле была реализована система запроса разрешения на использование камеры у пользователя. Далее эта система будет реализована в каждом элементе, где планируется использование каких-либо компонентов устройства, таких как камера или микрофон.
Так же была реализована логика вызова камеры и сохранения получившегося изображения в файлах смартфона.
![cameraStart](https://github.com/user-attachments/assets/6387eeb1-f617-4279-91f8-2655f11ce522)
Чтобы запустить камеру, необходимо тапнуть по экрану.
![cameraAllow](https://github.com/user-attachments/assets/304d77ab-d394-49f7-a766-081de10d7f00)
![cameraWork](https://github.com/user-attachments/assets/59e18868-9457-40b6-84a3-a2d8f14f5fa5)
![cameraPhoto](https://github.com/user-attachments/assets/3a6b29ae-4efc-418f-a1a2-f8fc250db3eb)
![cameraFile](https://github.com/user-attachments/assets/28f7c6f9-e70e-4d7d-b1c0-1274b5605975)


Модуль AudioRecord.


В данном модуле была реализована запись аудио. Так же необходимо разрешение от пользователя на доступк микрофону.
При нажатии на кнопку Start recording появляется запрос на разрешение использования микрофона и начинается запись, если разрешение было получено.
![recordAllow](https://github.com/user-attachments/assets/bc271cc2-cd50-45d5-bf32-99793d0c8e76)
![recording](https://github.com/user-attachments/assets/2e42c7d4-108d-432e-b3bb-395175ddb88f)
При остановке записи файл сохраняется в памяти смартфона.
![recordFile](https://github.com/user-attachments/assets/6da75e7e-6f8e-4990-b074-2b734c502beb)

MireaProject

Были реализованы несколько новых фрагментов: Gyroscope, Notes, VoiceRecorder. Каждый из них был добавлен в MainActivity.java, activity_main_drawer.xml и mobile_navigation.xml.
Для MainActivity.java
```
                R.id.nav_recorder,
                R.id.nav_gyroscope,
                R.id.nav_notes
```

Для activity_main_drawer.xml
```
        <item
            android:id="@+id/nav_recorder"
            android:icon="@android:drawable/ic_btn_speak_now"
            android:title="Диктофон" />
        <item
            android:id="@+id/nav_gyroscope"
            android:title="Уровень" />
        <item
            android:id="@+id/nav_notes"
            android:title="Заметки"/>
```

Для mobile_navigation.xml
```
    <fragment
        android:id="@+id/nav_recorder"
        android:name="ru.mirea.khudyakovma.mireaproject.ui.recorder.VoiceRecorder"
        android:label="Диктофон"
        tools:layout="@layout/fragment_voice_recorder" />
    <fragment
        android:id="@+id/nav_gyroscope"
        android:name="ru.mirea.khudyakovma.mireaproject.ui.gyroscope.Gyroscope"
        android:label="Уровень"
        tools:layout="@layout/fragment_gyroscope" />
    <fragment
        android:id="@+id/nav_notes"
        android:name="ru.mirea.khudyakovma.mireaproject.ui.note.Notes"
        android:label="Заметки"
        tools:layout="@layout/fragment_notes" />
```
![mireaprojRecorder](https://github.com/user-attachments/assets/3885c09b-f12e-440d-a95a-59487ffc99f4)



Фрагмент Gyroscope.


Данный фрагмент реализует "уровень", который показывает на ровной ли поверхности находится смартфон. Ровной считается поверхность, наклон которой менее 1 градуса.
![mireaprojGyro](https://github.com/user-attachments/assets/d609dfc5-8c14-4cbb-8e85-8d1ab3e6d9dd)
![mireaprojGyroEnd](https://github.com/user-attachments/assets/01bf86f5-d6bc-4d3e-9280-5178b467933d)


Фрагмент Notes.


В данном фрагменте реализованы фото-заметки. Для доступа к камере необходимо разрешение пользователя. Так же были добавлены все необходимые разрешения в манифесте.
```
    <uses-feature android:name="android.hardware.camera" android:required="false"/>
    <uses-permission android:name="android.permission.CAMERA" />
```
Каждая заметка обязательно требует имени, после чего нужно тапнуть на кнопку "Сделать фото", тогда откроется камера, где можно сделать фото. После этого сохранённая заметка отобразится в списке заметок, можно тапнуть на любую из них и откроется связанная с заметкой фотография.
![mireaprojNotes](https://github.com/user-attachments/assets/5bc3bf04-ff05-47af-a830-a4f08a9a892f)
![mireaprojNotesPhoto](https://github.com/user-attachments/assets/25e82724-4c3c-4c2d-b549-8bf1a9b39c20)
![mireaprojNotesEnd](https://github.com/user-attachments/assets/b51e5370-083b-4cdc-849f-b45f192baa35)
![mireaprojNotesFile](https://github.com/user-attachments/assets/a0cf7f6f-f0ef-4b4a-8aa4-699760dec704)


Фрагмент VoiceRecorder.


В данном фрагменте был реализован диктофон. Для доступа к микрофоту так же необходимо согласие пользователя. В манифесте было добавлено следующее разрешение:
```
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
```
После нажатия кнопки Start recording необходимо ввести название файла и начинается запись, после завершения она отображается в списке ниже.
![mireaprojRecStart](https://github.com/user-attachments/assets/d1db26d0-8709-48ae-bde3-ad17ee0cb91d)
![mireaprojRecording](https://github.com/user-attachments/assets/dc99d417-2ba4-4ec9-8cce-9aefdecbfae9)
![mireaprojRecStop](https://github.com/user-attachments/assets/07e44bf3-d9dd-47e0-8063-5aa69e59cd5e)
![mireaprojRecFile](https://github.com/user-attachments/assets/3f574c21-3976-401a-95e3-75bbd4d29812)
