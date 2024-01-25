<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, model.*" %>

<%
    List<Cart> cartItems = (List<Cart>) request.getAttribute("cartItems");
    String userID = (String) session.getAttribute("UserID");
    String deliveryLocation = (String) request.getAttribute("deliveryLocation");
    String paymentMethod = (String) request.getAttribute("paymentMethod");
%>    

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>確認画面</title>
    <style>
        table {
            float: left;
            margin-right: 20px;
        }

        #detail {
            float: left;
        }
    </style>
</head>
<body>
    <div class="confirmation-container" align="left">
        <h1>確認画面</h1>
        
        <table border="1">
            <thead>
                <tr>
                    <th width="250" height="40">商品名</th>
                    <th width="100" height="40">価格($)</th>
                    <th  width="100" height="40">数量(個)</th>
                </tr>
            </thead>
            <tbody>
               <% double allcost = 0; %>
               <% if (cartItems != null && cartItems.size() > 0) { %>
    
                   <% for (Cart cartitem : cartItems) { %>
                       <tr>
                           <td width="250" height="80"><%= cartitem.getItemName(cartitem.getItemID()) %></td>
                           <td width="100" height="80" align="center"><%= String.format("%.2f", cartitem.getPrice()) %></td>
                           <td width="100" height="80" align="center"><%= cartitem.getQuantity() %></td>
                       </tr>
                               <%
                                    double cost = cartitem.getPrice() * cartitem.getQuantity();
                                    allcost += cost;
                                %>   
                   <% } %>
               <% } %>
   
            </tbody>
        </table>
        </div>
        
        <div id="detail"  align="center">
        <table border="1">
            <thead>
                <tr>
                    <th width="250" height="40">お届け先住所</th>
                    <th width="100" height="40">お支払い方法</th>
                </tr>
            </thead>
               <tbody>
                <tr>
                    <td width="250" height="60"><%= deliveryLocation %></td>
                    <td width="100" height="60"><%= paymentMethod %></td>
                 </tr>
            </tbody>
                    </table>
            
            <table border="1">
               <thread>
                <tr>
                  <th width="100" height="40">合計($)</th>
                  </tr>
                </thread>
                <tbody>
                 <tr>
                    <td width="100" height="60" align="center"><%= String.format("%.2f", allcost) %></td>
                 </tr>
                </tbody>
            </table>
        </div>
        
        
        
        <form action="PurchaseCompleteServlet" method="post" >
    <input type="hidden" id="deliveryLocation" name="deliveryLocation" value="<%= deliveryLocation %>" >
    <input type="hidden" id="paymentMethod" name="paymentMethod" value="<%= paymentMethod %>" >
    <input type="submit" value="購入確定" >
</form>
    
    <a href="PurchaseServlet">前の画面に戻る</a>
    
</body>
</html>
