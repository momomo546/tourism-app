# ゲーミフィケーションを利用した観光アプリ開発
## 概要
研究で開発している観光アプリです。  
ゲーミフィケーションの効果を利用し、再訪促進を目指す観光アプリ開発を行っています。 
研究で開発した「まわりみち兼六園」というアプリを発展させ、現在は兼六園より大きな規模の様々な観光地でも利用できるようなアプリを目指して開発中です。  
まわりみち兼六園のリポジトリは[こちら](https://github.com/momomo546/kenrokuapp)  
論文はこちらから
- [Development of mobile application to promote revisits to Kenrokuen using gamification theory](https://doi.org/10.37020/jgtr.9.1_67)
- [Verification of effect for tourism application ‘Mawari-michi Kenrokuen’ using gamification](https://doi.org/10.37020/jgtr.9.2_103)

論文では「まわりみち兼六園」というアプリとして書かれていますが、現在は兼六園より大きな規模の様々な観光地でも利用できるようなアプリを目指して開発中です。  
![2 (1)](https://github.com/user-attachments/assets/05c3b681-a813-4dd5-ae6d-3355b6cdfdf0)
![1 (1)](https://github.com/user-attachments/assets/75e3e781-470c-4020-b3ba-b230eb5648b2)

## 使用技術
- Kotlin
- Android Studio Koala | 2024.1.2
- Google Maps API

## 機能一覧
- 観光地の地図表示
- スポット（観光地の見どころ）の詳細表示
- スポットへの到達を検出
- ゲーミフィケーション関連機能
  - 観光地内の歩数計測
  - 回ったスポットの記録
  - アチーブメント（バッジ獲得）機能

## 今後の開発
このアプリは、兼六園再訪促進アプリ「まわりみち兼六園」を発展させ、以下の機能の実装・修正を予定しています。  
- 観光地選択機能
- アチーブメント機能の修正
  - 複数のアチーブメントを用意し、観光地ごとにアチーブメントを選択して利用できるように修正

可能であれば以下の修正を予定しています。
- アチーブメント、観光地データをAPIで取得してくるように変更
