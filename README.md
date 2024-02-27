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
