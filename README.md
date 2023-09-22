# Odessa Poker (Расписной покер)
#### _Developed by Timur Sultanov_
UPD:  
      - добавлена возможность сохранять игру, и продолжить ее с тем игровым состоянием котрое было при сохранении.
      - при новой игре не удаляются существующие игроки, а появляется удобный селектор где можно выделить желающих игроков, либо добавить новых.
      - доп. фронтенд часть для создания удобного и красивого инетрфейса.
![I](https://images.midilibre.fr/api/v1/images/view/63612b611917b6293f057c1c/large/image.jpg?v=1)
The project was created for personal use with family and friends and is presented as a result of my current skills.

The game takes place in real life, this program only simplifies the scoring system and other game nuances during the game.

## **Functionality:**

Main menu with 2 buttons: new game and continue. The clickability of the second depends on the presence of created players in the database. The menu also displays the leaderboard for the previous game.

The new game removes the entire list of players from the base, if they were there. Next, the number of players is set (2 <number<11), and the nicknames for each of them. Each entity "player" can be edited/deleted, that is, there is all the functionality of the CRUD. 

The game has 5 game modes:
- Default
- No trumps
- Misere
- Dark
- Gold

Each mode describes its unique features. More information about them and about the rules of the game can be found here: *[rules of the game](https://ru.wikipedia.org/wiki/%D0%9E%D0%B4%D0%B5%D1%81%D1%81%D0%BA%D0%B8%D0%B9_%D0%BF%D0%BE%D0%BA%D0%B5%D1%80#%D0%A1%D0%BF%D0%B5%D1%86%D0%B8%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5_%D0%B8%D0%B3%D1%80%D1%8B)*. 

At the end of the whole game, the winner is displayed, 2nd place, 3rd ... and so on, with the option to go back to the main menu.

In the leaderboard there is an "Info" column where you can see the results of all the past games of each player with a date.

### (RU)
Проект был создан в целях личного пользования в кругу семьи и друзей и выставлен как результат моих текущих навыков.

Игра проходит вживую, эта программа лишь упрощает систему подсчета очков и других игровых нюансов в процессе игры.

## Функционал:

Главное меню с 2 кнопками: новая игра и продолжить. Кликабельность второй зависит от наличия созданных игроков в базе данных. Также в меню отображается таблица лидеров за предыдущую партию.

Новая игра удаляет весь список игроков с базы, если они там были. Далее установливается количество игроков (2<количество<11), и никнеймы каждому из них. Каждую сущность "игрок" можно редактировать/удалить, то есть вшит весь функционал CRUD. 

В игре есть 5 игровых режима:
- Дефолтный
- Без козырей
- Мизер
- Темная
- Золотая

Каждый режим описывает свои уникальные возможности. Подробнее о них и о самих правилах игры можно узнать здесь: *[правила расписного покера](https://ru.wikipedia.org/wiki/%D0%9E%D0%B4%D0%B5%D1%81%D1%81%D0%BA%D0%B8%D0%B9_%D0%BF%D0%BE%D0%BA%D0%B5%D1%80#%D0%A1%D0%BF%D0%B5%D1%86%D0%B8%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5_%D0%B8%D0%B3%D1%80%D1%8B)*. 

По окончанию всей игры выводится победитель, 2 место, 3... и так далее, с возможностью перейти обратно в главное меню.

В таблице лидеров есть колонка "Инфо" где можно увидеть результаты всех прошедших игр каждого игрока с его датой.

## Skills:

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)  ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)  ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)  ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)  ![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)  ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)  ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)  ![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
