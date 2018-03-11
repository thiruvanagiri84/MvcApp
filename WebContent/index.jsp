<form action="organization" method="post">
<table>
<tr>
<td><font face="verdana" size="2px">User Name:</font></td>
<td><input type="text" name="userName"></td>
</tr>
<tr>
<td><font face="verdana" size="2px">Password:</font></td>
<td><input type="password" name="userPassword"></td>
</tr>

<tr>
<td><font face="verdana" size="2px">Date:(In DD-MM-YYYY)</font></td>
<td><input type="text" name="expDate"></td>
</tr>
<tr>
<td><font face="verdana" size="2px">Category:</font></td>
<td><input type="password" name="expCategory"></td>
</tr>
<tr>
<td><font face="verdana" size="2px">Sub Category:</font></td>
<td><input type="text" name="expSubCategory"></td>
</tr>
<tr>
<td><font face="verdana" size="2px">Amount:</font></td>
<td><input type="password" name="expAmount"></td>
</tr>

</table>
<input type="submit" value="Login">
<input type="hidden" name="action" value="MainPage"/>
<%
String MainPageURL =  "http://localhost:8080/MvcApplication/organizatoin?action=MainPage";
%>

<a href="<%=MainPageURL%>">MainPage</a>
</form>
