Отчёт по практической работе №6.

В каждом модуле, где это требовалось, скриншот или файл были добавлены в директорию raw, которая находится в папке res.

Модуль app.


В данном модуле было необходимо реализовать сохранение информации введённой в текстовые поля через getSharedPreferences.
![appStart](https://github.com/user-attachments/assets/b7195ff6-7363-427d-8b4b-853672479281)
![appEnd](https://github.com/user-attachments/assets/2e974134-3576-40ae-b296-0c13e79a24d0)


Модуль SecureSharedPreferences.

Было необходимо отобразить портрет и ФИО любимого поэта. В моём случае Михаил Юрьевич Лермонтов. Фото и ФИО необходимо было сохранить в файл, предварительно закодировав данные, что делается с помощью EncryptedSharedPreferences.
![poet](https://github.com/user-attachments/assets/b56702ef-7b41-4857-bf10-8f9c8df2986d)


Модуль InternalFileStorage.


Нужно было создать экран ввода и сохранения текста в текстовый файл. По заданию, необходимо было написать памятную дату для России.
![9mayStart](https://github.com/user-attachments/assets/de1dbe21-705a-4a22-b0cf-7638c6cb34e5)
![9maySave](https://github.com/user-attachments/assets/bc9af481-1f66-4a62-a56b-fde27ab42b41)
![9mayEnd](https://github.com/user-attachments/assets/46304eb2-2dc4-490f-b79f-951dc383eb7d)


Модуль Notebook.


В данном модуле было необходимо создать интерфейс позволяющий записать название файла, его содержимое и сохранить его, либо же выгрузить содержимое уже существующего файла.
![notebookSave](https://github.com/user-attachments/assets/e0615c66-0052-4c31-9adc-64e4895fa29a)
![notebookFiles](https://github.com/user-attachments/assets/58ef10f8-bcbd-40cc-9901-bec2e9fd2a87)
![notebookLoad](https://github.com/user-attachments/assets/ea9130a5-126f-4781-ad5c-f06a6c4af4fd)
![notebookLoad2](https://github.com/user-attachments/assets/24deabfd-85e7-4642-8dfa-6520ad58316e)


Модуль EmployeeDB.


Было необходимо создать БД в которой будет храниться информация о супергероях. Было сделано 4 записи в БД. Первая запись была сначала отредактирована, после чего удалена, что было отражено в логах.
![Db](https://github.com/user-attachments/assets/09cda7ff-2c6d-4177-837e-880499aeff25)


MireaProject.


В данном проекте было решено реализовать простое заполенние профиля (имя и email) с последующим сохранением информации.
![mireaProjProfile](https://github.com/user-attachments/assets/24dd0c99-9f80-4c9f-b210-3c2828a48962)
![mireaProjProfileFile](https://github.com/user-attachments/assets/ced68650-3a66-4731-8670-8c30ca21f7da)


А так же был создан простой редактор текста, который позволяет создавать, сохранять и редактировать текстовые файлы. Все созданные файлы появляются на экране, по любому из них можно тапнуть, что позволит изменить содержимое. Файлы сохраняются в директорию: 
```
/storage/emulated/0/Android/data/ru.mirea.khudyakovma.mireaproject/files/Documents/Doc
```
![mireaProjRedNewFile](https://github.com/user-attachments/assets/2c82a609-a26b-4625-88a2-86dc35d3db95)
![mireaProjRedNewFile2](https://github.com/user-attachments/assets/ecbec804-eb2f-4236-aacb-fd3b0db2d0a5)
![mireaProjRedFile](https://github.com/user-attachments/assets/9f2aaa2f-cf9a-4781-bd17-c8fcab1eca03)
![mireaProjRedNewFile3](https://github.com/user-attachments/assets/5aca171d-52ad-4306-8992-b9b44008d40f)
![mireaProjRedNewFile4](https://github.com/user-attachments/assets/3ca5ac03-a1af-4a22-9b45-5c4c3d7d159d)
