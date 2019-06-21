# Адаптер Zidium для системы логирования log4j

Адаптер позволяет отправлять из приложения в систему мониторинга Zidium следующие данные:

1. Данные об ошибках
Перед отправкой ошибки группируются, что позволяет экономить трафик. Данные об ошибках изменяют состояние компонента. Об изменении статуса компонента отправляются уведомления.

2. Лог
Лог не изменяет состояние компонента, используется только для чтения из личного кабинета.

Через расширение лога можно отправить только данные об ошибках и логи, если вам нужно отправлять результаты проверок или метрик, то нужно использовать Zidium API (https://github.com/Zidium/ApiJava).

## Подключение

Добавьте раздел в ваш pom.xml:

    <dependency>
        <groupId>net.zidium</groupId>
        <artifactId>log4jAdapter</artifactId>
        <version>1.0.0</version>
    </dependency>

Используйте самую новую доступную версию.

## Настройка подключения к Zidium
Для хранения настроек рекомендуем создать файл zidium.properties в папке с выполняемым jar-файлом.

В файле zidium.properties укажите название вашего аккаунта и секретный ключ из ЛК:

    account=MYACCOUNT
    secretKey=7031880B-CCCD-4A05-A4DE-6AFADCD7BE6F

Если вы используете развёрнутый у вас Zidium, а не облачный сервис, то укажите также адрес службы Api:

    url=http://localhost:61000/

Можно также задать все настройки программно. Для этого в самое начало вашего приложения поместите код:

    IZidiumClient client = new ZidiumClient("MYACCOUNT", "7031880B-CCCD-4A05-A4DE-6AFADCD7BE6F", "http://localhost:61000/");
    ZidiumClient.setDefault(client);

## Настройка log4j

Минимальный файл log4j2.xml для работы адаптера:

    <?xml version="1.0" encoding="utf-8"?>
    <Configuration packages="net.zidium.log4jAdapter">
        <Appenders>
            <ZidiumLogs name="ZidiumLogs" />
            <ZidiumEvents name="ZidiumEvents" />
        </Appenders>
        <Loggers>
            <Root>
                <AppenderRef ref="ZidiumLogs" />
                <AppenderRef ref="ZidiumEvents" level="error" />
            </Root>
        </Loggers>
    </Configuration>

Пакет адаптера содержит два appender:

- *ZidiumLogs* используется для отправки лога в Zidium
- *ZidiumEvents* используется для мониторинга ошибок

Если вам нужен только веб-лог или только ошибки, то ненужный appender можно не подключать.

Адаптер использует функционал Zidium Api *компонент по умолчанию* (defaultComponent). Его нужно назначить в самом начале вашего приложения:

    IComponentControl component = ZidiumClient.getDefault().getRootComponentControl().getOrCreateChild("MyComponent");
    LoggerToComponentMap.setDefaultComponent(component);

Можно также получить компонент по Id, если он известен заранее:

    IComponentControl component = ZidiumClient.getDefault().getComponentControl("...");
    LoggerToComponentMap.setDefaultComponent(component);

## Использование

### Отправка ошибок

Для отправки ошибок в Zidium - просто запишите ошибку в лог:

    try {
        ...
    }
    catch (Exception exception) {
        LogManager.getLogger().error(exception);
    }

Чтобы посмотреть ошибки компонента, перейдите в раздел *Ошибки* личного кабинета и выберите нужный компонент.
Кликните по названию ошибки, чтобы увидеть более подробную информацию.

### Отправка логов

Для отправки сообщений лога в Zidium - просто выполняется запись сообщений в лог:

    LogManager.getLogger().info("Message");

Чтобы посмотреть записи лога, перейдите в раздел *Лог* личного кабинета.
Выберите нужный компонент в списке и нажмите кнопку *Найти*.
Будут показаны записи лога по этому компоненту.
Кликнув по записи лога, можно посмотреть её свойства.
Используйте фильтры в верхней части страницы, чтобы отобрать записи лога, которые вам нужны.

### Привязка компонентов к логгерам

В примерах выше все логгеры пишут в один default компонент. Иногда это бывает неудобно.
В таких случаях можно назначить индивидуальный компонент конкретному логгеру:

    IComponentControl component = ZidiumClient.getDefault().getRootComponentControl().getOrCreateChild("MySpecialComponent");
    LoggerToComponentMap.add("SpecialLogger", component);

Теперь запись в логгер "Special" будет использовать указанный компонент:

    LogManager.getLogger("SpecialLogger").info("Message");

## Самостоятельная сборка

Для сборки потребуется NetBeans IDE 8.2 или выше.
Проект использует Maven версии 3.0.5.

### Выполнение юнит-тестов

Перед запуском тестов нужно в файле src\test\resources\zidium.properties указать параметры вашего тестового аккаунта.

Пока что реализовано только небольшое количество тестов в формате Smoke testing.