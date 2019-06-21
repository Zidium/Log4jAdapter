# ������� Zidium ��� ������� ����������� log4j

������� ��������� ���������� �� ���������� � ������� ����������� Zidium ��������� ������:

1. ������ �� �������
����� ��������� ������ ������������, ��� ��������� ��������� ������. ������ �� ������� �������� ��������� ����������. �� ��������� ������� ���������� ������������ �����������.

2. ���
��� �� �������� ��������� ����������, ������������ ������ ��� ������ �� ������� ��������.

����� ���������� ���� ����� ��������� ������ ������ �� ������� � ����, ���� ��� ����� ���������� ���������� �������� ��� ������, �� ����� ������������ Zidium API (https://github.com/Zidium/ApiJava).

## �����������

�������� ������ � ��� pom.xml:

    <dependency>
        <groupId>net.zidium</groupId>
        <artifactId>log4jAdapter</artifactId>
        <version>1.0.0</version>
    </dependency>

����������� ����� ����� ��������� ������.

## ��������� ����������� � Zidium
��� �������� �������� ����������� ������� ���� zidium.properties � ����� � ����������� jar-������.

� ����� zidium.properties ������� �������� ������ �������� � ��������� ���� �� ��:

    account=MYACCOUNT
    secretKey=7031880B-CCCD-4A05-A4DE-6AFADCD7BE6F

���� �� ����������� ���������� � ��� Zidium, � �� �������� ������, �� ������� ����� ����� ������ Api:

    url=http://localhost:61000/

����� ����� ������ ��� ��������� ����������. ��� ����� � ����� ������ ������ ���������� ��������� ���:

    IZidiumClient client = new ZidiumClient("MYACCOUNT", "7031880B-CCCD-4A05-A4DE-6AFADCD7BE6F", "http://localhost:61000/");
    ZidiumClient.setDefault(client);

## ��������� log4j

����������� ���� log4j2.xml ��� ������ ��������:

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

����� �������� �������� ��� appender:

- *ZidiumLogs* ������������ ��� �������� ���� � Zidium
- *ZidiumEvents* ������������ ��� ����������� ������

���� ��� ����� ������ ���-��� ��� ������ ������, �� �������� appender ����� �� ����������.

������� ���������� ���������� Zidium Api *��������� �� ���������* (defaultComponent). ��� ����� ��������� � ����� ������ ������ ����������:

    IComponentControl component = ZidiumClient.getDefault().getRootComponentControl().getOrCreateChild("MyComponent");
    LoggerToComponentMap.setDefaultComponent(component);

����� ����� �������� ��������� �� Id, ���� �� �������� �������:

    IComponentControl component = ZidiumClient.getDefault().getComponentControl("...");
    LoggerToComponentMap.setDefaultComponent(component);

## �������������

### �������� ������

��� �������� ������ � Zidium - ������ �������� ������ � ���:

    try {
        ...
    }
    catch (Exception exception) {
        LogManager.getLogger().error(exception);
    }

����� ���������� ������ ����������, ��������� � ������ *������* ������� �������� � �������� ������ ���������.
�������� �� �������� ������, ����� ������� ����� ��������� ����������.

### �������� �����

��� �������� ��������� ���� � Zidium - ������ ����������� ������ ��������� � ���:

    LogManager.getLogger().info("Message");

����� ���������� ������ ����, ��������� � ������ *���* ������� ��������.
�������� ������ ��������� � ������ � ������� ������ *�����*.
����� �������� ������ ���� �� ����� ����������.
������� �� ������ ����, ����� ���������� � ��������.
����������� ������� � ������� ����� ��������, ����� �������� ������ ����, ������� ��� �����.

### �������� ����������� � ��������

� �������� ���� ��� ������� ����� � ���� default ���������. ������ ��� ������ ��������.
� ����� ������� ����� ��������� �������������� ��������� ����������� �������:

    IComponentControl component = ZidiumClient.getDefault().getRootComponentControl().getOrCreateChild("MySpecialComponent");
    LoggerToComponentMap.add("SpecialLogger", component);

������ ������ � ������ "Special" ����� ������������ ��������� ���������:

    LogManager.getLogger("SpecialLogger").info("Message");

## ��������������� ������

��� ������ ����������� NetBeans IDE 8.2 ��� ����.
������ ���������� Maven ������ 3.0.5.

### ���������� ����-������

����� �������� ������ ����� � ����� src\test\resources\zidium.properties ������� ��������� ������ ��������� ��������.

���� ��� ����������� ������ ��������� ���������� ������ � ������� Smoke testing.