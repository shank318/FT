

### Architecture:-

1. The App follows MVP pattern using Dagger2 as dependency injection and RXJava(for async calls).
2. "feature" folder contains all the features of the app.
3. Every feature will have a View, Presenter, and Service where the presenter will talk to service to fetch data from Github APIs and pass it to the view via the interface.
4. Dagger2 is been used to provide Service as a dependency to Presenter.

### How it works:-

1. Login with Github
1. The app will fetch the github repos of logged in user and cache the data in Realm database.
2. Search is provided for searching public repositories.

### Key Points:-

1. I am using Realm database for caching the data to avoid downloading the user repos every time user ope. 
2. In search only top 10 results will be shown sorted by no of stars.

### TODO:-

There is no official SDK/library for GitHub Authentication in Android. I have found some code for Github auth and customised some parts( folder name - githubauth). The code can be improved. 

### TESTING:-

Unit/Integrations tests are given. 
