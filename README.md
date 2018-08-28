# AndroidSketchpad

Dynamically convert my [blogger blog](https://androidsketchpad.blogspot.com)
to Android App.

Take data from rest api in json format. Parse json data and saved locally.
Use Room database for sql database. Use data and draw ui. Saved data automatically sync
with server that control by settings.

Use paging for loading data. Data load with user scrolling. When user scroll app send request to the server and save data locally
in background. After complete loading user show data in the ui.

Search option is available, user can search for specific post. If user
click on bookmark icon then searched post will saved locally if it not already stored.

Follow material guideline completely, use card view, swipe to refresh and Recyclerview

Blog: [AndroidSketchpad](https://androidsketchpad.blogspot.com)

## Blog Introduction


## Api
Used Api: [Blogger Api 3](https://developers.google.com/blogger/docs/3.0/getting_started)

## Libraries
#### Core
1. Support Library 27
2. Architecture Component
3. Room
4. Paging
5. KTX

#### Firebase
1. Analytics
2. Crash
3. App Indexing
4. Invites
5. Remote config
6. Cloud messaging

#### 3rd parties
1. ThemeLibrary
2. Dagger
3. Timber
4. Eventbus
5. OkHttp3
6. Retrofit
7. Gson
8. Joda Time
9. Picasso
10. Toasty
11. UCE-Handler
12. Shimmerlayout

## License
    Copyright {2018} {Shudipto Trafder}

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.





