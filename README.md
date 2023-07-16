# spring-weather-app
## Оглавление
* ### [Описание проекта](#defenition)
* ### [Введение](#introduction)
* ### [Функции](#mainfunctions)
* ### [Используемые технологии](#technologies)
* ### [Структура проекта](#structure)
* ### [Запуск проекта](#setup)
* ### [Применение](#usage)
* ### [Документация API](#api)

## Описание проекта <a name="defenition"></a>
Проект SpringWeather представляет собой веб-приложение, которое позволяет получать информацию о погоде в указанном городе в градусах Цельсия. Приложение использует различные провайдеры погоды, такие как OpenWeatherMap и Yandex.Weather, для получения данных о погоде. За основу проекта был использован фреймворк Spring Boot с использованием Maven.

## Введение <a name="introduction"></a>
SpringWeather предоставляет RESTful API, который позволяет клиентам получать информацию о погоде в заданном городе. Пользователь может указать название города и опционально выбрать провайдер погоды. Приложение возвращает текущую температуру и атмосферное явление для указанного города.

## Функции <a name="mainfunctions"></a>
Получение погоды по названию города в градусах Цельсия.
Поддержка различных провайдеров погоды (OpenWeatherMap, Yandex.Weather).
Возможность выбора провайдера погоды через параметр запроса.
## Используемые технологии <a name="technologies"></a>
* Java
* Spring Boot
* Maven
* PostgreSQL
* Hibernate
* Spring Data
* Spring RestTemplate
* Swagger (OpenAPI) для документации API
* Mockito
* JUnit

## Структура проекта <a name="structure"></a>
```
├───main
│   ├───java
│   │   └───com
│   │       └───example
│   │           └───springweather
│   │               ├───configure
│   │               │       Conf.java
│   │               │       OpenApiConfig.java
│   │               ├───controller
│   │               │       WeatherController.java
│   │               ├───entity
│   │               │       City.java
│   │               ├───exception
│   │               │       IncorrectServiceNameException.java
│   │               │       IncorrectСityNameException.java
│   │               ├───repository
│   │               │       CityRepository.java
│   │               └───service
│   │                       CityService.java
│   │                       OpenWeatherMapService.java
│   │                       WeatherService.java
│   │                       WeatherServiceAnnotation.java
│   │                       WeatherServiceFacade.java
│   │                       WeatherServiceRegistry.java
│   │                       YandexWeatherService.java
│   └───resources
│           application.properties
└───test
    ├───java
    │   └───com
    │       └───example
    │           └───springweather
    │               ├───controller
    │               │       WeatherControllerIntegrationTest.java
    │               └───service
    │                       CityServiceTest.java
    │                       WeatherServiceFacadeTest.java
    │                       WeatherServiceRegistryTest.java
    └───resources
            application-test.properties
            test-data.sql
```

## Запуск проекта <a name="setup"></a>
Для настройки и установки проекта, выполните следующие шаги:

* Убедитесь, что у вас установлена Java Development Kit (JDK).
* Склонируйте репозиторий проекта на свой локальный компьютер.
* Откройте проект в вашей среде разработки (например, IntelliJ IDEA или Eclipse).
* Установите все зависимости проекта, указанные в файле pom.xml.
* Создайте базу данных PostgreSQL и настройте соответствующие параметры в файле application.properties.
* Запустите приложение.


### Или

* Склонируйте репозиторий проекта на свой локальный компьютер.
* Установите docker.
* Введите в терминале в корневой папке команду:
```docker
docker-compose up
```
## Применение  <a name="usage"></a>
Для получения погоды в заданном городе, отправьте GET-запрос на эндпоинт `/weather/{CityName}.` Параметр `{CityName}` должен содержать название города.
Если вы хотите использовать определенного провайдера погоды, добавьте параметр запроса `serviceName` с именем провайдера.
Полученная погода будет возвращена в формате JSON с информацией о городе, широте, долготе, температуре и погодных условиях.
## Документация API <a name="api"></a>
Для получения документации по API приложения необходимо запустить приложение и перейти по адресу http://localhost:8080/swagger-ui.html.


Или в файле openapi.yaml и вставив все по ссылке: https://editor.swagger.io/


В Swagger документации описаны все доступные запросы и ответы на них.
