# MinecraftModByRadchuk

## Шаблон для создания модов Minecraft Forge

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.20.1-green)
![Forge Version](https://img.shields.io/badge/Forge-47.2.0-orange)
![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red)

## Описание

Этот проект представляет собой шаблон для создания модов Minecraft с использованием Forge API. Шаблон настроен для Minecraft 1.20.1 и содержит базовую структуру, необходимую для начала разработки собственного мода.

## Особенности шаблона

- Полностью настроенная среда разработки для Forge 47.2.0
- Интеграция с Lombok для уменьшения шаблонного кода
- Настроенные конфигурации для запуска клиента, сервера и тестов
- Поддержка официальных маппингов Mojang
- Готовая структура проекта с основными классами
- Настройки EditorConfig для поддержания единого стиля кода

## Структура проекта

```
MinecraftModByRadchuk/
├── gradle/                  # Файлы для работы Gradle
├── src/
│   ├── main/
│   │   ├── java/            # Исходный код мода
│   │   │   └── com/minecraft/mod/by/radchuk/
│   │   │       ├── common/  # Общие классы и конфигурация
│   │   │       └── MinecraftModByRadchuk.java  # Основной класс мода
│   │   └── resources/       # Ресурсы мода (текстуры, модели, звуки и т.д.)
│   └── generated/           # Автоматически сгенерированные ресурсы
├── .editorconfig            # Настройки форматирования кода
├── build.gradle             # Скрипт сборки Gradle
├── gradle.properties        # Свойства проекта и версии зависимостей
├── gradlew                  # Скрипт запуска Gradle для Linux/Mac
├── gradlew.bat              # Скрипт запуска Gradle для Windows
└── settings.gradle          # Настройки проекта Gradle
```

## Начало работы

1. Клонируйте репозиторий:
```bash
git clone https://github.com/yourusername/MinecraftModByRadchuk.git
cd MinecraftModByRadchuk
```

2. Настройте среду разработки:
```bash
# Для Windows
gradlew genEclipseRuns
# или
gradlew genIntellijRuns

# Для Linux/Mac
./gradlew genEclipseRuns
# или
./gradlew genIntellijRuns
```

3. Импортируйте проект в вашу IDE (Eclipse, IntelliJ IDEA и т.д.)

4. Запустите клиент Minecraft для тестирования:
```bash
# Для Windows
gradlew runClient

# Для Linux/Mac
./gradlew runClient
```

## Настройка мода

1. Измените идентификатор мода и другие свойства в файле `gradle.properties`
2. Обновите основной класс мода `MinecraftModByRadchuk.java` и класс конфигурации
3. Добавьте свои блоки, предметы, сущности и другие компоненты мода

## Сборка мода

Для создания JAR-файла мода выполните:

```bash
# Для Windows
gradlew build

# Для Linux/Mac
./gradlew build
```

Готовый JAR-файл будет находиться в директории `build/libs/`.

## Использованные технологии

- [Minecraft Forge](https://files.minecraftforge.net/) - API для создания модов
- [Gradle](https://gradle.org/) - Система сборки
- [Lombok](https://projectlombok.org/) - Библиотека для уменьшения шаблонного кода
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Язык программирования

## Лицензия

Все права защищены. Этот проект предоставляется в качестве шаблона для обучения и разработки.

## Автор

Radchuk - разработчик мода

---

*Этот шаблон создан для упрощения начала разработки модов Minecraft и может быть свободно модифицирован для ваших нужд.*
