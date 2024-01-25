<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン画面</title>
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

        .register-link {
            text-align: center;
            margin-top: 20px;
            /* 追加 */
            width: 85%;
        }
    </style>
</head>
<body>
    <div>
    <br>
    <br>
        <h2>ログイン</h2>
        <form action="login" method="post">
            <label for="userid">ユーザーID:</label>
            <input type="text" name="userid"/><br>

            <label for="password">パスワード:</label>
            <input type="password" name="password" /><br>

            <input type="submit" value="ログイン" />
        </form>

        <%-- エラーメッセージがセットされている場合に表示 --%>
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p><%= request.getAttribute("errorMessage") %></p>
        <% } %>
        
        <div class="register-link">
            <a href="register">アカウント作成はこちら</a>
        </div>

</body>
</html>