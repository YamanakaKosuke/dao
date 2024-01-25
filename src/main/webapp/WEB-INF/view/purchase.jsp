<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, model.*" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
    List<Cart> cartItems = (List<Cart>) request.getAttribute("cartItems");
    String userID = (String) session.getAttribute("UserID");
    List<User_Address> user_addresses = (List<User_Address>) request.getAttribute("user_addresses");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>レジ画面</title>
    <style>
        table {
            float: left;
            margin-right: 20px; /* テーブル間の間隔を調整するために適切な値に変更してください */
        }

        #detail {
            float: left;
        }
    </style>
</head>
<body>
    <div align="left">
    <h1>レジ画面</h1>
    <table border="1">
        <thead>
            <tr>
                <th width="40" height="40">商品ID</th>
                <th width="250" height="40">商品名</th>
                <th width="100" height="40">価格($)</th>
                <th width="100" height="40">数量(個)</th>
                <th width="100" height="40">小計($)</th>
            </tr>
        </thead>
        <tbody>
            <% if (cartItems != null && cartItems.size() > 0) { %>
                <% double allcost = 0; %>
                <% for (Cart cartitem : cartItems) { %>
                    <%
                        int cartItemId = cartitem.getItemID();
                        for (Item item : items) {
                            if (item.getId() == cartItemId) {
                    %>
                                <tr>
                                    <td width="40" height="80"><%= item.getId() %></td>
                                    <td width="250" height="80"><%= item.getName() %></td>
                                    <td width="100" height="80"><%= cartitem.getPrice() %></td>
                                    <td width="100" height="80"><%= cartitem.getQuantity() %></td>
                                    <td width="100" height="80"><%= String.format("%.2f",cartitem.getPrice() * cartitem.getQuantity()) %> </td>
                                </tr>
                                <%
                                    double cost = cartitem.getPrice() * cartitem.getQuantity();
                                    allcost += cost;
                                %>   
                    <%
                                break; // 一致するアイテムが見つかったらループを終了
                            }
                        }
                    %>
                <% } %>
                <tr>
                    <td colspan="4" align="right">合計($)　</td>
                    <td><%= String.format("%.2f", allcost) %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
    
    </div>
           
    <br>
            
    <!-- Delivery and Payment Table -->
    <div id="detail"  align="center">
        <form id="deliveryLocationForm" action="PurchaseCheckServlet" method="post">
            <table border="1">
                <thead>
                    <tr>
                        <th width="100" height="40">お届け先</th>
                        <th width="100" height="40">お支払い方法</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td width="100" height="60">
                            <select name="deliveryLocation" >
                                <option value="location1">お届け先を選択</option>
                                <% if (user_addresses != null && user_addresses.size() > 0) { %>
                                    <% for(User_Address user_address : user_addresses) { %>
                                           <option value="<%= user_address.getAddress()%>"><%= user_address.getAddress()%></option>
                                     <%} %>
                                    <%} %>                            
                                    </select>
                        </td>
                        <td width="100" height="60">
                            <select name="paymentMethod" >
                                <option value="method">お支払い方法を選択</option>
                                <option value="CreditCard">クレジットカード</option>
                                <option value="Cash on Delivery">代金引換</option>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>

            <input type="submit" value="確認画面へ進む">
        </form>
        <div align="right">
           <a href="Main">前の画面に戻る</a></div>
        
    </div>

</body>
</html>
