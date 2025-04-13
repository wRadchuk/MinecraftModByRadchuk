# MinecraftModByRadchuk

## Шаблон для создания модов Minecraft Forge с расширенными абстракциями

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.20.1-green)
![Forge Version](https://img.shields.io/badge/Forge-47.2.0-orange)
![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red)
![Version](https://img.shields.io/badge/Version-0.0.2-blue)

## Описание

Этот проект представляет собой расширенный шаблон для создания модификаций Minecraft с использованием Forge API. Шаблон настроен для Minecraft 1.20.1 и содержит набор полезных абстракций, которые значительно упрощают и ускоряют процесс разработки модов.

## Особенности шаблона

- Полностью настроенная среда разработки для Forge 47.2.0
- Набор абстракций для упрощения создания предметов, блоков и мобов
- Фабрики и билдеры для быстрого создания компонентов мода
- Корректная поддержка кириллицы в логах и сообщениях
- Интеграция с Lombok для уменьшения шаблонного кода
- Настроенные конфигурации для запуска клиента, сервера и тестов
- Поддержка официальных маппингов Mojang
- Готовая структура проекта с основными классами
- Настройки EditorConfig для поддержания единого стиля кода

## Ключевые абстракции

- **MobFactory** - фабрика для создания и регистрации мобов различных типов
- **ItemFactory** - фабрика для создания и регистрации предметов
- **BaseArmorMaterial** - универсальный класс для создания материалов брони
- **AttributesBuilder** - билдер для настройки атрибутов мобов
- **ModTabsRegistry** - утилита для создания и регистрации вкладок в творческом режиме

## Структура проекта

```
MinecraftModByRadchuk/
├── gradle/                  # Файлы для работы Gradle
├── src/
│   ├── main/
│   │   ├── java/            # Исходный код мода
│   │   │   └── com/minecraft/mod/by/radchuk/
│   │   │       ├── common/              # Общие классы и конфигурация
│   │   │       ├── custom_entities/     # Пользовательские мобы
│   │   │       ├── registry/            # Классы для регистрации компонентов
│   │   │       ├── utils/               # Утилиты и абстракции
│   │   │       │   ├── entities/        # Абстракции для работы с мобами
│   │   │       │   ├── items/           # Абстракции для работы с предметами
│   │   │       │   └── tabs/            # Утилиты для создания вкладок
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

## Примеры использования абстракций

### Создание и регистрация мобов

```java
// Получаем фабрику для нашего мода
MobFactory factory = MobFactory.getInstance(MinecraftModByRadchukConfiguration.MOD_ID);

// Регистрируем гуманоидного моба
RegistryObject<EntityType<CustomHumanMob>> CUSTOM_MOB_HUMAN = factory.createHumanoidMonster(
        "custom_human_mob",
        CustomHumanMob::new,
        MobCategory.MONSTER
    )
    .size(0.6F, 1.95F)
    .texture("textures/entity/custom_human_mob.png")
    .attributes(() -> AttributesBuilder.mob()
        .health(25.0D)
        .damage(4.0D)
        .speed(0.3D)
        .build())
    .build();

// Регистрируем фабрику в системе событий
factory.register(modEventBus);
```

### Создание материала брони

```java
// Создание брони с использованием билдера
ArmorMaterial customArmor = new BaseArmorMaterial.Builder("mymod", "custom_armor")
    .durabilityMultiplier(20)
    .protection(3, 6, 8, 3)
    .enchantability(15)
    .toughness(2.0f)
    .build();

// Или с использованием фабричных методов
ArmorMaterial enhancedDiamond = BaseArmorMaterial.Factory.diamondLike("mymod", "enhanced_diamond")
    .durabilityMultiplier(40)
    .enchantability(15)
    .build();
```

## Настройка мода

1. Измените идентификатор мода и другие свойства в файле `gradle.properties`
2. Обновите основной класс мода `MinecraftModByRadchuk.java` и класс конфигурации
3. Используйте предоставленные абстракции для создания компонентов мода

## Сборка мода

Для создания JAR-файла мода выполните:

```bash
# Для Windows
gradlew build

# Для Linux/Mac
./gradlew build
```

Готовый JAR-файл будет находиться в директории `build/libs/`.

## Системные требования

- Java 17 или выше
- Gradle 7+
- 3 ГБ оперативной памяти (минимум)

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
