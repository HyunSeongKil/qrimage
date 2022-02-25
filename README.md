# QRCode 생성 & 기존 이미지에 표시하기

## 기본정보

- 작성자 : HyunSeongKil
- 작성일 : 2022-02-17

## 사용기술

- git 2.x
- java 11.x
- gradle 6.x
- qrcode 생성 라이브러리
- commandline cli 라이브러리
  . 실행옵션 처리하는 라이브러리
- 실행가능 jar 생성하기
  . gradle.build에서 implementation이 아닌 compile 사용해야 함
  . gradle.build의 fatJar 참고
- vscode
- vscode이용하여 빌드시 args 사용하기
  . launch.json 참고
- TDD
- properties파일 로드

## git

```
// at local
소스코드 생성
git init
git add .
git commit -m "message"

// at github
repository 생성

// at local
git remote add origin [URL]
git remote -v // 확인
git push --set-upstream origin master
git push
```

## 실행방법

```
java -jar app-all.jar -prop "프로퍼티파일 전체 경로"
예) java -jar app-all.jar -prop "c:\\temp\\app.properties"
```
