# Small World Test

This test project contains all functionality that was requested in the document, along with some unit tests and UI tests for the activity that the project contains. More details are mentioned below.

### Developed Using
- Kotlin
- XML base UI
- Hilt
- ViewModel
- Coroutines 
- Flow
- Room
- Retrofit
- Paging 3
- Databinding

### Tests written for
- Data layer
  - Local Database
  - Retrofit Service
- ViewModel

## Architecture
Application is built using Clean-MVVM and Modern Android Architecture, consisting of Repository layer, which uses local (Room DB) and remote (Retrofit) data sources, ViewModel and a UI layer (fragments). The ViewModel and UI communicate through state and events somewhere and somewhere directly.
Separate Data classes have been made for each layer and data source.
- Entity for Room DB (can be mapped to Domain Model)
- DTO for Retrofit (can be mapped to Entity/Database Model)
- Normal data class used for displaying data
![system_design (1)](https://raw.githubusercontent.com/nmnfaisal/Movie/master/movie-app-png-drawio.png)

## References:
**Modern Android Architecture**: https://developer.android.com/topic/architecture

**Repository Pattern:** https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern#0

**Clean-MVVM:** https://www.youtube.com/watch?v=8YPXv7xKh2w&t=1322s **by Philipp Lackner**

**Testing:**

https://developer.android.com/training/data-storage/room/testing-db

