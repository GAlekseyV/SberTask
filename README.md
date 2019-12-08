Реализован SomeClass с методом doRefresh, который находит все поле в объекте класса помеченные аннотацией @Property и заполняет поля значениями сохраненными в файле. Имя файла хранится в поле filenameForRefresh объекта и задается методом setFileForRefresh(filename).

Для сборки проекта используется Gradle. Для запуска необходимо выполнить команду без аргументов

```
.\gradlew run
```

или с аргументом - именем файла со свойствами

```
.\gradlew run --args filename.propsrc/main/resources/someClass.properties
```

Для запуска программы без Gradle необходимо распаковать архив, расположенные в папке build/distributions и запустить SberTask.bat файл с параметром (если необходимо):

```
SberTask.bat fileName.properties.
```

Лог работы программы сохраняется в папке пользователя, для windows 10 это:
C:\Users\username\javaLogs.