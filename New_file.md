# git版本控制

## git基本操作
![](https://i.imgur.com/6L7DTfF.png)

初始化這個目錄，讓 Git 對這個目錄開始進行版控

```$ git init ```

查詢現在這個目錄的「狀態」

```$ git status```

建立一個內容為 “hello, git” 並命名為 welcome.html 的檔案

```$ echo "hello, git" > welcome.html```

把檔案交給 Git
* 單一檔案加入索引：```git add <檔案名稱>```
* 所有檔案加入索引：```git add .```

提交版本

```git commit -m "填寫版本資訊"```

瀏覽歷史紀錄

```git log```

push到遠端數據庫

![](https://i.imgur.com/UFtEQul.png)


![](https://i.imgur.com/zBqRbny.png)
1. 當我們建立數據庫後，立即新增一個檔案時，用 ```git status``` 查詢，會發現它是在 ```Untracked``` 狀態。

1. 這就表示此檔案還沒進到版本控制，藉由 ```git add .``` 將檔案加入到索引 (Staged) 後，準備提交成一個 commit 版本。

1. 藉由 ```git commit -m <提交訊息>``` 後，該檔案就會開始被追蹤，檔案狀態就會變成 ```UnModified``` 狀態。

https://w3c.hexschool.com/git/7ca21e02

## SourceTree
[https://hackmd.io/@09oU3M6LTU-7M6MkSqRj1A/Bymbf3y2?type=view](https://)

## Github
GitHub Pages - 建立靜態網站

[https://w3c.hexschool.com/git/21756c99](https://)
