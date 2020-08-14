# Square Employee Android app

The app displays a list of employees in a RecyclerView.  
MVVM was use RxJava and dagger to inject dependencies. The Square
retrofit to make rest api calls. The small images are lazy loaded and cached on the device.  
The app handles errors such as no internet connectivity, empty json, exception in making the call, malformed json and time out as required in the requirements.

