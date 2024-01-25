<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登録画面</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
            font-family: 'Arial', sans-serif;
        }

        div {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 20px; /* Increased margin-bottom for better spacing */
        }

        form {
            text-align: center;
            margin-top: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
        }

        input {
            padding: 8px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        p {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
        
    </style>
</head>
<body>
    <div>
        <%-- エラーメッセージがセットされている場合に表示 --%>
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p><%= request.getAttribute("errorMessage") %></p>
        <% } %>

        <h2>登録情報を入力</h2>
        <form action="register" method="post">
            <label for="userid">ユーザーID:</label>
            <input type="text" id="userid" name="userid" required><br>
            
            <label for="address">住所:</label>
            <input type="text" id="address" name="address" required><br>

            <label for="password">パスワード:</label>
            <input type="password" id="password" name="password" required><br>

            <input type="submit" value="登録">
        </form>
        <div class="login-link">
            <a href="login">ログインはこちら</a>
        </div>
    </div>
</body>
</html>
