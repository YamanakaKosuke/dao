<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,model.*" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
    List<Cart> cartItems = (List<Cart>) request.getAttribute("cartItems");
    String userID = (String) session.getAttribute("UserID");
    List<Category> categories = (List<Category>)request.getAttribute("categories");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>購入画面</title>
    
</head>
<body>
    <h2> <%= userID  %>さん、こんにちは</h2>
    <div id="menu">
    <h1>商品を選択</h1>
    <form id="category_id" action="SetCategoryServlet" method="post">
        <select name="category_id" onchange="submitCategory()">
            <option value=0>すべてのカテゴリー</option>
            <% if (categories != null && categories.size() > 0) { %>
            <% for(Category category : categories) { %>
                <option value="<%= category.getcategory_id()%>"><%= category.getname()%></option>
            <%} %>
           <%} %>
        </select>
    </form>
    
    <form id="sortmethod" action="SortServlet" method="post">
        <select name="sortmethod" onchange="submitMethod()">
            <option value="null">並び替えを選択</option>
            <option value="DESC">価格が高い順</option>
            <option value="ASC">価格が低い順</option>
        </select>
    </form>


<script>
    function submitCategory() {
        document.getElementById("category_id").submit();
    }

    function submitMethod() {
        document.getElementById("sortmethod").submit();
    }
</script>
       

    <% if (items != null && items.size() > 0) { %>
        <table border="1">
            <thead>
                <tr valign="top">
                    <th width="40" height="40">商品ID</th>
                    <th width="250" height="40">商品名</th>
                    <th width="100" height="40">価格($)</th>
                    <th width="40" height="40">数量(個)</th>
                </tr>
            </thead>
            <tbody>
                <% for (Item item : items) { %>
                    <tr>
                        <td width="40" height="40"><%= item.getId() %></td>
                        <td width="250" height="40"><%= item.getName() %></td>
                        <td width="100" height="40"><%= String.format("%.2f",item.getPrice()) %></td>
                        <td width="40" height="40">
                            <form action="AddToCartServlet" method="post" style="display: flex; align-items: center;">
    <input type="hidden" id="itemId" name="itemId" value="<%= item.getId() %>">
    <input type="number" id="quantity" name="quantity" step="1" value="0" max="<%= item.getStock() %>" style="margin-right: 10px;">
    <input type="submit" value="追加">
</form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        </div>
    <% } %>
    
    
    <div id="cart" style="position: absolute; top:10px; right:10px">
        <h2>カート</h2>
        <div >
         
    <form action="PurchaseServlet" method="post" style="display: inline-block; margin-right: 10px;">
        <input type="submit" value="レジへ進む">
    </form>
    
    <a href="AddAddressServlet">お届け先の追加</a> 
    　
    <a href="PurchaseHistoryServlet">購入履歴</a>
</div>
<br>
    
        <table border="1">
            <thead>
                <tr valign="top">
                    <th width="40" height="40">商品ID</th>
                    <th width="250" height="20">商品名</th>
                    <th width="100" height="20">価格($)</th>
                    <th width="100" height="20">数量(個)</th>
                    <th width="40" height="20">操作</th>
                </tr>
            </thead>
            <tbody>
               <% if (cartItems != null && cartItems.size() > 0) { %>
                   <% for (Cart cartitem : cartItems) { %>
                           <tr>
                               <td width="40" height="80"><%= cartitem.getItemID() %></td>
                               <td width="250" height="80"><%= cartitem.getItemName(cartitem.getItemID()) %></td>
                               <td width="100" height="80"><%= String.format("%.2f", cartitem.getPrice()) %></td>
                               <td width="100" height="80"><%= cartitem.getQuantity() %></td>
                               <td width="40" height="80">
                                   <form action="DeleteCartServlet" method="post">
                                       <input type="hidden"  id="itemId" name="itemId" value="<%= cartitem.getItemID() %>" >
                                       <input type="submit" value="削除">
                                   </form>
                               </td>
                           </tr>
                           <br>
                   <% } %>
               <% } %>
            </tbody>
        </table>
        <br>
        <div align="right">
            <a href="login">ログアウト</a>
        </div>
    </div>
</body>
</html>
