# RemindMe

## Introduction
RemindMe is an Android based application. The goal of the application is to set a reminder and then be gently reminded when the time comes. It uses Java as a main language and since events have to be stored somewhere, and more specifically in a local database, it uses the Room database. I wrote it as a solo project idea where I wanted to test and showcase my skills for creating an application that somehow interacts with a database (in this case, a local one).

## How to install
The only way to intall and use the application is to download the whole git repository. For this, you will need either Android Studio or any IDE that gives the option to have a package for Android Studio (e.g. IntelliJ). After you download and open the project, you have to run it on an emulator (minimum api is 23, but api 30 is recommended). The application should start right after that.

## How to use
Right after the applicatioon starts, you will be directed to a window called 'Daily View'. At the beginning it should be empty because you still have not added any events to be reminded of. The way you do this is by clicking on the 'Add' button (top right). There you have to write the name of the event, its description, the time and date when you want to be reminded. If you click on 'Save' (top right), you will be redirected to the entry sscreen but this time you will see your event uploaded. If you decide to cancel this event, there are two cases: you are in the 'Add event' screen and you want to cancel everything, so you just click on 'Back' (top left button) and ypu go to the 'Daily Activity' screen. If you want to have the event deleted and you have already added it, then you just need to swipe left on the event.

## Technologies used

RemindMe uses a the following technologies to work properly:

- [SQL] - Pretty standard way of working with data
- [Java] - Whole project done on Java
- [Room] - The local database that is used for this application

## Limitations and future updates
The app is in its first version. It is very rough, starting from the ui and going all the way to the functionality. I believe that is important for the user to know the limitations of the app he uses and to see that these issues have been acknowledged by me as well. These are the current flaws:
Concerning the functionality of the app - there are many corner cases which need to be tested and covered:
- unable to set a date and hour that is already in the past
- when the reminder pops up, what happens after you push it? why isnt there a button to cancel?
- when an event is deleted from the database, is the alarm deleted as well?
- purpose of calendar view is to show the events on the selected date, right now not working properly

Concerning the UI:
- the ui needs a redesign so it can be more modern-looking. but a referesh of the look of the buttons (adding some drawbales) will help move the app in that direction 



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [sql]:  <https://en.wikipedia.org/wiki/SQL>
   [room]: <https://developer.android.com/jetpack/androidx/releases/room>
   [java]: <https://www.java.com/en/>
