# RestaurantsMap

[![Build Status](https://travis-ci.com/grigoryfedorov/RestaurantsMap.svg?branch=master)](https://travis-ci.com/grigoryfedorov/RestaurantsMap)

WARNING: 

This application use a kind of Clean Architecture approach. 
Kotlin Coroutines with Retrofit and Okhttp used for threading. 
ViewModel and LiveData used for the presentation layer. 
Single activity approach used with fragment navigation base on shared ViewModel
DI made simply by hands. 

Something new or interesting used in this project:
* Passing layout id to activity constructor
* Fragment factory and dependency injection in fragment constructor
* FragmentContainerView layout
* Location permission obtained using new [Activity Result API](https://developer.android.com/training/basics/intents/result)
* Travis CI: lint and detekt checks break the build on warnings
