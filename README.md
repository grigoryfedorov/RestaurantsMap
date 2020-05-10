# RestaurantsMap

[![Build Status](https://travis-ci.com/grigoryfedorov/RestaurantsMap.svg?branch=master)](https://travis-ci.com/grigoryfedorov/RestaurantsMap)

WARNING: Google Maps and Foursquare APIs are used in this project. In order to get correctly working app you need to add your own API keys to local.properties file. You can use [local.properties.ci](https://github.com/grigoryfedorov/RestaurantsMap/blob/master/local.properties.ci) as sample.
For Google Maps check [this](https://developers.google.com/maps/documentation/embed/get-api-key) and [this](https://developers.google.com/maps/gmp-get-started) links. [Userless auth](https://developer.foursquare.com/docs/places-api/authentication/#userless-auth) used for Foursquare see [link](https://developer.foursquare.com/docs/places-api/getting-started/) to get client keys.

Key concepts:
* This application use a kind of Clean Architecture approach. 
* Kotlin Coroutines with Retrofit and Okhttp used for threading. 
* ViewModel and LiveData used for the presentation layer. 
* Single activity approach used with fragment navigation base on shared ViewModel
* DI made simply by hands. 

Something new or interesting used in this project:
* View Binding
* Fragment factory and dependency injection in fragment constructor
* Location permission obtained using new [Activity Result API](https://developer.android.com/training/basics/intents/result) 
* Passing layout id to activity constructor
* FragmentContainerView layout
* Travis CI: lint and detekt checks break the build on warnings
