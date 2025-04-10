Отчёт по практической работе №1.

Модуль LayoutTypes.

В ходе работы были созданы три основных layout: LinearLayout, TableLayout,  ConstraintLayout. Для распределения информации внутри них использовались LinearLayout и TableRow соответственно, для размещения объектов внутри ConstraintLayout используется привязка к границам самого layout. 
![constraintlayout](https://github.com/user-attachments/assets/435053cb-744f-417e-9a7e-1d714ec1e367)
![linearlayout](https://github.com/user-attachments/assets/4f01d3e1-c75d-46d8-b49b-2f71363da455)
![tablelayout](https://github.com/user-attachments/assets/61b8648f-c02d-46c9-9b3e-bc334e427724)
После чего был реализован следующий xml файл activity_second, который должен выводиться при запуске приложения. Чтобы этого добиться, было изменено название xml файла в MainActivity.java c "activity_main" на "activity_second".
![actsecondvert](https://github.com/user-attachments/assets/b2ed9bf5-c59d-4639-a414-579c24312590)
Далее был реализован поворот экрана, при изменении положения устройства. Чтобы реализовать данный функционал, необходимо было создать новый файл activity_main.xml с изменённой ориентацией на landscape. После этого был создан второй файл с изменённой ориентацией уже для activity_second.xml. Теперь при запуске приложения в эмуляторе открывался интерфейс activity_second.xml, который менялся при повороте устройства.
![actsecondhoriz](https://github.com/user-attachments/assets/07922de5-c569-41a6-af4e-3f5a44813bdc)
Модуль control lesson.

В данном модуле был создан "контакт" из телефонной книжки. Для его создания использовались такие элементы как: ImageView (изображение), несколько TextView и один Button. Для группировки элементов использовался LinearLayout.
![controltaskvert](https://github.com/user-attachments/assets/f2d3073c-3b06-4ced-9011-889e7fdfb373)
После создания основного экрана был создан альтернативный вид экрана в альбомной ориентации (activity_main.xml (land)).
![controltasthoriz](https://github.com/user-attachments/assets/cf64dae0-78f7-411f-9903-90b581d9e3ac)
Модуль FindVievById.

Было решено создать отдельный модуль для работы над заданием связанным с FindVievById. В данном модуле был написан скрипт в MainActivity.java, который при запуске приложения меняет текст в TextView, Button, оспользуя метод setText, а так же помечает CheckBox с помощью setChecked(true).
![findviewbyid](https://github.com/user-attachments/assets/a584161d-a4e8-4dc1-9f33-88f12d42335f)

Модуль Button Clicker.

В данном модуле было необходимо создать две кнопки и текст. Первая кнопка при нажатии меняет текст на "Мой номер в списке 26", вторая выодит такой же текст как уведомление. Для достижения данного результата был написан скрипт в MainActivity.java, который содержит в себе обработчик событий и метод OnClick, который меняет текст, на событие была подписана кнопка WhoIAm, для кнопки ItIsNotMe был создан отдельный метод onMyButtonClick, который использует Toast уведомления для вывода информации и отмечает CheckBox, меняя его значение на true с помощью метода setChecked, данный метод вызывает по событию On Click, которое было настроено через xml. 
![whoiam](https://github.com/user-attachments/assets/6477ccc9-90ff-424b-9f7f-a8f46c17c827)
![itisnotme](https://github.com/user-attachments/assets/77bd5cac-1799-46ae-8bcf-adf325936f53)
