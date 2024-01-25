<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,model.*" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>

<!DOCTYPE html>
<html lang="ja">
<html>
<head>
<meta charset="UTF-8">
<title>購入履歴</title>
</head>
<body>
    <h2>購入履歴</h2>
    
    <a href="Main">戻る</a>
    <div>
        <table border="1">
            <thead>
                <tr>
                    <th width="120" height="40">オーダーID</th>
                    <th width="120" height="40">購入日</th>
                    <th width="100" height="40">合計金額($)</th>
                    <th  width="180" height="40">お届け先</th>
                    <th width="100" height="40">お支払い方法</th>
                </tr>
            </thead>
            <tbody>
                <% if(orders != null && orders.size() > 0){ %>
                    <% for(Order order : orders) {%>
                    <tr>
                        <td width="120" height="80"><%=order.getOrderID() %></td>
                        <td width="120" height="80"><%=order.getOrderDate() %></td>
                        <td width="100" height="40"><%=order.getTotalAmount() %></td>
                        <td width="180" height="40"><%=order.getAddress() %></td>
                        <td width="100" height="40"><%=order.getpayment_method() %></td>
                     <%} %>
                 <%} %>
        </tbody>
    </table>
    </div>
</body>
</html>