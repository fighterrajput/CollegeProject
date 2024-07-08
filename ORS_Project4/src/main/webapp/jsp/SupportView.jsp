<%@page import="com.rays.pro4.controller.SupportCtl"%>
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
<title>Salary Page</title>

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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.SupportBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.SUPPORT_CTL%>" method="post">
			<%
				HashMap map = (HashMap) request.getAttribute("map");
				HashMap map1 = (HashMap) request.getAttribute("map1");
			%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Support </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Support</font></th>
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


				<th align="left">Technician <span style="color: red">*</span> :
				</th>
				<td>
					<%
						String hlist1 = HTMLUtility.getList("technician", String.valueOf(bean.getTechnician()), map1);
					%> <%=hlist1%>
				</td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("technician", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
				<tr>
					<th align="left">Type<span style="color: red">*</span> :
					</th>
					<td>
						<%
							String hlist = HTMLUtility.getList("type", String.valueOf(bean.getType()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("type", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Date<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="date" placeholder="Enter Date"
						size="25" readonly="readonly" id="udatee"
						value="<%=DataUtility.getDateString(bean.getDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("date", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Ticket No: <span style="color: red">*</span>

					</th>
					<td><input type="text" name="ticketno" maxlength="21"
						placeholder="Enter amount" oninput="validateNumericInput(this)"
						size="25"
						value="<%=(DataUtility.getStringData(bean.getTicketNo()))%>"></td>

					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("ticketno", request)%></font></td>

				</tr>


				<%
					if (bean.getId() > 0) {
				%>

				&nbsp;
				<td align="right" colspan="2">&nbsp; &emsp; <input
					type="submit" name="operation" value="<%=SupportCtl.OP_UPDATE%>">
					&nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=SupportCtl.OP_CANCEL%>"></td>

				<%
					} else {
				%>

				&nbsp;&nbsp;
				<td align="right" scolspan="2">&nbsp; &emsp; <input
					type="submit" name="operation" value="<%=SupportCtl.OP_SAVE%>">
					&nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=SupportCtl.OP_RESET%>"></td>

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