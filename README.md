# Docker環境構築
```
docker pull java
docker run -v ~/Desktop/shining/java_env: --name java_env -i -t java
```

```
cd daiki/share
```

# 環境変数CLASSPATHを設定。
```
export CLASSPATH=./:./lib/commons-lang3-3.9.jar:./lib/commons-cli-1.4.jar
export CLASSPATH=./:./lib/commons-cli-1.4.jar
```

# 以下はメモ。
コンパイル時。クラスパスを通す
```
javac -classpath ./lib/commons-lang3-3.9.jar:./lib/commons-cli-1.4.jar Main.java
```

起動時。クラスパスを通す
```
java -classpath ./:./lib/commons-lang3-3.9.jar:./lib/commons-cli-1.4.jar Main test
```
