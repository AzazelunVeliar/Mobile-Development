Отчёт по практической работе №3


Модуль simplefragmentapp


Создано приложение с двумя фрагментами FirstFragment и SecondFragment.
Реализовано переключение между ними через кнопки.

![simplefragmentStart](https://github.com/user-attachments/assets/bb84e723-36b9-48f5-ab7f-a7d6927c3c78)

![simplefragmentFirst](https://github.com/user-attachments/assets/fff64e20-e31f-4f65-8833-62642f9baa51)

![simplefragmentSecond](https://github.com/user-attachments/assets/1d499320-cfcc-4e7e-94d5-dc622a23f370)

В портретной ориентации используется FragmentContainerView, в альбомной — два fragment-контейнера с разметкой через layout-land.

![simplefragmentLand](https://github.com/user-attachments/assets/d1deb6bc-ab00-4e2d-8615-fdccedd5613d)

Модуль systemintentsapp


Приложение демонстрирует работу с системными Intent.

![systemintStart](https://github.com/user-attachments/assets/9fb2effc-bce4-44d5-b9b1-539dbabb409c)

Добавлены действия:

Открытие звонилки по номеру (ACTION_DIAL),

![systenintCall](https://github.com/user-attachments/assets/e1fdfabe-8329-4f12-97df-b456454da0a7)

Открытие веб-браузера (ACTION_VIEW),

![systemintBrowser](https://github.com/user-attachments/assets/3f1144cd-e472-4036-83cc-32a6fa109cdf)

Запуск карты по координатам (geo: URI).

![systemintMap](https://github.com/user-attachments/assets/0f7dc1d5-84e8-4226-b50e-4cb23e7e2076)

Обработка реализована через методы onClick.


Модуль favoritebook


Двухэкранное приложение. Первый экран содержит кнопку и TextView.

![favoritebookStart](https://github.com/user-attachments/assets/140c89d5-f97f-458f-b21c-5b76efdceb32)

Второй экран позволяет пользователю ввести любимую книгу и цитату.

![favoritebookData](https://github.com/user-attachments/assets/295fb07b-b2c6-415c-b489-089f0ac24def)
![favoritebookEnd](https://github.com/user-attachments/assets/63f54358-f15d-45cd-ab2a-266f324d6f19)

Передача данных реализована через Intent и ActivityResultLauncher.



Модуль sharer


Приложение реализует отправку данных через Intent.ACTION_SEND и выбор изображений через ACTION_PICK.

![sharerSelectPhoto](https://github.com/user-attachments/assets/9d334a54-5250-49f9-a0b6-02c3877919c1)
![sharerShareText](https://github.com/user-attachments/assets/0fb4e535-19f2-463a-ae73-eb0e070871fe)

Используется Intent.createChooser, EXTRA_TEXT, и ActivityResultLauncher для обратного получения результата.
Навигация осуществляется с учётом MIME-типов.

Модуль intentapp

![intappStart](https://github.com/user-attachments/assets/9c4b4646-bf42-46c4-8782-c891452b8c5e)

Приложение получает текущее системное время, форматирует его, и передаёт между двумя активностями.
В первой активности вычисляется строка с квадратом номера в списке группы и текущим временем.
Данные передаются во вторую активность через Intent, где отображаются в TextView.

![intappTime](https://github.com/user-attachments/assets/92dcc8c8-78ec-48d3-82d6-0e276d1f3bf2)

Модуль mireaproject


Создано приложение на основе шаблона Navigation Drawer Activity.
Добавлены два новых фрагмента:

![mireaprojMenu](https://github.com/user-attachments/assets/52404e2e-2fd0-4872-b26f-ce7c21976ada)

DataFragment — тематическая информация по 3D-моделированию, оформленная в Material-стиле.

![mireaprojData](https://github.com/user-attachments/assets/bde5b513-0568-4a44-96ad-2502baee2af9)

WebViewFragment — встроенный браузер на WebView с открытием сайта mirea.ru.

![mireaprojBrowser](https://github.com/user-attachments/assets/31d59266-511b-4df5-9c41-4bbb5220a494)


Фрагменты зарегистрированы в mobile_navigation.xml, activity_main_drawer.xml, и подключены в AppBarConfiguration.
