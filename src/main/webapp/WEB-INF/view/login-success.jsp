<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ログイン完了</title>
</head>
<body>
    <h2>ログインしました</h2>
    <p>Thank you for registering! <%= session.getAttribute("UserID") %></p>
     
    <a href="Main">メイン画面</a>
</body>
</html>
