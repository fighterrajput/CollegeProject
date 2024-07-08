
<%@page import="com.rays.pro4.controller.BankCtl"%>
<%@page import="com.rays.pro4.controller.UPICtl"%>
<%@page import="com.rays.pro4.controller.ProductCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<script src="<%=ORSView.APP_CONTEXT%>/js/ValidateToInput.js"></script>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Bank Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1900:2024',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>

<script>
	function validateNumericInput(inputField) {
		// Get the value entered by the user
		var inputValue = inputField.value;

		// Regular expression to check if the input is numeric
		var numericPattern = /^\d*$/;

		// Test the input value against the numeric pattern
		if (!numericPattern.test(inputValue)) {
			// If input is not numeric, clear the field
			inputField.value = inputValue.replace(/[^\d]/g, ''); // Remove non-numeric characters
		}
	}
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.BankBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.BANK_CTL%>" method="post">

			<%-- <%
				List list = (List) request.getAttribute("jList");
			%> --%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Bank </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Bank</font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>

				<%-- <tr>
					<th align="left">Name<span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Riya", "Riya");
							map.put("Ani", "Ani");
							map.put("Dharam", "Dharam");

							
							String hlist = HTMLUtility.getList("name", String.valueOf(bean.getName()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr> --%>


				<th align="left">Bank Name: <span style="color: red">*</span>

				</th>
				<td><input type="text" name="BankName" maxlength="21"
					placeholder="Enter Bank Name"
					onkeypress="return ValidateInput(event)" size="25"
					value="<%=DataUtility.getStringData(bean.getBankName())%>"></td>
				<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("BankName", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>



				<th align="left">Account Number: <span style="color: red">*</span>

				</th>
				<td><input type="text" name="AccountNumber"
					placeholder="Enter Account Number" maxlength="10"
					oninput="validateNumericInput(this)" size="25"
					value="<%=(DataUtility.getStringData(bean.getAccountNumber()).equals("0") ? ""
					: DataUtility.getStringData(bean.getAccountNumber()))%>"></td>
				<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("AccountNumber", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>




				<tr>
					<th align="left">Date<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="OpenDate"
						placeholder="Enter Date" size="25" readonly="readonly" id="udatee"
						value="<%=DataUtility.getDateString(bean.getOpenDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("OpenDate", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Account Type: <span style="color: red">*</span>

					</th>
					<td><input type="text" name="AccountType"
						placeholder="Enter Account Type" size="25"
						value="<%=DataUtility.getStringData(bean.getAccountType())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("AccountType", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<%
					if (bean.getId() > 0) {
				%>

				&nbsp;
				<td align="right" colspan="2">&nbsp; &emsp; <input
					type="submit" name="operation" value="<%=BankCtl.OP_UPDATE%>">
					&nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=BankCtl.OP_CANCEL%>"></td>

				<%
					} else {
				%>

				&nbsp;&nbsp;
				<td align="right" scolspan="2">&nbsp; &emsp; <input
					type="submit" name="operation" value="<%=BankCtl.OP_SAVE%>">
					&nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=BankCtl.OP_RESET%>"></td>

				<%
					}
				%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>