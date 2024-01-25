<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,model.*" %>
<%
List<User_Address> user_addresses = (List<User_Address>) request.getAttribute("user_addresses");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>住所の追加</title>
<style>
    .error-message {
        color: red;
    }
</style>
</head>
<body>
<div>
    <h2>住所の追加</h2>
    <a href="Main">戻る</a>
    <form action="AddAddressServlet" method="post">
        <label for="prefecture">都道府県:</label>
        <input type="text" id="prefecture" name="prefecture" required><br>
        <label for="city">市区町村:</label>
        <input type="text" id="city" name="city" required><br>
        <label for="number">番地　　:</label>
        <input type="text" id="number" name="number" required><br>
        <input type="submit" value="登録">
    </form>
</div>

<%-- エラーメッセージがセットされている場合に表示 --%>
<% if (request.getAttribute("errorMessage") != null) { %>
    <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
<% } %>

<br>
<div>
    <table border="1">
            <thead>
                
            </thead>
            <tbody>
                <% if (user_addresses != null && user_addresses.size() > 0) { %>
                <%int count = 1; %>
                    <% for(User_Address user_address : user_addresses) { %>
                    <tr>
                        <td width="30"height="40"><%= count++%></td>
                        <td width="250"height="40"><%= user_address.getAddress()%></td>
                        
                        
                            <% } %>
                        <% } %>
              </tbody>
    </table>
</div>
</body>
</html>
