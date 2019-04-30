# android-architecture-components-mvvm-kotlin

このプログラムソースは、以下のことを行なっています。
- android-architecture-components のサンプルコードをKotlinへコンバート
- ivacf/archiのMVVM (https://github.com/ivacf/archi/tree/master/app-mvvm)

## このアプリについて
Androidのアーキテクチャの勉強の為、作ったサンプルソースで
新規にアプリを開発する際のベースとして使おうと考えています。
よくあるローカルデータベースとAPIから、データを取得して絞り込み検索まで出来るように作っています。
勉強がてらに作ったものなので、試しに、ローカルデータベースの取り扱いでRoomとLiveDataを使い
APIでの取り扱いで、RetrofitとBindingAdapterを使って、ロジックの比較が出来るようにしています。


## 使われている主な技術・ライブラリ

- AndroidX
- Room
- LiveData
- ViewModelProviders
- RxJava & RxAndroid
- Retrofit 2

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

