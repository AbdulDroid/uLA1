![Android Build](https://github.com/AbdulDroid/uLA1/workflows/Android%20Build/badge.svg)

This project contains an implementation of MVVM based on requirement for an Assessment 


## Features
* MVVVM
* Kotlin Coroutines with Flow with LiveData for ViewModel and View Layers
* Dagger Hilt for Dependency Injection
* Retrofit for Network calls
* Picasso for Image loading
* GitHub actions for CI

## Prerequisite
To build this project, you require:
- Android Studio 4.0.1 or higher
- Gradle 6.1.1

## Note: 
  The Icon field in the object was used throughout the app for the images which seemed different from what was on the design as the thumbnail urls were not provided per lesson.  

### The App is broken down into five main packages 
* di
* model
* navigation
* view
* viewmodel

This abstraction enables me to separate concern of the project into separate packages
#### Model 
- The model package contains all the logic for data acquisition and processing this is further broken into four subpackages
* local
* models
* remote
* repositories

##### Local
- This package contains all the abstraction for my offline data management.
- I decided to break the returned data into their separate database tables to optimize data access from the database.
- I added a separate table and model named RecentlyViewed to hold lessons recently viewed by the user as using the same model will mean the data will be cleared each time the user refreshes their data from the server due to my conflict resolution strategy for my lesson table.
- I used a 1-to-1 relation to retrieve recently viewed lessons, leveraging Room's @Relation and @Embedded annotations, and a new data class named RecentlyViewedWithSubject was created as such.
- I used a 1-to-many relation to retrieve the chapters with their corresponding lessons, leveraging the same Room annotations as mention above and a new data class called ChapterWithLessons was created to house this.
  
##### Remote
- This package contains all abstractions for the remote functionalities of the app.
- I added a network checker that tries to access the base address from the given URL and only when connection is successful to this address is the user device deemed to be connected to an active internet source.
  
##### Repositories
- This package holds the repository classes. Its holds the logic that combines the data from the remote layer and the local layer.
- This data received from the network is stored directly to the db and emitted back to the UI again. 

##### Models
- This package holds all data classes used in the remote package.

##### View 
- This package contains all the UI classes and implementations to proper display data as requested in the designs shared.


##### ViewModel 
- This package contains all view-model classes. 


##### Navigation
-This package contains the abstraction for navigation all through the app.

#### Unit Testing 
- I was able to test the model and viewmodel packages of the app. 
 
#### CI/CD
- A Simple Pipeline was created to run build, run the test. 
- Achieved using Github Actions 

#### Linting 
- Linting was setup for the app Module using https://github.com/jlleitschuh/ktlint-gradle
- Configured in all modules.


#### Hilt 
- Hilt was chosen as the DI choice for this project with time being a factor as it has easy setup with less code and easy to use. 

#### Kotlin Coroutines 
- This was used in the projects for our network calls and asynchronous task. 
- their Usage can be found in the repository and viewmodel classes.  

 

