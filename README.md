# What is the Movie App 3.0.0?
It's a code sample for a movie app that display the movies in MVI architecture pattern.

# Requirements
Android application that displays the list of trending movies, when we select one movie it shows more details about it.

# User Stories
#### 1. As a user I wanna open the movie app, SO THAT I expect to see a list of movies.
##### Acceptance criteria
* The movie name, picture and rate should be displayed.
* The movies pages should be loaded once the user scroll down to the last item by displaying loader under the movies list.
* If the movies list is empty, a message should be displayed to the user that no movies available at this moment.
* If any error happened, a error message show be displayed to the user with a retry button.
* The user should be able to swipe to refresh to reload date from scratch.
---
#### 2. As a user I wanna tab on any movie in the list, SO THAT I expect to see the movie details.
##### Acceptance criteria
The Movie screen should contains:
* Movie Title
* Movie Picture
* Movie Rate
* Movie Overview

# Architecture Pattern
MVI is the architecture pattern of the app.
 
 # Libraries 
* Coroutines
* Compose
* Retrofit
* ViewModel
* Hilt
* Kotlin
* Coil
* Mockk

# Unit Tests
* Implement unit test for the view-models to test the logic of the app [MovieDetailsViewModelTest] and [MoviesViewModelTest].
