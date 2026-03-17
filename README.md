# Конвертер Файлів

Потужний Java CLI інструмент для конвертації файлів між форматами JSON, XML та CSV.

## Можливості

- **Двонаправлена конвертація**: Конвертація між будь-якими підтримуваними форматами
- **Автовизначення**: Автоматично визначає формати вводу/виводу за розширеннями файлів
- **Ручне перевизначення**: Вкажіть формати вручну за потреби
- **Підтримка Unicode**: Повна підтримка кодування UTF-8
- **Обробка помилок**: Комплексні повідомлення про помилки та валідація
- **Шаблони проєктування**: Реалізовано шаблони Factory, Strategy та Facade
- **Добре протестовано**: Комплексні модульні тести з високим покриттям

## Підтримувані формати

- **JSON** (`.json`)
- **XML** (`.xml`)
- **CSV** (`.csv`)

## Вимоги

- Java 21 або вище
- Maven 3.6 або вище

## Встановлення

1. Клонуйте репозиторій:
```bash
git clone https://github.com/oyartsev-cloud/java-file-converter.git
cd java-converter
```

2. Зберіть проєкт:
```bash
mvn clean package
```

3. Виконуваний JAR буде створено за адресою: `target/file-converter-1.0.0.jar`

## Використання

### Базове використання

```bash
java -jar target/file-converter-1.0.0.jar <вхідний_файл> <вихідний_файл>
```

Конвертер автоматично визначає формати за розширеннями файлів:

```bash
# Конвертація JSON в XML
java -jar target/file-converter-1.0.0.jar data.json output.xml

# Конвертація CSV в JSON
java -jar target/file-converter-1.0.0.jar input.csv output.json

# Конвертація XML в CSV
java -jar target/file-converter-1.0.0.jar data.xml output.csv
```

### Розширене використання

Вкажіть формати вручну:

```bash
java -jar target/file-converter-1.0.0.jar input.txt output.json --input-format xml --output-format json
```

### Опції командного рядка

- `<вхідний_файл>`: Шлях до вхідного файлу (обов'язково)
- `<вихідний_файл>`: Шлях до вихідного файлу (обов'язково)
- `-i, --input-format`: Вкажіть вхідний формат (json, xml, csv)
- `-o, --output-format`: Вкажіть вихідний формат (json, xml, csv)
- `-v, --verbose`: Увімкнути детальний вивід
- `-h, --help`: Показати довідку
- `--version`: Показати інформацію про версію

### Приклади

#### JSON в XML
```bash
java -jar target/file-converter-1.0.0.jar users.json users.xml
```

Вхідний файл (`users.json`):
```json
{
  "users": [
    {
      "name": "John Doe",
      "email": "john@example.com",
      "age": 30
    },
    {
      "name": "Jane Smith",
      "email": "jane@example.com",
      "age": 25
    }
  ]
}
```

Вихідний файл (`users.xml`):
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
  <users>
    <item_0>
      <name>John Doe</name>
      <email>john@example.com</email>
      <age>30</age>
    </item_0>
    <item_1>
      <name>Jane Smith</name>
      <email>jane@example.com</email>
      <age>25</age>
    </item_1>
  </users>
</root>
```

#### CSV в JSON
```bash
java -jar target/file-converter-1.0.0.jar products.csv products.json
```

Вхідний файл (`products.csv`):
```csv
name,price,category
Laptop,999.99,Electronics
Book,19.99,Education
Coffee,4.99,Food
```

Вихідний файл (`products.json`):
```json
[ {
  "name" : "Laptop",
  "price" : "999.99",
  "category" : "Electronics"
}, {
  "name" : "Book",
  "price" : "19.99",
  "category" : "Education"
}, {
  "name" : "Coffee",
  "price" : "4.99",
  "category" : "Food"
} ]
```

#### XML в CSV
```bash
java -jar target/file-converter-1.0.0.jar inventory.xml inventory.csv
```

## Архітектура

Проєкт слідує принципам чистої архітектури та реалізує кілька шаблонів проєктування:

### Шаблони проєктування

1. **Шаблон Factory**: `ParserFactory` та `GeneratorFactory` створюють відповідні парсери та генератори на основі формату файлу.

2. **Шаблон Strategy**: Інтерфейс `ConversionStrategy` дозволяє реалізовувати різні стратегії конвертації.

3. **Шаблон Facade**: `FileConverterFacade` надає спрощений інтерфейс для операцій конвертації файлів.

### Структура пакетів

```
com.fileconverter/
├── cli/                    # CLI інтерфейс та фасад
├── conversion/            # Стратегії конвертації
├── exception/             # Кастомні винятки
├── generator/             # Генератори файлів (JSON, XML, CSV)
├── parser/               # Парсери файлів (JSON, XML, CSV)
└── utils/                 # Утилітарні класи
```

## Тестування

Запустіть тестовий набір:

```bash
mvn test
```

Запустіть тести з покриттям:

```bash
mvn clean test jacoco:report
```

Звіт про покриття тестів буде згенеровано за адресою `target/site/jacoco/index.html`.

## Обробка помилок

Конвертер надає детальні повідомлення про помилки для:
- Непідтримуваних форматів файлів
- Недійсного синтаксису файлів
- Відсутніх файлів
- Проблем з дозволами
- Проблем з кодуванням

## Залежності

- **Jackson**: Обробка JSON та XML
- **OpenCSV**: Обробка CSV
- **Picocli**: Інтерфейс командного рядка
- **JUnit 5**: Модульне тестування
- **Mockito**: Мокування для тестів

## Збірка

### Розробницька збірка

```bash
mvn clean compile
```

### Продакшн збірка

```bash
mvn clean package
```

### Пропустити тести

```bash
mvn clean package -DskipTests
```

## Ліцензія

Цей проєкт є відкритим і доступним під ліцензією MIT.

## Внесок

1. Зробіть форк репозиторію
2. Створіть гілку функціоналу
3. Зробіть свої зміни
4. Додайте тести для нової функціональності
5. Запустіть тестовий набір
6. Надішліть pull request

## Усунення несправностей

### Поширені проблеми

1. **"Could not detect input file format"**
   - Переконайтеся, що вхідний файл має підтримуване розширення (.json, .xml, .csv)
   - Або вкажіть формат вручну за допомогою `--input-format`

2. **"Cannot read input file"**
   - Перевірте дозволи файлу
   - Переконайтеся, що файл існує і доступний для читання

3. **"Failed to parse JSON/XML/CSV file"**
   - Перевірте, що синтаксис файлу є дійсним
   - Перевірте наявність проблем з кодуванням

4. **Проблеми з пам'яттю з великими файлами**
   - Збільшіть розмір купи JVM: `-Xmx2g`
   - Розгляньте обробку файлів частинами (майбутнє покращення)
