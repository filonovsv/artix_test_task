# Тестовое задание ARTIX

Используемые технологии:

Java 21  
Spring Boot 3.2.2  
Gradle 8.5

Запуск:

```
./gradlew bootRun
```

## Этап 1

&emsp; Для реализации выбрана база данных PostgreSQL.  
&emsp; На данном этапе создание схемы данных и инициализация происходит с использованием файлов schema.sql и data.sql.

### Документация API

#### Узнать баланс

##### request:

```
GET balance/{cardId}
```

##### response:

```
{
    "id": integer($int64),
    "amount": integer($int64)
}
```

#### Начислить/списать бонусы

##### request:

```
POST /change_balance
```

body:

```
{
    "id": integer($int64),
    "bonusCardId": integer($int64),
    "changeAmount": integer($int64),
}
```

##### response:

```
{
    "id": integer($int64),
    "amount": integer($int64)
}
```

#### Отменить начисление/списание бонусов

##### request:

```
POST /cancel_operation/{operationId}
```

##### response:

```
{
    "id": integer($int64),
    "amount": integer($int64)
}
```

#### Показать историю начисления/списания бонусов

##### request:

```
GET /history/{cardId}
```

##### response:

```
[
    {
        "id": integer($int64),
        "bonusCardId": integer($int64),
        "changeAmount": integer($int64),
    },
    ...
]
```

## Этап 2

&emsp; Для реализации миграций использовался инструмент Flyway.

## Этап 3

&emsp; Добавлена авторизация. Для получения баланса и истории операций необходимо быть владельцем карты или иметь роль
ADMIN. Начисление/списание бонусов и отмена операции доступна всем пользователем с ролью ADMIN.

## Этап 4
&emsp; Добавлены индексы на столбцы username таблицы authorities, bonus_card_id таблицы operations.